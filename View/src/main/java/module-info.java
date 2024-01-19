module com.example.view {
    requires javafx.controls;
    requires javafx.fxml;
    requires ModelProject;
    requires org.apache.logging.log4j;


    opens com.example.view to javafx.fxml;
    exports com.example.view;
}