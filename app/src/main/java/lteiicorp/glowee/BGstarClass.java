package lteiicorp.glowee;

import android.graphics.Canvas;
import android.graphics.Paint;

public class BGstarClass {
    private int WIDTH;
    private int HEIGHT;

    private float X;
    private float Y;
    private float Rayon;
    private Paint P1;

    BGstarClass(int screensizex, int screensizey, float x, float y) {
        WIDTH = screensizex;
        HEIGHT = screensizey;

        X = x;
        Y = y;
        Rayon = WIDTH/80;

        P1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        P1.setARGB(100,255,255,255);
    }

    void Tick() {
        if (Y > HEIGHT) {
            Y = 0;
        } else if (Y < 0) {
            Y = HEIGHT;
        }
    }

    void Render(Canvas c) {
        c.drawCircle(X, Y, Rayon, P1);
    }

    public void CameraMove(double deplaY) {
        Y += deplaY;
    }
}
