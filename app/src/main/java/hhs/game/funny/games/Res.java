package hhs.game.funny.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Res
{

    public static int w = Gdx.graphics.getWidth();
	public static int h = Gdx.graphics.getHeight();
    static float tx = w / tool.PPM;
    static float ty = h / tool.PPM;

	public ImageButton b0,b1,b2,exit;

	public Res(final MyGame game)
	{

		TextureRegion a = tool.createRegion("move3.png");
        a.flip(true, false);
        TextureRegion b,c,d;
        b = tool.createRegion("move1.png");
        b.flip(false, true);
        c = tool.createRegion("move0.png");
        c.flip(false, true);
        d = tool.createRegion("move2.png");
        d.flip(true, false);
        b0 = new ImageButton(new TextureRegionDrawable(a), new TextureRegionDrawable(d));
        b1 = tool.createButton("move3.png", "move2.png");
        b2 = new ImageButton(new TextureRegionDrawable(b), new TextureRegionDrawable(c));

		b0.setPosition(0, 0);
        b1.setPosition(1.5f * b0.getWidth(), 0);
        b2.setPosition(Res.w - b2.getWidth(), 0);

		exit = tool.createButton(MyGame.ass.get("ui8.png", Texture.class));
		exit.setPosition(0, Res.h - exit.getHeight());
		exit.addListener(new InputListener()
			{

				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
				{
					game.goMain();
					return true;
				}
			});
	}

}
enum sprite
{
    left,right,stop,up,down;
}
