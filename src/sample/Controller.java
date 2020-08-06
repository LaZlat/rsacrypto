package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {
    @FXML
    private TextField firstNumberField, secondNumberField, privateField;
    @FXML
    private TextArea textArea;
    @FXML
    private Label errorLabel;

    public static int privateKey = 0;
    private DataDAO data = new DataDAOImpl();
    private Calc calc = new Calc();

    public void crypt(){
        Crypto cr = new Crypto(); //Bent 3 simboliai text boxe
        try {
            if (textArea.getText().length() < 4) {
                errorLabel.setText("Tekstas privalo buti didenis nei 3 simboliai");
            } else if (Integer.parseInt(firstNumberField.getText()) < 11 || Integer.parseInt(secondNumberField.getText()) < 11) {
                errorLabel.setText("Pirminiai skaičiai tinka tik nuo 11 ir tik sveikieji skaiciai");
            } else if (!calc.isPrime(Integer.parseInt(firstNumberField.getText())) && !calc.isPrime(Integer.parseInt(secondNumberField.getText()))) {
                errorLabel.setText("Vienas is skaičių nėra pirminis");
            } else {
                data.delete();

                cr.cryptText(Integer.parseInt(firstNumberField.getText()), Integer.parseInt(secondNumberField.getText()), textArea.getText());

                privateField.setText(String.valueOf(privateKey));
                errorLabel.setText("");
                textArea.clear();
                textArea.setText(data.fetchText());
            }
        } catch (Exception e) {
            errorLabel.setText("Error");
        }
    }

    public void decrypt() {
        Crypto cr = new Crypto();

        if(textArea.getText().length() < 4) {
            errorLabel.setText("Tekstas privalo buti didenis nei 3 simboliai");
        }
        else {
            textArea.setText(cr.decryptText(Integer.parseInt((privateField.getText())), textArea.getText()));
        }
    }


    public void fetchText(){
        textArea.setText(data.fetchText());
    }
}
