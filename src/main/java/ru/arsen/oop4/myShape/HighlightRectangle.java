package ru.arsen.oop4.myShape;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class HighlightRectangle extends Shape {


    public HighlightRectangle() {
        super();
    }

    public HighlightRectangle(double x, double y, double width, double height, Color color, int strokeWeight) {
        super(x, y, width, height, color, strokeWeight);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setLineDashes(10, 5);


        gc.setStroke(Color.LIGHTBLUE);
        gc.setLineWidth(2);


        gc.strokeRect(x, y, width, height);
        gc.setLineDashes(10,0);

        double cornerSize = 10;

         // Нижний правый угол
    }

    @Override
    public boolean contains(double px, double py) {
        return px >= x && px <= x + width && py >= y && py <= y + height;
    }

    public boolean isShapeInside(Shape shape) {
        return shape.getX() >= x && shape.getX() + shape.getWidth() <= x + width &&
                shape.getY() >= y && shape.getY() + shape.getHeight() <= y + height;
    }
}
