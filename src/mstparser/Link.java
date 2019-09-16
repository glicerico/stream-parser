/*
 * MSTparser class

 * Main attributes:
 *
 * Define Link structure to use in parser
 *
 * @author		Andres Suarez, suarezandres@hotmail.com
 * @since		Sept 2019
 */
package mstparser;

public class Link {
    int li, ri; // left/right indexes of link
    double score;

    // Constructor for a dummy link
    Link() {
        this(0, 0, 0);
    }

    // Class constructor
    Link(int left_index, int right_index, double score) {
        this.li = left_index;
        this.ri = right_index;
        this.score = score;
    }

    @Override
    public String toString() {
        return "(" + this.li + "-" + this.ri + ", " + this.score + ")";
    }
}

