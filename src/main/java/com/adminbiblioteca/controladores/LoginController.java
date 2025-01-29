package com.adminbiblioteca.controladores;

import com.adminbiblioteca.App;
import com.adminbiblioteca.utils.GestorIdioma;
import com.adminbiblioteca.utils.IdiomaUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controlador para la pantalla de inicio de sesión.
 */
public class LoginController {

    @FXML
    public TextField userField;
    @FXML
    public PasswordField passField;
    @FXML
    public Button loginButton;
    @FXML
    public ComboBox<String> languageBox;

    private static final String USUARIO_ADMIN = "admin";
    private static final String CONTRASENA_ADMIN = "1234";

    private GestorIdioma gestorIdioma;

    /**
     * Inicializa el controlador.
     */
    @FXML
    public void initialize() {
        gestorIdioma = GestorIdioma.getInstancia();
        actualizarIdioma("es_ES");  // Idioma predeterminado

        languageBox.getItems().addAll("Español", "English", "Deutsch");
        languageBox.setValue("Español");  // Idioma predeterminado

        languageBox.setOnAction(event -> {
            String idiomaSeleccionado = languageBox.getValue();
            if ("Español".equals(idiomaSeleccionado)) {
                actualizarIdioma("es_ES");
            } else if ("English".equals(idiomaSeleccionado)) {
                actualizarIdioma("en_US");
            } else if ("Deutsch".equals(idiomaSeleccionado)) {
                actualizarIdioma("de_DE");
            }
        });

        loginButton.setDefaultButton(true);
    }

    /**
     * Maneja el evento de inicio de sesión.
     *
     * @param actionEvent el evento de acción
     */
    @FXML
    public void iniciarSesion(ActionEvent actionEvent) {
        String usuario = userField.getText();
        String contrasena = passField.getText();

        if (usuario.equals(USUARIO_ADMIN) && contrasena.equals(CONTRASENA_ADMIN)) {
            abrirGenerador();
        } else {
            mostrarError();
        }
    }

    /**
     * Abre la ventana del generador.
     */
    private void abrirGenerador() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("generador.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(fxmlLoader.load()));
            Image icono = new Image(Objects.requireNonNull(App.class.getResourceAsStream("images/logobiblio.png")));
            stage.getIcons().add(icono);

            stage.show();
            Stage stageAnterior = (Stage) loginButton.getScene().getWindow();
            stageAnterior.close();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se pudo abrir la nueva ventana");
            alert.setContentText("Ocurrió un problema al intentar cargar la ventana principal.");
            alert.showAndWait();
        }
    }

    /**
     * Muestra un mensaje de error de autenticación.
     */
    private void mostrarError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de autenticación");
        alert.setHeaderText("Credenciales incorrectas");
        alert.setContentText("Verifica tu usuario y contraseña");
        alert.showAndWait();
    }

    /**
     * Actualiza el idioma de la interfaz.
     *
     * @param codigoIdioma el código del idioma
     */
    private void actualizarIdioma(String codigoIdioma) {
        Locale nuevoLocale = new Locale(codigoIdioma.split("_")[0], codigoIdioma.split("_")[1]);
        gestorIdioma.setLocale(nuevoLocale);

        Scene scene = loginButton.getScene();
        if (scene != null) {
            ResourceBundle bundle = gestorIdioma.getBundle();
            IdiomaUtil.actualizarTextos(scene.getRoot(), bundle);
        }
    }
}