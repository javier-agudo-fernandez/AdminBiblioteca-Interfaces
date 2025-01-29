package com.adminbiblioteca.utils;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.util.ResourceBundle;

public class IdiomaUtil {

    public static void actualizarTextos(Node root, ResourceBundle bundle) {
        // Verificar que el bundle no sea nulo
        if (bundle == null) {
            System.err.println("El ResourceBundle es nulo.");
            return;
        }

        // Si el nodo es un Labeled (Label, Button, etc.)
        if (root instanceof Labeled) {
            Labeled labeledNode = (Labeled) root;
            String key = labeledNode.getId(); // Usamos el ID como clave
            if (key != null && bundle.containsKey(key)) {
                try {
                    labeledNode.setText(bundle.getString(key));
                } catch (Exception e) {

                }
            }

            // Actualizar tooltip, si existe
            if (labeledNode.getTooltip() != null) {
                String tooltipKey = labeledNode.getTooltip().getId(); // Usamos el ID del tooltip como clave
                if (tooltipKey != null && bundle.containsKey(tooltipKey)) {
                    try {
                        labeledNode.getTooltip().setText(bundle.getString(tooltipKey));
                    } catch (Exception e) {
                        System.err.println("Error al actualizar texto para el tooltip con ID: " + tooltipKey);
                    }
                }
            }
        }

        // Si el nodo es un TextField
        else if (root instanceof TextField) {
            TextField textField = (TextField) root;
            String key = textField.getId(); // Usamos el ID como clave
            if (key != null && bundle.containsKey(key)) {
                try {
                    textField.setPromptText(bundle.getString(key));
                } catch (Exception e) {

                }
            }
        }

        // Si el nodo es un MenuBar
        else if (root instanceof MenuBar) {
            MenuBar menuBar = (MenuBar) root;
            for (Menu menu : menuBar.getMenus()) {
                actualizarMenu(menu, bundle);
            }
        }


        // Si el nodo tiene hijos, aplicar recursivamente
        if (root instanceof Parent) {
            Parent parent = (Parent) root;
            for (Node child : parent.getChildrenUnmodifiable()) {
                actualizarTextos(child, bundle);
            }
        }
    }

    // Método auxiliar para actualizar textos de un Menu y sus MenuItems
    private static void actualizarMenu(Menu menu, ResourceBundle bundle) {
        String key = menu.getId(); // Usamos el ID como clave
        if (key != null && bundle.containsKey(key)) {
            try {
                menu.setText(bundle.getString(key));
            } catch (Exception e) {
                System.err.println("Error al actualizar texto para el Menu con ID: " + key);
            }
        }

        // Actualizar los textos de los MenuItems dentro del Menu
        for (MenuItem menuItem : menu.getItems()) {
            String itemKey = menuItem.getId(); // Usamos el ID como clave
            if (itemKey != null && bundle.containsKey(itemKey)) {
                try {
                    menuItem.setText(bundle.getString(itemKey));
                } catch (Exception e) {
                    System.err.println("Error al actualizar texto para el MenuItem con ID: " + itemKey);
                }
            }

            // Si el MenuItem es un Submenu (tipo Menu), actualizar recursivamente
            if (menuItem instanceof Menu) {
                actualizarMenu((Menu) menuItem, bundle);
            }
        }
    }
}