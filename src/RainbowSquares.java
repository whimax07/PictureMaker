import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Float.max;
import static java.lang.Float.min;

public class RainbowSquares implements ImageManipulator {

    private final ColouringMode colouringMode;

    private int maxSize = 50;

    private int minSize = 50;

    private Graphics2D g2d;

    private int width;

    private int height;

    private final ArrayList<Point> startPoints = new ArrayList<>();

    private final HashSet<Point> filledArea = new HashSet<>();

    private Point startPoint;

    private int edgeLength;



    public RainbowSquares(ColouringMode colouringMode) {
        this.colouringMode = colouringMode;
    }



    @Override
    public void manipulate(Graphics2D g2d, int width, int height) {
        this.g2d = g2d;
        this.width = width;
        this.height = height;

        startPoints.add(new Point(0, 0));
        addSquares();
    }

    private void addSquares() {
        while (!startPoints.isEmpty()) {
            startPoint = startPoints.remove(new Random().nextInt(startPoints.size()));
            edgeLength = ThreadLocalRandom.current().nextInt(minSize, maxSize + 1);
            addSquare();
            updateBoundary();
            addNewStartPoints();
        }
    }

    private void addSquare() {
        g2d.setColor(getColour());
        g2d.fillRect(startPoint.x, startPoint.y, edgeLength, edgeLength);
    }

    private Color getColour() {
        float hue = 0.8f;
        float stauration = 1f;
        float brightness = 1f;
        switch (colouringMode) {
            case Arc -> hue = (startPoint.x * startPoint.x + startPoint.y * startPoint.y) / (float) (width * width + height * height);
            case Diagonal -> hue = (startPoint.x + startPoint.y) / (float) (width + height);
            case Horizontal -> hue = startPoint.y / (float) height;
            case Vertical -> hue = startPoint.x / (float) width;
            case Binary -> {
                int coin = ThreadLocalRandom.current().nextInt(0, 2);
                if (coin == 0) {
                    hue = 0.322f;
                    stauration = 0.12f;
                    brightness = 0.89f;
                } else {
                    hue = 0.795f;
                    stauration = 0.73f;
                    brightness = 0.25f;
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + colouringMode);
        }

        hue = min(1f, hue);
        hue = max(0f, hue);

        return new Color(Color.HSBtoRGB(hue, stauration, brightness));
    }

    private void updateBoundary() {
        ArrayList<Point> points = new ArrayList<>(edgeLength * edgeLength);
        for (int i = startPoint.x; i < startPoint.x + edgeLength; i++) {
            for (int j = startPoint.y; j < startPoint.y + edgeLength; j++) {
                points.add(new Point(i, j));
            }
        }
        filledArea.addAll(points);
    }

    private void addNewStartPoints() {
        if (startPoint.x < width) {
            checkNewStartPointX();
        }

        if (startPoint.y < height) {
            checkNewStartPointY();
        }
    }

    private void checkNewStartPointX() {
        int newX = startPoint.x + edgeLength;

        for (int i = newX; i < newX + maxSize; i++) {
            for (int j = startPoint.y; j < startPoint.y + maxSize; j++) {
                if (!filledArea.contains(new Point(i, j))) {
                    startPoints.add(new Point(newX, startPoint.y));
                    return;
                }
            }
        }
    }

    private void checkNewStartPointY() {
        int newY = startPoint.y + edgeLength;

        for (int i = startPoint.x; i < startPoint.x + maxSize; i++) {
            for (int j = newY; j < newY + maxSize; j++) {
                if (!filledArea.contains(new Point(i, j))) {
                    startPoints.add(new Point(startPoint.x, newY));
                    return;
                }
            }
        }
    }



    private record Point(int x, int y) { }



    public enum ColouringMode {
        Vertical,
        Horizontal,
        Diagonal,
        Arc,
        Binary
    }

}
