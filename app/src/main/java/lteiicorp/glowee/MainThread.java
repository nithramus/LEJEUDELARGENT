package lteiicorp.glowee;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
    private final SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    public static Canvas canvas;
    public Paint paint;

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }


    /*@Override
    public void run() {
        final long FPS = 30;
        final long frameMS = 1000/FPS;

        long lastTime = System.nanoTime();
        long newTime;
        long deltaTime;

        int framesC = 0;
        long timer = System.currentTimeMillis();

        long timeSpentMS;
        long sleepMS;

        System.out.println("RUNNING...");
        while (running) {
            //Time
            newTime = System.nanoTime();
            deltaTime = newTime-lastTime;
            lastTime = newTime;

            //Update-Draw
            canvas = null;
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gamePanel.update((float)(deltaTime)/1000000000);
                    this.gamePanel.draw(canvas);
                }
            } catch (Exception e) {
            }
            finally{
                if(canvas!=null)
                {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch(Exception e){e.printStackTrace();}
                }
            }

            //Compteur
            framesC++;

            //FPS prompt
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println(framesC +" Ticks");
                System.out.println((float)(deltaTime)/1000000000);
                framesC = 0;
            }

            //Sleep
            timeSpentMS = (newTime-System.nanoTime())/1000000;
            sleepMS = frameMS - timeSpentMS;
            try {
                Thread.sleep(sleepMS);
            } catch (InterruptedException e) {}

        }
    }*/

    @Override
    public void run() {
        final double FPS = 30.0; // frame/sec
        final double ns = 1000000000 / FPS; // durée d'une frame

        long lastTime = System.nanoTime();
        long newTime;
        long deltaTime;

        long lastM = System.nanoTime();
        long newM;
        double deltaS;
        double delta = 0;

        int updates = 0;
        int frames = 0;
        long timer = System.currentTimeMillis();

        System.out.println("Running...");
        while(running) {
            newTime = System.nanoTime();
            deltaTime = newTime-lastTime;
            delta += deltaTime;
            lastTime = newTime;

            if (delta >= ns) {
                newM = System.currentTimeMillis();
                deltaS = (double)(newM-lastM)/1000;
                lastM = newM;

                canvas = null;
                try {
                    canvas = this.surfaceHolder.lockCanvas();
                    //synchronized (surfaceHolder) {
                        /////////////////////////
                        this.gamePanel.update(deltaS);
                        this.gamePanel.draw(canvas);
                        /////////////////////////
                    //}
                } catch (Exception e) {
                    System.out.println("Exception 1");
                }
                finally{
                    if(canvas!=null)
                    {
                        try {
                            surfaceHolder.unlockCanvasAndPost(canvas);
                        }
                        catch(Exception e){
                            System.out.println("Exception 2");
                        }
                    }
                }

                updates++;
                delta -= ns;
            }

            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println(updates +" Ticks, "+ frames +" FPS");
                updates = 0;
                frames = 0;
            }
        }

    }

    public void setRunning(boolean b) {
        running=b;
    }
}