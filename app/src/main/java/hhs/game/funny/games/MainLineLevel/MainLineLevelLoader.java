package hhs.game.funny.games.MainLineLevel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import hhs.game.funny.MainActivity;
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
import com.badlogic.gdx.math.MathUtils;
import hhs.game.funny.games.hscreen;
import com.badlogic.gdx.graphics.Color;
import hhs.game.funny.games.tool;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
//主线关卡
public class MainLineLevelLoader extends CommonlyScreen
{
	MyGame game;
	OrthographicCamera cam;
	float ppm = 20,zoom = 50;
	SpriteBatch batch;
	//Box2DDebugRenderer ren = new Box2DDebugRenderer();
	DeadScreen ds;

	static float speed = 8;
	float nx,ny,ox,oy,ex;
	static funny zhu;

	TiledMap map;
	OrthogonalTiledMapRenderer render;

	World world;
	Drawist dist;
	static jumpConcat c;

	Mission mis;
	MissionStage ms;
	int l;

	Texture bg;
	float az = ppm + zoom + MyGame.zoom;
	int ax,ay;

    public MainLineLevelLoader(final MyGame game, String tmxFile, final int l)
	{
		super(game, new  RoleLogic()
			{

				@Override
				public void leftAction()
				{
					if (zhu.b2body.getLinearVelocity().x > -speed)
						zhu.b2body.applyForceToCenter(new Vector2(-speed, 0), true);	
				}

				@Override
				public void rightAction()
				{
					if (zhu.b2body.getLinearVelocity().x < speed)
						zhu.b2body.applyForceToCenter(new Vector2(speed, 0), true);
				}

				@Override
				public void upAction()
				{
					if (c.is)
					{
						game.ass.get("jump.mp3", Sound.class).play();
						zhu.b2body.applyForceToCenter(new  Vector2(0, 600), true);
						c.is = false;
					}
				}
			}, true);
		this.game = game;
		cam = new OrthographicCamera();
		cam.setToOrtho(false, Res.w / (ppm + zoom + MyGame.zoom), Res.h / (ppm + zoom + MyGame.zoom));
		batch = MyGame.batch;

		map = new TmxMapLoader().load(tmxFile);

		render = new OrthogonalTiledMapRenderer(map, 1 / ppm);
		dist = new Drawist();
		this.initBox2d();

		ds = new DeadScreen(game, MyGame.Misbatch)
		{
			@Override
			public void cilk(ImageButton bu)
			{
				zhu.b2body.setTransform(ox, oy, zhu.b2body.getAngle());
				zhu.b2body.setLinearVelocity(0, 0);
				Gdx.input.setInputProcessor(ui);
				cam.position.x = ox;
				cam.position.y = oy;
				cam.update();
				game.setScreen(MainLineLevelLoader.this);
			}
		};

		mis = new Mission("恭喜", "您成功通过第" + l + "关", MyGame.font)
		{
			@Override
			public void cilck(Dialog dialog)
			{
				String s = "tmx/" + (l + 1) + ".tmx";
				FileHandle fh =  Gdx.files.internal(s);
				if (fh.exists())
				{
					game.transition();
					MainLineLevelLoader mll = new MainLineLevelLoader(game, s, l + 1);
					Gdx.input.setInputProcessor(mll.ui);
					game.setScreen(mll);
					MainLineLevelLoader.this.dispose();
				}
				else
				{
					MainActivity.use.showQucikDialog("恭喜", "你通关了。\n你成功当上了沙雕之主\n你可以去往你的沙雕宫殿了", new Runnable()
						{
							@Override
							public void run()
							{							
								game.archive.putBoolean("WIN", true);
								game.archive.flush();
								game.h = new hscreen(game, batch);
								game.goMagicLand();
								MainLineLevelLoader.this.dispose();
							}
						});
				}
			}
		};
		mis.isShow = false;
		ms = new MissionStage();
		ms.addMission(mis);

		bg = game.ass.get("bg.png", Texture.class);
		Color c= new Color(0, 0.623f, 1, 1);
		game.clearColor = c;
	}

