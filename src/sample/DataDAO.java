package sample;

import java.util.ArrayList;

public interface DataDAO {

    void insert(int publicKeyOne, int publicKeyTwo, String allText);

    String fetchText();

    void delete();

    ArrayList<Integer> fetchPublicKey();
}
