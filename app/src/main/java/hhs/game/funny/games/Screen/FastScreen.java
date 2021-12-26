package hhs.game.funny.games.Screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import hhs.game.funny.games.MyGame;
import hhs.game.funny.games.Res;
import hhs.game.funny.games.funny;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;

/*
 快速实现一个场景
 */
public class FastScreen implements Screen
{
	SpriteBatch batch;
	MyGame game;
	float speed;
	int jumpH;

	TiledMap map;
	OrthogonalTiledMapRenderer render;
	OrthographicCamera cam;
	float ppm;

	ImageButton b0,b1,b2;
	Stage st;

	boolean up,left,stop;

	World world;
	funny zhu;
	float nx,ny;
	String[] r;
	int z,ra;

    public FastScreen(SpriteBatch batch, MyGame game, String TmxName, float speed, int jumpSpeed, float ppm, String[] ceng,int x,int y,int z,int ra)
	{
		this.ppm = ppm;
		this.game  = game;
		this.batch = batch;
		this.speed = speed;
		this.jumpH = jumpSpeed;

		map = new TmxMapLoader().load(TmxName);
		cam = new OrthographicCamera();
		cam.setToOrtho(false, Res.w / ppm , Res.h / ppm);
		render = new OrthogonalTiledMapRenderer(map, 1 / 60f);

		st = new Stage();
		Res r = new Res(game);
		b0 = r.b0;
		b1 = r.b1;
		b2 = r.b2;
		this.addLIstener();
		st.addActor(b0);
		st.addActor(b1);
		st.addActor(b2);
		st.addActor(r.exit);

		this.r = ceng;
		this.z = z;
		this.ra = ra;

		this.initBox2d(x,y);
	}

	void initBox2d(int x,int y)
	{
		world = new World(new Vector2(0, 9.81f), true);

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		bdef.type = BodyDef.BodyType.StaticBody;

		PolygonShape shape = new PolygonShape();
		for( String i : r )
		{
			for( RectangleMapObject ro : map.getLayers().get(i).getObjects().getByType(RectangleMapObject.class))
			{
				Rectangle r = ro.getRectangle();
				
				shape.setAsBox(r.getWidth() / 2 / ppm,r.getHeight() / 2 / ppm);
				fdef.shape = shape;
				
				Body body;
				bdef.position.set((r.getX() + r.getWidth() / 2) / ppm,(r.getY() + r.getHeight() / 2) / ppm);
				body = world.createBody(bdef);
				body.createFixture(fdef);
			}
			for( PolylineMapObject po : map.getLayers().get("ground").getObjects().getByType(PolylineMapObject.class) )
			{
				float d[] = po.getPolyline().getVertices();
				Polyline p = po.getPolyline();
				ChainShape edge = new ChainShape();

				for (int b = 0; b < d.length; ++b) {
					d[b] = d[b] / ppm;
				}

				edge.createChain(d);

				bdef.position.set((p.getX() + edge.getRadius()) / ppm, (p.getY() + edge.getRadius()) / ppm);

				Body body;
				body = world.createBody(bdef);

				fdef.shape = edge;
				body.createFixture(fdef);
			}
		}
		zhu = new funny(world,new Vector2(x,y),"w"+z+".png",ra / ppm);
	}

	@Override
	public void show()
	{
	}

	void update(float p1)
	{
		world.step(1 / 60f,2,6);
		
		nx = zhu.b2body.getPosition().x - ((16 / ppm) / 2);
		ny = zhu.b2body.getPosition().y - ((16 / ppm) / 2);
		
		cam.update();
		cam.position.x = nx;
		cam.position.y = ny;
		
		render.setView(cam);
		batch.setProjectionMatrix(cam.combined);
		
		this.move();
	}
	
	@Override
	public void render(float p1)
	{
		update(p1);
		
		batch.begin();
		batch.draw(zhu,nx,ny,ra * 2 / ppm,ra * 2 / ppm);
		batch.end();
		
		render.render();
		
		st.act();
		st.draw();
	}
	
	void move(){
		if(!stop)
		{
			if(left && zhu.b2body.getLinearVelocity().x < speed)
			{
				zhu.b2body.applyForceToCenter(speed,0,true);
			}else if(zhu.b2body.getLinearVelocity().x > -speed)
			{
				zhu.b2body.applyForceToCenter(-speed,0,true);
			}
		}
		if(up)
		{
			if(zhu.b2body.getLinearVelocity().y < 0.01f && zhu.b2body.getLinearVelocity().y > -0.01)
			{
				zhu.b2body.applyForceToCenter(0,jumpH,true);
			}
		}
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

	
	
	void addLIstener()
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
					up = true;
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y, int pointer, int button)
				{
					up = false;
				}

			});
	}

}
