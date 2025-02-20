package ru.arsen.oop4.myShape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Circle extends Ellipse {

    public Circle(double x, double y, double radius, Color color,int stroke) {
        super(x, y, radius, radius, color,stroke);
    }

    public double getRadius() {
        return radiusX;
    }

    public void setRadius(double radius) {
        this.radiusX = radius;
        this.radiusY = radius;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillOval(x , y ,  radiusX, radiusX);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(strokeWeight);
        gc.strokeOval(x , y , radiusX, radiusY);

    }
}