package com.adminbiblioteca.utils;

import javafx.scene.Parent;
import javafx.scene.control.Labeled;

import java.util.ResourceBundle;

/**
 * Utilidad para actualizar los textos de la interfaz según el idioma seleccionado.
 * @version 1.1
 * @author Javier Agudo
 */
public class IdiomaUtil {

    /**
     * Actualiza los textos de todos los nodos etiquetados (Labeled) en la interfaz.
     *
     * @param root el nodo raíz de la interfaz
     * @param bundle el paquete de recursos con los textos en el idioma seleccionado
     */
    public static void actualizarTextos(Parent root, ResourceBundle bundle) {
        for (javafx.scene.Node node : root.lookupAll("*")) {
            if (node instanceof Labeled) {
                Labeled labeledNode = (Labeled) node;
                String key = labeledNode.getId();
                if (key != null && bundle.containsKey(key)) {
                    labeledNode.setText(bundle.getString(key));
                }
            }
        }
    }
}