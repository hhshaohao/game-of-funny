package hhs.game.funny.games.MainLineLevel;

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
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import hhs.game.funny.games.Actor.PlatformActor;
import hhs.game.funny.games.MyGame;
import hhs.game.funny.games.Res;
import hhs.game.funny.games.Runnable.RoleLogic;
import hhs.game.funny.games.Screen.CommonlyScreen;
import hhs.game.funny.games.Actor.FastRoleActor;
import hhs.game.funny.games.Tools.Controler;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import hhs.game.funny.games.funny;
import hhs.game.funny.games.tool;

public class level1 extends CommonlyScreen
{
	MyGame game;
	SpriteBatch batch;
	float nx,ny;
	OrthographicCamera cam;
	float zoom = 100,ppm = 20f;
	static int speed = 8;
	Box2DDebugRenderer en  = new Box2DDebugRenderer();

	TiledMap map;
	OrthogonalTiledMapRenderer render;

	World world;

	static funny ac;

    public level1(MyGame game, SpriteBatch batch)
	{
		super(game, new RoleLogic(){

				@Override
				public void leftAction()
				{
					if( ac.b2body.getLinearVelocity().x > -speed )
						ac.b2body.applyForceToCenter(new Vector2(-speed, 0), true);	
				}

				@Override
				public void rightAction()
				{
					if( ac.b2body.getLinearVelocity().x < speed )
						ac.b2body.applyForceToCenter(new Vector2(speed, 0), true);
				}

				@Override
				public void upAction()
				{
					if( ac.b2body.getLinearVelocity().y < 0.1f && ac.b2body.getLinearVelocity().y > -0.1f )
					{
						ac.b2body.applyForceToCenter(new  Vector2(0, 600), true);
					}
				}
			});
		this.batch = batch;

		cam = new OrthographicCamera();
		cam.setToOrtho(false, Res.w / (ppm + zoom + game.zoom), Res.h / (ppm + zoom + game.zoom));
		//cam.setToOrtho(false,Res.w / ppm,Res.h /ppm);
		map = new TmxMapLoader().load("tmx/ml1.tmx");
		render = new OrthogonalTiledMapRenderer(map, 1f / ppm, batch);

		initBox2d();

		ac = new funny(world,new Vector2(36 / ppm,36 / ppm),"w0.png",9 / ppm);
		nx = ny = 0;
	}

	@Override
	public void render(float p1)
	{
		world.step(1 / 60f,2,6);
		
		cam.position.x = ac.b2body.getPosition().x;
		cam.position.y = ac.b2body.getPosition().y;
		cam.update();
		
		nx = ac.b2body.getPosition().x - 9 / ppm;
		ny = ac.b2body.getPosition().y - 9 / ppm;
		
		render.setView(cam);
		batch.setProjectionMatrix(cam.combined);
		
		render.render();
		
		batch.begin();
		batch.draw(ac,nx,ny,18 / ppm,18 / ppm);
		batch.draw(ac,0,0,18 / ppm,18 / ppm);
		batch.end();
		
		super.render(p1);
		en.render(world,cam.combined);
		//en.render(ac.world,cam.combined);
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

		for( RectangleMapObject ro : map.getLayers().get("b").getObjects().getByType(RectangleMapObject.class) )
		{
			Rectangle r = ro.getRectangle();

			shape.setAsBox(r.getWidth() / 2 / ppm, r.getHeight() / 2 / ppm);
			bdef.position.set((r.getX() + r.getWidth() / 2) / ppm, (r.getY() + r.getHeight() / 2) / ppm);

			b = world.createBody(bdef);
			b.createFixture(fdef);
		}
		/*for( RectangleMapObject ro : map.getLayers().get("moveAble").getObjects().getByType(RectangleMapObject.class) )
		{
			Rectangle r = ro.getRectangle();
			bdef.type = BodyDef.BodyType.KinematicBody;

			shape.setAsBox(r.getWidth() / 2 / ppm, r.getHeight() / 2 / ppm);
			bdef.position.set(r.getX() / ppm + shape.getRadius(), r.getY() / ppm + shape.getRadius());

			gs.addActor(new PlatformActor(new Texture("background/dead.jpg"),
										  new Vector2(r.getX() / ppm + shape.getRadius(), r.getY() / ppm + shape.getRadius()),
										  shape,
										  r.getX() / ppm + shape.getRadius(),
										  r.getX() / ppm + shape.getRadius() + 306,
										  new Vector2(10 / ppm, 0),
										  world,
										  new Vector2(r.getWidth() / 2 / ppm, r.getHeight() / 2 / ppm)));
		}*/
	}

}
