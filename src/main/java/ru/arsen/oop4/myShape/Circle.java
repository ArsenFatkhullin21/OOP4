package ru.arsen.oop4.myShape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.arsen.oop4.interfaces.Moveable;

public class Circle extends Ellipse implements Moveable {

    private double radius;

    public Circle(double x, double y, double radius, Color color,int stroke) {
        super(x, y, radius, radius, color,stroke);
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillOval(x , y ,  radiusX, radiusX);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(strokeWeight);
        gc.strokeOval(x , y , radiusX, radiusY);

    }

    @Override
    public boolean contains(double x, double y) {
        double dx = x - super.getX();
        double dy = y - super.getY();
        return dx * dx + dy * dy <= radius * radius;
    }


}