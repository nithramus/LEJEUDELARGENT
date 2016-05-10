package lteiicorp.glowee;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;


public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;

    int WIDTH;
    int HEIGHT;

    Context context;

    public GamePanel(Context context) {
        super(context);
        this.context = context;
        getHolder().addCallback(this); //add the callback to the surfaceholder to intercept events
        thread = new MainThread(getHolder(), this);
        setFocusable(true); //make gamePanel focusable so it can handle events

        Init();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while(retry) {
            try{thread.setRunning(false);
                thread.join();
            } catch(InterruptedException e){
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //we can safely start the game loop
        thread.setRunning(true);
        thread.start();
    }


    private BOSSClass BOSS;
    private void Init() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        WIDTH = size.x;
        HEIGHT = size.y;

        BOSS = new BOSSClass(WIDTH, HEIGHT, getContext());
    }


    public void update(double deltaS) {
        BOSS.Update(deltaS);
    }

    @Override
    public void draw(Canvas canvas) {
        BOSS.Draw(canvas);
    }

    private long LastPressM = 0;
    private long PressCD = 100;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int px = Math.round(event.getX());
        int py = Math.round(event.getY());

        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - LastPressM > PressCD) {
                BOSS.ScreenP(px, py);
                LastPressM = System.currentTimeMillis();
            }
            return true;
        }
        if (action == MotionEvent.ACTION_UP) {
            BOSS.ScreenR(px, py);
            return true;
        }
        return false;
        //return super.onTouchEvent(event);
    }
}