package app.controller;

import java.io.IOException;

import app.util.CreadorVistas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import app.Main;

public class MainMenuController {

	  // Reference to the main application.
    private Main mainApp;
    private CreadorVistas creador;

	public MainMenuController(){
        initialize();
	}

	@FXML
    private void initialize() {
        creador = new CreadorVistas();
    }

	public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

	@FXML
    private void handleButtonTrab(ActionEvent event) throws IOException {
        creador.crearVista("../view/WorkersView.fxml", event);
    }

	@FXML
    private void handleButtonMovimientos(ActionEvent event) throws IOException {
        creador.crearVista("../view/MainMovimientosView.fxml", event);
    }

    @FXML
    private void handleButtonCreacionDatos(ActionEvent event) throws IOException {
        creador.crearVista("../view/CreacionDatosView.fxml", event);
    }

    @FXML
    private void handleButtonInformes(ActionEvent event) throws IOException {
        creador.crearVista("../view/InformesView.fxml", event);
    }
}
