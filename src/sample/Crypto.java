package sample;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Crypto {

    private Calc calc = new Calc();
    private DataDAO data = new DataDAOImpl();


    private ArrayList<String> getASCIIText(String text) {
        ArrayList<String> ascii = new ArrayList<String>();

        for (int i  =0; i < text.length(); i++) {
            int c = text.charAt(i);
            ascii.add(Integer.toString(c));

        }

        return ascii;
    }

    public void cryptText(int firstNumber, int secondNumber, String text){

        ArrayList<String> ascii = getASCIIText(text);
        ArrayList<String> cryptedAscii = new ArrayList<String>();
        final ArrayList<Integer> publicKey = calc.getPublicKey(firstNumber,secondNumber);

        for(String x : ascii){
            BigInteger bIn = new BigInteger(x);
            BigInteger bMod = new BigInteger(String.valueOf(publicKey.get(1)));
            BigInteger result = bIn.pow(publicKey.get(0));

            cryptedAscii.add(String.valueOf(result.mod(bMod)));
        }

        System.out.println(ascii.toString());
        System.out.println(cryptedAscii.toString());
        System.out.println(cryptedAscii.get(0) + "&&&" + cryptedAscii.get(2));

        data.insert(publicKey.get(0),publicKey.get(1),cryptedAscii.toString());
    }


    public String decryptText(int d, String text) {
        String theText = text.replace("[","");
        String theText2 = theText.replace("]","");
        theText = theText2.replace(".0","");
        theText2 = theText.replace(" ","");

        ArrayList<String> cryptedAscii = new ArrayList<String>(Arrays.asList(theText2.split(",")));
        ArrayList<Integer> cryptedNumbersList = getIntegerList(cryptedAscii);
        ArrayList<Integer> numbersList = new ArrayList<>();
        ArrayList<Integer> publicKey = data.fetchPublicKey();
        ArrayList<Integer> primeNumbers =  calc.getPrimeNumbers(publicKey.get(1));
        StringBuilder finalSentence = new StringBuilder();


        int n = calc.findN(primeNumbers.get(0),primeNumbers.get(1));

        for (Integer x : cryptedNumbersList) {
            BigInteger bIn = new BigInteger(String.valueOf(x));
            BigInteger bMod = new BigInteger(String.valueOf(n));
            BigInteger result = bIn.pow(d);

            BigInteger hmm = result.mod(bMod);
            numbersList.add(Integer.parseInt(String.valueOf(hmm)));
        }

        for(Integer x : numbersList) {
            finalSentence.append((char)(int)x);
        }

        System.out.println(finalSentence);
        return finalSentence.toString();

    }


    private ArrayList<Integer> getIntegerList (ArrayList<String> cryptedAscii) {
        ArrayList<Integer> cryptedNumbersList = new ArrayList<>();

        for (String x : cryptedAscii) {
            cryptedNumbersList.add(Integer.parseInt(x));
        }

        return cryptedNumbersList;
    }

}
