package hhs.game.funny.games.Screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.SerializationException;
import hhs.game.funny.games.MyGame;
import hhs.game.funny.games.Res;
import hhs.game.funny.games.tool;
import hhs.game.funny.games.mainScreen;
import hhs.game.funny.MainActivity;

public class PractiseScreen implements Screen
{
    MyGame game;
	SpriteBatch batch;

	public Stage st;

	ImageButton ib[];
	Table ta;

	String file;
	FileHandle fd;

	String filearr[];

	public PractiseScreen(final MyGame game,String d)
	{
		this.game = game;
		file= d;
		
		batch = game.Misbatch;

		fd = Gdx.files.internal(file);

		st = new Stage();

		filearr = new String[fd.list().length];
		for (int i = 0; i < fd.list().length; ++i) 
		{
			filearr[i] = fd.list()[i].name();
		}

		ib = new ImageButton[filearr.length];

		int max = (Res.w - 200) / 225;
		int b = 0;//一行的量
		int c = 0;//下多少行
		for (int a = 0; a < ib.length; a++)
		{
			if (b < max)
			{
				ib[a] = tool.createButton("s0.png");
				ib[a].setBounds((b + 1) * 225, Res.h -  (c + 1) * 200, 200, 100);
				b++;
			}
			else
			{
				c++;
				b = 0;
				a--;
			}
		}

		for (int i = 0;i < filearr.length;++i)
		{
			final String filename = file + filearr[i];
			ib[i].addListener(new InputListener()
				{

					@Override
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
					{	
						PractiseScreen.this.game.transition();
						try
						{
							NormalMapLoaderScreen mll = new NormalMapLoaderScreen(game, filename);
							Gdx.input.setInputProcessor(mll.ui);
							PractiseScreen.this.game.setScreen(mll);
						}
						catch (SerializationException se)
						{
							MainActivity.use.showQuickTip("不是正确的地图文件");
							Gdx.input.setInputProcessor(PractiseScreen.this.st);
						}
						return true;
					}
				});
			st.addActor(ib[i]);
		}
		st.addActor(new Res(game).exit);
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
		for (int i = 0; i < ib.length; i++)
		{
			MyGame.font.draw(batch, filearr[i], ib[i].getX(), ib[i].getY());
		}
		batch.end();
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
