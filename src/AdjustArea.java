import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class AdjustArea implements ImageManipulator {

    private final AdjustMode mode;

    private final float adjustment;

    private final File rayFile;

    private ArrayList<Polygon> rays;

    private Graphics2D g2d;



    public AdjustArea(AdjustMode mode, float adjustment, String rayCsv) {
        this.mode = mode;
        this.adjustment = adjustment;
        this.rayFile = new File(rayCsv);
        if (!rayFile.isFile()) {
            throw new RuntimeException("Not a file, path: \"" + rayFile.getAbsolutePath() + "\".");
        }
    }



    @Override
    public void manipulate(Graphics2D g2d, int width, int height) {
        this.g2d = g2d;

        rays = new RayCsvReader().readCsv(rayFile, width, height);
        addRays();
    }

    private void addRays() {
        g2d.setColor(new Color(0, 0, 0, (int) (255 * adjustment)));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (Polygon ray : rays) {
            g2d.fillPolygon(ray);
        }
    }



    enum AdjustMode {
        Dim
    }

}
