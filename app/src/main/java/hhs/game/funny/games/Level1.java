package hhs.game.funny.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import hhs.game.funny.games.Screen.DeadScreen;
import hhs.game.funny.games.contactListener.contact;
/*
 第一关场景
 */
public class Level1 implements Screen
{

	private TiledMap map;
	private OrthogonalTiledMapRenderer render;
	private OrthographicCamera cam;
	private ImageButton b0,b1,b2,skill;
	private funny zhu;
	private SpriteBatch batch;
	private funny pei[],boss;
	private Mission mis,next,ng;
	private MyGame game;
	private DeadScreen dead;
	contact con;
	float time,time1 = 6f;
	float tmp = 0;
	boolean s=true,first=true,nextS = true,ndead = false;
	static Stage st;
	//TextureRegionDrawable d1,d2;

	boolean jump,left,jumping,stop = true;
	Box2DDebugRenderer ren;

	static boolean show  = true;
	static int suo = 100;
	static int speed = 8;
	float nx,ny;
	Res bu;


	private World world;

	public Level1(SpriteBatch batch, final MyGame game)
	{
		this.game = game;

		ren = new Box2DDebugRenderer();
		bu = new Res();

		this.batch = batch;
		//anim = MyGame.anim1;

		st = new Stage();

		cam = new OrthographicCamera();
		cam.setToOrtho(false, Res.w / (tool.le1 + suo), Res.h / (tool.le1 + suo));

		map = new TmxMapLoader().load("tmx/le1.tmx");

		render = new OrthogonalTiledMapRenderer(map, 1f / tool.le1);

		//TextureRegionDrawable d1 = new TextureRegionDrawable(new TextureRegion(MyGame.ass.get("skill.png", Texture.class), 0, 0, 192, 192));
		//TextureRegionDrawable d2 = new TextureRegionDrawable(new TextureRegion(MyGame.ass.get("skill.png", Texture.class), 192, 0, 192, 192));

		//d1 = new TextureRegionDrawable(new TextureRegion(new Texture("skill.png"), 0, 0, 191.5f, 192));
		//d2 = new TextureRegionDrawable(new TextureRegion(new Texture("skill.png"), 191.5f, 0, 383, 192));

		//skill = new ImageButton(d1, d2);

		b0 = bu.b0;
		b1 = bu.b1;
		b2 = bu.b2;

		//st.addActor(skill);
		st.addActor(b0);
		st.addActor(b1);
		st.addActor(b2);

		this.addListen();

		this.initbox2d();
		zhu = new funny(world, new Vector2(100 / tool.le1, 300 / tool.le1), "w0.png", "zhu", 8 / tool.le1);

		world.setContactListener(con);

		//Gdx.input.setInputProcessor(st);
		mis = new Mission("任务", "进入终点水管", MyGame.font);
		ng = new Mission("恭喜", "您已通过第一关", MyGame.font){


			public void cilck(Dialog dialog)
			{
				MyGame.pre.putInteger("level", 2);
				MyGame.pre.flush();
				game.goLevel2();
				this.dispose();
			}

		};
		next  = new Mission("提示", "恭喜通过第一区域\n这一区域有BOSS出现", MyGame.font){


			public void cilck(Dialog dialog)
			{
				for( int i = 0; i < pei.length; i++ )
				{
					world.destroyBody(pei[i].b2body);
                }
				dialog.remove();
				nextS = false;
				Gdx.input.setInputProcessor(st);
			}
		};

		cam.position.y = 300 / tool.le1;

		dead = new DeadScreen(game, batch){

			@Override
			public void cilk(ImageButton bu)
			{
				zhu.b2body.setTransform(new Vector2(100 / tool.le1, 300 / tool.le1), zhu.b2body.getAngle());
				jump = left = jumping = false;
				s = true;
				first = true;
				ndead = false;
				stop = true;

				super.cilk(bu);
				if( nextS )
				{
					for( int i =0;i < pei.length;i++ )
					{
						//int a = MathUtils.random(0, 2);
						pei[i].b2body.setTransform(new Vector2(1300 / tool.le1 , MathUtils.random(700 / tool.le1, 1500 / tool.le1)), pei[i].b2body.getAngle());
					}
				}
				if( !nextS )
				{
					boss.b2body.setTransform(new Vector2(128 / tool.le1 , 32 / tool.le1), boss.b2body.getAngle());
				}
				Gdx.input.setInputProcessor(Level1.st);
				game.setScreen(Level1.this);
			}

		};

	}

