package hhs.game.funny.games.Screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import hhs.game.funny.games.tool;
import hhs.game.funny.games.MyGame;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.Stage;
import hhs.game.funny.games.Res;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.graphics.Color;
/*
一个死亡复活功能的API
*/
public class DeadScreen implements Screen
{

	SpriteBatch batch;
	ImageButton fu;
	Texture back;
	public Stage st;
	MyGame game;

	public DeadScreen ( final MyGame game, SpriteBatch batch )
	{
		this.game = game;
		this.batch = batch;

		st = new Stage();

		back = MyGame.ass.get("background/dead.jpg", Texture.class);

		fu = tool.createButton(MyGame.ass.get("ui5.png", Texture.class));
		fu.setPosition(Res.w / 2, Res.h / 2);
		fu.addListener(new InputListener(){

				@Override
				public boolean touchDown ( InputEvent event, float x, float y, int pointer, int button )
				{
					cilk(fu);
					return true;
				}
			});

		st.addActor(fu);
	}

	public void cilk ( ImageButton bu )
	{

	}

	@Override
	public void show ( )
	{
	}

	@Override
	public void render ( float p1 )
	{
		
		batch.begin();
		batch.draw(back, 0, 0, Res.w, Res.h);
		batch.end();

		st.act();
		st.draw();
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
