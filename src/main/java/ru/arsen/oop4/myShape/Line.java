package ru.arsen.oop4.myShape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.arsen.oop4.interfaces.Changeable;
import ru.arsen.oop4.interfaces.Moveable;

public class Line  extends Shape implements Moveable, Changeable {

    private double x1;
    private double y1;
    private double x2;
    private double y2;


    public Line(double x, double y,double x1, double y1, double x2, double y2,  Color color,int stroke) {
        super(x, y,x2-x1,y2-y1, color,stroke);
        this.x1 = x1;
        this.y1 = y1;
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
        gc.strokeLine(x1, y1, x2, y2);
    }

    @Override
    public boolean contains(double px, double py) {
        // Проверим, находится ли точка на линии (в пределах ограничений от x до x2 и от y до y2)
        double minX = Math.min(x1, x2);
        double maxX = Math.max(x1, x2);
        double minY = Math.min(y1, y2);
        double maxY = Math.max(y1, y2);

        if (px < minX || px > maxX || py < minY || py > maxY) {
            return false;  // Точка вне границ линии
        }

        // Используем формулу для расстояния от точки до прямой
        double distance = Math.abs((y2 - y1) * px - (x2 - x1) * py + x2 * y1 - y2 * x1) /
                Math.sqrt(Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2));
        return distance < 5;  // Если расстояние от точки до линии меньше 2 пикселей, точка считается на линии
    }

    @Override
    public void move(double deltaX, double deltaY) {
        super.x += deltaX;
        super.y += deltaY;
        x1 += deltaX;
        y1 += deltaY;
        x2 += deltaX;
        y2 += deltaY;
    }

    @Override
    public void changeColor(Color newColor) {
        super.color=newColor;
    }

    @Override
    public void resize(double newWidth, double newHeight) {
        this.x2 += newWidth;
        this.y2 += newHeight;
        super.width += newWidth;
        super.height += newHeight;
    }
}
