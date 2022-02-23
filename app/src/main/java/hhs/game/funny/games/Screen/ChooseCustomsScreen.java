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
import hhs.game.funny.MainActivity;
import hhs.game.funny.games.MainLineLevel.MainLineLevelLoader;
import hhs.game.funny.games.MyGame;
import hhs.game.funny.games.Res;
import hhs.game.funny.games.tool;
//选择关卡页
public class ChooseCustomsScreen implements Screen
{
	MyGame game;
	SpriteBatch batch;
	int i;

	public Stage st;

	ImageButton ib[];
	Table ta;

	public ChooseCustomsScreen(final MyGame game)
	{
		this.game = game;
		batch = game.Misbatch;

		st = new Stage();

		ib = new ImageButton[11];
		int max = (Res.w - 200) / 225;
		int b = 0;//一行的量
		int c = 0;//下多少行
		for( int a = 0; a < ib.length; a++ )
		{
			if( b < max )
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
		/*for (int i = 0; i < ib.length; i++) 
		 {
		 ib[i] = tool.createButton("s0.png");
		 }*/
		ta  = new Table();
		ta.setFillParent(true);
		ta.center().top();
		for( i = 0;i < ib.length;i++ )
		{
			final String file = "tmx/" + i + ".tmx";
			final int l = i;
			ib[i].addListener(new InputListener()
				{

					@Override
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
					{	
						FileHandle fh =  Gdx.files.internal(file);
						if( fh.exists() )
						{
							game.transition();
							MainLineLevelLoader mll = new MainLineLevelLoader(game, file, l);
							Gdx.input.setInputProcessor(mll.ui);
							game.setScreen(mll);
						}
						else
						{
							MainActivity.use.showQucikDialog("抱歉", "此关暂未开发", new Runnable()
								{
									@Override
									public void run()
									{
										MainActivity.use.showQuickTip("抱歉" + file);
									}
								});
						}
						return true;
					}
				});
			st.addActor(ib[i]);
		}
		st.addActor(new Res(game).exit);
		st.addActor(ta);
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
		for( int i = 0; i < ib.length; i++ )
		{
			MyGame.font.draw(batch, "第" + i + "关", ib[i].getX(), ib[i].getY());
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
