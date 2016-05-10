package lteiicorp.glowee;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Vibrator;

public class NotesClass {
    int WIDTH;
    int HEIGHT;
    private Context context;
    private Vibrator vibrator;

    private Drawable D[];
    private int Dint;

    public String Scene;

    NotesClass(int screensizex, int screensizey, Context context) {
        WIDTH = screensizex;
        HEIGHT = screensizey;
        this.context = context;
        this.vibrator = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);

        D = new Drawable[2];
        Dint = 0;
        InitD(context);

        Scene = "Notes";
    }

    public void Update() {

    }

    public void Render(Canvas c) {
        D[Dint].draw(c);
    }

    public void ScreenP(int mx, int my) {
        float scaley = HEIGHT/20;
        if (mx > WIDTH/4 && mx < 3*WIDTH/4) {
            if (Dint != 1 && my > 17*scaley && my < 19*scaley) {
                vibrator.vibrate(30);
                Dint = 1;
            }
        }
    }

    public void ScreenR(int mx, int my) {
        float scaley = HEIGHT/20;
        if (mx > WIDTH/4 && mx < 3*WIDTH/4) {
            if (Dint == 1 && my > 17*scaley && my < 19*scaley) {
                Scene = "Menu";
            }
        }
        Dint = 0;
    }

    //Gestion des jpgs

    private void InitD(Context context) {
        D[0] = context.getResources().getDrawable(R.drawable.notes0);
        D[1] = context.getResources().getDrawable(R.drawable.notes1);
        for (int i=0; i<D.length; i++) {
            D[i].setBounds(0,0,WIDTH,HEIGHT);
        }
    }
}