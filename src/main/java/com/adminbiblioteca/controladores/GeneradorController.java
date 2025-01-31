package com.adminbiblioteca.controladores;

import com.adminbiblioteca.App;
import com.adminbiblioteca.utils.GestorIdioma;
import com.adminbiblioteca.utils.IdiomaUtil;
import com.adminbiblioteca.modelo.CarnetDatos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.Stack;

import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Controlador para la generación de carnets.
 * @version 1.1
 * @author Javier Agudo
 */
public class GeneradorController {

    @FXML
    private TextField nombreField;
    @FXML
    private Button buttonImg;
    @FXML
    private TextField apellidosField;
    @FXML
    private TextField idClienteField;
    @FXML
    private DatePicker fechaNacimientoPicker;
    @FXML
    private CheckBox discapacidadCheck;
    @FXML
    private CheckBox suscripcionPremiumCheck;
    @FXML
    private ComboBox<String> tipoDireccionComboBox;
    @FXML
    private TextField denominacionField;
    @FXML
    private TextField portalField;
    @FXML
    private TextField pisoLetraField;
    @FXML
    private TextField ciudadField;
    @FXML
    private ComboBox<String> provinciaComboBox;
    @FXML
    private ComboBox<String> languageComboBox;
    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuItem deshacerButton;
    @FXML
    private MenuItem rehacerButton;
    @FXML
    private Menu catMenuArchivo;
    @FXML
    private Menu catMenuEdicion;

    private File fotoSeleccionada;
    private GestorIdioma gestorIdioma;
    private Stack<String> undoStack = new Stack<>();
    private Stack<String> redoStack = new Stack<>();
    private String currentText = "";

