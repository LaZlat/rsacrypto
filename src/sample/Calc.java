package sample;

import javafx.scene.control.Control;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;

public class Calc {

    public int findN (int firstNumber, int secondNumber) {
        int n;

        n = firstNumber * secondNumber;
        System.out.println("N is " + n);

        return n;
    }

    public int findFiN (int firstNumber, int secondNumber) {
        int fiN;

        fiN = (firstNumber - 1) * (secondNumber - 1);

        System.out.println("fiN is " + fiN);

        return fiN;
    }

    public void getPrivateKey(int e, int fiN){
        BigInteger bIMod = new BigInteger(String.valueOf(fiN));
        for(int i = 3; ; i++){
            BigInteger result = new BigInteger(String.valueOf(i * e));
            if ((Integer.parseInt(String.valueOf(result.mod(bIMod))) == 1)) {
                Controller.privateKey = i;
                System.out.println(i + " "+"private key@@@@@@");
                break;
            }
        }
    }

    public ArrayList<Integer> getPublicKey(int firstNumber, int secondNumber) {
        int n   = this.findN(firstNumber, secondNumber);
        int fiN = this.findFiN(firstNumber, secondNumber);
        int e = this.getdbdNumber(fiN, n);
        ArrayList<Integer> publicKey = new ArrayList<Integer>();

        publicKey.add(e);
        publicKey.add(n);

        this.getPrivateKey(e,fiN);

        return publicKey;
    }

    private int getdbdNumber(int finN, int n) {
        ArrayList<Integer> dbdNumbers = new ArrayList<Integer>();
        for (int i = 2; i < finN; i++){ //PAKEICIAU CAI IS 1 i 2 ir nuimiau shuffle
            if (!this.isPrime(i)) {
                continue;
            }

            if (finN % i == 0){
                continue;
            }

            dbdNumbers.add(i);
        }

        //Collections.shuffle(dbdNumbers);
        System.out.println("e is " + dbdNumbers.get(0));
        return dbdNumbers.get(0);
    }

    public boolean isPrime(int n) {
        if (n % 2 == 0) {
            return false;
        }

        for (int i = 3; i * i <= n; i += 2) {
            if ( n % i == 0) {
                return false;
            }
        }

        return true;
    }


    public ArrayList<Integer> getPrimeNumbers(int n) {
        ArrayList<Integer> primesList = null;

        for (int i = 3; i <= n; i++) {
            if (isPrime(i) && n % i == 0 && isPrime(n / i)) {
                primesList = new ArrayList<>();
                primesList.add(i);
                primesList.add(n / i);
                break;
            }
        }
        return primesList;
    }

    /*private boolean getPrivateKey(int d) {
        DataDAO data = new DataDAOImpl();
        ArrayList<Integer> publicKey = data.fetchPublicKey();
        int e = publicKey.get(0);
        int fiN = 0;
        ArrayList<Integer> primesList = getPrimeNumbers(publicKey.get(1));

        if(primesList.size() == 0){
            System.out.println("Wrong number");
            return false;
        } else {
            fiN = findFiN(primesList.get(0),primesList.get(1));
        }

        if ((d * e) % fiN == 1) {
            return true;
        }
        else {
            return false;
        }
    }*/
}