	@Override
	public void render(float p1)
	{
		world.step(1 / 60f, 2, 6);

		nx = zhu.b2body.getPosition().x - zhu.ra;
		ny = zhu.b2body.getPosition().y - zhu.ra;

		tool.update(cam,zhu.b2body.getPosition().x,zhu.b2body.getPosition().y);

		cam.update();

		render.setView(cam);
		batch.setProjectionMatrix(cam.combined);

		batch.begin();
		batch.draw(bg, cam.position.x - Res.w / az / 2 - 0.2f * nx,
				   cam.position.y - Res.h / az / 2 - 0.2f * ny,
				   Res.w / (az),
				   Res.h / (az));
		batch.draw(bg, cam.position.x - Res.w / az / 2 - 0.2f * nx + bg.getWidth() / az,
				   cam.position.y - Res.h / az / 2 - 0.2f * ny,
				   Res.w / (az),
				   Res.h / (az));
		batch.end();

		render.render();

		batch.begin();
		batch.draw(zhu, nx, ny, zhu.ra * 2, zhu.ra * 2);

		dist.act(p1);
		dist.draw(batch);

		batch.end();

		ms.act();
		ms.draw();

		super.render(p1);

		if (ny < 0)
		{
			//zhu.b2body.applyForceToCenter(new Vector2(0, 1200), true);
			ui.cancelTouchFocus();
			Gdx.input.setInputProcessor(ds.st);
			game.setScreen(ds);
		}
		if (nx > ex)
		{
			ui.cancelTouchFocus();
			Gdx.input.setInputProcessor(mis);
			mis.isShow = true;
		}
		//ren.render(world,cam.combined);
	}

	void initBox2d()
	{
		world = new World(new Vector2(0, -9.81f), true);
		world.setContactListener(c = new jumpConcat());
		BodyDef bdef = new BodyDef();
		bdef.type = BodyDef.BodyType.StaticBody;

		Body body;

		PolygonShape shape = new PolygonShape();

		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;

		for (RectangleMapObject ro : map.getLayers().get("ground").getObjects().getByType(RectangleMapObject.class))
		{
			Rectangle r = ro.getRectangle();

			shape.setAsBox(r.getWidth() / 2 / ppm, r.getHeight() / 2 / ppm);

			bdef.position.set((r.getX() + r.getWidth() / 2) / ppm, (r.getY() + r.getHeight() / 2) / ppm);

			body = world.createBody(bdef);
			
			fdef.filter.categoryBits = tool.ground;
			fdef.filter.maskBits = tool.play + 1;

			body.createFixture(fdef);
		}
		/*ChainShape chain = new ChainShape();
		 for( PolylineMapObject po : map.getLayers().get("ground").getObjects().getByType(PolylineMapObject.class) )
		 {
		 Polyline p = po.getPolyline();

		 float[] pos = p.getVertices();
		 for( int i = 0; i < pos.length; i++ )
		 {
		 pos[i] = pos[i] / ppm;
		 }
		 chain.createChain(pos);

		 fdef.shape = chain;

		 bdef.position.set((p.getX() + chain.getRadius()) / ppm, (p.getY() + chain.getRadius()) / ppm);

		 body = world.createBody(bdef);
		 body.setUserData("g");
		 body.createFixture(fdef);
		 }*/
		for (RectangleMapObject ro : map.getLayers().get("born").getObjects().getByType(RectangleMapObject.class))
		{
			Rectangle r = ro.getRectangle();
			zhu = new funny(world,
							new Vector2(ox = (r.getX() + r.getWidth() / 2) / ppm, oy = (r.getY() + r.getHeight() / 2) / ppm),
							0, r.getWidth() / 2 / ppm
							);
			super.ac(zhu);
			break;
		}
		for (RectangleMapObject ro : map.getLayers().get("moveAble").getObjects().getByType(RectangleMapObject.class))
		{
			Rectangle r = ro.getRectangle();
			bdef.type = BodyDef.BodyType.KinematicBody;

			shape.setAsBox(r.getWidth() / 2 / ppm, r.getHeight() / 2 / ppm);
			bdef.position.set(r.getX() / ppm + shape.getRadius(), r.getY() / ppm + shape.getRadius());

			dist.addRenderer(new PlatformActor(new Texture("background/dead.jpg"),
											   new Vector2(MathUtils.random(Integer.parseInt(ro.getProperties().get("s", String.class)), Integer.parseInt(ro.getProperties().get("e", String.class))) / ppm, r.getY() / ppm + r.getHeight() / 2 / ppm),
											   shape,
											   Integer.parseInt(ro.getProperties().get("s", String.class)) / ppm,
											   Integer.parseInt(ro.getProperties().get("e", String.class)) / ppm,
											   new Vector2(2, 0),
											   world,
											   new Vector2(r.getWidth() / 2 / ppm, r.getHeight() / 2 / ppm),
											   new Vector2(r.getWidth() / ppm, r.getHeight() / ppm)));
		}
		for (RectangleMapObject ro : map.getLayers().get("e").getObjects().getByType(RectangleMapObject.class))
		{
			ex = ro.getRectangle().getX() / ppm;
			break;
		}
	}

	@Override
	public void dispose()
	{
		super.dispose();
		ds.dispose();
		map.dispose();
		render.dispose();
		world.dispose();
		ms.dispose();
	}
}
