/**
 * DummyVocab class

 * Main attributes:
 * 
 * Dummy generator of a vocabulary HashMap, to test rest of parser
 * 
 * @author		Andres Suarez, suarezandres@hotmail.com
 * @since		Sept 2019
*/
package mstparser;

import java.util.HashMap;

public class DummyVocab {

	HashMap<String, Integer> dummyVocabTable = new HashMap<String, Integer>();

	public DummyVocab() {
		this.dummyVocabTable.put("primera", 1);
		this.dummyVocabTable.put("segunda", 2);
		this.dummyVocabTable.put("tercera", 3);
		this.dummyVocabTable.put("cuarta", 4);
	}
      
    public HashMap getScoreTable() {
    	return dummyVocabTable;
    }
}