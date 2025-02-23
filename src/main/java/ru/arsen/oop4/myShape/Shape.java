package ru.arsen.oop4.myShape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeType;

public abstract class Shape {

    protected double x;
    protected double y;
    protected double width;
    protected double height;
    protected Color color;
    protected int strokeWeight;

    public Shape() {
    }

    public Shape(double x, double y, double width, double height, Color color, int strokeWeight) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.strokeWeight=strokeWeight;
        this.width=width;
        this.height=height;
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

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getStrokeWeight() {
        return strokeWeight;
    }

    public void setStrokeWeight(int strokeWeight) {
        this.strokeWeight = strokeWeight;
    }

    public abstract void draw(GraphicsContext gc);

    public abstract boolean contains(double x, double y);
}
