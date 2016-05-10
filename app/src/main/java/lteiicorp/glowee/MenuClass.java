package lteiicorp.glowee;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Vibrator;

public class MenuClass {
    private int WIDTH;
    private int HEIGHT;
    private Context context;
    private Vibrator vibrator;

    private Drawable[] D;
    private int Dint;

    public String Scene;

    MenuClass (int ssX, int ssY, Context context) {
        WIDTH = ssX;
        HEIGHT = ssY;
        this.context = context;
        this.vibrator = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);

        D = new Drawable[5];
        Dint = 0;
        InitD(context);

        Scene = "Menu";
    }

    public void Update() {

    }

    public void Draw(Canvas canvas) {
        D[Dint].draw(canvas);
    }

    public void ScreenP(int mx, int my) {
        float scaley = HEIGHT/20;
        if (mx > WIDTH/4 && mx < 3*WIDTH/4) {
            if (Dint != 1 && my > 8*scaley && my < 10*scaley) {
                vibrator.vibrate(30);
                Dint = 1;
            } else if (Dint != 2 && my > 11*scaley && my < 13*scaley) {
                vibrator.vibrate(30);
                Dint = 2;
            } else if (Dint != 3 && my > 14*scaley && my < 16*scaley) {
                vibrator.vibrate(30);
                Dint = 3;
            } else if (Dint != 4 && my > 17*scaley && my < 19*scaley) {
                vibrator.vibrate(30);
                Dint = 4;
            }
        }
    }

    public void ScreenR(int mx, int my) {
        float scaley = HEIGHT/20;
        if (mx > WIDTH/4 && mx < 3*WIDTH/4) {
            if (Dint == 1 && my > 8*scaley && my < 10*scaley) {
                Scene = "Game";
            } else if (Dint == 2 && my > 11*scaley && my < 13*scaley) {
                Scene = "Profile";
            } else if (Dint == 3 && my > 14*scaley && my < 16*scaley) {
                Scene = "Notes";
            } else if (Dint == 4 && my > 17*scaley && my < 19*scaley) {
                System.exit(0);
            }
        }
        Dint = 0;
    }

    private void InitD(Context context) {
        D[0] = context.getResources().getDrawable(R.drawable.menu0);
        D[1] = context.getResources().getDrawable(R.drawable.menu1);
        D[2] = context.getResources().getDrawable(R.drawable.menu2);
        D[3] = context.getResources().getDrawable(R.drawable.menu3);
        D[4] = context.getResources().getDrawable(R.drawable.menu4);
        for (int i=0; i<D.length; i++) {
            D[i].setBounds(0,0,WIDTH,HEIGHT);
        }
    }


}
