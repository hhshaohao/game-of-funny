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
/*
选关页面
*/
public class hscreen implements Screen
{

	MyGame g;
	SpriteBatch batch;
	Stage st;
	Table ta;
	ImageButton start,br,so,mario,openWorld;
	Texture l,r;
	float t;
	boolean z;
	OrthographicCamera cam;
	
	World world;
	funny role[];
	//Box2DDebugRenderer ren = new Box2DDebugRenderer();

	public hscreen ( MyGame m, SpriteBatch batch )
	{

		g = m;
		this.batch = batch;
		ta = new Table();

		st = new Stage();
		Gdx.input.setInputProcessor(st);

		start = tool.createButton("ui1.png", "s0.png");
		br = tool.createButton("ui0.png", "s0.png");
		so = tool.createButton("ui2.png", "s1.png");

		ta.setFillParent(true);
		ta.center();
		ta.add(start);
		ta.add(so).padLeft(50);
		ta.row();
		ta.add(br).padTop(100);

		st.addActor(ta);
		
		mario = tool.createButton("ui6.png");
		openWorld = tool.createButton("ui7.png");

		mario.setPosition(0,Res.h - 100);
		openWorld.setPosition(mario.getX(),mario.getHeight() - 100);
		
		st.addActor(mario);
		st.addActor(openWorld);
		
		this.addListener();

		l = MyGame.ass.get("f0.jpg", Texture.class);
		r = MyGame.ass.get("f1.jpg", Texture.class);


		MyGame.font.getData().setScale(5);
		MyGame.font.setColor(Color.BLACK);
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false,Res.w / (tool.le1 ),Res.h / (tool.le1 ));
		
		initBox2d();

		Gdx.input.setInputProcessor(st);
	}
	
	void initBox2d()
	{
		world = new World(new Vector2(0,-9.81f),true);
		
		BodyDef bdef = new BodyDef();
		FixtureDef fdef= new FixtureDef();
		Body body;
		
		bdef.type = BodyDef.BodyType.StaticBody;
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(Res.w / tool.le1,1 / tool.le1);
		//下层框
		fdef.shape = shape;
		bdef.position.set(shape.getRadius() / tool.le1,shape.getRadius() / tool.le1);
		body = world.createBody(bdef);
		body.createFixture(fdef);
		//上层框
		bdef.position.set(shape.getRadius() / tool.le1,(Res.h + shape.getRadius()) / tool.le1);
		body = world.createBody(bdef);
		body.createFixture(fdef);
		//左层框
		shape.setAsBox(1 / tool.le1,Res. h / tool.le1);
		fdef.shape = shape;
		bdef.position.set(shape.getRadius() / tool.le1,shape.getRadius() / tool.le1);
		body = world.createBody(bdef);
		body.createFixture(fdef);
		//右层框
		bdef.position.set((Res.w + shape.getRadius()) / tool.le1,shape.getRadius() / tool.le1);
		body = world.createBody(bdef);
		body.createFixture(fdef);
		
		role = new funny[7];
		for (int i = 0; i < role.length; ++i) {
			role[i] = new funny(world,new Vector2(MathUtils.random(8 / tool.le1,Res.w / tool.le1),MathUtils.random(8 / tool.le1,Res.h / tool.le1)),
			"w"+MathUtils.random(0,2)+".png",32 / tool.le1,1.0f);
			
		}
	}

	@Override
	public void show ( )
	{
	}

	void move()
	{
		for (int i = 0; i < role.length; i++) {
			if(role[i].b2body.getLinearVelocity().y == 0)
			{
				role[i].b2body.applyForceToCenter(MathUtils.random(-500,500),0,true);
			}
		}
	}
	
	@Override
	public void render ( float p1 )
	{
		this.move();
		world.step(1 / 60f,1,1);

		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		batch.draw(l, 0, 0, Res.w / 2 / tool.le1, Res.h / tool.le1);
		batch.draw(r, Res.w / 2 / tool.le1, 0, Res.w / 2 / tool.le1, Res.h / tool.le1);

		MyGame.font.draw(batch, "沙雕之主", Res.w / 2 / tool.le1, (Res.h / 2 + 50) / tool.le1);
		MyGame.font.setColor(0, 0, 0, t);
		if ( z )
		{
			t -= p1;
			if ( t < 0 )
				z = false;
		}
		else
		{
			t += p1;
			if ( t > 1 )
				z = true;
		}
			
		batch.end();

		st.act();
		st.draw();
		
		batch.begin();
		for (int i = 0; i < role.length; i++) {
			batch.draw(role[i], role[i].b2body.getPosition().x - ((64 / tool.le1) / 2), role[i].b2body.getPosition().y - ((64 / tool.le1) / 2), 64 / tool.le1, 64 / tool.le1);
		}
		batch.end();
		
		//ren.render(world,cam.combined);
	}
	
	void addListener(){
		br.addListener(new InputListener(){
				@Override
				public boolean touchDown ( InputEvent event, float x, float y, int pointer, int button )
				{
					return true;
				}

				@Override
				public void touchUp ( InputEvent event, float x, float y, int pointer, int button )
				{
					Gdx.app.exit();
					//super.touchUp(event, x, y, pointer, button);
				}

			});

		start.addListener(new InputListener(){

				@Override
				public boolean touchDown ( InputEvent event, float x, float y, int pointer, int button )
				{
					return true;
				}

				@Override
				public void touchUp ( InputEvent event, float x, float y, int pointer, int button )
				{
					g.goGame();
					//super.touchUp(event, x, y, pointer, button);
				}

			});

		so.addListener(new InputListener(){

				@Override
				public boolean touchDown ( InputEvent event, float x, float y, int pointer, int button )
				{
					return true;
				}

				@Override
				public void touchUp ( InputEvent event, float x, float y, int pointer, int button )
				{
					g.goSo();
					//super.touchUp(event, x, y, pointer, button);
				}

			});
			
			mario.addListener(new InputListener(){
				@Override
				public boolean touchDown ( InputEvent event, float x, float y, int pointer, int button )
				{
					return true;
				}

				@Override
				public void touchUp ( InputEvent event, float x, float y, int pointer, int button )
				{
					g.goMario();
					//super.touchUp(event, x, y, pointer, button);
				}
			});
			
			openWorld.addListener(new InputListener(){
				@Override
				public boolean touchDown ( InputEvent event, float x, float y, int pointer, int button )
				{
					return true;
				}

				@Override
				public void touchUp ( InputEvent event, float x, float y, int pointer, int button )
				{
					g.goMagicLand();
					//super.touchUp(event, x, y, pointer, button);
				}
			});
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
		st.dispose();
		l.dispose();
		r.dispose();
	}




}
