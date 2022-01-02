package hhs.game.funny.games;

import hhs.game.funny.games.Screen.TmxScreen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;

public class lernSkill implements Screen
{

	@Override
	public void show ( )
	{

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


	private SpriteBatch batch;
	private ImageButton b0,b1,b2,skill;
	private boolean right,stop,up;
	public static int suo = 100;
	public float ppm;
	public OrthoCachedTiledMapRenderer render;
	public OrthographicCamera cam;
	public TiledMap map;
	private MyGame game;
	Box2DDebugRenderer ren;
	Stage st;
	World world;
	funny zhu;
	float nx,ny,time1;
	static int speed = Level1.speed;

    public lernSkill ( MyGame game, SpriteBatch batch )
	{
		stop = right = true;

		ren = new Box2DDebugRenderer();

		this.game = game;
		map = new TmxMapLoader().load("tmx/le2.tmx");

		ppm = tool.le1;

		cam = new OrthographicCamera();
		cam.setToOrtho(false, Res.w / (ppm + suo + MyGame.zoom), Res.h / (ppm + suo + MyGame.zoom));
		render = new OrthoCachedTiledMapRenderer(map, 1f / ppm);

		st = new Stage();

		this.batch = batch;

		Res r = new Res(game);
		b0 = r.b0;
		b1 = r.b1;
		b2 = r.b2;
		st.addActor(r.exit);

		TextureRegionDrawable d1 = new TextureRegionDrawable(new TextureRegion(MyGame.ass.get("skill.png", Texture.class), 0, 0, 192, 192));
		TextureRegionDrawable d2 = new TextureRegionDrawable(new TextureRegion(MyGame.ass.get("skill.png", Texture.class), 192, 0, 192, 192));
		skill = new ImageButton(d1, d2);
		skill.setBounds(3 * Res.w / 4, Res.h / 3, 200, 200);


		addListener();
		st.addActor(b0);
		st.addActor(b1);
		st.addActor(b2);
		st.addActor(skill);

		initBox2d();
	}


	public void initBox2d ( )
	{
		world = new World(new Vector2(0, -9.81f), true);

		zhu = new funny(world, new Vector2(32 / ppm, 192 / ppm), "w0.png", "funny", 8 / ppm);

		BodyDef bdef = new BodyDef();
		bdef.type = BodyDef.BodyType.StaticBody;
		FixtureDef fdef = new FixtureDef();

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(8 / ppm, 8 / ppm);
		fdef.shape = shape;

		for ( RectangleMapObject rect : map.getLayers().get("rect").getObjects().getByType(RectangleMapObject.class) )
		{
			Body body;
			Rectangle r = rect.getRectangle();
			bdef.position.set((r.x + r.width / 2) / ppm, (r.y + r.height / 2) / ppm);

			body = world.createBody(bdef);

			body.createFixture(fdef);
		}
	}

	public void update ( )
	{
		world.step(1 / 60f, 2, 6);

		cam.update();
		cam.position.x = nx;
		cam.position.y = ny;

		batch.setProjectionMatrix(cam.combined);

		nx = zhu.b2body.getPosition().x - ((16 / ppm) / 2);
		ny = zhu.b2body.getPosition().y - ((16 / ppm) / 2);

		render.setView(cam);

		this.move();
	}

	public void move ( )
	{
		if ( !stop )
		{
			if ( right )
			{
				if ( zhu.b2body.getLinearVelocity().x < speed )
					zhu.b2body.applyForceToCenter(new Vector2(speed, 0), true);
			}
			else
			{
				if ( zhu.b2body.getLinearVelocity().x > -speed )
					zhu.b2body.applyForceToCenter(new Vector2(-speed, 0), true);

			}
		}
		if ( up && zhu.b2body.getLinearVelocity().y < 0.01 && zhu.b2body.getLinearVelocity().y > -0.01 )
		{
			zhu.b2body.applyForceToCenter(new Vector2(0, 450), true);
		}
	}

	@Override
	public void render ( float p1 )
	{
		time1 += p1;

		this.update();

		render.render();

		batch.begin();
		batch.draw(zhu, nx, ny, 16 / ppm, 16 / ppm);
		batch.end();

		st.act();
		st.draw();

		//ren.render(world, cam.combined);
	}


	public void addListener ( )
	{
		b0.addListener(new InputListener(){

				@Override
				public boolean touchDown ( InputEvent event, float x, float y, int pointer, int button )
				{
					right = false;
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
					right = true;
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

		skill.addListener(new InputListener(){

				@Override
				public boolean touchDown ( InputEvent event, float x, float y, int pointer, int button )
				{
					if ( time1 > 2f )
					{
						time1 = 0;
						zhu.b2body.applyForceToCenter(0, 600, true);
					}
					else
					{

					}
					return true;
				}

				@Override
				public void touchUp ( InputEvent event, float x, float y, int pointer, int button )
				{

				}

			});
	}




}
