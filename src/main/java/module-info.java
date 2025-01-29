module com.adminbiblioteca {
    requires javafx.controls;
    requires javafx.fxml;
    requires io;
    requires kernel;
    requires layout;

    requires javafx.media;
    requires javafx.swing;


    opens com.adminbiblioteca to javafx.fxml;
    exports com.adminbiblioteca;
    exports com.adminbiblioteca.controladores;
    opens com.adminbiblioteca.controladores to javafx.fxml;
}