package hhs.game.funny.games.Screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import hhs.game.funny.games.MyGame;
import hhs.game.funny.games.Res;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import hhs.game.funny.games.tool;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class EmojiViewScreen implements Screen
{

	public Stage st;
	SpriteBatch batch;

	Texture text0,text1;

	CheckBox cb0,cb1;
	Label l0;

	public EmojiViewScreen(final MyGame game)
	{
		st = new Stage();

		Res r = new Res(game);

		st.addActor(r.exit);

		batch = MyGame.Misbatch;

		text0 = game.ass.get("p0.png", Texture.class);
		text1 = game.ass.get("p1.png", Texture.class);
		CheckBox.CheckBoxStyle style = new CheckBox.CheckBoxStyle(tool.createDrawable(game.ass.get("s0.png", Texture.class)),
																  tool.createDrawable(game.ass.get("s1.png", Texture.class)),
																  game.font, Color.BLACK);
		cb0 = new CheckBox("选择", style);
		cb1 = new CheckBox("选择", style);

		cb0.setPosition(0, 0);
		cb1.setPosition(Res.w / 5, 0);

		cb0.addListener(new ChangeListener()
			{
				@Override
				public void changed(ChangeListener.ChangeEvent p1, Actor p2)
				{
					game.emojiF.putBoolean("0",cb0.isChecked());
				}
			});
		cb1.addListener(new ChangeListener()
			{
				@Override
				public void changed(ChangeListener.ChangeEvent p1, Actor p2)
				{
					game.emojiF.putBoolean("1",cb0.isChecked());
				}
			});

		//st.addActor(cb0);
		//st.addActor(cb1);
		
		Label.LabelStyle style1 = new Label.LabelStyle(game.font,Color.BLACK);
		
		l0 = new Label("选择游戏中使用的表情包\n重启生效",style1);
		
		l0.setPosition(0,Res.h / 5 + Res.h / 3 + 25);
		//st.addActor(l0);
	}

	@Override
	public void show()
	{
	}

	@Override
	public void render(float p1)
	{
		batch.begin();
		batch.draw(text0, 0, Res.h / 5, Res.h / 3, Res.h / 3);
		batch.draw(text1, Res.w / 5, Res.h / 5, Res.h / 3, Res.h / 3);
		batch.end();

		st.act();
		st.draw();

		MyGame.jump.act();
		MyGame.jump.draw();
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