	private void initbox2d()
	{
		con = new contact();

		world = new World(new Vector2(0, -9.81f), true);
		pei = new funny[70];
		boss = new funny(world, new Vector2(128 / tool.le1 , 32 / tool.le1), "w2.png", "boss", 64 / tool.le1);

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		bdef.type = BodyDef.BodyType.StaticBody;

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(8 / tool.le1, 8 / tool.le1);
		fdef.shape = shape;

		for( RectangleMapObject re : map.getLayers().get("rect").getObjects().getByType(RectangleMapObject.class) )
		{
			Rectangle rect = re.getRectangle();

			bdef.position.set((rect.getX() + rect.getWidth() / 2) / tool.le1, (rect.getY() + rect.getHeight() / 2) / tool.le1);
			Body body;
			body = world.createBody(bdef);

			body.createFixture(fdef);
		}

		/*for (RectangleMapObject re : map.getLayers().get("listener").getObjects().getByType(RectangleMapObject.class)) {
		 Rectangle rect = re.getRectangle();

		 bdef.position.set((rect.getX() + rect.getWidth() / 2) / tool.le1, (rect.getY() + rect.getHeight() / 2) / tool.le1);
		 Body body;
		 body = world.createBody(bdef);

		 PolygonShape shape = new PolygonShape();
		 shape.setAsBox(rect.getWidth() / 2 / tool.le1, rect.getHeight() / 2 / tool.le1);
		 fdef.shape = shape;
		 Fixture fix = body.createFixture(fdef);
		 fix.setUserData("lost");
		 }*/

		for( int i =0;i < pei.length;i++ )
		{
			int a = MathUtils.random(0, 2);
			pei[i] = new funny(world, new Vector2(1300 / tool.le1 , MathUtils.random(700 / tool.le1, 1500 / tool.le1)), "w" + a + ".png", "pei", 8 / tool.le1);
		}
	}
	private void addListen()
	{
		b0.addListener(new InputListener(){

				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
				{
					left = true;
					stop = false;
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y, int pointer, int button)
				{
					stop = true;
				}

			});
		b1.addListener(new InputListener(){

				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
				{
					left = false;
					stop = false;
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y, int pointer, int button)
				{
					stop = true;
				}

			});
		b2.addListener(new InputListener(){

				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
				{
					jump = true;
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y, int pointer, int button)
				{
					jump = false;
				}

			});


	}

	public void move()
	{
		if( left == true && stop == false )
		{
			if( zhu.b2body.getLinearVelocity().x > -speed )
				zhu.b2body.applyForceToCenter(new Vector2(-speed, 0), true);
		}
		else if( stop == false )
		{
			if( zhu.b2body.getLinearVelocity().x < speed )
				zhu.b2body.applyForceToCenter(new Vector2(speed, 0), true);
		}

		/*if (jump == true) {
		 if (zhu.b2body.getLinearVelocity().y < 0.01f && zhu.b2body.getLinearVelocity().y > -0.01f) {
		 MyGame.ass.get("jump.mp3", Sound.class).play();
		 zhu.b2body.applyForceToCenter(new Vector2(0, 450), true);
		 }
		 if (zhu.b2body.getLinearVelocity().y > 1) {
		 jumping = true;
		 } else {
		 jumping = false;
		 }
		 }*/
		if( jump == true )
		{
			if( zhu.b2body.getLinearVelocity().y < 0.1f && zhu.b2body.getLinearVelocity().y > -0.1f )
			{
				MyGame.ass.get("jump.mp3", Sound.class).play();
				zhu.b2body.applyForceToCenter(new Vector2(0, 450), true);
			}
		}

		if( nextS )
		{
			for( int i = 0; i < pei.length; i++ )
			{
				//if (pei[i].b2body.getPosition().x < nx + Res.w / (tool.le1 + suo) && pei[i].b2body.getPosition().x > nx - Res.w / (tool.le1 + suo))
				if( pei[i].b2body.getLinearVelocity().y == 0 )
				{
					if( zhu.b2body.getPosition().x > pei[i].b2body.getPosition().x )
					{
						pei[i].b2body.applyForceToCenter(new Vector2(speed * 10, 425), true);
					}
					else
					{
						pei[i].b2body.applyForceToCenter(new Vector2(-speed * 10, 425), true);
					}
				}
			}
		}
		else
		{

			//if (pei[i].b2body.getPosition().x < nx + Res.w / (tool.le1 + suo) && pei[i].b2body.getPosition().x > nx - Res.w / (tool.le1 + suo))
			if( boss.b2body.getLinearVelocity().y == 0 )
			{
				if( zhu.b2body.getPosition().x > boss.b2body.getPosition().x )
				{
					boss.b2body.applyForceToCenter(new Vector2(speed * 15, 500), true);
				}
				else
				{
					boss.b2body.applyForceToCenter(new Vector2(-speed * 15, 500), true);
				}
			}

		}
	}

