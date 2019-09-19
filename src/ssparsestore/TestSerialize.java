package ssparsestore;

import org.ojalgo.matrix.store.SparseStore;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class TestSerialize{

    public static void main(String[] args) throws IOException {
        //SparseStore<Double> testMatrix = SparseStore.PRIMITIVE.make(3,3);
        double testMatrix = 1.2;
        SSparseStore instanceSSparseStore = new SSparseStore(testMatrix);

        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("test.store"));
            out.writeObject(instanceSSparseStore);
            //instanceSSparseStore.writeObject(out);
            System.out.println(instanceSSparseStore);
        } catch (IOException i) {
           i.printStackTrace();
        }

    }
}
