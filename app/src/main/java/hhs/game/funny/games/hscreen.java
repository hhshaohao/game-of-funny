package hhs.game.funny.games;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import hhs.game.funny.games.Screen.Jumper;
import hhs.game.funny.MainActivity;
/*
 选关页面
 */
public class hscreen implements Screen
{

	MyGame g;
	SpriteBatch batch;
	Stage st;
	Table ta;
	ImageButton start,br,so,mario,openWorld,setting,goC;
	Texture l,r;
	float t;
	boolean z;
	OrthographicCamera cam;

	Jumper j;
	//Box2DDebugRenderer ren = new Box2DDebugRenderer();

	public hscreen(MyGame m, SpriteBatch batch)
	{

		g = m;
		this.batch = batch;
		ta = new Table();

		st = new Stage();

		start = tool.createButton("ui1.png", "s0.png");
		br = tool.createButton("ui0.png", "s0.png");
		so = tool.createButton("ui2.png", "s1.png");
		goC = tool.createButton("ui10.png","s1.png");
		ta.setFillParent(true);
		ta.center();
		
		ta.add(goC);
		if(MyGame.archive.getBoolean("MAIN"))
		{
			ta.add(goC);
			MainActivity.use.showQuickTip("恭喜");
		}
		else
		{
			ta.add(start);
		}
		ta.add(so).padLeft(50);
		ta.row();
		ta.add(br).padTop(100);

		st.addActor(ta);

		mario = tool.createButton("ui6.png");
		openWorld = tool.createButton("ui7.png");
		setting = tool.createButton("ui9.png");

		setting.setPosition(Res.w - 200, Res.h - 100);
		mario.setPosition(0, Res.h - 100);
		openWorld.setPosition(mario.getX(), mario.getHeight() - 100);

		st.addActor(mario);
		st.addActor(openWorld);
		st.addActor(setting);

		this.addListener();

		l = MyGame.ass.get("f0.jpg", Texture.class);
		r = MyGame.ass.get("f1.jpg", Texture.class);


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
		batch.draw(l, 0, 0, Res.w / 2, Res.h);
		batch.draw(r, Res.w / 2, 0, Res.w / 2, Res.h);

		MyGame.font.draw(batch, "沙雕之主", Res.w / 2, (Res.h / 2 + 50));
		MyGame.font.setColor(0, 0, 0, t);
		if( z )
		{
			t -= p1;
			if( t < 0 )
				z = false;
		}
		else
		{
			t += p1;
			if( t > 1 )
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
		st.dispose();
		l.dispose();
		r.dispose();
	}

}
