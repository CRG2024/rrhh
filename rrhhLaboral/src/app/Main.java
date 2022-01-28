package app;

import java.io.IOException;

import app.view.MainMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application {

	private Stage primaryStage;
    private BorderPane rootLayout;

    public Main() {
	}


	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("RRHH WORKERS");
        initRootLayout();
        showMainView();
	}

	/**
     * Inicializamos el root. Buscamos el fxmk que se va a gastar como anclaje al resto (ventana donde se cargan el resto)
     */
    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/NavView.fxml"));
            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            //TODO averiguar como hacer el logo mas grande
            primaryStage.getIcons().add(new Image("images/logo_empresa.PNG"));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Añadimos al root el fxml que vamos a usar (En este caso la pantalla principal Menu)
     */
    public void showMainView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/MainView.fxml"));
            AnchorPane menuView = (AnchorPane) loader.load();
            rootLayout.setCenter(menuView);

            MainMenuController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean showMainApp(){
		return false;
    }

	/**
	 * Returns the main stage.
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}



	public static void main(String[] args) {
		launch(args);
	}

}
