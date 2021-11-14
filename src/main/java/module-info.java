module com.clayawky.clayawky {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires webmagic.core;
    requires org.junit.jupiter.api;
    requires  org.seleniumhq.selenium.api;
    requires jfxrt;
    requires rt;

    opens com.clayawky.clayawky to javafx.fxml;
    exports com.clayawky.clayawky;
}