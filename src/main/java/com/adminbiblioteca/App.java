package com.adminbiblioteca;

import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 * Clase App que inicia la aplicación.
 * @version 1.1.1
 * @author Javier Agudo
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Carga de Splash screen
        FXMLLoader splashLoader = new FXMLLoader(App.class.getResource("splash-view.fxml"));
        Scene splashScene = new Scene(splashLoader.load());
        Stage splashStage = new Stage();
        splashStage.setScene(splashScene);
        splashStage.initStyle(StageStyle.UNDECORATED); // Oculta la barra de título y botones

        // Desactiva el cierre por teclado
        splashStage.setOnCloseRequest(Event::consume);
        splashStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
