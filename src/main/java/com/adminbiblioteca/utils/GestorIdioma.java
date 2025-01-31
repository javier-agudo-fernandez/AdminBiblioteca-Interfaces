package com.adminbiblioteca.utils;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Clase que gestiona el idioma de la aplicación.
 * Utiliza el patrón Singleton para asegurar que solo haya una instancia de GestorIdioma.
 * @version 1.0
 * @author Javier Agudo
 */
public class GestorIdioma {
    private static GestorIdioma instancia;
    private ResourceBundle bundle;

    /**
     * Constructor privado para evitar la creación de instancias adicionales.
     * Carga el paquete de idioma en español (España) por defecto.
     */
    private GestorIdioma() {
        setLocale(new Locale("es", "ES"));
    }

    /**
     * Obtiene la instancia única de GestorIdioma.
     *
     * @return la instancia única de GestorIdioma
     */
    public static GestorIdioma getInstancia() {
        if (instancia == null) {
            instancia = new GestorIdioma();
        }
        return instancia;
    }

    /**
     * Establece el locale y carga el paquete de recursos correspondiente.
     *
     * @param locale el nuevo locale
     */
    public void setLocale(Locale locale) {
        try {
            bundle = ResourceBundle.getBundle("com.adminbiblioteca.i18n.idioma", locale);
        } catch (java.util.MissingResourceException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene el paquete de recursos actual.
     *
     * @return el paquete de recursos actual
     */
    public ResourceBundle getBundle() {
        return bundle;
    }
}