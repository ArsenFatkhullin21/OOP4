package ru.arsen.oop4.myShape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Line  extends Shape {

    private double x2;
    private double y2;


    public Line(double x1, double y1, double x2, double y2,  Color color,int stroke) {
        super(x1, y1, color,stroke);
        this.x2 = x2;
        this.y2 = y2;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public double getY2() {
        return y2;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(color);  // Устанавливаем цвет линии

        gc.setLineWidth(strokeWeight);// Толщина линии
        gc.strokeLine(x, y, x2, y2);
    }

    @Override
    public boolean contains(double x, double y) {
        return false;
    }
}
