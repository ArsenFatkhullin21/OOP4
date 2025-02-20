package ru.arsen.oop4;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ru.arsen.oop4.items.MyToolBar;
import ru.arsen.oop4.myList.MyList;
import ru.arsen.oop4.myShape.*;

import java.io.IOException;

public class Paint extends Application {


    private boolean isFirstClick = true;
    private double startX;
    private double startY;
    private ToolBar toolBar;
    public static String chosenShape = "rectangle";

    boolean[] shiftIsPressed = {false};
    MyList<Shape> shapes = new MyList<>();
    MyList<Shape> highlightedShapes = new MyList<>();

    private Canvas canvas = new Canvas(1000,1000);
    private GraphicsContext gc = canvas.getGraphicsContext2D();

    private Shape selectedShape = null;
    private double offsetX, offsetY;



    @Override
    public void start(Stage stage) throws IOException {



        Pane pane = new Pane();
        pane.setPrefSize(1000,1000);

        toolBar = new ToolBar();
        MyToolBar myToolBar = new MyToolBar(toolBar);
        myToolBar.createToolBar();



        VBox vBox = new VBox(toolBar, pane);
        Scene scene = new Scene(vBox, 1000, 1000);


        scene.setOnKeyPressed(event -> {
            KeyCode key = event.getCode();
            if (key == KeyCode.SHIFT){
                shiftIsPressed[0] = true;
            }
        });

        scene.setOnKeyReleased(event -> {
            KeyCode key = event.getCode();
            if (key == KeyCode.SHIFT){
                shiftIsPressed[0] = false;
            }
        });



        pane.setOnMousePressed(event -> {
            startX = event.getX();
            startY = event.getY();

            selectedShape = null;
            for (int i = 0; i < shapes.size(); i++) {
                Shape shape = shapes.get(i);
                if (shape.contains(startX, startY)) {
                    selectedShape = shape;
                    shapes.remove(i);
                    offsetX = startX - shape.getX();
                    offsetY = startY - shape.getY();
                    shape.setColor(Color.RED); // Выделяем фигуру (можно использовать цвет или стиль)
                    break;
                }
            }
        });

        pane.setOnMouseDragged(event -> {
            if (selectedShape != null) {
                // Перетаскиваем выбранную фигуру
                double newX = event.getX() - offsetX;
                double newY = event.getY() - offsetY;
                selectedShape.setX(newX);
                selectedShape.setY(newY);
                shapes.add(selectedShape);


                // Очищаем холст и рисуем только фигуры
                redrawShapes();
            } else {
                // Рисуем фигуру, если нет выбранного объекта
                display(event);
            }
        });

        pane.setOnMouseReleased(event -> {
            if (selectedShape != null) {
                selectedShape.setColor(Color.BISQUE); // Вернуть цвет фигуры в исходное состояние
            }
            if (selectedShape == null) {
                drawOnPane(event);
            }
        });

        pane.getChildren().add(canvas);




        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    private void redrawShapes() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (int i = 0; i < shapes.size(); i++) {
            Shape shape = shapes.get(i);
            shape.draw(gc);
        }
    }


    private void display(MouseEvent event){
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (int i=0; i<shapes.size(); i++) {
            Shape shape = shapes.get(i);
            shape.draw(gc);
        }


        // Получаем текущие координаты мыши
        double endX = event.getX();
        double endY = event.getY();

        // Вычисляем ширину и высоту
        double width = Math.abs(endX - startX);
        double height = Math.abs(endY - startY);

        // Находим верхний левый угол
        double topLeftX = Math.min(startX, endX);
        double topLeftY = Math.min(startY, endY);

        // Временно рисуем прямоугольник
        gc.setStroke(Color.GRAY); // Временный цвет контура
        gc.setLineWidth(2);
        Shape shape =null;
        switch (chosenShape){
            case "rectangle":

                if (shiftIsPressed[0]==true){
                    shape = new Square(topLeftX, topLeftY, width, Color.TRANSPARENT, 2);

                } else {
                    shape = new Rectangle(topLeftX, topLeftY, width, height, Color.TRANSPARENT, 2);
                }

                break;
            case "circle":
                if (shiftIsPressed[0]==true){
                    shape = new Circle(topLeftX, topLeftY, width/2, Color.TRANSPARENT, 2);
                } else {
                    shape = new Ellipse(topLeftX, topLeftY, width, height, Color.TRANSPARENT, 2);
                }

                break;
            case "triangle":
                if (shiftIsPressed[0]==true){
                    double base = width;
                    double heigh = Math.sqrt(3) / 2 * base;
                    shape = new Triangle(topLeftX,topLeftY + heigh,
                            topLeftX+base, topLeftY + heigh,
                            topLeftX + base / 2,topLeftY,
                            Color.TRANSPARENT, 2);
                } else {
                    shape =new Triangle(topLeftX + width / 2,topLeftY,
                            topLeftX,topLeftY + height,
                            topLeftX + width,topLeftY + height,
                            Color.TRANSPARENT, 2);

                }
                break;
            case "line":

                shape = new Line(startX,startY,endX,endY,Color.BLACK, 2);
                break;
            default:
                break;
        }
        shape.draw(gc);
    }


    private void drawOnPane(MouseEvent event){
        // Получаем конечные координаты
        double endX = event.getX();
        double endY = event.getY();

        // Вычисляем ширину и высоту
        double width = Math.abs(endX - startX);
        double height = Math.abs(endY - startY);

        // Находим верхний левый угол
        double topLeftX = Math.min(startX, endX);
        double topLeftY = Math.min(startY, endY);


        Shape shape =null;
        switch (chosenShape){
            case "rectangle":

                if (shiftIsPressed[0]==true){
                    shape = new Square(topLeftX, topLeftY, width, Color.BISQUE, 2);
                } else {
                    shape = new Rectangle(topLeftX, topLeftY, width, height, Color.BISQUE, 2);
                }

                break;
            case "circle":
                if (shiftIsPressed[0]==true){
                    shape = new Circle(topLeftX, topLeftY, width/2, Color.BISQUE, 2);
                } else {
                    shape = new Ellipse(topLeftX, topLeftY, width, height, Color.BISQUE, 2);
                }

                break;
            case "triangle":
                if (shiftIsPressed[0]==true){
                    double base = width;
                    double heigh = Math.sqrt(3) / 2 * base;
                    shape = new Triangle(topLeftX,topLeftY + heigh,
                            topLeftX+base, topLeftY + heigh,
                            topLeftX + base / 2,topLeftY,
                            Color.BISQUE, 2);
                } else {
                    shape =new Triangle(topLeftX + width / 2,topLeftY,
                            topLeftX,topLeftY + height,
                            topLeftX + width,topLeftY + height,
                            Color.BISQUE, 2);

                }
                break;
            case "line":

                shape = new Line(startX,startY,endX,endY,Color.BLACK, 2);

                break;
            default:
                break;
        }


        shapes.add(shape);


        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int i=0; i<shapes.size(); i++) {
            Shape sh = shapes.get(i);
            sh.draw(gc);
        }
    }






    public static void main(String[] args) {
        launch();
    }
}