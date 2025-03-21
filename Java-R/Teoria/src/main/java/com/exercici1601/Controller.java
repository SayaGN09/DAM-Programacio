package com.exercici1601;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;

public class Controller {

    // dos textField on l'usuari pot introduir text, amb un boto de guardar que es guardara en la ruta de ./data/text.json

    @FXML
    private Button saveButton;

    @FXML
    private TextField textField1;

    @FXML
    private TextField textField2;

    @FXML
    void saveText(ActionEvent event) { // guarda el text introduit en els textField en un fitxer JSON
        String text1 = textField1.getText();
        String text2 = textField2.getText();

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Title", text1);
            jsonObject.put("Text", text2);

            FileWriter file = new FileWriter("./data/text.json");
            file.write(jsonObject.toString());
            file.close(); // tanca el fitxer
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
