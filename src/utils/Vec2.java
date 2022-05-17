package utils;

import java.awt.*;

public record Vec2(double x, double y) {

    public Point toPoint() {
        return new Point((int) Math.round(x), (int) Math.round(y));
    }

    public Vec2 add(Vec2 input) {
        return new Vec2(x + input.x(), y + input.y());
    }

}
