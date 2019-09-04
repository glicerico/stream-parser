/**
 * MSTparser class

 * Main attributes:
 * 
 * Defines the MSTparser class and its methods
 *
 * @author		Andres Suarez, suarezandres@hotmail.com
 * @since		Sept 2019
*/
package mstparser;

import java.util.ArrayList;

public class MSTparser {

	public float scores;
	public String vocabulary;
	private ArrayList stack = new ArrayList();
	private ArrayList minlink = new ArrayList();
	private ArrayList links = new ArrayList();
	private ArrayList proc_sentence = new ArrayList();

	static class Link {
		public int li, ri;
		public Link(int left_index, int right_index) {
			this.li = left_index;
			this.ri = right_index;
		}

		@Override
		public String toString() {
			return "Link: " + this.li + "-" + this.ri;
		}
	}

	public MSTparser(String scores, String vocabulary) {
		this.scores = Float.parseFloat(scores);
		this.vocabulary = vocabulary;
	}

	public void parseWord(String word, int window) {
		proc_sentence.add(word);
		int word_num = proc_sentence.size();

		// if this is the first word parsed, just add it to proc_sentece
		if (word_num < 2) { // initialize sentence elsewhere to remove this conditional??
			return;
		}

		links.add(new Link(word_num, word_num - 1));
		minlink.add(new Link(0, 0));

	}

	public void printParse() {
		System.out.println("Current parse:");
		System.out.println(proc_sentence.toString());
		System.out.println(links.toString());
	}

	@Override
	public String toString() {
		return "Score: " + scores + "\nVocabulary: " + vocabulary;
	}
}
