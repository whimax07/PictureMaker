import drawing.CascadingImage;
import utils.Vec2;

import java.awt.*;
import java.io.File;
import java.io.IOException;


public class Main {

    private static final int WIDTH = 1600;

    private static final int HEIGHT = 1600;



    public static void main(String[] args) {
        MakeImage makeImage = new MakeImage();
        makeImage.createImage("Test1", WIDTH, HEIGHT);

//        makeImage.use(new drawing.RainbowSquares(drawing.RainbowSquares.ColouringMode.Binary));
//        makeImage.use(new drawing.AdjustArea(drawing.AdjustArea.AdjustMode.Dim, 0.7f, "resources/configs/BasicRayConfig.csv"));

        final CascadingImage.ShadeCascade rainbowColourFromTime
                = new CascadingImage.ShadeCascade(new Color(220, 29, 29));

        makeImage.use(
                new CascadingImage(
                        new Point(320, 320),
                        new Vec2(20, -40),
                        new Vec2(0, 4),
                        new CascadingImage.BoundingBox(0, WIDTH, 0, HEIGHT),
                        (Point startPoint) -> new Rectangle(startPoint.x, startPoint.y, 40, 40),
                        rainbowColourFromTime::getNextColour
                )
        );

        File newImg = makeImage.finaliseAndSaveImage();
        try {
            Desktop.getDesktop().open(newImg);
        } catch (IOException e) {
            System.out.println("Could not open the default system viewer.");
            WindowOpen.openImage(newImg);
        }
    }

}
