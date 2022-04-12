package hhs.game.funny.games.MainLineLevel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import hhs.game.funny.games.Actor.PlatformActor;
import hhs.game.funny.games.Mission;
import hhs.game.funny.games.MyGame;
import hhs.game.funny.games.Res;
import hhs.game.funny.games.Runnable.RoleLogic;
import hhs.game.funny.games.Screen.CommonlyScreen;
import hhs.game.funny.games.Screen.DeadScreen;
import hhs.game.funny.games.Stage.MissionStage;
import hhs.game.funny.games.Tools.Drawist;
import hhs.game.funny.games.contactListener.jumpConcat;
import hhs.game.funny.games.funny;
//主线关卡
public class level1 extends CommonlyScreen
{
	MyGame game;
	SpriteBatch batch;
	Drawist dist;
	float nx,ny;
	OrthographicCamera cam;
	float zoom = 50,ppm = 20f;
	static int speed = 8;
	Box2DDebugRenderer en  = new Box2DDebugRenderer();
	static jumpConcat c;
	DeadScreen ds;

	TiledMap map;
	OrthogonalTiledMapRenderer render;

	World world;

	static funny ac;

	MissionStage ms;
	Mission mis;
	boolean start = true;;

    public level1(final MyGame game, SpriteBatch batch)
	{
		super(game, new RoleLogic(){

				@Override
				public void leftAction()
				{
					if (ac.b2body.getLinearVelocity().x > -speed)
						ac.b2body.applyForceToCenter(new Vector2(-speed, 0), true);	
				}

				@Override
				public void rightAction()
				{
					if (ac.b2body.getLinearVelocity().x < speed)
						ac.b2body.applyForceToCenter(new Vector2(speed, 0), true);
				}

				@Override
				public void upAction()
				{
					if (c.is)
					{
						game.ass.get("jump.mp3", Sound.class).play();
						ac.b2body.applyForceToCenter(new  Vector2(0, 600), true);
						c.is = false;
					}
				}
			});
		this.batch = batch;

		cam = new OrthographicCamera();
		cam.setToOrtho(false, Res.w / (ppm + zoom + game.zoom), Res.h / (ppm + zoom + game.zoom));
		//cam.setToOrtho(false,Res.w / ppm,Res.h /ppm);
		map = new TmxMapLoader().load("tmx/0.tmx");
		render = new OrthogonalTiledMapRenderer(map, 1f / ppm, batch);

		dist = new Drawist(4);

		initBox2d();
		world.setContactListener(c = new jumpConcat());
		ac = new funny(world, new Vector2(36 / ppm, 36 / ppm), "w0.png", 9 / ppm);
		nx = 1602 / ppm;
		ny  = 108 / ppm;
		//ac.b2body.setTransform(nx = 1602 / ppm,ny  = 108 / ppm,ac.b2body.getAngle());
		this.game = game;
		ds = new DeadScreen(game, MyGame.Misbatch)
		{
			@Override
			public void cilk(ImageButton bu)
			{
				ac.b2body.setTransform(36 / ppm, 36 / ppm, ac.b2body.getAngle());
				ac.b2body.setLinearVelocity(0, 0);
				Gdx.input.setInputProcessor(ui);
				game.setScreen(level1.this);
			}
		};

		ms = new MissionStage(3);
		mis = new Mission("恭喜", "你逃出了一众表情的围捕,\n下面努力比赛吧!", game.font)
		{

			@Override
			public void cilck(Dialog dialog)
			{
				Gdx.input.setInputProcessor(ui);
				start = false;isShow = false;
			}
		};
		ms.addMission(mis);

	}

	@Override
	public void render(float p1)
	{
		world.step(1 / 60f, 2, 6);

		cam.position.x = ac.b2body.getPosition().x;
		cam.position.y = ac.b2body.getPosition().y;
		cam.update();

		nx = ac.b2body.getPosition().x - 9 / ppm;
		ny = ac.b2body.getPosition().y - 9 / ppm;

		render.setView(cam);
		batch.setProjectionMatrix(cam.combined);

		render.render();

		batch.begin();

		batch.draw(ac, nx, ny, 18 / ppm, 18 / ppm);

		dist.act(p1);
		dist.draw(batch);

		batch.end();

		super.render(p1);
		en.render(world, cam.combined);
		//en.render(ac.world,cam.combined);

		ms.act();
		ms.draw();

		if (start)
		{
			mis.isShow = true;
			Gdx.input.setInputProcessor(mis);
		}

		if (ny < 0)
		{
			ui.cancelTouchFocus();
			Gdx.input.setInputProcessor(ds.st);
			game.setScreen(ds);
		}
		if (nx > 3546 / ppm)
		{
			MyGame.archive.putBoolean("MAIN", true);
			MyGame.archive.flush();
		}
	}

	void initBox2d()
	{
		world = new World(new Vector2(0, -9.81f), true);
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		Body b;

		bdef.type = BodyDef.BodyType.StaticBody;
		fdef.shape = shape;

		for (RectangleMapObject ro : map.getLayers().get("ground").getObjects().getByType(RectangleMapObject.class))
		{
			Rectangle r = ro.getRectangle();

			shape.setAsBox(r.getWidth() / 2 / ppm, r.getHeight() / 2 / ppm);
			bdef.position.set((r.getX() + r.getWidth() / 2) / ppm, (r.getY() + r.getHeight() / 2) / ppm);

			b = world.createBody(bdef);
			b.createFixture(fdef);
		}
		for (RectangleMapObject ro : map.getLayers().get("moveAble").getObjects().getByType(RectangleMapObject.class))
		{
			Rectangle r = ro.getRectangle();
			bdef.type = BodyDef.BodyType.KinematicBody;

			shape.setAsBox(r.getWidth() / 2 / ppm, r.getHeight() / 2 / ppm);
			bdef.position.set(r.getX() / ppm + shape.getRadius(), r.getY() / ppm + shape.getRadius());

			dist.addRenderer(new PlatformActor(new Texture("background/dead.jpg"),
											   new Vector2(r.getX() / ppm + r.getWidth() / 2 / ppm, r.getY() / ppm + r.getHeight() / 2 / ppm),
											   shape,
											   r.getX() / ppm + r.getWidth() / 2 / ppm,
											   r.getX() / ppm + r.getWidth() / 2 / ppm + ro.getProperties().get("move", 330, Integer.class) / ppm,
											   new Vector2(2, 0),
											   world,
											   new Vector2(r.getWidth() / 2 / ppm, r.getHeight() / 2 / ppm),
											   new Vector2(r.getWidth() / ppm, r.getHeight() / ppm)));
		}
	}

}
