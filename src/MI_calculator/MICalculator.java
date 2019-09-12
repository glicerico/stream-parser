/**
 * 
 * Mutual Information Class.
 *
 * Calculates, stores, imports a HashMap of PMI values from a corpus.
 * PMI as defined in: https://en.wikipedia.org/wiki/Pointwise_mutual_information
 * 		
 * @author		Andres Suarez, suarezandres@hotmail.com
 * @since		Sept 2019
*/
package micalculator;

import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MICalculator {

	//private SparseMatrix obsMatrix = new ... // TODO
	float[][] obsMatrix = new float[5][5];
	private HashMap<String,Integer> vocabulary;
	private HashMap<String,Float> miTable;

	public MICalculator(HashMap vocabulary) {
		this.vocabulary = vocabulary;
	}

	public void ObserveFile(String text_file, int window) {
		try {
			Scanner scanner = new Scanner(new File(text_file));
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

	// Counts ordered word-pair occurences (observations) in a sentence. 
	// Only counts pairs occurring within a given window.
	private void ObserveSentence(String sentence, int window) {
		String[] split_sent = sentence.split("\\s+");

		// Observe pairs of words ocurring within window in sentence
		for (int i = 0; i < split_sent.length - 1; i++) {
			if (vocabulary.containsKey(split_sent[i])) {
				wl_id = vocabulary.get(split_sent[i]);
				win_edge = min(i + window, split_sent.length - 1);
				for (int j = i + 1; j < win_edge; j++) {
					if (vocabulary.containsKey(split_sent[j])) {
						wr_id = vocabulary.get(split_sent[j]);
						obsMatrix(wl_id, wr_id) += 1; // TODO: other counting weights?
					}
				}
			}
		}
	}

	public float[][] getObsMatrix() {
		return obsMatrix;
	}

}
