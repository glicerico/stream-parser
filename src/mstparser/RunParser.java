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

		testParser.parseSentence("primera segunda segunda", 3);
		testParser.printParse();
		testParser.exportParse("trash/test.ull");

		testParser.parseFile("data/sample_corpus/sample_corpus1.txt", 3, "trash/test2.ull");
	}
}
