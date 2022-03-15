import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WindowOpen {

    public static void openImage(File imgFile) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame(imgFile.getName());
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

                BufferedImage image = null;
                try {
                    image = ImageIO.read(imgFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert image != null;
                ImageIcon icon = new ImageIcon(image);
                JLabel label = new JLabel();
                label.setIcon(icon);
                frame.getContentPane().add(label);

                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        MakeImage makeImage = new MakeImage();
        makeImage.createImage("Test1", 400, 400);
        makeImage.use(new DrawLines());
        File newImg = makeImage.finaliseAndSaveImage();
        openImage(newImg);
    }
}
