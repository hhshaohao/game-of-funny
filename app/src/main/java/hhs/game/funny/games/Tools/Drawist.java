package hhs.game.funny.games.Tools;
import java.util.ArrayList;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Drawist {
	
   public  ArrayList<RendererObject> arr;
    
    public Drawist()
	{
		arr = new ArrayList<>();
	}
	public Drawist(int m)
	{
		arr = new ArrayList<>(m);
	}
	public void addRenderer(RendererObject ro)
	{
		arr.add(ro);
	}
	public void act(float p1)
	{
		for(RendererObject ro : arr)
		{
			ro.act(p1);
		}
	}
	public void draw(SpriteBatch batch)
	{
		for(RendererObject ro : arr)
		{
			ro.draw(batch);
		}
	}
	public RendererObject getRenderer(int value)
	{
		return arr.get(value);
	}
}
