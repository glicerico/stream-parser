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
		int window = 6;

		System.out.println("Stream parser!");

		System.out.println("Reading vocabulary...");
		GetVocabulary vocabTableInstance = new GetVocabulary("data/sample_vocab.dict");
		//GetVocabulary vocabTableInstance = new GetVocabulary("data/CDS/CDS.dict");
		//GetVocabulary vocabTableInstance = new GetVocabulary("data/GC/GC.dict");
		HashMap<String,Integer> vocabTable = vocabTableInstance.getVocabTable();

		System.out.println("Calculating PMI values...");
		MICalculator calculatorInstance = new MICalculator(vocabTable);
		calculatorInstance.ObserveDirectory(new File("data/sample_corpus"), window);
		//calculatorInstance.ObserveDirectory(new File("data/CDS/corpus"), window);
		//calculatorInstance.ObserveDirectory(new File("data/GC/MSL25-2019JUL01"), window);
		SparseStore<Double> scoreMatrix = calculatorInstance.CalculateExpPMI();

		System.out.println("Exporting PMI values...");
		calculatorInstance.ExportPMIMatrix("data/testMatrix.fmi");
		scoreMatrix = calculatorInstance.ImportPMIMatrix("data/testMatrix.fmi");

		System.out.println("Creating score function...");
		ScorerFn scorer = new ScorerFn(vocabTable, scoreMatrix);
		MSTparser testParser = new MSTparser(scorer);

		System.out.println("Parsing corpus...");
		testParser.parseCorpus("data/sample_corpus", window, "data/sample_parses");
		//testParser.parseCorpus("data/CDS/corpus", window, "data/CDS/parses");
		//testParser.parseCorpus("data/GC/MSL25-2019JUL01", window, "data/GC/parses");

		System.out.println("DONE!");
	}
}
