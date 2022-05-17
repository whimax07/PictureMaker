package utils;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class RayCsvReader {

    private static final int numberOfPoints = 4;

    private final ArrayList<Polygon> rays = new ArrayList<>();

    private File csv;

    private int width;

    private int height;

    private int lineCount = 0;



    public ArrayList<Polygon> readCsv(File file, int imageWidth, int imageHeight) {
        csv = file;
        width = imageWidth;
        height = imageHeight;
        rays.clear();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            read(bufferedReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rays;
    }

    private void read(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        while (line != null) {
            lineCount += 1;
            parsePoint(line);
            line = bufferedReader.readLine();
        }
    }

    private void parsePoint(String line) {
        int numOfInts = numberOfPoints * 2;

        String[] split = line.split(",");

        if (split.length != numOfInts) {
            throw new RuntimeException("Incorrect number of values in csv: \"" + csv.getName() + "\" line " +
                    lineCount + ".\n" + csv.getAbsolutePath());
        }

        int[] xs = new int[numberOfPoints];
        int[] ys = new int[numberOfPoints];

        for (int i = 0; i < numOfInts; i += 1) {
            String val = split[i];

            if (val.equals("MAX")) {
                if (i % 2 == 0) {
                    xs[i / 2] = width;
                } else {
                    ys[i / 2] = height;
                }
                continue;
            }

            double d = Double.parseDouble(val);
            if (i % 2 == 0) {
                xs[i / 2] = (int) d;
            } else {
                ys[i / 2] = (int) d;
            }
        }

        rays.add(new Polygon(xs, ys, numberOfPoints));
    }

}
