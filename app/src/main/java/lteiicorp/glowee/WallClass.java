package lteiicorp.glowee;

import android.graphics.Canvas;
import android.graphics.Paint;

public class WallClass {
    int WIDTH;
    int HEIGHT;

    private float WallSize;
    private Paint P1;

    WallClass(int screensizex, int screensizey) {
        WIDTH = screensizex;
        HEIGHT = screensizey;

        WallSize = WIDTH/20;
        P1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        P1.setARGB(255,0,50,100);
    }

    void Draw(Canvas c) {
        c.drawRect(0, 0, WallSize, HEIGHT, P1);
        c.drawRect(WIDTH-WallSize, 0, WIDTH, HEIGHT, P1);
        c.drawRect(0, HEIGHT-WallSize, WIDTH, HEIGHT, P1);
    }

    public float getWallSize() {
        return WallSize;
    }
}