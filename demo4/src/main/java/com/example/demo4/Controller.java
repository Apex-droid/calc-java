package com.example.demo4;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class Controller {

    private Model model = new Model();
    @FXML
    public Text output;
    private boolean start = true;
    @FXML
    private void num(ActionEvent event){


            String value = ((Button)event.getSource()).getText();

            model.listener = txt -> {
                output.setText(txt);
            };

            model.edit(value);
    }

    @FXML
    protected void operator(ActionEvent event){

    }
}