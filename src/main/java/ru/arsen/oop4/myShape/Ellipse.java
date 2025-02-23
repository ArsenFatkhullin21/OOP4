package ru.arsen.oop4.myShape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.arsen.oop4.interfaces.Changeable;
import ru.arsen.oop4.interfaces.Moveable;

public class Ellipse extends Shape  implements Moveable, Changeable {


    protected double radiusX;
    protected double radiusY;



    public Ellipse(double x, double y,double radiusX, double radiusY, Color color, int stroke) {
        super(x, y,radiusX,radiusY, color,stroke);

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

    @Override
    public void move(double deltaX, double deltaY) {
        this.x += deltaX;
        this.y += deltaY;
    }

    @Override
    public void changeColor(Color newColor) {
        super.color =newColor;
    }

    @Override
    public void resize(double newWidth, double newHeight) {
        super.width += newWidth;
        super.height += newHeight;
        this.radiusX += newWidth;
        this.radiusY += newHeight;
    }
}
