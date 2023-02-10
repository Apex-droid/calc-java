package com.example.demo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller {

    public boolean hist_pr = false;
    public HBox Hbox;
    private Stage stage;
    //private Scene scene;
    public void switchToScene1(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
    public void switchToScene2(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Controller.class.getResource("hello-view1.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("JavaFX Graph Example");
        stage.show();
    }
    //Controller() {
      //  if (model == null)
        //    model =  new Model();
    //}
    private static Model model = new Model();



    @FXML
    private LineChart<Double, Double> lineGraph;

    @FXML
    private AreaChart<Double, Double> areaGraph;

    @FXML
    private Button lineGraphButton;

    @FXML
    private Button areaGraphButton;
    //@FXML
    //public VBox VbOx;
    @FXML
    public Text output;
    private boolean start = true;
    @FXML
    private void num(ActionEvent event) throws IOException {

        if (model == null)
            model =  new Model();

            String value = ((Button)event.getSource()).getText();

            model.listener = txt -> {
                output.setText(txt);
            };

            model.edit(value);
    }
    @FXML
    private ListView<String> history;
    @FXML
    private Button clearButton;
    @FXML
    private void show_history(ActionEvent event) {

        if (hist_pr == false && !model.isHistEmpty()) {
            history = new ListView<>();
            Hbox.getChildren().add(history);
            hist_pr = true;
        }
        else
        {
            Hbox.getChildren().remove(history);
            hist_pr = false;
        }


    }
    private MyGraph mathsGraph;
    private MyGraph areaMathsGraph;

    @FXML
    protected void operator(ActionEvent event){
    }

    @FXML
    public void initialize(final URL url, final ResourceBundle rb) {
        mathsGraph = new MyGraph(lineGraph, 10);
        areaMathsGraph = new MyGraph(areaGraph, 10);
    }

    @FXML
    public void graficButton(final ActionEvent button){
        //initialize();
        mathsGraph = new MyGraph(lineGraph, 10);
        areaMathsGraph = new MyGraph(areaGraph, 10);
        areaMathsGraph.plotLine(model);
    }



}