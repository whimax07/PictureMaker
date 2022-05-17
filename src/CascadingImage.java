import java.awt.*;
import java.util.function.Function;

public class CascadingImage implements ImageManipulator {

    private Vec2 currentPosition;

    private Vec2 velocity;

    private final Vec2 acceleration;

    private final BoundingBox endBounds;

    private final Function<Point, Shape> shapeMaker;

    private final Function<CascadingImage, Color> colourMaker;



    public CascadingImage(Point startPoint, Vec2 velocity, Vec2 acceleration, BoundingBox endBounds,
                          Function<Point, Shape> shapeMaker, Function<CascadingImage, Color> colourMaker) {
        currentPosition = new Vec2(startPoint.x(), startPoint.y());
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.endBounds = endBounds;
        this.shapeMaker = shapeMaker;
        this.colourMaker = colourMaker;
    }



    @Override
    public void manipulate(Graphics2D g2d, int width, int height) {
        Color startColour = g2d.getColor();
        while (inBounds()) {
            drawShape(g2d);
            step();
        }
        g2d.setColor(startColour);
    }

    private boolean inBounds() {
        return currentPosition.x() > endBounds.lowWidthBound()
                && currentPosition.x() < endBounds.highWidthBound()
                && currentPosition.y() > endBounds.lowHeightBound()
                && currentPosition.y() < endBounds.highHeightBound();
    }

    private void drawShape(Graphics2D g2d) {
        Point currentPoint = currentPosition.toPoint();

        g2d.setColor(colourMaker.apply(this));
        g2d.draw(shapeMaker.apply(currentPoint));
    }

    private void step() {
        currentPosition = currentPosition.add(velocity);
        velocity = velocity.add(acceleration);
    }



    public record BoundingBox(int lowWidthBound, int highWidthBound, int lowHeightBound, int highHeightBound) { }

    public static class RaindowColourFromTime {

        private Color color;



        public RaindowColourFromTime(Color startColor) {
            this.color = startColor;
        }



        public Color getNextColour(CascadingImage cascadingImage) {
            float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);

            color = Color.getHSBColor((hsb[0] + 0.02f) % 1.0f, hsb[1], hsb[2]);
            return color;
        }

    }

}
