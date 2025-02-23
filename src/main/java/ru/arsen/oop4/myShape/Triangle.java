package ru.arsen.oop4.myShape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.arsen.oop4.interfaces.Changeable;
import ru.arsen.oop4.interfaces.Moveable;

public class Triangle extends Shape implements Moveable,Changeable {


    private double x1;
    private double y1;
    private double x2;
    private double y2;
    private double x3;
    private double y3;






    public Triangle(double x, double y, double x1, double y1,double x2, double y2, double x3, double y3, Color color,int stroke) {
        super(x, y, Math.abs(x3-x1),Math.abs(y2-y1), color,stroke);
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
    }

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getY1() {
        return y1;
    }

    public void setY1(double y1) {
        this.y1 = y1;
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

        double[] xPoints = {x1,x2,x3};
        double[] yPoints = {y1,y2,y3};
        gc.fillPolygon(xPoints, yPoints, 3);

        gc.strokePolygon(xPoints, yPoints, 3);

    }

    private double area(double x1, double y1, double x2, double y2, double x3, double y3) {
        return Math.abs((x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)) / 2.0);
    }



    // Метод для проверки, находится ли точка внутри треугольника
    @Override
    public boolean contains(double _x, double _y) {
        // Вычисляем площади треугольников, образованных точкой (_x, _y) и вершинами исходного треугольника
        double area1 = area(_x, _y, x2, y2, x3, y3);
        double area2 = area(x1, y1, _x, _y, x3, y3);
        double area3 = area(x1, y1, x2, y2, _x, _y);

        // Сравниваем суммы площадей с площадью самого треугольника
        double totalArea = area(x1, y1, x2, y2, x3, y3);
        return area1 + area2 + area3 == totalArea;
    }


    @Override
    public void move(double deltaX, double deltaY) {
        this.x1 += deltaX;
        this.y1 += deltaY;
        this.x2 += deltaX;
        this.y2 += deltaY;
        this.x3 += deltaX;
        this.y3 += deltaY;
        this.width = Math.abs(x3 - x1);
        this.height = Math.abs(y2 - y1);

        super.x += deltaX;
        super.y += deltaY;
    }

    @Override
    public void changeColor(Color color) {
        super.color = color;
    }

    @Override
    public void resize(double newWidth, double newHeight) {
        super.width += newWidth;
        super.height += newHeight;

        this.x2 += newWidth/2;
        this.x3 += newWidth;

        this.y1 += newHeight;
        this.y3 += newHeight;



    }
}
