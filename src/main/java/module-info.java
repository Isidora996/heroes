module cl.isidora.heroes {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;

    opens cl.isidora.heroes to javafx.fxml;
    opens cl.isidora.heroes.controller to javafx.fxml;
    opens cl.isidora.heroes.dto to javafx.base, com.google.gson;

    exports cl.isidora.heroes;
}