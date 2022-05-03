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
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.Gdx;
import hhs.game.funny.MainActivity;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class LocalMapEntrance implements Screen
{
	MyGame game;
	BitmapFont font;

	public Stage st;
	Table l;

	TextField tf;
	ImageButton start;
	Label l0,l1,l2;

	public LocalMapEntrance(final MyGame game)
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

		Label.LabelStyle style2 = new Label.LabelStyle();
		style2.font = MyGame.font;
		style2.fontColor = Color.BLACK;
		
		tf = new TextField(Gdx.files.getExternalStoragePath(), style);
		tf.setSize(Res.w, 100);
		tf.setAlignment(Align.center);
		//tf.setPosition(Res.w / 2 - 350, Res.h + 200);

		start = tool.createButton("ui15.png", "s1.png");

		l0 = new Label("深色图标是文件夹",style2);
		l1 = new Label("浅色图标是文件",style2);
		l2 = new Label("在下面输入地图文件路径",style2);
		
		l.add(l0);
		l.row();
		l.add(l1);
		l.row();
		l.add(start);
		l.row();
		l.add(l2);

		st.addActor(r.exit);
		st.addActor(tf);

		start.addListener(new InputListener(){

				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
				{
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y, int pointer, int button)
				{
					FileHandle fd = Gdx.files.absolute(tf.getText());
					if (fd.exists())
					{
						if (fd.isDirectory())
						{
							PractiseScreen ps = new PractiseScreen(game, tf.getText(),true);
							MyGame.font.getData().setScale(1);
							game.teampScreen = ps;
							Gdx.input.setInputProcessor(ps.st);
							game.setScreen(ps);
						}
						else
						{
							NormalMapLoaderScreen nmls = new NormalMapLoaderScreen(game, tf.getText());
							Gdx.input.setInputProcessor(nmls.ui);
							game.setScreen(nmls);
						}
					}
					else
					{
						//如果目录不存在就弹出
						MainActivity.use.showQuickTip("不存在");
					}
					//super.touchUp(event, x, y, pointer, button);
				}

			});
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
