/**
 * Serializable Sparse Store
 *
 * Class that extends ojalgo's SparseStore to implement for serialization
 */
package ssparsestore;

import org.ojalgo.matrix.store.SparseStore;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SSparseStore implements Serializable {
    transient private SparseStore<Double> matrix;
    public int dummy = 3;

    public SSparseStore(SparseStore<Double> matrix) {
        this.matrix = matrix;
    }

    public void writeObject(ObjectOutputStream outputStream) throws IOException {
        outputStream.writeInt(dummy);
       //outputStream.defaultWriteObject();
    }

    public void readObject(ObjectInputStream inputStream) throws ClassNotFoundException, IOException {
        dummy = inputStream.readInt();
    }


}
