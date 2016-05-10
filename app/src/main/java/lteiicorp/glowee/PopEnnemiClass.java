package lteiicorp.glowee;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class PopEnnemiClass {
    private int WIDTH;
    private int HEIGHT;

    private double X;
    private double Y;
    private double Rayon;
    private double PixelSeconde;
    private double Angle;
    private double VitX;
    private double VitY;

    private Paint P1;

    PopEnnemiClass(int screensizex, int screensizey, double rayon, double angle) {
        WIDTH = screensizex;
        HEIGHT = screensizey;
        PixelSeconde = (WIDTH/8)*(1+Math.random());

        Rayon = rayon;
        Angle = angle;

        WallClass wall = new WallClass(WIDTH, HEIGHT);
        float wsize = wall.getWallSize();
        double rand = Math.random();
        X = (wsize+Rayon)*rand+(HEIGHT-wsize-Rayon)*(1-rand);
        Y = -HEIGHT/2;
        VitX = PixelSeconde*Math.cos(Angle);
        VitY = PixelSeconde*Math.sin(Angle);

        P1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        P1.setARGB(255,255,0,0);

    }

    public void Tick(double deltaS) {
        X += deltaS*VitX;
        Y += deltaS*VitY;
        WallCollision();
    }

    public void Render(Canvas c) {
        float wSize = WIDTH/20;
        float x = (float) X;
        float y = (float) Y;
        float rayon = (float) Rayon;
        if(y+rayon>3*wSize) {
            c.drawCircle(x, y, rayon, P1);
        }
    }

    private void WallCollision() {
        if ( X-Rayon < WIDTH/20 ) {
            VitX=-VitX;
            X = (WIDTH/20)+Rayon;
        } else if ( X+Rayon > 19*WIDTH/20 ) {
            VitX=-VitX;
            X = (19*WIDTH/20)-Rayon;
        }
    }

    public boolean destroy() {
        return (Y > HEIGHT+Rayon || X<0 || X > WIDTH);
    }

    public void CameraMove(double deplaY) {
        Y += deplaY;
    }



    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public double getRayon() {
        return Rayon;
    }
}
