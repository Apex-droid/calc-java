package com.example.demo;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller {

    public boolean hist_pr = false;
    public HBox Hbox;
    public Label output;
   private Stage stage;
   
   
   private class MyListCell extends ListCell<String> {
       public MyListCell() {
           super();
           updateListView(history);
           setSkin(createDefaultSkin());
       }
   }
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
        stage.setTitle("some Graph");
        stage.show();
    }
    private void setPreferredWidth(ObservableList<String> items) {
    	 ListCell<String> cell = new MyListCell();
    	 cell.autosize();
    	 cell.wrapTextProperty();
    	 cell.setMaxWidth(900);
         double width = 0.0;
        
         for (String item : items) {
             cell.setText(item);
             width = Math.max(width, cell.prefWidth(100));
         }
         history.setPrefWidth(width + history.getInsets().getLeft() + history.getInsets().getRight() + 50);
         history.setMinWidth(width + history.getInsets().getLeft() + history.getInsets().getRight() + 50);
         history.setMaxWidth(width + history.getInsets().getLeft() + history.getInsets().getRight() + 50);
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

    private boolean start = true;
    @FXML
    private void num(ActionEvent event) throws IOException {

        if (model == null)
            model =  new Model();

            String value = ((Button)event.getSource()).getText();
            //model.edit(value);
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
    	
    	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	
        if (hist_pr == false && !model.isHistEmpty()) {
            history = new ListView<>();
            history.applyCss();
            //history.setMaxHeight(570);
            history.autosize();
            history.setMaxWidth(900);
            history.borderProperty();
            history.setCellFactory(param -> new ListCell<String>(){
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item==null) {
                        setGraphic(null);
                        setText(null); 
                        // other stuff to do...

                    }else{

                        // set the width's
                        setMinWidth(param.getWidth());
                        setMaxWidth(param.getWidth());
                        setPrefWidth(param.getWidth());

                        // allow wrapping
                        setWrapText(true);

                        setText(item.toString());


                    }
                }
            });
            //history.setMaxWidth(Region.USE_COMPUTED_SIZE);
            //history.setMinWidth(Region.USE_COMPUTED_SIZE);
        
            history.getItems().addAll(model.result_array());
            //history.prefWidthProperty().bind(Bindings.size(history.getChildrenUnmodifiable()));
            history.getSelectionModel().selectedItemProperty().addListener((
            		ObservableValue<? extends String> ov, String old_val, String new_val)-> takeViewElement(event));
            setPreferredWidth(history.getItems());
            Hbox.getChildren().add(history);
            stage.setMinWidth(500 + history.getMaxWidth());
            //stage.setMaxWidth(500 + history.getWidth());
            //stage.setMaxWidth(700);
            hist_pr = true;
        }
        else {
            Hbox.getChildren().remove(history);
            hist_pr = false;
            stage.setMinWidth(483);
            //stage.setMaxWidth(483);
        }


    }
    private MyGraph mathsGraph;
    private MyGraph areaMathsGraph;

    @FXML
    protected void operator(ActionEvent event){
        model.enter();
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
    @FXML 
    protected void takeViewElement(ActionEvent event) {
    	
    	int index = history.getSelectionModel().getSelectedIndex();
    	 model.listener = txt -> {
             output.setText(txt);
         };

         model.expFromHist(index);
    	
    }
   @FXML 
   protected void takeHistoryUp(ActionEvent event)
   {
	   model.listener = txt -> {
           output.setText(txt);
       };
       model.takeHistoryUp();
	   
   }
   @FXML
   protected void takeHistoryDown(ActionEvent event)
   {
	   model.listener = txt -> {
           output.setText(txt);
       };
       model.takeHistoryUp();
	   
   }
   @FXML
   protected void clearHistory(ActionEvent event)
   {
	   model.HistoryClear();
	   
   }


}