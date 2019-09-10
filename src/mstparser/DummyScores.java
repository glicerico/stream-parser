/**
 * DummyScores class

 * Main attributes:
 * 
 * Dummy generator of a scores HashMap, to test rest of parser
 * 
 * @author		Andres Suarez, suarezandres@hotmail.com
 * @since		Sept 2019
*/
package mstparser;

import java.util.HashMap;

public class DummyScores {

	HashMap<String, Float> dummyScoreTable = new HashMap<String, Float>();

	public DummyScores() {
		this.dummyScoreTable.put("0_1", 1.0f);
		this.dummyScoreTable.put("0_2", 1.1f);
		this.dummyScoreTable.put("0_3", 1.2f);
		this.dummyScoreTable.put("1_2", 0.5f);
		this.dummyScoreTable.put("1_3", 0.6f);
		this.dummyScoreTable.put("2_3", 0.8f);
	}
      
    public HashMap returnScoreTable() {
    	return dummyScoreTable;
    }
}