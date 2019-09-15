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
import java.util.HashMap;

public class RunParser {

	public static void main(String[] args) {
		System.out.println("Stream parser!");

		GetVocabulary vocabTableInstance = new GetVocabulary("data/sample_vocab.dict");
		HashMap vocabTable = vocabTableInstance.getVocabTable();

		MICalculator calculatorInstance = new MICalculator(vocabTable);
		calculatorInstance.ObserveDirectory(new File("data/sample_corpus/"), 3);
		SparseStore<Double> scoreMatrix = calculatorInstance.CalculateExpPMI();

		ScorerFn scorer = new ScorerFn(vocabTable, scoreMatrix);
		MSTparser testParser = new MSTparser(scorer);

		testParser.parseWord("primera", 4);
		testParser.printParse();
		testParser.parseWord("segunda", 4);
		testParser.printParse();
		testParser.parseWord("tercera", 4);
		testParser.printParse();
		testParser.parseWord("cuarta", 4);
		testParser.printParse();
		testParser.parseWord("quinta", 4);
		testParser.printParse();
	}
}
