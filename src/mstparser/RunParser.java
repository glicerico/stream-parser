/**
 * 
 * Entry point for the MST-parser. Calls the class and executes the parser
 *
 * @author		Andres Suarez, suarezandres@hotmail.com
 * @since		Sept 2019
*/
package mstparser;

import micalculator.MICalculator;
import org.ojalgo.matrix.store.SparseStore;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class RunParser {

	public static void main(String[] args) throws IOException {
		int windowObserve = 6;
		int windowParse = 30;
		boolean calculateScores = false; // false implies scores are imported
		boolean exportScores = false; // only relevant if calculateScores is true
		String vocabFile = "data/GC/GC.dict";
		String scoresFilePath = "data/GC/scoreMatrices/MSL25-2019JUL01.fmi";
		String observeCorpusPath = "data/GC/corpora/MSL25-2019JUL01/";
		String corpusPath = "data/GC/corpora/SS";
		String parsesPath = "data/GC/parses/SS";

		SparseStore<Double> scoreMatrix = null;

		System.out.println("Stream parser!");
		System.out.println("Reading vocabulary...");
		GetVocabulary vocabTableInstance = new GetVocabulary(vocabFile);
		HashMap<String,Integer> vocabTable = vocabTableInstance.getVocabTable();

		MICalculator calculatorInstance = new MICalculator(vocabTable);
		if (calculateScores) {
			System.out.println("Calculating PMI values...");
			calculatorInstance.ObserveDirectory(new File(observeCorpusPath), windowObserve);
			scoreMatrix = calculatorInstance.CalculateExpPMI();
			if (exportScores) {
				System.out.println("Exporting PMI values...");
				calculatorInstance.ExportPMIMatrix(scoresFilePath);
		} else {
			System.out.println("Importing PMI values...");
			scoreMatrix = calculatorInstance.ImportPMIMatrix(scoresFilePath);
			}
		}

		System.out.println("Creating score function...");
		ScorerFn scorer = new ScorerFn(vocabTable, scoreMatrix);
		MSTparser testParser = new MSTparser(scorer);

		System.out.println("Parsing corpus...");
		testParser.parseCorpus(corpusPath, windowParse, parsesPath);

		System.out.println("DONE!");
	}
}
