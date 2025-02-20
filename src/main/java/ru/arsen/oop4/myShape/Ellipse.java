package ru.arsen.oop4.myShape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ellipse extends Shape {


    protected double radiusX;
    protected double radiusY;



    public Ellipse(double x, double y,double radiusX, double radiusY, Color color, int stroke) {
        super(x, y, color,stroke);

        this.radiusX = radiusX;
        this.radiusY = radiusY;
    }


    public double getRadiusX() {
        return radiusX;
    }

    public void setRadiusX(double radiusX) {
        this.radiusX = radiusX;
    }

    public double getRadiusY() {
        return radiusY;
    }

    public void setRadiusY(double radiusY) {
        this.radiusY = radiusY;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillOval(x , y , radiusX, radiusY);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(strokeWeight);
        gc.strokeOval(x , y , radiusX, radiusY);

    }

    @Override
    public boolean contains(double x, double y) {
        double dx = x - super.getX();
        double dy = y - super.getY();
        return (dx * dx) / (radiusX * radiusX) + (dy * dy) / (radiusY * radiusY) <= 1;
    }

}
