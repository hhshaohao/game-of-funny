package hhs.game.funny.games.Screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import hhs.game.funny.games.MyGame;
import hhs.game.funny.games.Res;
import hhs.game.funny.games.tool;
/*
 一个死亡复活功能的API
 */
public class DeadScreen implements Screen
{

	public SpriteBatch batch;
	ImageButton fu;
	Texture back,g;
	public Stage st;
	MyGame game;
	float time;

	public DeadScreen(final MyGame game, SpriteBatch batch)
	{
		this.game = game;
		this.batch = batch;

		st = new Stage();

		back = MyGame.ass.get("background/dead.jpg", Texture.class);

		fu = tool.createButton(MyGame.ass.get("ui5.png", Texture.class));
		fu.setPosition(Res.w / 2 - fu.getWidth() / 2, Res.h / 2 - fu.getHeight() / 2);
		fu.addListener(new InputListener(){

				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
				{
					cilk(fu);
					return true;
				}
			});

		st.addActor(fu);

		//g = game.ass.get("GameOver.png",Texture.class);
	}

	public void cilk(ImageButton bu)
	{

	}

	@Override
	public void show()
	{
	}

	@Override
	public void render(float p1)
	{
		time += p1;
		batch.begin();
		batch.draw(back, 0, 0, Res.w, Res.h);
		//batch.draw(g,Res.w / 2 - g.getWidth() / 2,Res.h / 2 - g.getHeight() / 2);
		//batch.draw(game.boom.getKeyFrame(time,true),0,0,200,200);
		batch.end();


		st.act();
		st.draw();
	}

	@Override
	public void resize(int p1, int p2)
	{

	}

	@Override
	public void pause()
	{

	}

	@Override
	public void resume()
	{

	}

	@Override
	public void hide()
	{

	}

	@Override
	public void dispose()
	{

	}
}
