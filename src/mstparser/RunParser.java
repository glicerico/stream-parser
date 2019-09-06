/**
 * JavaDoc comments look like this. Used to describe the Class or various
 * attributes of a Class.
 * Main attributes:
 * 
 * Entry point for the MST-parser. Calls the class and executes the parser
 *
 * @author		Andres Suarez, suarezandres@hotmail.com
 * @since		Sept 2019
*/
package mstparser;

import mstparser.*;

public class RunParser {

	public static void main(String[] args) {
		System.out.println("Stream parser!");

		ScorerFn scorer = new ScorerFn("test");
		MSTparser testParser = new MSTparser(scorer);

		testParser.parseWord("primera", 4);
		testParser.printParse();
		testParser.parseWord("segunda", 4);
		testParser.printParse();
		testParser.parseWord("tercera", 4);
		testParser.printParse();
		testParser.parseWord("cuarta", 4);
		testParser.printParse();
		testParser.parseWord("quinta", 4);
		testParser.printParse();
	}
}
