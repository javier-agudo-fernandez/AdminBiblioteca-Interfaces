package com.adminbiblioteca.controladores;

import com.adminbiblioteca.App;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

/**
 * Controlador para la pantalla de carga (Splash Screen).
 */
public class SplashScreenController {

    @FXML
    private MediaView mediaView;
    @FXML
    private ProgressBar loadingBar;

    private MediaPlayer mediaPlayer;

    /**
     * Inicializa el controlador.
     * Carga y reproduce el video de la Splash Screen en un MediaView.
     * Configura una línea de tiempo para actualizar la barra de progreso.
     */
    @FXML
    public void initialize() {
        // Cargar el video de la Splash Screen y reproducirlo en un MediaView
        String videoPath = String.valueOf(App.class.getResource("video/logobiblio.mp4"));
        Media media = new Media(videoPath);
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);

        mediaPlayer.setAutoPlay(true); // Reproducción automática

        // Timeline para actualizar barra de progreso
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(loadingBar.progressProperty(), 0)), // Empieza en 0
                new KeyFrame(Duration.seconds(5), new KeyValue(loadingBar.progressProperty(), 1)) // En 5 segundos llega al 100%
        );

        timeline.setOnFinished(event -> closeSplashScreen()); // Cerrar la Splash Screen al terminar timeline
        timeline.play();
    }

    /**
     * Cierra la ventana de la Splash Screen y muestra la ventana principal.
     */
    private void closeSplashScreen() {
        mediaPlayer.stop();  // Detener el video
        Stage splashStage = (Stage) mediaView.getScene().getWindow();
        splashStage.close();  // Cerrar la Splash Screen
        Platform.runLater(this::showMainApplication);
    }

    /**
     * Muestra la ventana principal de la aplicación.
     */
    private void showMainApplication() {
        try {
            // Cargar ventana principal y mostrarla
            Stage mainStage = new Stage();
            FXMLLoader loader = new FXMLLoader(App.class.getResource("login.fxml"));
            Parent root = loader.load();

            Stage loginDialog = new Stage();
            Image icono = new Image(Objects.requireNonNull(App.class.getResourceAsStream("images/logobiblio.png")));
            loginDialog.getIcons().add(icono);
            loginDialog.setTitle("Inicio de Sesión");
            loginDialog.initModality(Modality.APPLICATION_MODAL);
            loginDialog.setScene(new Scene(root, 700, 450));

            // Mostrar el diálogo y esperar a que se cierre
            loginDialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}