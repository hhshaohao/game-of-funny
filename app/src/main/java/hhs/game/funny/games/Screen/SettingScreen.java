package hhs.game.funny.games.Screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import hhs.game.funny.games.MyGame;
import hhs.game.funny.games.Res;

public class SettingScreen implements Screen
{
	MyGame game;

	public Stage st;
	ImageButton b0,b1;
	Table ta;
	Label l0,l1;

	int zoom;

	public SettingScreen(final MyGame game)
	{
		this.game = game;

		if (!MyGame.setting.contains("zoom"))
		{
			MyGame.setting.putInteger("zoom", 0);
		}
		else
		{
			zoom = MyGame.setting.getInteger("zoom");
		}

		st = new Stage();
		ta = new Table();
		Res r  = new Res(game);
		r.exit.clearListeners();
		r.exit.addListener(new InputListener()
			{
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
				{
					MyGame.setting.putInteger("zoom", zoom);
					MyGame.setting.flush();
					MyGame.zoom = zoom;
					game.goMain();
					return true;
				}
			});
		st.addActor(r.exit);

		b0 = r.b0;
		b1 = r.b1;

		Label.LabelStyle s = new Label.LabelStyle();
		s.font = MyGame.font;
		s.fontColor = Color.BLACK;
		l0 = new Label("null", s);
		l1 = new Label("本游戏完全免费，请不要在非官方途径下载\n联系方式：\nQQ:1265177365\n微信：quququqqq",s);

		b0.addListener(new InputListener()
			{

				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
				{
					zoom -= 10;
					return true;
				}
			});
		b1.addListener(new InputListener()
			{

				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
				{
					zoom += 10;
					return true;
				}
			});

		b0.setPosition(0, 0);
		b1.setPosition(0, 0);

		ta.setFillParent(true);
		ta.center().top();
		ta.add(b0);
		ta.add(l0);
		ta.add(b1);
		ta.row();
		ta.add(l1);

		st.addActor(ta);
	}

	@Override
	public void show()
	{
	}

	@Override
	public void render(float p1)
	{

		l0.setText("屏幕缩放为" + zoom);

		MyGame.jump.act();
		MyGame.jump.draw();

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
