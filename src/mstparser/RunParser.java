/*
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
		System.out.println("Stream parser!");

		GetVocabulary vocabTableInstance = new GetVocabulary("data/sample_vocab.dict");
		HashMap<String,Integer> vocabTable = vocabTableInstance.getVocabTable();

		MICalculator calculatorInstance = new MICalculator(vocabTable);
		calculatorInstance.ObserveDirectory(new File("data/sample_corpus/"), 3);
		SparseStore<Double> scoreMatrix = calculatorInstance.CalculateExpPMI();

		ScorerFn scorer = new ScorerFn(vocabTable, scoreMatrix);
		MSTparser testParser = new MSTparser(scorer);

		testParser.parseFile("data/sample_corpus/sample_corpus1.txt", 3, "trash");

		testParser.parseCorpus("data/sample_corpus", 3, "trash");
	}
}
