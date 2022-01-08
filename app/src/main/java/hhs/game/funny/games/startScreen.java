package hhs.game.funny.games;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public class startScreen implements Screen
{

	SpriteBatch batch;
	Texture t,j;
	float temp;
	BitmapFont font;
	static boolean isOk;
	
	public startScreen ( SpriteBatch batch )
	{
		this.batch = batch;
		j = new Texture("s1.png");
		t = new Texture("icon.png");

		font  = new BitmapFont();

		font.getData().setScale(5);
	}

	@Override
	public void show ( )
	{
	}

	@Override
	public void render ( float p1 )
	{
		//tool.clearScreen(Color.WHITE);
		float s = MyGame.ass.getProgress();
		batch.begin();
		batch.draw(t, Res.w / 2 - 250, Res.h / 2 - 250);
		batch.draw(j, 0, 0, Res.w * s, 100);
		font.draw(batch, "Loading:" + (int)(s * 100)+"%", Res.w / 2 - 100, Res.h / 4);
		batch.end();
		if ( MyGame.ass.update() )
		{
			isOk = true;
		}
	}

	@Override
	public void resize ( int p1, int p2 )
	{
	}

	@Override
	public void pause ( )
	{
	}

	@Override
	public void resume ( )
	{
	}

	@Override
	public void hide ( )
	{
	}

	@Override
	public void dispose ( )
	{
	}




}
