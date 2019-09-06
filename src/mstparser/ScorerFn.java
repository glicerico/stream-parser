/**
 * ScorerFn class

 * Main attributes:
 * 
 * Scoring function that searches the provided HashMap
 * for the score of the two given words.
 * It determines the hash table index of the words. This allows
 * to accomodate word-pair global scores, as well as 
 * sentence-specific ones.
 * 
 * @author		Andres Suarez, suarezandres@hotmail.com
 * @since		Sept 2019
*/
package mstparser;

import java.util.HashMap;

public class ScorerFn {
	private HashMap<String,Float> scores; // structure with word-pair scores
	private String vocabulary;

	// Class constructor
	public ScorerFn(String vocab) {
		this.vocabulary = vocab;
	}

	public float getScore(String w1, String w2) {
		return 1;
	}
}
