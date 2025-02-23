package ru.arsen.oop4.items;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ru.arsen.oop4.Paint;

public class MyToolBar {

    private final ToolBar toolBar;

    public static Rectangle currentColorRectangle = new Rectangle(33,33, Color.TRANSPARENT);


    public MyToolBar(ToolBar toolBar) {

        this.toolBar = toolBar;
    }

    private Canvas createRectangleGraphic(double width, double height) {
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.strokeRect(0, 0, width, height);
        return canvas;
    }

    private Canvas createCircleGraphic(double width, double height) {
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.strokeOval(0, 0, width, height);
        return canvas;
    }

    private Canvas createTriangleGraphic(double width, double height) {
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);

        // Определим координаты трех вершин треугольника
        double[] xPoints = {width / 2, 0, width};
        double[] yPoints = {0, height, height};

        // Рисуем треугольник
        gc.strokePolygon(xPoints, yPoints, 3);

        return canvas;
    }

    private Canvas createLineGraphic(double width, double height) {
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);

        gc.strokeLine( width, height,0,0);

        return canvas;
    }

    private Canvas createHighlightRectangleGraphic(double width, double height) {

        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setLineDashes(3, 2);  // 10 - длина отрезка, 5 - пробел между отрезками


        gc.setStroke(Color.BLACK);

        gc.strokeRect(0, 0, width, height);
        return canvas;
    }

    private Canvas createBotleGraphic(double width, double height) {
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        gc.setFill(Color.BLACK);

        double[] xPoints = {2, 2,  4, 3, 3, 7, 7, 6, 8,8};
        double[] yPoints = {10, 5, 2, 2, 1, 1, 2, 2, 4,10};

        gc.fillPolygon(xPoints, yPoints, 10);


        return canvas;
    }





    public void createToolBar() {
        Button rectangleButton = new Button();
        rectangleButton.setGraphic(createRectangleGraphic(10, 10));
        rectangleButton.setOnAction(actionEvent -> {
            Paint.chosenShape = "rectangle";
        });

        Button circleButton = new Button();
        circleButton.setGraphic(createCircleGraphic(10, 10));
        circleButton.setOnAction(actionEvent -> {
            Paint.chosenShape = "circle";
        });

        Button triangleButton = new Button();
        triangleButton.setGraphic(createTriangleGraphic(10, 10));
        triangleButton.setOnAction(actionEvent -> {
            Paint.chosenShape = "triangle";
        });

        Button lineButton = new Button();
        lineButton.setGraphic(createLineGraphic(10, 10));
        lineButton.setOnAction(actionEvent -> {
            Paint.chosenShape = "line";
        });



        Button cursorButton = new Button("K");
        cursorButton.setOnAction(actionEvent -> {
            Paint.chosenShape = "cursor";
        });

        Button highlightRectangleButton = new Button();
        highlightRectangleButton.setGraphic(createHighlightRectangleGraphic(10, 10));
        highlightRectangleButton.setOnAction(actionEvent -> {
            Paint.chosenShape = "highlightRectangle";
        });


        ToggleButton changeColorButton = new ToggleButton();
        changeColorButton.setGraphic(createBotleGraphic(10, 10));

        changeColorButton.setOnAction(  actionEvent -> {
            if (changeColorButton.isSelected()) {
                Paint.changeColorButton[0] = false;
            } else {
                Paint.changeColorButton[0] = true;
            }
        });




        currentColorRectangle.setStroke(Color.BLACK);


        Rectangle redButton = ColorButtonFactory.createColorButton(Color.RED);
        Rectangle transparentButton = ColorButtonFactory.createColorButton(Color.TRANSPARENT);
        Rectangle greenButton = ColorButtonFactory.createColorButton(Color.GREEN);
        Rectangle blueButton = ColorButtonFactory.createColorButton(Color.BLUE);
        Rectangle yellowButton = ColorButtonFactory.createColorButton(Color.YELLOW);
        Rectangle pinkButton = ColorButtonFactory.createColorButton(Color.PINK);
        Rectangle grayButton = ColorButtonFactory.createColorButton(Color.GRAY);
        Rectangle cyanButton = ColorButtonFactory.createColorButton(Color.CYAN);
        Rectangle brownButton = ColorButtonFactory.createColorButton(Color.BROWN);
        Rectangle violetButton = ColorButtonFactory.createColorButton(Color.VIOLET);
        Rectangle darkBlueButton = ColorButtonFactory.createColorButton(Color.DARKBLUE);
        Rectangle lightGreenButton = ColorButtonFactory.createColorButton(Color.LIGHTGREEN);







        GridPane gridPane = new GridPane();
        gridPane.setHgap(3);
        gridPane.setVgap(3);

        gridPane.add(greenButton, 0, 0);
        gridPane.add(transparentButton, 0, 1);
        gridPane.add(redButton, 1, 0);
        gridPane.add(blueButton, 1, 1);
        gridPane.add(violetButton, 2, 0);
        gridPane.add(yellowButton, 2,1);
        gridPane.add(cyanButton, 3, 0);
        gridPane.add(pinkButton, 3,1);
        gridPane.add(grayButton, 4, 0);
        gridPane.add(brownButton, 4,1);
        gridPane.add(darkBlueButton, 5, 0);
        gridPane.add(lightGreenButton, 5,1);





        // Добавляем кнопки в панель инструментов
        toolBar.getItems().addAll(cursorButton,highlightRectangleButton,
                rectangleButton, circleButton, triangleButton, lineButton, currentColorRectangle,
                gridPane);
    }






}


