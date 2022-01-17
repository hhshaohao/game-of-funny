package hhs.game.funny.games;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.graphics.Color;

public class Mario implements Screen
{

	MyGame game;
	SpriteBatch batch;
	Stage st;

	int speed = 8;
	boolean up,stop,left;
	float nx,ny;
	funny zhu;

	OrthographicCamera cam;
	public static int suo = 100;
	public static float ppm;

	TiledMap map;
	OrthogonalTiledMapRenderer render;

	ImageButton b0,b1,b2;

	World world;
	//Box2DDebugRenderer ren;

	public Mario ( MyGame game, SpriteBatch batch )
	{
		this.game = game;
		this.batch = batch;
		st = new Stage();
		
		ppm = tool.le1;
		stop = left = true;

		cam = new OrthographicCamera();
		cam.setToOrtho(false, Res.w / (ppm + suo + MyGame.zoom), Res.h / (ppm + suo + MyGame.zoom));

		map = new TmxMapLoader().load("tmx/level1.tmx");
		render = new OrthogonalTiledMapRenderer(map, 1 / ppm,batch);

		Res r = new Res(game);
		b0 = r.b0;
		b1 = r.b1;
		b2 = r.b2;
		this.addListener();
		st.addActor(b0);
		st.addActor(b1);
		st.addActor(b2);
		st.addActor(r.exit);
		
		this.initBox2d();
	}

	void initBox2d ( )
	{
		world = new World(new Vector2(0, -9.81f), true);

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		PolygonShape shape = new PolygonShape();

		bdef.type = BodyDef.BodyType.StaticBody;

		for ( RectangleMapObject rt : map.getLayers().get("Ground").getObjects().getByType(RectangleMapObject.class) )
		{
			Rectangle rect = rt.getRectangle();

			bdef.position.set((rect.getX() + rect.getWidth() / 2) / tool.le1, (rect.getY() + rect.getHeight() / 2) / tool.le1);
			Body body;
			body = world.createBody(bdef);
			shape.setAsBox(rect.getWidth() / 2 / ppm , rect.getHeight() / 2 / ppm);
			fdef.shape = shape;

			body.createFixture(fdef);
		}
		
		for ( RectangleMapObject rt : map.getLayers().get("Pipes").getObjects().getByType(RectangleMapObject.class) )
		{
			Rectangle rect = rt.getRectangle();

			bdef.position.set((rect.getX() + rect.getWidth() / 2) / tool.le1, (rect.getY() + rect.getHeight() / 2) / tool.le1);
			Body body;
			body = world.createBody(bdef);
			shape.setAsBox(rect.getWidth() / 2 / ppm, rect.getHeight() / 2 / ppm);
			fdef.shape = shape;

			body.createFixture(fdef);
		}
		
		for ( RectangleMapObject rt : map.getLayers().get("Bricks").getObjects().getByType(RectangleMapObject.class) )
		{
			Rectangle rect = rt.getRectangle();

			bdef.position.set((rect.getX() + rect.getWidth() / 2) / tool.le1, (rect.getY() + rect.getHeight() / 2) / tool.le1);
			Body body;
			body = world.createBody(bdef);
			shape.setAsBox(rect.getWidth() / 2 / ppm , rect.getHeight() / 2 / ppm);
			fdef.shape = shape;

			body.createFixture(fdef);
		}
		
		for ( RectangleMapObject rt : map.getLayers().get("Coins").getObjects().getByType(RectangleMapObject.class) )
		{
			Rectangle rect = rt.getRectangle();

			bdef.position.set((rect.getX() + rect.getWidth() / 2) / tool.le1, (rect.getY() + rect.getHeight() / 2) / tool.le1);
			Body body;
			body = world.createBody(bdef);
			shape.setAsBox(rect.getWidth() / 2 / ppm, rect.getHeight() / 2 / ppm);
			fdef.shape = shape;

			body.createFixture(fdef);
		}
		
		bdef.position.set(-16 / ppm + 4 / ppm,0);
		Body body;
		body = world.createBody(bdef);
		shape.setAsBox(8 / ppm,128);
		fdef.shape = shape;
		body.createFixture(fdef);
		
		zhu = new funny(world, new Vector2(4 * 16 / ppm, 16 / ppm), "w0.png");
		
		//ren = new Box2DDebugRenderer();
	}

	@Override
	public void show ( )
	{
	}

	void update ( float time )
	{
		world.step(1 / 60f, 2, 6);

		cam.update();
		cam.position.x = nx;
		cam.position.y = ny;
		
		batch.setProjectionMatrix(cam.combined);

		render.setView(cam);

		nx = zhu.b2body.getPosition().x - ((16 / tool.le1) / 2);
		ny = zhu.b2body.getPosition().y - ((16 / tool.le1) / 2);

		this.move();
	}

	@Override
	public void render ( float p1 )
	{
		
		this.update(p1);
		
		render.render();
		
		batch.begin();
		batch.draw(zhu, nx, ny,16 / ppm,16 / ppm);
		batch.end();
		
		st.act();
		st.draw();
		
		//ren.render(world,cam.combined);
	}

	void move ( )
	{
		if ( !stop )
		{
			if ( left && zhu.b2body.getLinearVelocity().x > -speed)
			{
				zhu.b2body.applyForceToCenter(new Vector2(-speed, 0), true);
			}
			else if(zhu.b2body.getLinearVelocity().x < speed)
			{
				zhu.b2body.applyForceToCenter(new Vector2(speed, 0), true);
			}
		}
		if ( up && zhu.b2body.getLinearVelocity().y < 0.01f && zhu.b2body.getLinearVelocity().y > -0.01f )
		{
			zhu.b2body.applyForceToCenter(new  Vector2(0, 500), true);
		}
	}

	@Override
	public void resize ( int p1, int p2 )
	{
	}

	@Override
	public void pause ( )
	{
	}

	@Override
	public void resume ( )
	{
	}

	@Override
	public void hide ( )
	{
	}

	@Override
	public void dispose ( )
	{
	}


    void addListener ( )
	{
		b0.addListener(new InputListener(){

				@Override
				public boolean touchDown ( InputEvent event, float x, float y, int pointer, int button )
				{
					left = true;
					stop = false;
					return true;
				}

				@Override
				public void touchUp ( InputEvent event, float x, float y, int pointer, int button )
				{
					stop = true;
				}

			});
		b1.addListener(new InputListener(){

				@Override
				public boolean touchDown ( InputEvent event, float x, float y, int pointer, int button )
				{
					left = false;
					stop = false;
					return true;
				}

				@Override
				public void touchUp ( InputEvent event, float x, float y, int pointer, int button )
				{
					stop = true;
				}

			});
		b2.addListener(new InputListener(){

				@Override
				public boolean touchDown ( InputEvent event, float x, float y, int pointer, int button )
				{
					up = true;
					return true;
				}

				@Override
				public void touchUp ( InputEvent event, float x, float y, int pointer, int button )
				{
					up = false;
				}

			});
	}



}
