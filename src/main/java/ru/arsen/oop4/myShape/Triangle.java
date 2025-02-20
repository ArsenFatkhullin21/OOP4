package ru.arsen.oop4.myShape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Triangle extends Shape {

    private double x2;
    private double y2;
    private double x3;
    private double y3;




    public Triangle(double x1, double y1, double x2, double y2, double x3, double y3, Color color,int stroke) {
        super(x1, y1, color,stroke);
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
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

    public double getX3() {
        return x3;
    }

    public void setX3(double x3) {
        this.x3 = x3;
    }

    public double getY3() {
        return y3;
    }

    public void setY3(double y3) {
        this.y3 = y3;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(strokeWeight);

        double[] xPoints = {x,x2,x3};
        double[] yPoints = {y,y2,y3};
        gc.fillPolygon(xPoints, yPoints, 3);

        gc.strokePolygon(xPoints, yPoints, 3);

    }
}
