package app.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import app.Main;

public class MainMovimientosController {

	  // Reference to the main application.
    private Main mainApp;

	public MainMovimientosController(){
	}

	@FXML
    private void initialize() {
    }

	public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

	@FXML
    private void handleButtonLlamamientos(ActionEvent event) throws IOException {
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("../view/LlamamientosCreateView.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide(); //optional
        app_stage.setScene(home_page_scene);
        app_stage.show();

    }


	@FXML
    private void handleButtonHistoricoLlamamientos(ActionEvent event) throws IOException {
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("../view/LlamamientosView.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.hide(); //optional
        app_stage.setScene(home_page_scene);
        app_stage.show();
    }
	
	 @FXML
	 private void buttonVolver(ActionEvent event) throws IOException {
	    Parent home_page_parent = FXMLLoader.load(getClass().getResource("../view/MainView.fxml"));
	    Scene home_page_scene = new Scene(home_page_parent);
	    Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    app_stage.hide(); //optional
	    app_stage.setScene(home_page_scene);
	    app_stage.show();

	 }

	
}
