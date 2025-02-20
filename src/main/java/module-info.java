module ru.arsen.oop4 {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.arsen.oop4 to javafx.fxml;
    exports ru.arsen.oop4;
}