import java.io.File;

public class Main {

    public static void main(String[] args) {
        MakeImage makeImage = new MakeImage();
        makeImage.createImage("Test1", 400, 400);

        makeImage.use(new RainbowSquares(RainbowSquares.ColouringMode.Binary));
        makeImage.use(new AdjustArea(AdjustArea.AdjustMode.Dim, 0.7f, "resources/configs/BasicRayConfig.csv"));

        File newImg = makeImage.finaliseAndSaveImage();
        WindowOpen.openImage(newImg);
    }

}
