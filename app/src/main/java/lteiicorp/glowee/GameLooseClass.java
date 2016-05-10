package lteiicorp.glowee;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Vibrator;

public class GameLooseClass {
    int WIDTH;
    int HEIGHT;
    Context context;
    private Vibrator vibrator;

    private Drawable D[];
    private int Dint;

    private long Millis0;
    private double X;
    private double Y;
    private double Rayon;
    private double RayonMax;

    private Paint P1;

    private int[] RGB0;
    private int[] RGB1;

    public boolean Restart;
    public boolean Menu;

    GameLooseClass(int screensizex, int screensizey, PopEnnemiClass ennemi, Context context) {
        WIDTH = screensizex;
        HEIGHT = screensizey;
        this.context = context;
        this.vibrator = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);

        X = ennemi.getX();
        Y = ennemi.getY();
        Rayon = ennemi.getRayon();

        double r1 = Math.sqrt( Math.pow(X, 2) + Math.pow(Y, 2) );
        double r2 = Math.sqrt( Math.pow(X-WIDTH, 2) + Math.pow(Y, 2) );
        double r3 = Math.sqrt( Math.pow(X, 2) + Math.pow(Y-HEIGHT, 2) );
        double r4 = Math.sqrt( Math.pow(X-WIDTH, 2) + Math.pow(Y-HEIGHT, 2) );
        RayonMax = Math.max(Math.max(r1,r2), Math.max(r3, r4));

        D = new Drawable[3];
        Dint = 0;
        InitD(context);

        P1 = new Paint(Paint.ANTI_ALIAS_FLAG);

        RGB0 = new int[3];
        RGB0[0] = 255;
        RGB0[1] = 0;
        RGB0[2] = 0;
        RGB1 = new int[3];
        RGB1[0] = 0;
        RGB1[1] = 20;
        RGB1[2] = 100;

        Millis0 = System.currentTimeMillis();
    }

    void Update(double deltaS) {
        if (Rayon < RayonMax) {
            Rayon = RayonMax*(System.currentTimeMillis()-Millis0)/2000;
        }
    }

    void Draw(Canvas c) {
        double x = Math.pow(Rayon/(RayonMax), 2);
        if (x < 1) {
            P1.setARGB(255, (int) (RGB1[0] * x + RGB0[0] * (1 - x)), (int) (RGB1[1] * x + RGB0[1] * (1 - x)), (int) (RGB1[2] * x + RGB0[2] * (1 - x)));
            c.drawCircle((float)X, (float)Y, (float)Rayon, P1);
        } else {
            D[Dint].draw(c);
        }
    }

    void ScreenP (int mx, int my) {
        if (Rayon > RayonMax) {
            if (mx > WIDTH / 4 && mx < 3 * WIDTH / 4) {
                if (my > 11 * HEIGHT / 20 && my < 13 * HEIGHT / 20) {
                    vibrator.vibrate(30);
                    Dint = 1;
                } else if (my > 14 * HEIGHT / 20 && my < 16 * HEIGHT / 20) {
                    vibrator.vibrate(30);
                    Dint = 2;
                }
            }
        }
    }

    void ScreenR (int mx, int my) {
        if (Rayon > RayonMax) {
            if (mx > WIDTH / 4 && mx < 3 * WIDTH / 4) {
                if (Dint == 1 && my > 11 * HEIGHT / 20 && my < 13 * HEIGHT / 20) {
                    Restart = true;
                } else if (Dint == 2 && my > 14 * HEIGHT / 20 && my < 16 * HEIGHT / 20) {
                    Menu = true;
                }
            }
            Dint = 0;
        }
    }

    //Gestion des jpgs

    private void InitD(Context context) {
        Drawable d;
        D[0] = context.getResources().getDrawable(R.drawable.gameloose0);
        D[1] = context.getResources().getDrawable(R.drawable.gameloose1);
        D[2] = context.getResources().getDrawable(R.drawable.gameloose2);
        for (int i=0; i<D.length; i++) {
            D[i].setBounds(0,0,WIDTH,HEIGHT);
        }
    }
}
