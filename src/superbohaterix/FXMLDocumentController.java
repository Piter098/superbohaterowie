/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package superbohaterix;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author Dabrze
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private javafx.scene.canvas.Canvas map;
    @FXML
    private javafx.scene.canvas.Canvas sidePanel;
    
    public javafx.scene.canvas.Canvas getMap(){
        return this.map;
    }
    public javafx.scene.canvas.Canvas getSidePanel(){
        return this.sidePanel;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
