package hhs.game.funny.games.Tools;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract interface RendererObject {
	
    public void act(float p1);
	
	public void draw(SpriteBatch batch);
    
    public void setSize(float size);
}
