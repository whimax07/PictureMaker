import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class MakeImage {

    private String name = "test";

    private final String format = "png";

    private BufferedImage image;

    private Graphics2D g2d;



    public void createImage(String name, int width, int height) {
        this.name = name;
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        g2d = image.createGraphics();
    }

    public void finaliseImage() {
        g2d.dispose();
    }

    public File saveImage() {
        Path newImgPath = Path.of("./resources/", name + "." + format);
        File imgFile = new File(newImgPath.toUri());
        try {
            ImageIO.write(image, format, imgFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imgFile;
    }

    public File finaliseAndSaveImage() {
        finaliseImage();
        return saveImage();
    }

    public void use(ImageManipulator imageManipulator) {
        imageManipulator.manipulate(g2d, image.getWidth(), image.getHeight());
    }



    public Graphics2D getGraphics() {
        return g2d;
    }

}