    /**
     * Inicializa el controlador con todos los listeners y datos necesarios.
     */
    @FXML
    public void initialize() {
        gestorIdioma = GestorIdioma.getInstancia();
        ObservableList<String> tiposDeDireccion = FXCollections.observableArrayList(
                "Calle", "Avenida", "Plaza", "Camino", "Carretera", "Paseo", "Travesía", "Ronda", "Pasaje"
        );
        tipoDireccionComboBox.setItems(tiposDeDireccion);

        ObservableList<String> provincias = FXCollections.observableArrayList(
                "Álava", "Albacete", "Alicante", "Almería", "Asturias", "Ávila", "Badajoz", "Barcelona",
                "Burgos", "Cáceres", "Cádiz", "Cantabria", "Castellón", "Ciudad Real", "Córdoba",
                "Cuenca", "Girona", "Granada", "Guadalajara", "Guipúzcoa", "Huelva", "Huesca",
                "Islas Baleares", "Jaén", "La Coruña", "La Rioja", "Las Palmas", "León", "Lleida",
                "Lugo", "Madrid", "Málaga", "Murcia", "Navarra", "Orense", "Palencia", "Pontevedra",
                "Salamanca", "Segovia", "Sevilla", "Soria", "Tarragona", "Santa Cruz de Tenerife",
                "Teruel", "Toledo", "Valencia", "Valladolid", "Vizcaya", "Zamora", "Zaragoza"
        );
        provinciaComboBox.setItems(provincias);

        agregarListenerCampo(nombreField);
        agregarListenerCampo(apellidosField);
        agregarListenerCampo(idClienteField);
        agregarListenerCampo(denominacionField);
        agregarListenerCampo(portalField);
        agregarListenerCampo(pisoLetraField);
        agregarListenerCampo(ciudadField);

        javafx.application.Platform.runLater(() -> {
            Scene scene = tipoDireccionComboBox.getScene();
            if (scene != null) {
                scene.getStylesheets().add(getClass().getResource("/com/adminbiblioteca/estilos/styles.css").toExternalForm());
            }
            actualizarTextos();
        });

        nombreField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]*")) {
                nombreField.setText(oldValue);
            }
        });
        apellidosField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]*")) {
                apellidosField.setText(oldValue);
            }
        });

        deshacerButton.setAccelerator(KeyCombination.keyCombination("Ctrl+Z"));
        rehacerButton.setAccelerator(KeyCombination.keyCombination("Ctrl+Y"));
    }

    /**
     * Agrega listeners a los campos de texto para deshacer y rehacer.
     *
     * @param campo el campo de texto
     */
    private void agregarListenerCampo(TextField campo) {
        campo.textProperty().addListener((observable, oldValue, newValue) -> {
            if (campo instanceof TextField) {
                undoStack.push(oldValue);
            }
        });
    }

    /**
     * Deshace la última acción.
     */
    @FXML
    private void deshacer() {
        if (!undoStack.isEmpty()) {
            String valorAnterior = undoStack.pop();
            if (valorAnterior != null) {
                if (nombreField.isFocused()) {
                    nombreField.setText(valorAnterior);
                } else if (apellidosField.isFocused()) {
                    apellidosField.setText(valorAnterior);
                } else if (idClienteField.isFocused()) {
                    idClienteField.setText(valorAnterior);
                } else if (denominacionField.isFocused()) {
                    denominacionField.setText(valorAnterior);
                } else if (portalField.isFocused()) {
                    portalField.setText(valorAnterior);
                } else if (pisoLetraField.isFocused()) {
                    pisoLetraField.setText(valorAnterior);
                } else if (ciudadField.isFocused()) {
                    ciudadField.setText(valorAnterior);
                }
            }
            redoStack.push(valorAnterior);
        } else {
            System.out.println("No hay valores previos para deshacer.");
        }
    }

    /**
     * Rehace la última acción deshecha.
     */
    @FXML
    private void rehacer() {
        if (!redoStack.isEmpty()) {
            String valorFuturo = redoStack.pop();
            undoStack.push(campoTextoActual());
            restaurarValorCampos(valorFuturo);
        }
    }

    /**
     * Obtiene el valor actual de los campos.
     *
     * @return el valor actual de los campos
     */
    private String campoTextoActual() {
        return nombreField.getText() + "|" +
                apellidosField.getText() + "|" +
                idClienteField.getText() + "|" +
                denominacionField.getText() + "|" +
                portalField.getText() + "|" +
                pisoLetraField.getText() + "|" +
                ciudadField.getText();
    }

    /**
     * Restaura los valores de los campos.
     *
     * @param valores los valores a restaurar
     */
    private void restaurarValorCampos(String valores) {
        String[] valoresCampos = valores.split("\\|");
        nombreField.setText(valoresCampos[0]);
        apellidosField.setText(valoresCampos[1]);
        idClienteField.setText(valoresCampos[2]);
        denominacionField.setText(valoresCampos[3]);
        portalField.setText(valoresCampos[4]);
        pisoLetraField.setText(valoresCampos[5]);
        ciudadField.setText(valoresCampos[6]);
    }

    /**
     * Selecciona una foto.
     */
    @FXML
    private void seleccionarFoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar foto de carnet");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes", "*.jpg", "*.png", "*.jpeg"),
                new FileChooser.ExtensionFilter("Todos los archivos", "*.*")
        );
        File rutaSeleccionada = fileChooser.showOpenDialog(null);
        if (rutaSeleccionada != null) {
            fotoSeleccionada = rutaSeleccionada;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No se encontro el archivo");
        }
    }

    /**
     * Genera una vista previa del carnet usando los datos del formulario.
     */
    @FXML
    private void generarVistaPrevia() {
        if (!validarCampos()) {
            return;
        }
        String nombre = nombreField.getText();
        String apellidos = apellidosField.getText();
        String idCliente = idClienteField.getText();
        LocalDate fechaNacimiento = fechaNacimientoPicker.getValue();
        boolean tieneDiscapacidad = discapacidadCheck.isSelected();
        boolean esPremium = suscripcionPremiumCheck.isSelected();
        String tipoDireccion = tipoDireccionComboBox.getValue();
        String denominacion = denominacionField.getText();
        String portal = portalField.getText();
        String pisoLetra = pisoLetraField.getText();
        String ciudad = ciudadField.getText();
        String provincia = provinciaComboBox.getValue();

        CarnetDatos datos = new CarnetDatos(
                nombre, apellidos, idCliente, fechaNacimiento,
                tieneDiscapacidad, esPremium, tipoDireccion, denominacion,
                portal, pisoLetra, ciudad, provincia, fotoSeleccionada
        );

        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("vistaprevia.fxml"));
            Parent root = loader.load();
            VistaPreviaController controlador = loader.getController();
            controlador.inicializarDatos(datos);
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/com/adminbiblioteca/estilos/styles.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Vista Previa del Carnet");
            stage.show();
        } catch (IOException e) {
            System.out.println("Error al cargar el FXML" + e.getMessage());
        }
    }

    /**
     * Valida los campos del formulario.
     *
     * @return true si los campos son válidos, false si no lo son
     */
    private boolean validarCampos() {
        Scene scene = portalField.getScene();
        scene.getStylesheets().add(getClass().getResource("/com/adminbiblioteca/estilos/styles.css").toExternalForm());

        boolean esValido = true;

        if (nombreField.getText().isEmpty() || apellidosField.getText().isEmpty()
                || idClienteField.getText().isEmpty() || fechaNacimientoPicker.getValue() == null
                || tipoDireccionComboBox.getValue() == null || provinciaComboBox.getValue() == null
                || denominacionField.getText().isEmpty() || portalField.getText().isEmpty()
                || pisoLetraField.getText().isEmpty() || ciudadField.getText().isEmpty()
                || fotoSeleccionada == null) {
            mostrarAlerta("Error", "Por favor, rellene todos los campos obligatorios.");
            esValido = false;
        }

        if (idClienteField.getText().length() < 5) {
            idClienteField.getStyleClass().add("error");
            mostrarAlerta("Error", "El ID de cliente debe tener al menos 5 dígitos.");
            esValido = false;
        } else {
            idClienteField.getStyleClass().remove("error");
        }

        if (!portalField.getText().matches("\\d+")) {
            portalField.getStyleClass().add("error");
            mostrarAlerta("Error", "El portal debe ser un valor numérico.");
            esValido = false;
        } else {
            portalField.getStyleClass().remove("error");
        }

        return esValido;
    }

    /**
     * Muestra una alerta con un mensaje.
     *
     * @param titulo el título de la alerta
     * @param mensaje el mensaje de la alerta
     */
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    /**
     * Limpia el formulario.
     */
    private void limpiarFormulario() {
        nombreField.clear();
        apellidosField.clear();
        idClienteField.clear();
        fechaNacimientoPicker.setValue(null);
        discapacidadCheck.setSelected(false);
        suscripcionPremiumCheck.setSelected(false);
        tipoDireccionComboBox.setValue(null);
        denominacionField.clear();
        portalField.clear();
        pisoLetraField.clear();
        ciudadField.clear();
        provinciaComboBox.setValue(null);
    }

    /**
     * Crea un nuevo registro.
     */
    @FXML
    private void nuevoRegistro() {
        limpiarFormulario();
    }

    /**
     * Cierra la aplicación.
     */
    @FXML
    private void salir() {
        Stage stage = (Stage) languageComboBox.getScene().getWindow();
        stage.close();
    }

    /**
     * Actualiza los textos de la interfaz según el idioma seleccionado.
     */
    private void actualizarTextos() {
        ResourceBundle bundle = gestorIdioma.getBundle();
        Scene scene = nombreField.getScene();
        if (scene != null) {
            IdiomaUtil.actualizarTextos(scene.getRoot(), bundle);
        }
    }
}