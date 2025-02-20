package ru.arsen.oop4.myShape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Square  extends  Rectangle {


    public Square(double x, double y, double side, Color color, int stroke) {
        super(x, y, side, side, color,stroke);
    }


    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(strokeWeight);
        gc.fillRect(x, y, width, height);
        gc.strokeRect(x, y, width, height);
    }
}