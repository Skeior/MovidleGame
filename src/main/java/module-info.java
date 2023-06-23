module bm.erciyes.edu.tr.project.View {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.jsoup;
    requires org.json;
    requires javafx.media;
    requires java.desktop;

    opens bm.erciyes.edu.tr.project.View to javafx.fxml;
    exports bm.erciyes.edu.tr.project.View;
}