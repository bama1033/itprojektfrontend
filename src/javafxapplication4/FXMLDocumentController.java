/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication4;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;

import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Martin
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private LineChart id_graph;
    @FXML
    private ComboBox<String> id_city;
    @FXML
    private ComboBox<String> id_station ;
    @FXML
    private ComboBox<String> id_date;
    @FXML
    private Label cursorCoords;
    @FXML
    private TextField GKu;
    @FXML
    private TextField GKb;
    @FXML
    private TextField HAu;
    @FXML
    private TextField HAb;
    
    private double counterGKu;
    
    private double counterGkb;
    
    private double highu;
    
    private double highb;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        selection("both");
    }
    @FXML
    private void handleButtonActionBahn(ActionEvent event){
        selection("bahn");
    }
    @FXML
    private void handleButtonActionBike(ActionEvent event){
        selection("bike");
    }
   
    
    @FXML
    private void handleButtonActionClear(ActionEvent event) {
        id_graph.setAnimated(false);
        id_graph.getData().clear();
        id_graph.setAnimated(true);
        
        counterGKu=0;
        GKu.setText(""+counterGKu);
        
        counterGkb=0;
        GKb.setText(""+counterGkb);
        highb=0;
        HAb.setText(""+highb);
        highu=0;
        HAu.setText(""+highu);
        initgraph();
    }


    @FXML
    private void handleChangeDate(MouseEvent event){
        id_date.getItems().clear();
        if(id_station.getSelectionModel().getSelectedItem().equals("Willy-Brandt-Platz")){
        id_date.getItems().addAll(
            "24.10.2016");
        }
        else if(id_station.getSelectionModel().getSelectedItem().equals("Eschenheimer Tor")){
        id_date.getItems().addAll(
            "10.08.2016");
        }
        else if (id_station.getSelectionModel().getSelectedItem().equals("Alte Oper")){
        id_date.getItems().addAll(
            "28.10.2016");
        }
        else if (id_station.getSelectionModel().getSelectedItem().equals("Festhalle/Messe")){
        id_date.getItems().addAll(
            "15.09.2016");
        }
        else if (id_station.getSelectionModel().getSelectedItem().equals("Merianplatz")){
        id_date.getItems().addAll(
            "18.09.2016");
        }
        else if (id_station.getSelectionModel().getSelectedItem().equals("Industriehof")){
        id_date.getItems().addAll(
            "12.09.2016");
        }
    }
    private void selection(String param){
        String choice_city = id_city.getSelectionModel().getSelectedItem();
        String choice_station = id_station.getSelectionModel().getSelectedItem();//.toString möglich
        String choice_date = id_date.getSelectionModel().getSelectedItem();

        id_graph.setTitle(choice_city+" - "+choice_station);
        
        id_graph.setCursor(Cursor.CROSSHAIR);
        
        if(choice_station!=null && choice_date!=null){
            loadchart(param);
            cursorCoords=createCursorGraphCoordsMonitorLabel(id_graph);
        }
        if(choice_station==null){
            choice_station="keine Station gewählt";
        }
        if(choice_date==null){
            choice_date="kein valides Datum";
        }
}

    private void loadchart(String param){
        String choice_station = id_station.getSelectionModel().getSelectedItem();//.toString möglich
        String choice_date = id_date.getSelectionModel().getSelectedItem();
        XYChart.Series<Number, Number>series1 = new XYChart.Series();
        series1.setName("U-Bahn "+choice_station+" "+choice_date);
        for (double c=0;c<24;c++){
            series1.getData().add(new XYChart.Data( c, numgenUBahn(c)));      
        }

        XYChart.Series<Number, Number>series2 = new XYChart.Series();
        series2.setName("Call-a-Bike "+choice_station+" "+choice_date);
        for (double c=0;c<24;c++){
            series2.getData().add(new XYChart.Data( c, numgenBike(c)));
        }
                
        if(param.equals("bike")){
        id_graph.getData().add(series2);
        GKb.setText(""+truncate(counterGkb));
        HAb.setText(""+truncate(highb));
        highb=0;
        counterGkb=0;
        }
        if(param.equals("bahn")){
        id_graph.getData().add(series1);
        GKu.setText(""+truncate(counterGKu));
        HAu.setText(""+truncate(highu));
        highu=0;
        counterGKu=0;
        }
        
        if(param.equals("both")){
        id_graph.getData().add(series1);
        id_graph.getData().add(series2);
        GKu.setText(""+truncate(counterGKu));
        counterGKu=0;
        GKb.setText(""+truncate(counterGkb));
        counterGkb=0;
        HAb.setText(""+truncate(highb));
        highb=0;
        HAu.setText(""+truncate(highu));
        highu=0;
        }
    }
    private String truncate(double number){
        DecimalFormat df = new DecimalFormat("##");
        String resultnew =  df.format(number);
       // int aux = (int)(number*100);//1243
      //  double result = aux/100d;//12.43
        return resultnew;
    }
    
    private double numgenUBahn(double c){
        if (c<5 || c>23){
        double r =Math.random()*20;
        counterGKu=counterGKu+r;
        return r;
        }
        else if (c>=5 && c<10){
        double r =(Math.random()*40)+10;
        counterGKu=counterGKu+r;
        return r;
        }
        else if (c>=10 && c<20){
        double r =(Math.random()*40)+30;
        counterGKu=counterGKu+r;
        if(highu<r){
            highu=r;
        }
        return r;
        }
        else if (c>=20 && c<=24){
        double r =(Math.random()*20)+15;
        counterGKu=counterGKu+r;
        return r;
        }
        else {
        return Math.random()*50;
        }
    }
    
        private double numgenBike(double c){
        if (c<5 || c>23){
        double r =Math.random()*20;
        counterGkb=counterGkb+r;
        return r;
        }
        else if (c>=5 && c<10){
        double r =(Math.random()*10);
        counterGkb=counterGkb+r;
        return r;
        }
        else if (c>=10 && c<20){
        double r =(Math.random()*30)+10;
        counterGkb=counterGkb+r;
        if(highb<r){
        highb=r;
        }
        return r;
        }
        else if (c>=20 && c<=24){
        double r =(Math.random()*15)+5;
        counterGkb=counterGkb+r;
        return r;
        }
        else {
        return Math.random()*10;
        }
    }
   
         
    
    private Label createCursorGraphCoordsMonitorLabel(LineChart<Number, Number> lineChart) {
    final Axis<Number> xAxis = lineChart.getXAxis();
    final Axis<Number> yAxis = lineChart.getYAxis();

 

    final Node chartBackground = lineChart.lookup(".chart-plot-background");
    for (Node n: chartBackground.getParent().getChildrenUnmodifiable()) {
      if (n != chartBackground && n != xAxis && n != yAxis) {
        n.setMouseTransparent(true);
      }
    }

    chartBackground.setOnMouseEntered(new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent mouseEvent) {
        cursorCoords.setVisible(true);
      }
    });

    chartBackground.setOnMouseMoved(new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent mouseEvent) {
        cursorCoords.setText("Kapazität in %: "+
          String.format(
            " %.2f",
          //  xAxis.getValueForDisplay(mouseEvent.getX()),
            yAxis.getValueForDisplay(mouseEvent.getY())
          )
        +"%"+"\n"+ String.format(
            " %.0f",
           xAxis.getValueForDisplay(mouseEvent.getX())
                
          )+" Uhr");
      }
    });

    chartBackground.setOnMouseExited(new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent mouseEvent) {
        cursorCoords.setVisible(false);
      }
    });

    xAxis.setOnMouseEntered(new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent mouseEvent) {
        cursorCoords.setVisible(true);
      }
    });

