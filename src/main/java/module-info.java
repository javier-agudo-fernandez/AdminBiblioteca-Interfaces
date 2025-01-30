module com.adminbiblioteca {
    requires javafx.fxml;
    requires io;
    requires kernel;
    requires layout;

    requires javafx.swing;
    requires javafx.controls;
    requires javafx.web;
    requires javafx.media;


    opens com.adminbiblioteca to javafx.fxml;
    exports com.adminbiblioteca;
    exports com.adminbiblioteca.controladores;
    opens com.adminbiblioteca.controladores to javafx.fxml;
}