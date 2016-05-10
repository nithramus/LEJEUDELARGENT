package lteiicorp.glowee;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Vibrator;

public class GameClass {
    public int WIDTH;
    public int HEIGHT;
    private Context context;
    private MediaPlayer Mgame1;

    public String Scene;

    private float AVANCE;
    private float SCORE;
    private int COINSEARNT;

    public GlowyClass Glowy;
    public BackGroundClass BackGround;
    public WallClass Wall;
    public PopManagerClass PopManager;

    public GameLooseClass GameLoose;
    private boolean Loose;

    //Interface
    private Paint Prec;
    private Paint Ptxt;

    GameClass(int ssx, int ssy, Context context) {
        WIDTH = ssx;
        HEIGHT = ssy;
        this.context = context;

        Scene = "Game";

        //Interface
        Prec = new Paint();
        Prec.setARGB(255,0,75,150);
        Ptxt = new Paint(Paint.ANTI_ALIAS_FLAG);
        Ptxt.setARGB(255,0,255,0);
        Ptxt.setTextAlign(Paint.Align.CENTER);
        Ptxt.setTextSize(HEIGHT / 40);

        Start();
    }

    public void Start() {
        AVANCE = 0;
        SCORE = 0;
        COINSEARNT = 0;

        Glowy = new GlowyClass(WIDTH, HEIGHT, context);
        BackGround = new BackGroundClass(WIDTH, HEIGHT, context);
        Wall = new WallClass(WIDTH, HEIGHT);
        PopManager = new PopManagerClass(WIDTH, HEIGHT);

        GameLoose = null;
        Loose = false;

        Mgame1 = MediaPlayer.create(context, R.raw.game1);
        Mgame1.setLooping(true);
        Mgame1.start();
    }

    public void Update(double deltaS) {
        if (!Loose) {
            Collision();
            if (!Loose) {
                Glowy.Update(deltaS);
                PopManager.Update(deltaS, AVANCE, SCORE);
                BackGround.Update(deltaS);
                CameraMove(deltaS);
            }
        } else if (GameLoose != null){
            GameLoose.Update(deltaS);
        }
    }

    public void Draw(Canvas c) {
        if (!Loose) {
            BackGround.Draw(c);
            Glowy.Draw(c);
            PopManager.Draw(c);
            Wall.Draw(c);
        } else if (GameLoose != null) {
            GameLoose.Draw(c);
        }

        //Interface
        c.drawRect(0, 0, WIDTH, 3*WIDTH/20, Prec);
        String string = "Score : "+Integer.toString((int) SCORE);
        c.drawText(string, WIDTH/3, HEIGHT/22, Ptxt);
        string = "Coins : "+Integer.toString(COINSEARNT);
        c.drawText(string, 2*WIDTH/3, HEIGHT/22, Ptxt);
    }

    public void ScreenP(int mx, int my) {
        if (!Loose) {
            Glowy.ScreenP(mx, my);
        } else {
            GameLoose.ScreenP(mx, my);
        }
    }

    public void ScreenR(int mx, int my) {
        if (Loose && GameLoose != null) {
            GameLoose.ScreenR(mx, my);
            if (GameLoose.Restart) {
                Start();
            } else if (GameLoose.Menu) {
                Scene = "Menu";
            }
        }
    }

    private void CameraMove(double deltaS) {
        //Calcul Centre
        double centreY;
        GlowyShotClass shot = Glowy.Shot;
        if (shot != null) {
            float x = shot.getLifeS();
            x = x*x;
            centreY = shot.getY()*x+Glowy.getY()*(1-x);
        } else {
            centreY = Glowy.getY();
        }
        //Calcul Vect
        //double vectY = (8*HEIGHT/10)-centreY;
        double vectY = (HEIGHT)-centreY;

        if (vectY > 1) {
            //Calcul Move
            //double deplaY = 2*vectY*deltaS;
            double deplaY = 1.2*vectY*deltaS;
            AVANCE += deplaY;
            SCORE = 10*AVANCE/HEIGHT;

            Glowy.CameraMove(deplaY);
            PopManager.CameraMove(deplaY);
            BackGround.CameraMove(deplaY);
        }
    }

    private void Collision() {
        //Glowy-Ennemi
        for (int i = 0; i < PopManager.PopEnnemi.size(); i++) {
            if (Glowy.ToucheEnnemi(PopManager.PopEnnemi.get(i))) {
                Loose(PopManager.PopEnnemi.get(i));
            }
        }
        //Glowy-Coin
        for (int i = 0; i < PopManager.PopCoin.size(); i++) {
            if (Glowy.ToucheCoin(PopManager.PopCoin.get(i))) {
                COINSEARNT += 1;
                PopManager.PopCoin.remove(i);
            }
        }
        //GlowyShot-Coin
        if (Glowy.Shot != null) {
            for (int i = 0; i < PopManager.PopCoin.size(); i++) {
                if (Glowy.Shot.ToucheCoin(PopManager.PopCoin.get(i))) {
                    COINSEARNT += 1;
                    PopManager.PopCoin.remove(i);
                }
            }
        }
    }

    private void Loose(PopEnnemiClass ennemi){
        FileManager f = new FileManager(context);
        f.writeToFile("data", Integer.toString((int) SCORE)+" "+Integer.toString(COINSEARNT));
        Mgame1.stop();
        Vibrator v = (Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(100);
        try {
            Thread.sleep(200);
        } catch (Exception e) {}
        GameLoose = new GameLooseClass(WIDTH, HEIGHT, ennemi, context);
        Loose = true;
    }

}