//    xAxis.setOnMouseMoved(new EventHandler<MouseEvent>() {
//      @Override public void handle(MouseEvent mouseEvent) {
//        cursorCoords.setText(
//          String.format(
//            "x = %.2f",
//            xAxis.getValueForDisplay(mouseEvent.getX())
//          )
//        );
//      }
//    });

    xAxis.setOnMouseExited(new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent mouseEvent) {
        cursorCoords.setVisible(false);
      }
    });

    yAxis.setOnMouseEntered(new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent mouseEvent) {
        cursorCoords.setVisible(true);
      }
    });

//    yAxis.setOnMouseMoved(new EventHandler<MouseEvent>() {
//      @Override public void handle(MouseEvent mouseEvent) {
//        cursorCoords.setText(
//          String.format(
//            "y = %.2f",
//            yAxis.getValueForDisplay(mouseEvent.getY())
//          )
//        );
//      }
//    });

    yAxis.setOnMouseExited(new EventHandler<MouseEvent>() {
      @Override public void handle(MouseEvent mouseEvent) {
        cursorCoords.setVisible(false);
      }
    });

    return cursorCoords;
  }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initgraph();

    }    
    private void initgraph(){
        XYChart.Series<Number, Number>low = new XYChart.Series();
        low.setName("Geringe Auslastung");
        low.getData().add(new XYChart.Data( 0, 15));
        low.getData().add(new XYChart.Data( 24, 15));

                XYChart.Series<Number, Number>mid = new XYChart.Series();
        mid.setName("Mittlere Auslastung");
        mid.getData().add(new XYChart.Data( 0, 50));
        mid.getData().add(new XYChart.Data( 24, 50));
        
                XYChart.Series<Number, Number>high = new XYChart.Series();
        high.setName("Hohe Auslastung");
        high.getData().add(new XYChart.Data( 0, 85));
        high.getData().add(new XYChart.Data( 24, 85));
        
        id_graph.getData().add(low);
        id_graph.getData().add(mid);
        id_graph.getData().add(high);
    }

    
}
