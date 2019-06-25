/**
 * Sample Skeleton for 'Crimes.fxml' Controller Class
 */

package it.polito.tdp.crimes;

import java.net.URL;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.model.District;
import it.polito.tdp.model.DistrictDistance;
import it.polito.tdp.model.Model;
import it.polito.tdp.simulator.Simulator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CrimesController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxAnno"
    private ComboBox<Year> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxMese"
    private ComboBox<String> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="boxGiorno"
    private ComboBox<String> boxGiorno; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaReteCittadina"
    private Button btnCreaReteCittadina; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaReteCittadina(ActionEvent event) {
    	
    	txtResult.clear();
    	
    	Year year = boxAnno.getValue();
    	
    	if ( year != null ) {
    		model.buildGraph(year);
        	
        	for ( District d : model.getGraph().vertexSet() ) {
        		List<DistrictDistance> list = model.getNeighbors(d);
        		
        		txtResult.appendText("Lista distretti per distanza dal distretto: "+d+"\n");
        		
        		for ( DistrictDistance dd : list )
        			txtResult.appendText(dd.toString()+"\n");
        		
        		txtResult.appendText("\n");
        	}
    	} else {
    		txtResult.setText("Errore, inserire un anno!");
    	}
    }

    @FXML
    void doSimula(ActionEvent event) {
    	
    	boolean flag = true;
    	Year year = boxAnno.getValue();
    	String month = boxMese.getValue();
    	String day = boxGiorno.getValue();
    	txtResult.clear();
    	
    	int n = -1;
    	try { 
    	   n = Integer.parseInt(txtN.getText().trim());
    	} catch(NumberFormatException nfe) {
    		txtResult.setText("Errore, inserire tutti i parametri!");
    	}
    	if ( year != null && flag == true ) {
    		Simulator sim = new Simulator(year,month,day,n,model);
    		sim.run();
    		txtResult.setText(sim.getNot()+" crimini gestiti male.");
    	}

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert boxGiorno != null : "fx:id=\"boxGiorno\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnCreaReteCittadina != null : "fx:id=\"btnCreaReteCittadina\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Crimes.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Crimes.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	boxAnno.getItems().addAll(model.getYearsList());
    	boxMese.getItems().addAll("01","02","03","04","05","06","07","08","09","10","11","12");
    	boxGiorno.getItems().addAll("01","02","03","04","05","06","07","08","09","10","11","12","13","14",
    			"15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31");
    }
}
