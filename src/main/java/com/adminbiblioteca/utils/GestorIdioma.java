package com.adminbiblioteca.utils;

import java.util.Locale;
import java.util.ResourceBundle;

public class GestorIdioma {
    private static GestorIdioma instancia;
    private ResourceBundle bundle;

    private GestorIdioma() {
        // Por defecto, cargar el paquete de idioma en español (España)
        setLocale(new Locale("es", "ES"));
    }
    //Metodo para obtener la instancia de el gestor del idioma con el idioma seleccionado, para poder ser usado en todas las ventanas
    public static GestorIdioma getInstancia() {
        if (instancia == null) {
            instancia = new GestorIdioma();
        }
        return instancia;
    }

    public void setLocale(Locale locale) {
        try {
            bundle = ResourceBundle.getBundle("com.adminbiblioteca.i18n.idioma", locale);
        } catch (java.util.MissingResourceException e) {

            e.printStackTrace();
        }
    }

    public ResourceBundle getBundle() {
        return bundle;
    }
}
