/**
 * DummyScores class

 * Main attributes:
 * 
 * Dummy generator of a score function, to test rest of parser
 * 
 * @author		Andres Suarez, suarezandres@hotmail.com
 * @since		Sept 2019
*/
package mstparser;

import java.util.HashMap;

public class DummyScores {

	HashMap<String, Float> dummyScoreTable = new HashMap<String, Float>();

	public DummyScores() {
		this.dummyScoreTable.put("1_2", 1.0);
		this.dummyScoreTable.put("1_3", 1.1);
		this.dummyScoreTable.put("1_4", 1.2);
		this.dummyScoreTable.put("2_3", 0.5);
		this.dummyScoreTable.put("2_4", 0.6);
		this.dummyScoreTable.put("3_4", 0.8);
	}
      
    public HashMap returnScoreTable() {
    	return dummyScoreTable;
    }
}