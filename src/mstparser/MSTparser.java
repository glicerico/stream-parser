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

	public int window; // parsing window
	private ArrayList<Link> stack;
	private ArrayList<Link> minlink; // stores min valued Links
	private ArrayList<Link> links;
	private ArrayList<String> proc_sentence;
	private ScorerFn scorer;

	// Resets structures, ready to parse a new sentence
	public void PrepareNewSentence() {
		stack = new ArrayList<Link>();
		minlink = new ArrayList<Link>();
		links = new ArrayList<Link>();
		proc_sentence = new ArrayList<String>();
	}

	// Class constructor
	public MSTparser(ScorerFn scorer) {
		PrepareNewSentence();
		this.scorer = scorer;
	}

	// TODO: parseSentence method;
	// TODO: export parse method;
	// TODO: parseFile method?;
	// TODO: parseCorpus method?;

    // Processes sentence, sending word by word to parseWord
	public void parseSentence(String sentence, int window) {
		if (!sentence.trim().equals("")) { // Skip empty lines
			for(String currWord : sentence.split("\\s+")){
				parseWord(currWord, window);
			}
		}
	}

	// Adds one word to the current parsed sentence if other words have been
	// processed. If this is the first word in parse, it just gets added to
	// the processed sentence.
	public void parseWord(String word, int window) { //TODO: include window
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
			if (last != null) {
				minlink.set(i, getMinLink(last, minlink.get(last.ri)));
			}
			double curr_score = scorer.getScore(proc_sentence.get(i), proc_sentence.get(word_num - 1));
			if (
			  (curr_score > 0) 
			  && (curr_score > getLinkScore(minlink.get(i)))
			  && (curr_score > getStackMaxScore())) {
				stack.forEach(stack_link->unLink(stack_link)); // unlink weakest crossing links
				stack = new ArrayList<Link>();
				unLink(minlink.get(i)); // unlink weakest link in loop
				Link new_link = new Link(i, word_num - 1, curr_score);
				minlink.set(i, new_link); // assign weakest link from i to word
				links.add(new_link);
			}
			pushLeftLinks(i);
		}

	}

	private double getLinkScore(Link this_link) {
		return this_link.score;
	}

	// Return the highest score from the stack, or 0 if it's empty
	private double getStackMaxScore() {
		double curr_max = 0;
		for (Link curr_link : stack) {
			if (curr_link.score > curr_max) {
				curr_max = curr_link.score;
			}
		}

		return curr_max;
	}

	// Removes dead_link from current links
	private void unLink(Link dead_link) {
		int dead_id = links.indexOf(dead_link);
		if (dead_id >= 0) {
			links.remove(dead_id);
		}
	}

	// Searches the stack from right to left for the right links
	// of the given index. If there's a match, pops the link.
	// Returns the last link popped (the leftmost).
	private Link popRightLinks(int index) {
		Link result = null;
		// Traverse the stack in reverse, to function as stack
		if (stack.size() > 0) {
			for (int j = stack.size() - 1; j >= 0; j--) {
				Link curr_link = stack.get(j);
				if (curr_link.li == index) {
					result = curr_link;
					stack.remove(j);
				}
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
		});
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
}