	@Override
	public void show()
	{

	}

	private void update()
	{
		if( !show )
			this.move();
		cam.position.x = nx;
		if( first )
		{
			tmp = cam.position.y;
			first = false;
		}

		cam.update();

		cam.position.y = tmp;
		if( ny > cam.position.y + (Res.h / (tool.le1 + suo)) / 4 )
		{
			tmp += 2 / tool.le1;
		}
		if( ny < cam.position.y )
		{
			tmp -= 2 / tool.le1;
		}

		world.step(1 / 60f, 1, 1);

		render.setView(cam);

		batch.setProjectionMatrix(cam.combined);

		nx = zhu.b2body.getPosition().x - ((16 / tool.le1) / 2);
		ny = zhu.b2body.getPosition().y - ((16 / tool.le1) / 2);

	}

	@Override
	public void render(float p1)
	{
		this.update();

		time += p1;
		time1 += p1;

		/*if (time > 2.5f && !nextS) {
		 for (int i = 0; i < boss.length / 3; i++) {
		 boss[i].b2body.setTransform(nx, ny, boss[i].b2body.getAngle());
		 }
		 time = 0;
		 }*/

		render.render();

		batch.begin();
		
		batch.draw(zhu, nx, ny, 16 / tool.le1, 16 / tool.le1);
		
		if( nextS )
		{
			for( int i = 0; i < pei.length; ++i )
			{
				if( pei[i].b2body.getPosition().x < nx + Res.w / (tool.le1 + suo) && pei[i].b2body.getPosition().x > nx - Res.w / (tool.le1 + suo) )
				{
					batch.draw(pei[i], pei[i].b2body.getPosition().x - ((16 / tool.le1) / 2), pei[i].b2body.getPosition().y - ((16 / tool.le1) / 2), 16 / tool.le1, 16 / tool.le1);
				}
			}
		}
		else
		{
			if( boss.b2body.getPosition().x < nx + Res.w / (tool.le1 + suo) && boss.b2body.getPosition().x > nx - Res.w / (tool.le1 + suo) )
			{
				batch.draw(boss, boss.b2body.getPosition().x - ((128 / tool.le1) / 2), boss.b2body.getPosition().y - ((128 / tool.le1) / 2), 128 / tool.le1, 128 / tool.le1);
			}
			if( nx < 32 / tool.le1 )
			{
				Gdx.input.setInputProcessor(ng);
				ng.act();
				ng.draw();
			}
		}
		//batch.draw(MyGame.ass.get("skill.png",Texture.class),0,0,383/tool.le1,198/tool.le1);
		batch.end();

		st.act();
		st.draw();

		if( show )
		{
			Gdx.input.setInputProcessor(mis);
			mis.act();
			mis.draw();
		}
		if( nextS && ny < 16 * 4 / tool.le1 )
		{
			Gdx.input.setInputProcessor(next);
			next.act();
			next.draw();
		}
		if( ny < 0 && !ndead )
		{
			ndead = true;
			Gdx.input.setInputProcessor(dead.st);
			game.setScreen(dead);
		}
		/*if (con.is) {
		 zhu.b2body.applyForceToCenter(0, 1000, false);
		 }*/
		//ren.render(world,cam.combined);
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
		world.dispose();
		zhu.getTexture().dispose();
		render.dispose();
		map.dispose();
		st.dispose();
		mis.dispose();
	}




}
