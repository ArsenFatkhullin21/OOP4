package ru.arsen.oop4.myShape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Rectangle extends Shape {

    protected double width;
    protected double height;

    public Rectangle(double x, double y, double width, double height,Color color,int stroke) {
        super(x, y, color,stroke);
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(strokeWeight);
        gc.fillRect(x,y, width, height);
        gc.strokeRect(x, y, width, height);
    }

    @Override
    public boolean contains(double x, double y) {
        return x >= getX() && x <= getX() + width && y >= getY() && y <= getY() + height;
    }
}
