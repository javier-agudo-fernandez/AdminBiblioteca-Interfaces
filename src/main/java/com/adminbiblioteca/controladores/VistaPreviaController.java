package com.adminbiblioteca.controladores;

import com.adminbiblioteca.modelo.CarnetDatos;
import com.adminbiblioteca.utils.GestorIdioma;
import com.adminbiblioteca.utils.IdiomaUtil;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ResourceBundle;

public class VistaPreviaController {

    @FXML
    private Label nombreLabel;
    @FXML
    private Label apellidoLabel;
    @FXML
    private Label idClienteLabel;
    @FXML
    private Label direccionLabel;
    @FXML
    private Label fechaNacimientoLabel;
    @FXML
    private Label fechaEmisionLabel;
    @FXML
    private Label fechaVencimientoLabel;
    @FXML
    private Label suscripcionLabel;
    @FXML
    private ImageView fotoImageView;
    @FXML
    private Label barcodeLabel;
    @FXML
    private AnchorPane panelCarnet;
    private GestorIdioma gestorIdioma;

    @FXML
    public void initialize() {
        gestorIdioma = GestorIdioma.getInstancia();
        javafx.application.Platform.runLater(() -> {
            Scene scene = nombreLabel.getScene();
            if (scene != null) {
                scene.getStylesheets().add(getClass().getResource("/com/adminbiblioteca/estilos/styles.css").toExternalForm());
            }
            actualizarTextos();
        });
    }

    public void inicializarDatos(CarnetDatos datos) {
        nombreLabel.setText(datos.getNombre());
        apellidoLabel.setText(datos.getApellidos());
        fechaNacimientoLabel.setText(datos.getFechaNacimiento().toString());
        idClienteLabel.setText(datos.getIdCliente());
        direccionLabel.setText(datos.getTipoDireccion() + " " + datos.getDenominacion() + ", Portal: " + datos.getPortal()
                + ", Piso: " + datos.getPisoLetra() + " " + datos.getCiudad() + ", " + datos.getProvincia());
        suscripcionLabel.setText(datos.isEsPremium() || datos.isTieneDiscapacidad() ? "Cliente Premium" : "Cliente Regular");
        fechaEmisionLabel.setText(datos.getFechaEmision().toString());
        fechaVencimientoLabel.setText(datos.getFechaVencimiento().toString());
        barcodeLabel.setText(datos.getIdCliente());


        if (datos.getFoto() != null) {
            Image imagen = new Image(datos.getFoto().toURI().toString());
            fotoImageView.setImage(imagen);
        }
    }
    @FXML
    public void enviarImpresora(){

            // Crear el trabajo de impresión
            PrinterJob job = PrinterJob.createPrinterJob();
            if (job != null && job.showPrintDialog(panelCarnet.getScene().getWindow())) {
                // Intentar imprimir el nodo
                boolean success = job.printPage(panelCarnet);
                if (success) {
                    job.endJob(); // Finalizar el trabajo si fue exitoso
                } else {
                    mostrarAlerta("Error", "No se pudo imprimir el contenido.");
                }
            } else {
                mostrarAlerta("Información", "El trabajo de impresión fue cancelado.");
            }
        }

    @FXML
    public void enviarPdf() {
        // Seleccionar el archivo PDF para guardar
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar como PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivo PDF", "*.pdf"));
        File archivoPdf = fileChooser.showSaveDialog(panelCarnet.getScene().getWindow());
        if (archivoPdf == null) {
            return; // Usuario canceló
        }

        try {
            // Tomar una captura del AnchorPane como imagen
            WritableImage image = panelCarnet.snapshot(new SnapshotParameters(), null);

            // Crear el documento PDF
            PdfWriter writer = new PdfWriter(archivoPdf);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Convertir la imagen de FX a un formato que iText pueda manejar
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);

            // Convertir BufferedImage a ImageData de iText
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos);
            baos.flush();
            byte[] imageBytes = baos.toByteArray();
            baos.close();

            // Crear ImageData desde los bytes de la imagen
            ImageData imageData = ImageDataFactory.create(imageBytes);
            com.itextpdf.layout.element.Image pdfImage = new com.itextpdf.layout.element.Image(imageData);
            pdfImage.setAutoScale(true); // Escalar automáticamente
            document.add(pdfImage);

            // Cerrar el documento
            document.close();

            Alert alertaPdf = new Alert(Alert.AlertType.CONFIRMATION);
            alertaPdf.setTitle("PDF Generado");
            alertaPdf.setContentText("El PDF se ha generado correctamente");
            alertaPdf.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo generar el archivo PDF.");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    private void actualizarTextos() {
        // Obtener el bundle con las traducciones
        ResourceBundle bundle = gestorIdioma.getBundle();

        Scene scene = nombreLabel.getScene();
        if (scene != null) {
            IdiomaUtil.actualizarTextos(scene.getRoot(), bundle);
        }
    }


}