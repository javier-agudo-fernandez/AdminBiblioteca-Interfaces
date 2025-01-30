package com.adminbiblioteca.controladores;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * Controlador para la pantalla de ayuda.
 */
public class HelpController {

    @FXML
    private WebView webView;

    /**
     * Inicializa la ventana de ayuda.
     *
     */
    @FXML
    public void initialize() {
        WebEngine webEngine = webView.getEngine();
        webEngine.load(getClass().getResource("/ayuda/help.html").toExternalForm());
    }
}