package ru.arsen.oop4.myShape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.arsen.oop4.interfaces.Changeable;
import ru.arsen.oop4.interfaces.Moveable;

public class Rectangle extends Shape implements Moveable, Changeable {


    public Rectangle(double x, double y, double width, double height,Color color,int stroke) {
        super(x, y,width,height, color,stroke);
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
        gc.fillRect(x, y, width, height);
        gc.strokeRect(x, y, width, height);

        // Рисуем маркеры для изменения размера

    }

    @Override
    public boolean contains(double x, double y) {
        return x >= getX() && x <= getX() + width && y >= getY() && y <= getY() + height;
    }

    @Override
    public void move(double deltaX, double deltaY) {
        this.x += deltaX;
        this.y += deltaY;
    }

    @Override
    public void changeColor(Color newColor) {
        super.color=newColor;
    }

    @Override
    public void resize(double newWidth, double newHeight) {
        this.width += newWidth;
        this.height += newHeight;
    }


}
