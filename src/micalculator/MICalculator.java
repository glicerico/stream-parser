/*
 * 
 * Mutual Information Class.
 *
 * Calculates, stores, imports a HashMap of e^PMI values from a corpus.
 * PMI as defined in: https://en.wikipedia.org/wiki/Pointwise_mutual_information
 * PMI = log_2[N(x,y) * N(*,*) / N(x,*) / N(*,y)]
 * @author		Andres Suarez, suarezandres@hotmail.com
 * @since		Sept 2019
*/
package micalculator;

import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.ojalgo.array.Array1D;
import org.ojalgo.matrix.store.SparseStore;
import org.ojalgo.function.aggregator.Aggregator;
import org.ojalgo.array.SparseArray;
import org.ojalgo.array.Primitive64Array;

public class MICalculator {

    private SparseStore<Double> obsMatrix;
	private HashMap<String,Integer> vocabulary;
	private int dim; // vocabulary size
    private int fileCount;

	public MICalculator(HashMap<String,Integer> vocabulary) {
		this.vocabulary = vocabulary;
		dim = vocabulary.size();
		obsMatrix = SparseStore.PRIMITIVE.make(dim, dim);
	}

	public void ObserveDirectory(final File folder, int window) {
		fileCount = 0;
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				ObserveDirectory(fileEntry, window);
			} else {
				ObserveFile(fileEntry, window);
			}
		}
	}

	public void ObserveFile(final File textFile, int window) {
		try {
			System.out.println("Observing file #" + ++fileCount + ": " + textFile.getName());
			Scanner scanner = new Scanner(textFile);
			while (scanner.hasNextLine()) {
				String currLine = scanner.nextLine();
				if (!currLine.trim().equals("")) { // Skip empty lines
					ObserveSentence(currLine, window);
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	// Counts ordered word-pair occurrence (observations) in a sentence.
	// Only counts pairs occurring within a given window.
	private void ObserveSentence(String sentence, int window) {
		String[] splitSent = sentence.split("\\s+");

		// Observe pairs of words occurring within window in sentence
		for (int i = 0; i < splitSent.length - 1; i++) {
			if (vocabulary.containsKey(splitSent[i])) {
				int wl_id = vocabulary.get(splitSent[i]);
				int win_edge = Math.min(i + window, splitSent.length - 1);
				for (int j = i + 1; j <= win_edge; j++) {
					if (vocabulary.containsKey(splitSent[j])) {
						int wr_id = vocabulary.get(splitSent[j]);
						Double currCount = obsMatrix.get(wl_id, wr_id);
						obsMatrix.set(wl_id, wr_id, ++currCount); // TODO: other counting weights?
					}
				}
			}
		}
	}

	// Converts observation counts into 2^PMI, as done by Levy, Goldberg and Dagan (Levy et al. 2015).
	public SparseStore<Double> CalculateExpPMI() {
		//SparseArray<Double> lhCounts = SparseArray.factory(Primitive64Array.FACTORY, dim).make();
		SparseArray<Double> rhCounts = SparseArray.factory(Primitive64Array.FACTORY, dim).make();
		Array1D<Double> lhCounts = Array1D.PRIMITIVE64.makeSparse(dim);
		SparseStore<Double> diagonalLH = SparseStore.PRIMITIVE.make(dim, dim);
		SparseStore<Double> diagonalRH = SparseStore.PRIMITIVE.make(dim, dim);
		SparseStore<Double> pmi = SparseStore.PRIMITIVE.make(dim, dim);

		// Get wild_card counts for words on right/left hand sides (rh/lh, respectively)
		obsMatrix.reduceColumns(Aggregator.SUM, rhCounts); // rh: N(*, y)
		obsMatrix.reduceRows(Aggregator.SUM, lhCounts); // lh: N(x, *)

		// Get total number of counts N(*,*)
		//double sum_total = lhCounts.nonzeros().stream().mapToDouble(nz -> nz.doubleValue()).sum();
		double sumTotal = lhCounts.aggregateAll(Aggregator.SUM);

		// Create diagonal matrices with inverted wildcard counts
		for (int i = 0; i < dim; i++){
			diagonalLH.set(i, i, 1/lhCounts.get(i));
			diagonalRH.set(i, i, 1/rhCounts.get(i));
		}

		diagonalLH.multiply(obsMatrix).supplyTo(pmi); // Multiply by 1/N(x,*)
		pmi.multiply(diagonalRH).supplyTo(pmi); // Multiply by 1/N(*,y)
		pmi.multiply(sumTotal).supplyTo(pmi); // Multiply by N(*,*)

		return pmi;
	}

	void PrintObsMatrix() {
		System.out.println("Observations Matrix:");
		System.out.println(obsMatrix);
	}
}
