package lteiicorp.glowee;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class PopCoinClass {
    private int WIDTH;
    private int HEIGHT;

    private double X;
    private double Y;
    private double Rayon;

    private Paint Pext;
    private Paint Pint;
    private Paint Ptxt;

    PopCoinClass(int screensizex, int screensizey, float x, float y) {
        WIDTH = screensizex;
        HEIGHT = screensizey;
        Rayon = WIDTH/20;

        X = x;
        Y = y;

        Pext = new Paint(Paint.ANTI_ALIAS_FLAG);
        Pext.setARGB(255,255,200,0);
        Pint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Pint.setARGB(255,255,255,0);
        Ptxt = new Paint(Paint.ANTI_ALIAS_FLAG);
        Ptxt.setARGB(255,0,0,0);
        Ptxt.setTextAlign(Paint.Align.CENTER);
        Ptxt.setTextSize((float)Rayon);
    }

    public void Tick(double deltaS) {
        WallCollision();
    }

    public void Render(Canvas c) {
        c.drawCircle((float) X, (float) Y, (float) Rayon, Pext);
        c.drawCircle((float) X, (float) Y, (float) (Rayon*0.7), Pint);
        c.drawText("$", (float) X, (float) (Y+Rayon/3), Ptxt);
    }

    private void WallCollision() {
        if ( X-Rayon < WIDTH/20 ) {
            X = (WIDTH/20)+Rayon;
        } else if ( X+Rayon > 19*WIDTH/20 ) {
            X = (19*WIDTH/20)-Rayon;
        }
    }


    public boolean destroy() {
        return (Y>HEIGHT || X<0 || X > WIDTH);
    }

    public void CameraMove(double deplaY) {
        Y += deplaY;
    }



    public double getY() {
        return Y;
    }

    public double getX() {
        return X;
    }


    public double getRayon() {
        return Rayon;
    }

}
