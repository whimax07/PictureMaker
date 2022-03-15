import java.awt.*;

public class DrawLines implements ImageManipulator {

    @Override
    public void manipulate(Graphics2D g2d, int width, int height) {
        Stroke stroke = g2d.getStroke();
        g2d.setColor(Color.BLUE);
        g2d.draw(new Rectangle(0, 0, width/2, height/2));
        g2d.setStroke(stroke);
    }

}
