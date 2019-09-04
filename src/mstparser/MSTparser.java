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

	public float scores; // structure with word-pair scores
	public String vocabulary;
	public int window; // parsing window
	private ArrayList stack = new ArrayList();
	private ArrayList minlink = new ArrayList(); // stores min valued Links
	private ArrayList links = new ArrayList();
	private ArrayList proc_sentence = new ArrayList();

	// Define Link structure to use in parser
	static class Link {
		public int li, ri; // left/right indexes of link
		public float score;

		// Constructor for a dummy link
		public Link() {
			this(0, 0, 0);
		}

		// Class constructor
		public Link(int left_index, int right_index, float score) {
			this.li = left_index;
			this.ri = right_index;
			this.score = score;
		}

		@Override
		public String toString() {
			return "(" + this.li + "-" + this.ri + ", " + this.score + ")";
		}
	}

	// Class constructor
	public MSTparser(String scores, String vocabulary, int window) {
		this.scores = Float.parseFloat(scores);
		this.vocabulary = vocabulary;
		this.window = window;
	}

	// Adds one word to the current parsed sentence if other words have been
	// processed.
	public void parseWord(String word, int window) {
		proc_sentence.add(word);
		int word_num = proc_sentence.size();

		// if this is the first word parsed, just add it to proc_sentece
		if (word_num < 2) { // initialize sentence elsewhere to remove this conditional??
			return;
		}

		minlink.add(new Link()); // initialize position with dummy link

		// try to connect new word with every word to its left
		for (int i = word_num - 2; i >= 0 ; i--) {
			Link last = popRightLinks(i);
			minlink.set(i, getMinLink(last, minlink(last.ri())));
			curr_score = getScore(i, word) // order matters for ordered pairs TODO: implement
			if (
			  (curr_score > 0) 
			  and (curr_score > minlink.get(i).score)
			  and (curr_score > getStackMaxScore())) {
				stack.forEach(stack_link->unLink(stack_link)) // unlink weakest crossing links
				stack = new ArrayList();
				unLink(minlink.get(i)); // unlink weakest link in loop
				Link new_link = new Link(i, word_num, curr_score);
				minlink.set(i, new_link); // assign weakest link from i to word
				links.add(new_link);
			}
			pushLeftLinks(i);
		}

	}

	// Return the highest score from the stack, or 0 if it's empty
	private float getStackMaxScore() {
		curr_max = 0;
		stack.forEach(curr_link->{
			if (curr_link.score > curr_max) {
				curr_max = curr_link.score;
			}
		})

		return curr_max;
	}

	// Removes dead_link from current links
	private void unLink(Link dead_link) {
		int dead_id = links.indexOf(dead_link);
		links.remove(dead_id);
	}

	// Searches the stack from right to left for the right links
	// of the given index. If there's a match, pops the link.
	// Returns the last link popped (the leftmost).
	private Link popRightLinks(int index) {
		Link result;
		for (int j = stack.size() - 1; j >= 0; j--) {
			curr_link = stack.get(j);
			if (curr_link.li == index) {
				result = curr_link;
				stack.remove(j);
			}
		}

		return result;
	}

	// Pushes to the stack all left links of the given index.
	// Yuret's algorithm is not clear on the order to push them, so
	// it is done here from left to right in the current link list.
	private void pushLeftLinks(int index) {
		links.forEach(link->{
			if(index == link.ri){
				stack.add(link);
			}
		})
	}

	// Given two links, returns the one with minumum score
	private Link getMinLink(Link l1, Link l2) {
		return (l1.score >= l2.score) ? l2 : l1;
	}

	// Prints the current sentence and its parse
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
