/**
 * 
 * Entry point for the MI calculator. Calls the class to process a text file
 * and calculate pair-wise MI from it.
 *
 * @author		Andres Suarez, suarezandres@hotmail.com
 * @since		Sept 2019
*/
package micalculator;

import mstparser.*;
import java.util.HashMap;

public class RunCalculator {

	public static void main(String[] args) {
		System.out.println("MI Calculator!");

		GetVocabulary vocabTableInstance = new GetVocabulary("test_files/sample_vocab.txt");
		HashMap vocabTable = vocabTableInstance.getVocabTable();

		MICalculator calculatorInstance = new MICalculator(vocabTable);
		calculatorInstance.ObserveFile("test_files/sample_corpus.txt", 3);
		calculatorInstance.getObsMatrix();
	}
}
