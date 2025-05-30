module com.sebasth.alifood {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens com.sebasth.alifood to javafx.fxml;
    exports com.sebasth.alifood;
    exports com.sebasth.alifood.controllers;
    opens com.sebasth.alifood.controllers to javafx.fxml;
}