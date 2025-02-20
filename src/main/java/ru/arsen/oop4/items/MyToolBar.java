package ru.arsen.oop4.items;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.paint.Color;
import ru.arsen.oop4.Paint;

public class MyToolBar {

    private final ToolBar toolBar;

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

        // Добавляем кнопки в панель инструментов
        toolBar.getItems().addAll(rectangleButton, circleButton, triangleButton, lineButton);
    }


}
