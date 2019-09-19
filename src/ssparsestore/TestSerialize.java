package ssparsestore;

import org.ojalgo.matrix.store.SparseStore;

import java.io.*;

public class TestSerialize{

    public static void main(String[] args) throws IOException {
        SparseStore<Double> testMatrix = SparseStore.PRIMITIVE.make(3,3);
        SSparseStore instanceSSparseStore = new SSparseStore(testMatrix);
        SSparseStore readInstance = null;

        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("test.store"));
            out.writeObject(instanceSSparseStore);
            System.out.println(instanceSSparseStore);
            out.close();
        } catch (IOException i) {
           i.printStackTrace();
        }

        try{
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("test.store"));
            readInstance = (SSparseStore) in.readObject();
            System.out.println(readInstance.dummy);
            in.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("SSpareStore class not found");
            c.printStackTrace();
            return;
        }

    }
}
