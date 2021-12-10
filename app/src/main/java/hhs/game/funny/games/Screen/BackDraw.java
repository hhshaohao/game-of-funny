package hhs.game.funny.games.Screen;

import com.badlogic.gdx.utils.Disposable;

public abstract class BackDraw implements Disposable
{
    abstract public void act();
	abstract public void draw();
}
