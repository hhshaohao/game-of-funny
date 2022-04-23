package hhs.game.funny.games.Screen;

import com.badlogic.gdx.Screen;
import hhs.game.funny.games.MyGame;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import hhs.game.funny.games.tool;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import hhs.game.funny.games.Res;
import com.badlogic.gdx.utils.Align;

public class LocalMapEntrance implements Screen
{
	MyGame game;
	BitmapFont font;

	public Stage st;
	Table l;

	TextField tf;
	ImageButton start;

	public LocalMapEntrance(MyGame game)
	{
		this.game = game;

		font = game.font;

		st = new Stage();
		l = new Table();

		Res r  = new Res(game);

		l.setFillParent(true);
		l.center();

		st.addActor(l);

		TextField.TextFieldStyle style = new TextField.TextFieldStyle();
		style.font = font;
		style.background = tool.createDrawable(new Texture("s0.png"));
		style.cursor = new TextureRegionDrawable(new TextureRegion(new Texture("s1.png"), 0, 0, 10, 100));
		style.fontColor = Color.BLACK;

		tf = new TextField("请输入文件夹地址", style);
		tf.setSize(700, 100);
		tf.setAlignment(Align.center);
		//tf.setPosition(Res.w / 2 - 350, Res.h + 200);

		start = tool.createButton("ui15.png", "s1.png");

		l.row();
		l.add(start);

		st.addActor(r.exit);
		st.addActor(tf);
	}

	@Override
	public void show()
	{
	}

	@Override
	public void render(float p1)
	{
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
