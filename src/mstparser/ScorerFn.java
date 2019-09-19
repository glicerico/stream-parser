/*
 * ScorerFn class

 * Main attributes:
 *
 * Scoring function that searches the provided SparseArray
 * for the score of the two given words.
 * It determines the vocabulary indexes of the words. This allows
 * to accommodate word-pair global scores, as well as
 * sentence-specific ones.
 * THIS SCORER WORKS FOR CONTEXT-INDEPENDENT SCORES
 *
 * @author		Andres Suarez, suarezandres@hotmail.com
 * @since		Sept 2019
*/
package mstparser;

import java.util.HashMap;
import org.ojalgo.matrix.store.SparseStore;

public class ScorerFn {
	private SparseStore<Double> scores; // structure with word-pair scores
	private HashMap<String,Integer> vocabulary;

	// Class constructor
	public ScorerFn(HashMap<String,Integer> vocab, SparseStore<Double> scores) {
		this.vocabulary = vocab;
		this.scores = scores;
	}

	private double log2(double x) {
		// 1.4426950408889634 is 1/0.6931471805599453 is 1/log(2)
		return Math.log(x) * 1.4426950408889634;
	}

	double getScore(String w1, String w2) {
		double curr_score = Math.pow(2, -1); // negative score for words not in vocabulary;
		if (vocabulary.containsKey(w1) && vocabulary.containsKey(w2)) {
			int w1_index = vocabulary.get(w1);
			int w2_index = vocabulary.get(w2);
			curr_score = scores.get(w1_index, w2_index);
		}

		return log2(curr_score); // THIS ASSUMES THAT scores obtained are 2^PMI
	}
}
