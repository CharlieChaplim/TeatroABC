module br.eng.gjkl.teatro {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires com.google.gson;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires eu.hansolo.tilesfx;
    requires caelum.stella.core;

    opens br.eng.gjkl.teatro to javafx.fxml;
    exports br.eng.gjkl.teatro;
    exports br.eng.gjkl.teatro.ui;
    opens br.eng.gjkl.teatro.ui to javafx.fxml;
    exports br.eng.gjkl.teatro.classes;
    opens br.eng.gjkl.teatro.classes to javafx.fxml, com.google.gson;
}