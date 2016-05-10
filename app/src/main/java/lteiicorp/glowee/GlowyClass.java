package lteiicorp.glowee;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RotateDrawable;
import android.os.Vibrator;

public class GlowyClass {
    private int WIDTH;
    private int HEIGHT;
    private Context context;
    private Vibrator vibrator;

    //Stats
    public GlowyShotClass Shot;
    private double X;
    private double Y;
    private double VitX;
    private double VitY;
    private double Rayon;
    private double Angle;

    //Graphics
    private Paint Pglowy;
    //private Drawable Dglowee0;


    public GlowyClass(int ssx, int ssy, Context context) {
        WIDTH = ssx;
        HEIGHT = ssy;
        this.context = context;
        this.vibrator = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);

        X = ssx/2;
        Y = 80*ssy/100;
        VitX =0;
        VitY =0;
        Rayon = ssx/20;
        Angle = 3*Math.PI/4;

        Pglowy = new Paint(Paint.ANTI_ALIAS_FLAG);
        Pglowy.setARGB(255,0,200,0);
        //InitD(context);
        //InitRotD(context, 180);
    }


    public void Update(double deltaS) {
        //Shot
        if (Shot != null) {
            Shot.Update(deltaS);
            //Destruction
            if (Shot != null && Shot.destroy()) {
                TpToShot();
            }
        }
        //Move
        VitX -= 0.1*VitX*deltaS;
        VitY -= 0.1*VitY*deltaS;
        X += VitX*deltaS;
        Y += VitY*deltaS;
        //Collision
        WallCollision();
    }

    private void WallCollision() {
        float wSize = WIDTH/20;
        if ( X-Rayon < wSize ) {
            VitX=-VitX;
            X =wSize+Rayon;
        } else if ( X+Rayon > WIDTH-wSize ) {
            VitX=-VitX;
            X =(WIDTH-wSize)-Rayon;
        } else if ( Y+Rayon > HEIGHT-wSize ){
            VitY=-VitY;
            Y =(HEIGHT-wSize)-Rayon;
        }
    }

    public void Draw(Canvas c) {
        if (Shot != null) {
            Shot.Draw(c);
        }
        c.drawCircle((float)X, (float)Y, (float)Rayon, Pglowy);
        //Dglowee0.setBounds((int) (X - Rayon), (int) (Y - Rayon), (int) (X + Rayon), (int) (Y + Rayon));
        //Dglowee0.draw(c);
    }

    public void ScreenP(int mx, int my) {
        vibrator.vibrate(15);
        if (this.Shot == null) {
            //Calcul Vect
            double vectx = mx-X;
            double vecty = my-Y;
            double vectl = Math.sqrt(Math.pow(vectx,2)+Math.pow(vecty,2));
            //Calcul Angle
            double angle = Math.acos(vectx/vectl);
            if (Math.asin(vecty/vectl) < 0) {
                angle = 2*Math.PI-angle;
            }
            Shot = new GlowyShotClass(this.WIDTH, this.HEIGHT,X,Y,angle);
        } else  { //if (sqrt(pow(mx-Shot.X,2)+pow(my-Shot.Y,2)) < Shot.Rayon*10) {
            TpToShot();
        }
    }


    public  void TpToShot() {
        //InitRotD(context, (float)(180*Shot.getAngle()/Math.PI)-90 );
        X = Shot.getX();
        Y = Shot.getY();
        VitX = 4*Shot.getVitX()/5;
        VitY = 4*Shot.getVitY()/5;
        Shot = null;
    }

    public boolean ToucheEnnemi(PopEnnemiClass ennemi) {
        return (Math.sqrt(Math.pow(ennemi.getX()-this.X, 2)+Math.pow(ennemi.getY()-this.Y, 2)) < ennemi.getRayon()+this.Rayon);
    }

    public boolean ToucheCoin (PopCoinClass coin) {
        return (Math.sqrt(Math.pow(coin.getX()-this.X, 2)+Math.pow(coin.getY()-this.Y, 2)) < coin.getRayon()+this.Rayon);
    }

    public void CameraMove(double deplaY) {
        Y += deplaY;
        if (Shot != null) {
            Shot.CameraMove(deplaY);
        }
    }


    /*private void InitD(Context context) {
        Dglowee0 = context.getResources().getDrawable(R.drawable.glowee0);
    }

    private void InitRotD(Context context, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        Drawable drawable = context.getResources().getDrawable(R.drawable.glowee0);
        Bitmap bitmap = drawableToBitmap(drawable);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        Dglowee0 = new BitmapDrawable(rotatedBitmap);
    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }*/

    public double getX() {
        return X;
    }
    public double getY() {
        return Y;
    }

}