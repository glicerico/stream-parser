/**
 * GetVocabulary class

 * Main attributes:
 * 
 * Generator of a vocabulary HashMap from a list of vocabulary.
 * The vocabulary is expected to match the indexing used in the
 * scores HashMap.
 * 
 * @author		Andres Suarez, suarezandres@hotmail.com
 * @since		Sept 2019
*/
package mstparser;

import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GetVocabulary {

	HashMap<String, Integer> vocabularyTable = new HashMap<String, Integer>();

	// The constructor opens the file and builds the hashmap from it
	public GetVocabulary(String vocab_file) {
		try {
			Scanner scanner = new Scanner(new File(vocab_file));
			int index = 0;
			while (scanner.hasNextLine()) {
				String[] split_line = scanner.nextLine().split("\\s+");
				this.vocabularyTable.put(split_line[0], index++);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
      
    public HashMap getVocabTable() {
    	return vocabularyTable;
    }
}