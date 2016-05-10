package lteiicorp.glowee;

import android.graphics.Canvas;
import android.graphics.Paint;

public class GlowyShotClass {
    private int WiDTH;
    private int HEIGHT;

    //Stats
    private float LifeS;

    private double X;
    private double Y;
    private double CibleX;
    private double CibleY;
    private double PixelSeconde;
    private double VitX;
    private double VitY;
    private double Angle;
    private double Rayon0;
    private double Rayon;

    //Grahpics
    private Paint Pshot;
    private Paint Pcible;


    GlowyShotClass(int ssx, int ssy, double x, double y, double angle) {
        WiDTH=ssx;
        HEIGHT=ssy;

        LifeS = 0;
        PixelSeconde = 2*ssy/5;
        Angle = angle;
        VitX = (float) (PixelSeconde * Math.cos(Angle));
        VitY = (float) (PixelSeconde*Math.sin(Angle));
        X = x;
        Y = y;
        CibleX = X+VitX;
        CibleY = Y+VitY;

        Rayon0 = ssx/20;
        Rayon = Rayon0;

        Pshot = new Paint(Paint.ANTI_ALIAS_FLAG);
        Pshot.setARGB(255,0,150,150);
        Pcible = new Paint(Paint.ANTI_ALIAS_FLAG);
        Pcible.setARGB(255,0,100,100);
    }


    public void Update(double deltaS) {
        LifeS += deltaS;
        X += VitX*deltaS;
        Y += VitY*deltaS;
        Rayon = (float) (Rayon0* (3*Math.pow(LifeS, 2)-3*LifeS+1));
        //Collision
        WallCollision();
    }

    private void WallCollision() {
        if ( X-Rayon < WiDTH/20 ) {
            VitX = -VitX;
            X = (WiDTH/20)+Rayon;
        } else if ( X+Rayon > 19*WiDTH/20 ) {
            VitX = -VitX;
            X = (19*WiDTH/20)-Rayon;
        } else if ( Y+Rayon > HEIGHT-WiDTH/20 ){
            VitY = -VitY;
            Y = (HEIGHT-WiDTH/20)-Rayon;
        }
    }

    public void Draw(Canvas c) {
        c.drawCircle((float) CibleX, (float) CibleY, (float) Rayon0, Pcible);;
        c.drawCircle((float)X, (float)Y, (float)Rayon, Pshot);
    }


    public boolean ToucheCoin (PopCoinClass coin) {
        return (Math.sqrt(Math.pow(coin.getX()-X, 2)+Math.pow(coin.getY()-Y, 2)) < coin.getRayon()+Rayon);
    }

    public boolean destroy() {
        return this.LifeS>1;
    }

    public void CameraMove(double deplaY) {
        Y += deplaY;
        CibleY += deplaY;
    }


    public double getX() {return X;}
    public double getY() {return Y;}
    public double getVitX() {return VitX;}
    public double getVitY() {return VitY;}
    public double getAngle() {return Angle;}
    public float getLifeS() {return this.LifeS;}
}
