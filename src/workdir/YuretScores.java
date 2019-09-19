/*
* Simple score class
* */
package workdir;

import org.ojalgo.matrix.store.SparseStore;

public class YuretScores {

    SparseStore<Double> yuretScores = SparseStore.PRIMITIVE.make(12, 12);

    public YuretScores(){
        yuretScores.set(0, 1, Math.pow(2, 1.18));
        yuretScores.set(1, 2, Math.pow(2, 3.48));
        yuretScores.set(0, 2, Math.pow(2, 0.55));
        yuretScores.set(2, 3, Math.pow(2,-1.64));
        yuretScores.set(0, 3, Math.pow(2,1.78));
        yuretScores.set(3, 4, Math.pow(2,1.43));
        yuretScores.set(2, 4, Math.pow(2,3.15));
        yuretScores.set(2, 5, Math.pow(2,1.26));
        yuretScores.set(0, 6, Math.pow(2,0.53));
        yuretScores.set(5, 7, Math.pow(2,4.01));
        yuretScores.set(6, 7, Math.pow(2,0.43));
        yuretScores.set(4, 7, Math.pow(2,2.09));
        yuretScores.set(7, 8, Math.pow(2,2.61));
        yuretScores.set(7, 9, Math.pow(2,3.92));
        yuretScores.set(8, 9, Math.pow(2,2.58));
        yuretScores.set(9, 10, Math.pow(2,1.07));
        yuretScores.set(10, 11, Math.pow(2,4.51));
    }

    public SparseStore<Double> ReturnScores() {
        return yuretScores;
    }
}
