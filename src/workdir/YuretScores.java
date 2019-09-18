/*
* Simple score class
* */
package workdir;

import org.ojalgo.matrix.store.SparseStore;

public class YuretScores {

    SparseStore<Double> yuretScores = SparseStore.PRIMITIVE.make(12, 12);

    public YuretScores(){
        yuretScores.set(0, 1, 1.18);
        yuretScores.set(1, 2, 3.48);
        yuretScores.set(0, 2, 0.55);
        yuretScores.set(2, 3, -1.64);
        yuretScores.set(0, 3, 1.78);
        yuretScores.set(3, 4, 1.43);
        yuretScores.set(2, 4, 3.15);
        yuretScores.set(2, 5, 1.26);
        yuretScores.set(0, 6, 0.53);
        yuretScores.set(5, 7, 4.01);
        yuretScores.set(6, 7, 0.43);
        yuretScores.set(4, 7, 2.09);
        yuretScores.set(7, 8, 2.61);
        yuretScores.set(7, 9, 3.92);
        yuretScores.set(8, 9, 2.58);
        yuretScores.set(9, 10, 1.07);
        yuretScores.set(10, 11, 4.51);
    }

    public SparseStore<Double> ReturnScores() {
        return yuretScores;
    }
}
