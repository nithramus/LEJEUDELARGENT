package lteiicorp.glowee;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

public class BOSSClass {
    private int WIDTH;
    private int HEIGHT;
    private Context context;

    private String Scene;

    private MenuClass Menu;
    private GameClass Game;
    private ProfileClass Profile;
    private NotesClass Notes;

    BOSSClass(int ssX, int ssY, Context context) {
        WIDTH = ssX;
        HEIGHT = ssY;
        this.context = context;

        Scene = "Menu";
        Menu = new MenuClass(WIDTH, HEIGHT, context);
    }

    public void Update(double deltaS) {
        if (Scene == "Menu" && Menu != null) {
            Menu.Update();
        }
        else if (Scene == "Game" && Game != null) {
            Game.Update(deltaS);
        }
        else if (Scene == "Profile" && Profile != null) {
            Profile.Update();
        }
        else if (Scene == "Notes" && Notes != null) {
            Notes.Update();
        }
    }

    public void Draw(Canvas c) {
        if (Scene == "Menu" && Menu != null) {
            Menu.Draw(c);
        }
        else if (Scene == "Game" && Game != null) {
            Game.Draw(c);
        }
        else if (Scene == "Profile" && Profile != null) {
            Profile.Render(c);
        }
        else if (Scene == "Notes" && Notes != null) {
            Notes.Render(c);
        }
    }



    public void ScreenP (int px, int py) {
        if (Scene == "Menu" && Menu != null) {
            Menu.ScreenP(px, py);
        }
        else if (Scene == "Game" && Game != null) {
            Game.ScreenP(px, py);
        }
        else if (Scene == "Profile" && Profile != null) {
            Profile.ScreenP(px, py);
        }
        else if (Scene == "Notes" && Notes != null) {
            Notes.ScreenP(px, py);
        }
    }

    public void ScreenR (int px, int py) {
        if (Scene == "Menu" && Menu != null) {
            Menu.ScreenR(px, py);
            if (Menu.Scene != "Menu") {
                ChangeScene(Menu.Scene);
            }
        }
        else if (Scene == "Game" && Game != null) {
            Game.ScreenR(px, py);
            if (Game.Scene != "Game") {
                ChangeScene(Game.Scene);
            }
        }
        else if (Scene == "Profile" && Profile != null) {
            Profile.ScreenR(px, py);
            if (Profile.Scene != "Profile") {
                ChangeScene(Profile.Scene);
            }
        }
        else if (Scene == "Notes" && Notes != null) {
            Notes.ScreenR(px, py);
            if (Notes.Scene != "Notes") {
                ChangeScene(Notes.Scene);
            }
        }
    }




    void ChangeScene(String scene) {
        Scene = scene;
        if (scene == "Menu") {
            ResetScene();
            Menu = new MenuClass(WIDTH, HEIGHT, context);
        }
        else if (scene == "Game") {
            ResetScene();
            Game = new GameClass(WIDTH, HEIGHT, context);
        }
        else if (scene == "Profile") {
            ResetScene();
            Profile = new ProfileClass(WIDTH, HEIGHT, context);
        }
        else if (scene == "Notes") {
            ResetScene();
            Notes = new NotesClass(WIDTH, HEIGHT, context);
        }
    }

    void ResetScene() {
        Menu = null;
        Game = null;
        Profile = null;
        Notes = null;
    }
}
