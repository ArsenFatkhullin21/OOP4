package ru.arsen.oop4;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.Cursor;

// Abstract Shape class with basic movement functionality
abstract class ShapeBase {
    protected double orgSceneX, orgSceneY;
    protected double orgTranslateX, orgTranslateY;
    protected boolean dragging = false;

    // Method to enable dragging of any custom shape
    protected void enableDrag(Canvas canvas) {
        canvas.setOnMousePressed(mouseEvent -> {
            orgSceneX = mouseEvent.getSceneX();
            orgSceneY = mouseEvent.getSceneY();
            orgTranslateX = canvas.getTranslateX();
            orgTranslateY = canvas.getTranslateY();
            dragging = true;
        });

        canvas.setOnMouseDragged(mouseEvent -> {
            if (dragging) {
                double offsetX = mouseEvent.getSceneX() - orgSceneX;
                double offsetY = mouseEvent.getSceneY() - orgSceneY;
                canvas.setTranslateX(orgTranslateX + offsetX);
                canvas.setTranslateY(orgTranslateY + offsetY);
            }
        });

        canvas.setOnMouseReleased(mouseEvent -> dragging = false);
    }
}

// Rectangle shape using Canvas
class RectangleShape extends ShapeBase {
    private final Canvas canvas;
    private double width;
    private double height;
    private final Color color;
    private boolean resizingRight = false;
    private boolean resizingBottom = false;
    private boolean resizingLeft = false;
    private boolean resizingTop = false;
    private boolean selected = false;

    public RectangleShape(double x, double y, double width, double height, Color color) {
        this.width = width;
        this.height = height;
        this.color = color;

        canvas = new Canvas(width, height);
        canvas.setTranslateX(x);
        canvas.setTranslateY(y);

        drawRectangle();
        enableDrag(canvas); // Enable dragging functionality
        enableResize();     // Enable resizing functionality
    }

    private void drawRectangle() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(color);
        gc.fillRect(0, 0, width, height);

        if (selected) {
            gc.setStroke(Color.RED);
            gc.setLineWidth(2);
            gc.strokeRect(0, 0, width, height);
        }
    }

    private void enableResize() {
        canvas.setOnMouseMoved(this::updateCursor);
        canvas.setOnMousePressed(this::handleMousePressed);
        canvas.setOnMouseDragged(this::handleMouseDragged);
    }

    private void updateCursor(MouseEvent event) {
        double mouseX = event.getX();
        double mouseY = event.getY();

        if (Math.abs(mouseX - width) < 10 && Math.abs(mouseY - height) < 10) {
            canvas.setCursor(Cursor.SE_RESIZE);
        } else if (Math.abs(mouseX - width) < 10) {
            canvas.setCursor(Cursor.E_RESIZE);
        } else if (Math.abs(mouseY - height) < 10) {
            canvas.setCursor(Cursor.S_RESIZE);
        } else if (Math.abs(mouseX) < 10) {
            canvas.setCursor(Cursor.W_RESIZE);
        } else if (Math.abs(mouseY) < 10) {
            canvas.setCursor(Cursor.N_RESIZE);
        } else {
            canvas.setCursor(Cursor.DEFAULT);
        }
    }

    private void handleMousePressed(MouseEvent event) {
        orgSceneX = event.getSceneX();
        orgSceneY = event.getSceneY();

        double mouseX = event.getX();
        double mouseY = event.getY();

        // Reset all resizing flags
        resizingRight = resizingBottom = resizingLeft = resizingTop = false;

        // Check if mouse is near the edges for resizing
        resizingRight = Math.abs(mouseX - width) < 10;
        resizingBottom = Math.abs(mouseY - height) < 10;
        resizingLeft = Math.abs(mouseX) < 10;
        resizingTop = Math.abs(mouseY) < 10;

        if (!resizingRight && !resizingBottom && !resizingLeft && !resizingTop) {
            // If not near any edge, prepare for dragging and select the shape
            dragging = true;
            selected = true;
            drawRectangle();
            orgTranslateX = canvas.getTranslateX();
            orgTranslateY = canvas.getTranslateY();
        }
    }

    private void handleMouseDragged(MouseEvent event) {
        double offsetX = event.getSceneX() - orgSceneX;
        double offsetY = event.getSceneY() - orgSceneY;

        if (resizingRight) {
            width += offsetX;
        }
        if (resizingBottom) {
            height += offsetY;
        }
        if (resizingLeft) {
            width -= offsetX;
            canvas.setTranslateX(canvas.getTranslateX() + offsetX); // Move the shape left
        }
        if (resizingTop) {
            height -= offsetY;
            canvas.setTranslateY(canvas.getTranslateY() + offsetY); // Move the shape up
        }

        // Ensure width and height remain positive
        width = Math.max(10, width);
        height = Math.max(10, height);

        // Update canvas size and redraw
        canvas.setWidth(width);
        canvas.setHeight(height);
        drawRectangle();

        // Update original scene positions for the next drag event
        orgSceneX = event.getSceneX();
        orgSceneY = event.getSceneY();
    }

    public Canvas getCanvas() {
        return canvas;
    }
}

// Main JavaFX Application
public class demo extends Application {
    @Override
    public void start(Stage primaryStage) {
        Pane pane = new Pane();

        // Create a rectangle
        RectangleShape rectangleShape = new RectangleShape(50, 50, 100, 100, Color.BLUE);
        pane.getChildren().add(rectangleShape.getCanvas());

        // Set up the scene and stage
        Scene scene = new Scene(pane, 400, 400);
        primaryStage.setTitle("Draggable and Resizable Rectangle Shape");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
