package hhs.game.funny.games.Screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.SerializationException;
import hhs.game.funny.MainActivity;
import hhs.game.funny.games.MyGame;
import hhs.game.funny.games.Res;
import hhs.game.funny.games.tool;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.graphics.Color;

public class PractiseScreen implements Screen
{
    MyGame game;
	SpriteBatch batch;

	public Stage st;

	ImageButton ib[];
	Label lb[];
	Table ta;

	String file;
	FileHandle fd;

	String filearr[];

	ScrollPane sp;
	Table t0;

	public PractiseScreen(final MyGame game, String d, final boolean out)
	{
		this.game = game;
		file = d;

		batch = game.Misbatch;

		if (out)
		{
			fd = Gdx.files.absolute(file);
		}
		else
		{
			fd = Gdx.files.internal(file);
		}

		st = new Stage();

		filearr = new String[fd.list().length];
		for (int i = 0; i < fd.list().length; ++i) 
		{
			filearr[i] = fd.list()[i].name();
		}

		TextureRegionDrawable d0 =  tool.createDrawable("tran.jpg");
		ScrollPane.ScrollPaneStyle style = new ScrollPane.ScrollPaneStyle(d0, d0, d0, d0, d0);

		Label.LabelStyle style1 = new Label.LabelStyle(game.font, Color.BLACK);

		ib = new ImageButton[filearr.length + 1];
		lb = new Label[filearr.length + 1];
		if (out)
		{
			ib[0] = tool.createButton("s1.png");
			lb[0] = new Label("上一级", style1);

			ib[0].addListener(new InputListener(){

					@Override
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
					{
						return true;
					}

					@Override
					public void touchUp(InputEvent event, float x, float y, int pointer, int button)
					{
						if (!event.isTouchFocusCancel())
						{
							PractiseScreen next = new PractiseScreen(game, fd.parent().path(), true);
							Gdx.input.setInputProcessor(next.st);
							game.teampScreen = next;
							game.setScreen(next);
						}
					}

				});
		}
		MainActivity.use.showQuickTip(ib.length+" "+fd.list().length);
		for (int a = 0; a < fd.list().length; a++)
		{
			if (fd.list()[a].isDirectory())
			{
				ib[a+1] = tool.createButton("s1.png");
			}
			else
			{
				ib[a+1] = tool.createButton("s0.png");
			}

		}
		t0 = new Table();
		
		t0.add(ib[0]).padRight(50);
		t0.add(lb[0]);
		
		int num = 1;
		for (int i = 0;i < filearr.length;++i)
		{
			final String filename;
			if (file.getBytes()[file.getBytes().length - 1] != '/')
			{
				filename = file + '/' + filearr[i];
			}
			else
			{
				filename = file + filearr[i];
			}
			ib[i + 1].addListener(new InputListener()
				{

					@Override
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
					{	
						return true;
					}

					@Override
					public void touchUp(InputEvent event, float x, float y, int pointer, int button)
					{
						if (!event.isTouchFocusCancel())
						{
							PractiseScreen.this.game.transition();
							NormalMapLoaderScreen mll = null;
							try
							{
								mll = new NormalMapLoaderScreen(game, filename, out);
								Gdx.input.setInputProcessor(mll.ui);
								PractiseScreen.this.game.setScreen(mll);
							} catch (SerializationException se)
							{
								if (Gdx.files.absolute(filename).isDirectory())
								{
									PractiseScreen next = new PractiseScreen(game, filename, true);
									Gdx.input.setInputProcessor(next.st);
									game.teampScreen = next;
									game.setScreen(next);
								}
								else
								{
									MainActivity.use.showQuickTip("不是正确的地图文件");
									Gdx.input.setInputProcessor(PractiseScreen.this.st);
								}
							}
						}
					}
				});
			lb[i + 1] = new Label(filearr[i], style1);
			if (num < 1)
			{
				t0.add(ib[i+1]).padRight(50);
				t0.add(lb[i+1]);

				num++;
			}
			else
			{
				t0.row().padTop(50);
				t0.add(ib[i+1]);
				t0.add(lb[i+1]);
				num = 0;
			}
		}

		sp = new ScrollPane(t0, style);
		sp.setBounds(0, 0, Res.w, Res.h - 100);
		sp.setScrollingDisabled(false, false);
		sp.setSmoothScrolling(true);

		st.addActor(sp);
		st.addActor(new Res(game).exit);
		game.teampScreen = this;
	}

	@Override
	public void show()
	{
	}

	@Override
	public void render(float p1)
	{
		st.act();
		st.draw();

		batch.begin();

		batch.end();

		if (!game.teampScreen.equals(this))
		{
			game.teampScreen = this;
		}
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
