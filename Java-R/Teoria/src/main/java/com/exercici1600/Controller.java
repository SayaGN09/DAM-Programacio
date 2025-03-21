package com.exercici1600;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;

public class Controller {

    @FXML
    private Button buttonCalculate;

    @FXML
    private Text textCounter;

    @FXML
    private TextField textCounter1;

    @FXML
    private TextField textCounter11;

    private int num1;
    private int num2;

    @FXML
    private void actionCalculate(ActionEvent event) {
        num1 = Integer.parseInt(textCounter1.getText());
        num2 = Integer.parseInt(textCounter11.getText());

        int counter = num1 + num2;

        textCounter.setText(String.valueOf(counter));
        textCounter1.getText();
        textCounter11.getText();
    }
}
