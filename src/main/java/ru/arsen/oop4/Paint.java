package ru.arsen.oop4;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Cursor;
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
import ru.arsen.oop4.interfaces.Changeable;
import ru.arsen.oop4.interfaces.Moveable;
import ru.arsen.oop4.items.MyToolBar;
import ru.arsen.oop4.myList.MyList;
import ru.arsen.oop4.myShape.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Paint extends Application {

    private boolean isFirstClick = true;
    private double startX;
    private double startY;

    private ToolBar toolBar;
    public static String chosenShape;
    public static Color newColor;

    public  static boolean[] changeColorButton = {false};

    boolean[] shiftIsPressed = {false};
    boolean[] controlIsPressed = {false};
    MyList<Shape> shapes = new MyList<>();
    MyList<Shape> highlightedShapes = new MyList<>();

    public static Canvas canvas = new Canvas(1000,1000);

    private GraphicsContext gc = canvas.getGraphicsContext2D();

    @Override
    public void start(Stage stage) throws IOException {

        canvas.setStyle("-fx-border-color: black; -fx-border-width: 3; -fx-background-color: white;");

        Pane pane = new Pane();
        pane.setStyle("-fx-border-color: black; -fx-border-width: 3; -fx-background-color: white;");
        pane.setPrefSize(1000, 1000);

        toolBar = new ToolBar();
        MyToolBar myToolBar = new MyToolBar(toolBar);
        myToolBar.createToolBar();

        VBox vBox = new VBox(toolBar, pane);
        Scene scene = new Scene(vBox, 1000, 1000);

        scene.setOnKeyPressed(event -> {

            KeyCode key = event.getCode();
            if (key == KeyCode.SHIFT) {
                shiftIsPressed[0] = true;
            } else if (key == KeyCode.BACK_SPACE) {
                deleteSelectedShapes();
            } else if (key == KeyCode.CONTROL) {
                controlIsPressed[0] = true;

            }
        });

        scene.setOnKeyReleased(event -> {
            KeyCode key = event.getCode();
            if (key == KeyCode.SHIFT) {
                shiftIsPressed[0] = false;
            } else if (key == KeyCode.BACK_SPACE) {
                deleteSelectedShapes();
            } else if (key == KeyCode.CONTROL) {
                controlIsPressed[0] = false;

            }

        });

        pane.setOnMousePressed(event -> {
            handleMousePressed(event);

        });

        pane.setOnMouseDragged(event -> {

            handleMouseDragged(event);
        });


        pane.setOnMouseReleased(event -> {
            handleMouseReleased(event);

        });

        pane.getChildren().add(canvas);



        stage.setTitle("Paint");
        stage.setScene(scene);
        stage.show();
    }

    private void redrawCanvas() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (Shape shape : shapes) {
            shape.draw(gc);
        }

        // Рисуем рамку для выделенных фигур
        for (Shape shape : highlightedShapes) {
            gc.setStroke(Color.rgb(255, 0, 0, 0.5)); // Красный цвет для выделения
            gc.setLineWidth(3); // Толщина линии рамки
//            if (shape instanceof  Triangle) {
//                gc.strokeRect((shape.getX() - 2), (shape.getY() - 2) - shape.getHeight(), shape.getWidth() + 4, shape.getHeight() + 4);
//                gc.setFill(Color.BLACK);
//                 } else if (shape instanceof HighlightRectangle) {
//
//
//
//            }
//             else {
//                gc.strokeRect(shape.getX() - 2, shape.getY() - 2, shape.getWidth() + 4, shape.getHeight() + 4);
//            }
            gc.strokeRect(shape.getX() - 2, shape.getY() - 2, shape.getWidth() + 4, shape.getHeight() + 4);

        }
    }

    private void deleteSelectedShapes() {
        shapes.removeAll(highlightedShapes); // Удаляем выделенные фигуры
        highlightedShapes.removeAll(); // Очищаем список выделенных фигур
        redrawCanvas(); // Перерисовываем холст
    }

    private void display(MouseEvent event) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (Shape shape : shapes) {
            shape.draw(gc);
        }

        double endX = event.getX();
        double endY = event.getY();
        double width = Math.abs(endX - startX);
        double height = Math.abs(endY - startY);
        double topLeftX = Math.min(startX, endX);
        double topLeftY = Math.min(startY, endY);

        Shape tempShape = null;
        switch (chosenShape) {

            case "rectangle":
                if (shiftIsPressed[0]) {
                    tempShape = new Square(topLeftX, topLeftY, width, Color.TRANSPARENT, 2);
                } else {
                    tempShape = new Rectangle(topLeftX, topLeftY, width, height, Color.TRANSPARENT, 2);
                }
                break;
            case "circle":
                if (shiftIsPressed[0]) {
                    tempShape = new Circle(topLeftX, topLeftY, width / 2, Color.TRANSPARENT, 2);
                } else {
                    tempShape = new Ellipse(topLeftX, topLeftY, width, height, Color.TRANSPARENT, 2);
                }
                break;
            case "triangle":
                if (shiftIsPressed[0]) {
                    double base = width;
                    double heightTri = Math.sqrt(3) / 2 * base;
                    tempShape = new Triangle(topLeftX,topLeftY,topLeftX, topLeftY + heightTri,
                            topLeftX + base, topLeftY + heightTri,
                            topLeftX + base / 2, topLeftY,
                            Color.TRANSPARENT, 2);
                } else {
                    tempShape = new Triangle(topLeftX,topLeftY,topLeftX + width / 2, topLeftY,
                            topLeftX, topLeftY + height,
                            topLeftX + width, topLeftY + height,
                            Color.TRANSPARENT, 2);
                }

                break;
            case "line":
                tempShape = new Line(topLeftX,topLeftY,startX, startY, endX, endY, Color.BLACK, 2);
                break;
            case "cursor":
                tempShape = null;
                break;
            case "highlightRectangle":
                tempShape = new HighlightRectangle(topLeftX, topLeftY, width, height, Color.TRANSPARENT, 2);
            default:
                break;
        }

        if (tempShape != null) {
            tempShape.draw(gc);
        }
    }

    private void drawOnPane(MouseEvent event) {
        double endX = event.getX();
        double endY = event.getY();
        double width = Math.abs(endX - startX);
        double height = Math.abs(endY - startY);
        double topLeftX = Math.min(startX, endX);
        double topLeftY = Math.min(startY, endY);

        Shape newShape = null;
        switch (chosenShape) {
            case "highlightRectangle":
                newShape = new HighlightRectangle(topLeftX, topLeftY, width, height, Color.TRANSPARENT, 2);
                break;
            case "rectangle":
                if (shiftIsPressed[0]) {
                    newShape = new Square(topLeftX, topLeftY, width, newColor, 2);
                } else {
                    newShape = new Rectangle(topLeftX, topLeftY, width, height, newColor, 2);
                }
                break;
            case "circle":
                if (shiftIsPressed[0]) {
                    newShape = new Circle(topLeftX, topLeftY, width / 2, newColor, 2);
                } else {
                    newShape = new Ellipse(topLeftX, topLeftY, width, height, newColor, 2);
                }
                break;
            case "triangle":
                if (shiftIsPressed[0]) {
                    double base = width;
                    double heightTri = Math.sqrt(3) / 2 * base;
                    newShape = new Triangle(topLeftX,topLeftY,topLeftX, topLeftY + heightTri,
                            topLeftX + base/2, topLeftY ,
                            topLeftX + base , topLeftY + heightTri,
                            newColor, 2);
                } else {
                    newShape = new Triangle(topLeftX,topLeftY,topLeftX , topLeftY+height,
                            topLeftX+width/2, topLeftY,
                            topLeftX + width, topLeftY + height,
                            newColor, 2);
                }
                break;
            case "line":
                Color lineColor = Color.BLACK;
                if (newColor != null){
                    lineColor = newColor;
                }
                newShape = new Line(topLeftX,topLeftY,startX, startY, endX, endY, lineColor, 2);
                break;
            case "cursor":
                newShape = null;
                break;
            default:
                break;
        }
        HighlightRectangle highlightRectangle= new HighlightRectangle();
        if (newShape != null) {
            shapes.add(newShape);
            if (newShape instanceof HighlightRectangle) {
                highlightRectangle= (HighlightRectangle) newShape;
                // Теперь выделим все фигуры, попадающие в область прямоугольника
                for (Shape shape : shapes) {
                    if (highlightRectangle.isShapeInside(shape)) {
                        highlightedShapes.add(shape);
                    }
                }

            }

        }
        if (highlightRectangle!=null) {
            shapes.remove(highlightRectangle);
        }

        redrawCanvas();
    }


    private void handleMousePressed(MouseEvent event) {
        startX = event.getX();
        startY = event.getY();

        boolean found = false;

        for (Shape shape : highlightedShapes) {
            if ((shape instanceof Changeable) && (controlIsPressed[0]==true) ){
                ((Changeable) shape).changeColor(newColor);
            }
        }

        if ("highlightRectangle".equals(chosenShape)) {
            handleHighlightRectangle(event);
        } else {
            handleOtherShapes(event);
        }

        if ((!found) && (controlIsPressed[0] == false)) {
            highlightedShapes.removeAll();
        }





        redrawCanvas();
    }

    // Метод для выделения области прямоугольником
    private void handleHighlightRectangle(MouseEvent event) {
        HighlightRectangle highlightRectangle = new HighlightRectangle(startX, startY, 0, 0, Color.TRANSPARENT, 2);
        for (int i = shapes.size() - 1; i >= 0; i--) {
            Shape shape = shapes.get(i);
            if (highlightRectangle.contains(startX, startY)) {
                if (highlightRectangle.isShapeInside(shape)) {
                    if (!highlightedShapes.contains(shape)) {
                        highlightedShapes.add(shape);
                    }
                }
            }
        }
    }

    private void handleOtherShapes(MouseEvent event) {
        boolean found = false;
        for (int i = shapes.size() - 1; i >= 0; i--) {
            Shape shape = shapes.get(i);
            if (shape.contains(startX, startY) && (controlIsPressed[0] == true)) {
                if (highlightedShapes.contains(shape)) {
                    highlightedShapes.remove(shape);
                } else {
                    highlightedShapes.add(shape);
                }
                found = true;
                break;
            }
        }
    }

    // Метод для обработки MouseDragged
    private void handleMouseDragged(MouseEvent event) {
        display(event);

        if (!highlightedShapes.isEmpty()) {
            double deltaX = event.getX() - startX;
            double deltaY = event.getY() - startY;

            for (Shape shape : highlightedShapes) {
                // Если курсор находится вблизи центра фигуры, то перемещаем фигуру
                if (isNearCenter(event, shape)) {
                    if (shape instanceof Moveable) {
                        // Проверка выхода за границы
                        double newX = shape.getX() + deltaX;
                        double newY = shape.getY() + deltaY;

                        // Ограничиваем движение внутри холста
                        double maxX = canvas.getWidth() - shape.getWidth();
                        double maxY = canvas.getHeight() - shape.getHeight();
                        newX = Math.max(0, Math.min(newX, maxX));
                        newY = Math.max(0, Math.min(newY, maxY));

                        ((Moveable) shape).move(newX - shape.getX(), newY - shape.getY());
                    }
                }
                // Если курсор находится вблизи краев или углов фигуры, то изменяем размеры
                else if (isNearEdge(event, shape) || isNearCorner(event, shape)) {
                    if (shape instanceof Changeable) {
                        ((Changeable) shape).resize(deltaX, deltaY);
                    }
                }
            }

            startX = event.getX();
            startY = event.getY();

            redrawCanvas();
        }
    }

    // Метод для обработки MouseReleased
    private void handleMouseReleased(MouseEvent event) {
        drawOnPane(event);

        if (!highlightedShapes.isEmpty()) {
            System.out.println("Перемещение завершено");
        }

        startX = -1;
        startY = -1;
    }


    private boolean isNearEdge(MouseEvent event, Shape shape) {
        double margin = 10;
        if (shape instanceof Triangle) {
            return isNearLine(event, ((Triangle) shape).getX1(), ((Triangle) shape).getY1(), ((Triangle) shape).getX2(), ((Triangle) shape).getY2(), margin) ||
                    isNearLine(event, ((Triangle) shape).getX2(), ((Triangle) shape).getY2(), ((Triangle) shape).getX3(), ((Triangle) shape).getY3(), margin) ||
                    isNearLine(event, ((Triangle) shape).getX3(), ((Triangle) shape).getY3(), ((Triangle) shape).getX1(), ((Triangle) shape).getY1(), margin);
        } else {
            // Определяем область вокруг углов и сторон
            return event.getX() >= shape.getX() - margin && event.getX() <= shape.getX() + shape.getWidth() + margin
                    && event.getY() >= shape.getY() - margin && event.getY() <= shape.getY() + shape.getHeight() + margin;
        }
    }

    private boolean isNearLine(MouseEvent event, double x1, double y1, double x2, double y2, double margin) {
        // Расстояние от точки до линии (линейное уравнение)
        double lineLength = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        double distance = Math.abs((x2 - x1) * (y1 - event.getY()) - (x1 - event.getX()) * (y2 - y1)) / lineLength;
        return distance <= margin;
    }

    private boolean isNearCorner(MouseEvent event, Shape shape) {
        double cornerSize = 10; // Размер области для захвата угла
        if (shape instanceof Triangle) {
            return (Math.abs(event.getX() - ((Triangle) shape).getX1()) <= cornerSize && Math.abs(event.getY() - ((Triangle) shape).getY1()) <= cornerSize) ||
                    (Math.abs(event.getX() - ((Triangle) shape).getX2()) <= cornerSize && Math.abs(event.getY() - ((Triangle) shape).getY2()) <= cornerSize) ||
                    (Math.abs(event.getX() - ((Triangle) shape).getX3()) <= cornerSize && Math.abs(event.getY() - ((Triangle) shape).getY3()) <= cornerSize);

        }
        return (event.getX() >= shape.getX() + shape.getWidth() - cornerSize && event.getX() <= shape.getX() + shape.getWidth() + cornerSize
                && event.getY() >= shape.getY() + shape.getHeight() - cornerSize && event.getY() <= shape.getY() + shape.getHeight() + cornerSize);
    }

    private boolean isNearCenter(MouseEvent event, Shape shape) {
        if (shape instanceof Triangle){
            double centerX = (((Triangle) shape).getX1() + ((Triangle) shape).getX2() +((Triangle) shape).getX3()) / 3;
            double centerY = (((Triangle) shape).getY1() + ((Triangle) shape).getY2() +((Triangle) shape).getY3()) / 3;
            double margin = 20; // Размер области для захвата центра

            return event.getX() >= centerX - margin && event.getX() <= centerX + margin
                    && event.getY() >= centerY - margin && event.getY() <= centerY + margin;
        } else {

            double centerX = shape.getX() + shape.getWidth() / 2;
            double centerY = shape.getY() + shape.getHeight() / 2;
            double margin = 20; // Размер области для захвата центра

            return event.getX() >= centerX - margin && event.getX() <= centerX + margin
                    && event.getY() >= centerY - margin && event.getY() <= centerY + margin;
        }
    }

    public static void main(String[] args) {
        launch();
    }
}