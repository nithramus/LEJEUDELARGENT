package lteiicorp.glowee;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;

public class PopManagerClass {
    private int WIDTH;
    private int HEIGHT;

    private float AVANCE;
    private float SCORE;

    ArrayList<PopEnnemiClass> PopEnnemi;
    private float EnnemiAvanc;

    ArrayList<PopCoinClass> PopCoin;
    private float CoinAvanc;

    PopManagerClass(int screensizex, int screensizey) {
        WIDTH = screensizex;
        HEIGHT = screensizey;

        AVANCE = 0;
        SCORE = 0;

        PopEnnemi = new ArrayList<PopEnnemiClass>();
        EnnemiAvanc = 0;

        PopCoin = new ArrayList<PopCoinClass>();
        CoinAvanc = 0;
    }

    public void Update(double deltaS, float avance, float score) {
        AVANCE = avance;
        SCORE = score;


        ManageEnnemi1(HEIGHT / 10, 50 + SCORE / 20, deltaS);
        ManageCoin(HEIGHT, 50, deltaS);
    }

    public void Draw(Canvas c) {
        for (int i=0; i<PopEnnemi.size(); i++) {
            PopEnnemi.get(i).Render(c);
        }
        for (int i=0; i<PopCoin.size(); i++) {
            PopCoin.get(i).Render(c);
        }
    }

    private void ManageEnnemi1(float popspace, float popproba, double deltaS) {
        if (AVANCE-EnnemiAvanc > popspace) {
            if (100*Math.random()<popproba) {
                if (Math.random() < 0.5) {
                    PopEnnemi.add(new PopEnnemiClass(WIDTH, HEIGHT, WIDTH/30, 0));
                } else {
                    PopEnnemi.add(new PopEnnemiClass(WIDTH, HEIGHT, WIDTH/30, Math.PI));
                }
            }

            if (100*Math.random()<popproba) {
                if (Math.random() < 0.9) {
                    PopEnnemi.add(new PopEnnemiClass(WIDTH, HEIGHT, WIDTH/30, Math.PI/2));
                } else {
                    PopEnnemi.add(new PopEnnemiClass(WIDTH, HEIGHT, (WIDTH/10)*(1+Math.random()), Math.PI/2));
                }
            }

            EnnemiAvanc = AVANCE;
        }

        for (int i=0; i<PopEnnemi.size(); i++) {
            if (PopEnnemi.get(i).destroy()) {
                PopEnnemi.remove(i);
            } else {
                PopEnnemi.get(i).Tick(deltaS);
            }
        }
    }

    private void ManageCoin(float popspace, float popproba, double deltaS) {
        float rand;
        if (AVANCE-CoinAvanc > popspace) {
            rand = (float)(100*Math.random());
            if (rand<popproba) {
                PopCoin.add(new PopCoinClass(WIDTH, HEIGHT, (float)((WIDTH/20)+Math.random()*(18*WIDTH/20)), 0));
            }
            CoinAvanc = AVANCE;
        }

        for (int i=0; i<PopCoin.size(); i++) {
            if (PopCoin.get(i).destroy()) {
                PopCoin.remove(i);
            } else {
                PopCoin.get(i).Tick(deltaS);
            }
        }
    }

    public void CameraMove(double deplaY) {
        for (int i=0; i<PopEnnemi.size(); i++) {
            PopEnnemi.get(i).CameraMove(deplaY);
        }
        for (int i=0; i<PopCoin.size(); i++) {
            PopCoin.get(i).CameraMove(deplaY);
        }
    }
}
