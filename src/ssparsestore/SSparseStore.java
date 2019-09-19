/**
 * Serializable Sparse Store
 *
 * Class that extends ojalgo's SparseStore to implement for serialization
 */
package ssparsestore;

import org.ojalgo.matrix.store.SparseStore;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SSparseStore implements Serializable {
    //private SparseStore<Double> matrix;
    double matrix;

    //SSparseStore(SparseStore<Double> matrix) {
    public SSparseStore(double matrix) {
        this.matrix = matrix;
    }

    public void writeObject(ObjectOutputStream outputStream) throws IOException {
       outputStream.defaultWriteObject();
    }


}
