package hhs.game.funny.games.Tools;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public abstract interface RendererObject
{

    public void act(float p1);

	public void draw(SpriteBatch batch);

    public void setSize(float size);
}
