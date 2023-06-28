/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.nyc;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.nyc.model.Location;
import it.polito.tdp.nyc.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="cmbProvider"
    private ComboBox<String> cmbProvider; // Value injected by FXMLLoader

    @FXML // fx:id="txtDistanza"
    private TextField txtDistanza; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="txtStringa"
    private TextField txtStringa; // Value injected by FXMLLoader
    
    @FXML // fx:id="txtTarget"
    private ComboBox<Location> txtTarget; // Value injected by FXMLLoader

    @FXML
    void doAnalisiGrafo(ActionEvent event) {
    	List<Location> migliori= this.model.getMigliori();
    	if(migliori.size()!=0 && migliori!=null) {
    		this.txtResult.appendText("\n Trovati migliori con"+migliori.get(migliori.size()-1).getNumerovicini()+" vicini \n");
    		for (Location l: migliori) {
    			this.txtResult.appendText(l.getName()+" vicini: "+l.getNumerovicini()+"\n");
   
    		}
    	}
    	
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	Location l=this.txtTarget.getValue();
    	String stringa= this.txtStringa.getText();
    	if(l==null || stringa==" ") {
    		this.txtResult.setText("inserire una location e una stringa");
    		return;
    	}
    	List<Location> percorso=this.model.calcolaPercorso(l, stringa);
    	if(percorso!=null) {
    		this.txtResult.setText("trovato percorso\n dimensione percorso: "+percorso.size()+"\n");
    		for(Location loc: percorso) {
    			this.txtResult.appendText(loc+"\n");
    		}
    	}else {
    			this.txtResult.setText("percorso non trovato");
    			
    		}
    		
    	}
    	
    	


    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.txtTarget.getItems().clear();
    	String provider=this.cmbProvider.getValue();
    	String xS=this.txtDistanza.getText();
    	if(xS=="") {
    		this.txtResult.setText("inserire distanza minima");
    		return;
    	}
    	if(provider==null) {
    		this.txtResult.setText("inserire un provider");
    		return;
    	}
    	try {
    		Double distance=Double.parseDouble(xS);
    		if(distance<0) {
    			this.txtResult.setText("inserire una distanza positiva");
    			return;
    		}
    		List<Location> vertici=this.model.creaGrafo(provider,distance);
    		if(vertici.size()!=0) {
    			this.txtTarget.getItems().addAll(vertici);
    			this.txtResult.setText("grafico creato correttamente\n");
    			this.txtResult.appendText("vertici: "+this.model.getVertici()+"\n");
    			this.txtResult.appendText("archi: "+this.model.getArchi());	
    		}else {
    			this.txtResult.setText("grafo non creato");
    		}
    	}catch(NumberFormatException e) {
    		this.txtResult.setText("inserire valori numerici come distanza");
    	}
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbProvider != null : "fx:id=\"cmbProvider\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtDistanza != null : "fx:id=\"txtDistanza\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtStringa != null : "fx:id=\"txtStringa\" was not injected: check your FXML file 'Scene.fxml'.";

    }

    public void setModel(Model model) {
    	this.model = model;
    	List<String> providers=this.model.getProviders();
    	this.cmbProvider.getItems().addAll(providers);
    }
}
