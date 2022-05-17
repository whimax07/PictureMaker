import java.awt.*;
import java.io.File;

public class Main {

    private static final int WIDTH = 400;

    private static final int HEIGHT = 400;



    public static void main(String[] args) {
        MakeImage makeImage = new MakeImage();
        makeImage.createImage("Test1", WIDTH, HEIGHT);

//        makeImage.use(new RainbowSquares(RainbowSquares.ColouringMode.Binary));
//        makeImage.use(new AdjustArea(AdjustArea.AdjustMode.Dim, 0.7f, "resources/configs/BasicRayConfig.csv"));

        final CascadingImage.RaindowColourFromTime rainbowColourFromTime
                = new CascadingImage.RaindowColourFromTime(new Color(232, 25, 25));

        makeImage.use(
                new CascadingImage(
                        new Point(80, 80),
                        new Vec2(5, -10),
                        new Vec2(0, 1),
                        new CascadingImage.BoundingBox(0, WIDTH, 0, HEIGHT),
                        (Point startPoint) -> new Rectangle(startPoint.x(), startPoint.y(), 10, 10),
                        rainbowColourFromTime::getNextColour
                )
        );

        File newImg = makeImage.finaliseAndSaveImage();
        WindowOpen.openImage(newImg);
    }

}
