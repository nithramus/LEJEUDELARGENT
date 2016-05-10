package lteiicorp.glowee;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

public class BackGroundClass {
    BGstarClass[] BGstar;
    int WIDTH;
    int HEIGHT;
    private Context context;

    private Drawable D;
    //private double DY;

    BackGroundClass(int ssx, int ssy, Context context) {
        WIDTH = ssx;
        HEIGHT = ssy;
        this.context = context;

        InitD(context);

        InitStar();
    }

    void Update(double deltaS) {
        /*DY+=(HEIGHT/10)*deltaS;
        if (DY>0) {
            DY = -HEIGHT;
        }*/

        for (int i=0; i<BGstar.length; i++) {
            BGstar[i].Tick();
        }
    }

    void Draw(Canvas c) {
        D.draw(c);

        for (int i=0; i<BGstar.length; i++) {
            BGstar[i].Render(c);
        }
    }

    private void InitStar() {
        BGstar = new BGstarClass[10];
        BGstar[0] = new BGstarClass(WIDTH, HEIGHT, WIDTH/10, HEIGHT/10);
        BGstar[1] = new BGstarClass(WIDTH, HEIGHT, 7*WIDTH/10, HEIGHT/10);
        BGstar[2] = new BGstarClass(WIDTH, HEIGHT, 5*WIDTH/10, 3*HEIGHT/10);
        BGstar[3] = new BGstarClass(WIDTH, HEIGHT, 9*WIDTH/10, 3*HEIGHT/10);
        BGstar[4] = new BGstarClass(WIDTH, HEIGHT, 2*WIDTH/10, 5*HEIGHT/10);
        BGstar[5] = new BGstarClass(WIDTH, HEIGHT, 7*WIDTH/10, 6*HEIGHT/10);
        BGstar[6] = new BGstarClass(WIDTH, HEIGHT, 4*WIDTH/10, 8*HEIGHT/10);
        BGstar[7] = new BGstarClass(WIDTH, HEIGHT, 5*WIDTH/10, 8*HEIGHT/10);
        BGstar[8] = new BGstarClass(WIDTH, HEIGHT, WIDTH/10, 9*HEIGHT/10);
        BGstar[9] = new BGstarClass(WIDTH, HEIGHT, 8*WIDTH/10, 9*HEIGHT/10);
    }

    public void CameraMove(double deplaY) {
        /*DY+=deplaY;
        if (DY>0) {
            DY = -HEIGHT;
        }*/

        for (int i=0; i<BGstar.length; i++) {
            BGstar[i].CameraMove(deplaY);
        }
    }

    private void InitD(Context context) {
        /*DY=-HEIGHT;*/
        int wSize = (WIDTH/20);
        D = context.getResources().getDrawable(R.drawable.fgame0);
        D.setBounds(wSize, 3*wSize, WIDTH-wSize, HEIGHT-wSize);
    }
}