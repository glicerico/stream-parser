/*
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

		GetVocabulary vocabTableInstance = new GetVocabulary("data/sample_vocab.dict");
		HashMap vocabTable = vocabTableInstance.getVocabTable();

		MICalculator calculatorInstance = new MICalculator(vocabTable);
		calculatorInstance.ObserveFile("data/sample_corpus/sample_corpus1.txt", 3);
		calculatorInstance.ObserveFile("data/sample_corpus/sample_corpus2.txt", 3);

		calculatorInstance.PrintObsMatrix();
		calculatorInstance.CalculateExpPMI();
	}
}
