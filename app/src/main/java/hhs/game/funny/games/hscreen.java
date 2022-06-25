package hhs.game.funny.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import hhs.game.funny.MainActivity;
import hhs.game.funny.games.Screen.Jumper;
import com.badlogic.gdx.audio.Music;
/*
 选择主页面
 */
public class hscreen implements Screen
{

	MyGame g;
	SpriteBatch batch;
	Stage st;
	Table ta;
	ImageButton start,br,so,mario,openWorld,setting,goC,newStart,practise,goLocal,allEmoji;
	Texture b;
	float t;
	boolean z;
	OrthographicCamera cam;

	Jumper j;
	//Box2DDebugRenderer ren = new Box2DDebugRenderer();
	Music m0;
	
	public hscreen(MyGame m, SpriteBatch batch)
	{

		g = m;
		this.batch = batch;
		ta = new Table();

		st = new Stage();

		start = tool.createButton("ui1.png", "s0.png");
		br = tool.createButton("ui0.png", "s0.png");
		so = tool.createButton("ui2.png", "s1.png");
		goC = tool.createButton("ui10.png", "s1.png");
		newStart = tool.createButton("ui11.png", "s0.png");
		ta.setFillParent(true);
		ta.center();

		ta.add(newStart).padRight(50);
		if (MyGame.archive.getBoolean("MAIN"))
		{
			ta.add(goC);
		}
		ta.add(start).padLeft(50);
		ta.add(so).padLeft(50);
		ta.row();
		ta.add(br).padTop(100);

		st.addActor(ta);

		mario = tool.createButton("ui6.png");
		openWorld = tool.createButton("ui7.png");
		setting = tool.createButton("ui9.png");
		practise = tool.createButton("ui13.png", "s0.png");
		goLocal = tool.createButton("ui14.png", "s1.png");
		allEmoji = tool.createButton("ui18.png","s1.png");

		setting.setPosition(Res.w - 200, Res.h - 100);
		mario.setPosition(0, Res.h - 100);
		openWorld.setPosition(mario.getX(), mario.getHeight() - 100);
		practise.setPosition(Res.w / 2 - practise.getWidth() / 2, 0);
		goLocal.setPosition(Res.w / 2 - goLocal.getWidth() / 2, Res.h - goLocal.getHeight());
		allEmoji.setPosition(Res.w - allEmoji.getWidth(),0);
		
		st.addActor(mario);
		st.addActor(openWorld);//暂时不加上(怕有版权问题)
		st.addActor(setting);
		st.addActor(practise);
		st.addActor(goLocal);
		st.addActor(allEmoji);

		this.addListener();

		b = MyGame.ass.get("f0.jpg", Texture.class);

		MyGame.font.getData().setScale(5);
		MyGame.font.setColor(Color.BLACK);

		cam = new OrthographicCamera();
		cam.setToOrtho(false, Res.w , Res.h);

		j = MyGame.jump;

		Gdx.input.setInputProcessor(st);
		
	}



	@Override
	public void show()
	{
	}



	@Override
	public void render(float p1)
	{

		batch.setProjectionMatrix(cam.combined);
		
		batch.begin();
		batch.draw(b, 0, 0, Res.w, Res.h);
		
		MyGame.font.draw(batch, "沙雕之主", Res.w / 2, (Res.h / 2 + 50));
		MyGame.font.setColor(0, 0,  0, t);
		if (z)
		{
			t -= p1;
			if (t < 0)
				z = false;
		}
		else
		{
			t += p1;
			if (t > 1)
				z = true;
		}

		batch.end();

		j.act();
		j.draw();

		st.act();
		st.draw();

		//ren.render(world,cam.combined);
	}

	void addListener()
	{
		br.addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
				{
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y, int pointer, int button)
				{
					Gdx.app.exit();
					//super.touchUp(event, x, y, pointer, button);
				}

			});

		start.addListener(new InputListener(){

				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
				{
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y, int pointer, int button)
				{
					g.goGame();
					//super.touchUp(event, x, y, pointer, button);
				}

			});

		so.addListener(new InputListener(){

				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
				{
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y, int pointer, int button)
				{
					g.goSo();
					//super.touchUp(event, x, y, pointer, button);
				}

			});

		mario.addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
				{
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y, int pointer, int button)
				{
					g.goMario();
					//super.touchUp(event, x, y, pointer, button);
				}
			});

		openWorld.addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
				{
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y, int pointer, int button)
				{
					g.goMagicLand();
					//super.touchUp(event, x, y, pointer, button);
				}
			});

		setting.addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
				{
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y, int pointer, int button)
				{
					g.goSetting();
					//super.touchUp(event, x, y, pointer, button);
				}
			});

		goC.addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
				{
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y, int pointer, int button)
				{
					g.goChooser();
					//super.touchUp(event, x, y, pointer, button);
				}
			});

		newStart.addListener(new InputListener()
			{
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
				{
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y, int pointer, int button)
				{
					MainActivity.use.showDialog("重新开始?", "清除所有游戏进度?", new Runnable(){
							@Override
							public void run()
							{
								g.archive.putBoolean("MAIN", false);
								g.archive.putBoolean("WIN", false);
								g.archive.flush();
								g.reStart();
							}
						}, new Runnable(){
							@Override
							public void run()
							{
							}
						});
					//super.touchUp(event, x, y, pointer, button);
				}
			});

		practise.addListener(new InputListener()
			{
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
				{
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y, int pointer, int button)
				{
					g.goPractise();
				}
			});

		goLocal.addListener(new InputListener()
			{
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
				{
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y, int pointer, int button)
				{
					g.goLocal();
				}
			});
		allEmoji.addListener(new InputListener()
			{
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
				{
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y, int pointer, int button)
				{
					g.goViewEmoji();
				}
			});
	};

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
		st.dispose();
		b.dispose();
	}

}
