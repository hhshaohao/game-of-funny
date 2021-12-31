package hhs.game.funny.games;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class mainScreen implements Screen
{

    private MyGame game;
    private SpriteBatch batch;
    private TiledMap map;
    private OrthogonalTiledMapRenderer tiledRender;
    private Sprite msprite;
    private ImageButton b0,b1,b2,b3;
	private Texture fun;
	private Label welcome,ready;
	private Body funb[];
	//private Rectangle[] rectm;

    float nx,ny;
    int screenZoom = 15;
    OrthographicCamera cam;
    Stage st;
	float time,t;
	boolean right,jump,stop = true;

    //box2d对象
    World world;
    Body mbody;
    //Box2DDebugRenderer renderofb;

    int speed = 7;

	public mainScreen ( MyGame game, SpriteBatch batch )
	{
        this.game = game;
        this.batch = batch;

		//rectm = new Rectangle[3];

        cam = new OrthographicCamera();
        cam.setToOrtho(false, (Res.w / (tool.PPM + screenZoom)), (Res.h / (tool.PPM + screenZoom)));

        map = new TmxMapLoader().load("tmx/map0.tmx");
        tiledRender = new OrthogonalTiledMapRenderer(map, 1 / tool.PPM);

        msprite = new Sprite(new Texture("w0.png"));

        st = new Stage();
        Gdx.input.setInputProcessor(st);

        //初始化ui
        TextureRegion a = tool.createRegion("move3.png");
        a.flip(true, false);
        TextureRegion b,c,d;
        b = tool.createRegion("move1.png");
        b.flip(false, true);
        c = tool.createRegion("move0.png");
        c.flip(false, true);
        d = tool.createRegion("move2.png");
        d.flip(true, false);
        b0 = new ImageButton(new TextureRegionDrawable(a), new TextureRegionDrawable(d));
        b1 = tool.createButton("move3.png", "move2.png");
        b2 = new ImageButton(new TextureRegionDrawable(b), new TextureRegionDrawable(c));
		b3 = tool.createButton("ui4.png");

        b0.setPosition(0, 0);
        b1.setPosition(2 * b0.getWidth() / 2, 0);
        b2.setPosition(Res.w - b2.getWidth(), 0);
		b3.setPosition(0, Res.h - b3.getHeight());

        //添加演员（ui）
        st.addActor(b0);
        st.addActor(b1);
        st.addActor(b2);
		st.addActor(b3);

		fun = new Texture("w0.png");

		MyGame.font.getData().setScale(1.2f);
		Label.LabelStyle style = new Label.LabelStyle(MyGame.font, Color.BLUE);

		welcome = new Label("欢迎来到沙雕世界", style);
		welcome.setPosition(Res.w / 2, Res.h / 2);

		ready = new Label("请你先熟悉一下操作然后点击'正式开始'以开始游戏", style);
		ready.setPosition(Res.w / 2, Res.h / 2 - welcome.getHeight());

		st.addActor(welcome);
		st.addActor(ready);

		//增加监听
        this.addListen();

        //初始化物理引擎（box2d）
        this.init_box2d();
    }

    public void addListen ( )
	{

        b0.addListener(new InputListener(){

                @Override
                public boolean touchDown ( InputEvent event, float x, float y, int pointer, int button )
				{
					right = false;
					stop = false;
                    return true;
                    //return super.touchDown(event, x, y, pointer, button);
                }

                @Override
                public void touchUp ( InputEvent event, float x, float y, int pointer, int button )
				{
                    stop = true;
                    // super.touchUp(event, x, y, pointer, button);
                }

            });

        b1.addListener(new InputListener(){

                @Override
                public boolean touchDown ( InputEvent event, float x, float y, int pointer, int button )
				{
					right  = true;
					stop = false;
                    return true;
                    //return super.touchDown(event, x, y, pointer, button);
                }

                @Override
                public void touchUp ( InputEvent event, float x, float y, int pointer, int button )
				{
                    stop = true;
                    //super.touchUp(event, x, y, pointer, button);
                }

            });
        b2.addListener(new InputListener(){

                @Override
                public boolean touchDown ( InputEvent event, float x, float y, int pointer, int button )
				{
                    jump = true;
                    return true;
                    //return super.touchDown(event, x, y, pointer, button);
                }

				@Override
                public void touchUp ( InputEvent event, float x, float y, int pointer, int button )
				{
                    jump = false;
                    //super.touchUp(event, x, y, pointer, button);
                }

            });
		b3.addListener(new InputListener(){

				@Override
				public boolean touchDown ( InputEvent event, float x, float y, int pointer, int button )
				{
					game.goLevel1();
					return true;
					//return super.touchDown(event, x, y, pointer, button);
				}
			});
    }

    public void init_box2d ( )
	{
		funb = new Body[3];

        world = new World(new Vector2(0, -9.81f), true);
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(50 / tool.PPM, 500 / tool.PPM);
        //mbody.setUserData(new Image());
        mbody = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(26 / tool.PPM);
        fdef.restitution = 0f;
        fdef.shape = shape;
        mbody.createFixture(fdef);

        for ( RectangleMapObject mo: map.getLayers().get("rect").getObjects().getByType(RectangleMapObject.class) )
		{
			Rectangle rect = mo.getRectangle();
			//if (!mo.getProperties().get("name").equals("im")) {
			Body body;
			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set((rect.getX() + rect.getWidth() / 2) / tool.PPM, (rect.getY() + rect.getHeight() / 2) / tool.PPM);
			body = world.createBody(bdef);
			PolygonShape r = new PolygonShape();
			r.setAsBox((rect.getWidth() / 2) / tool.PPM, (rect.getHeight() / 2) / tool.PPM);
			fdef.shape = r;
			body.createFixture(fdef);
			/*}else{
			 rectm[i]=rect;
			 i++;
			 }*/
        }
        //renderofb = new Box2DDebugRenderer();

		fdef.restitution = 0;
		bdef.type = BodyDef.BodyType.DynamicBody;
		for ( int i = 0; i < funb.length; i++ )
		{
			bdef.position.set(MathUtils.random(90 / tool.PPM, 300 / tool.PPM), MathUtils.random(500 / tool.PPM, 600 / tool.PPM));
			funb [ i ] = world.createBody(bdef);
			CircleShape ms;
			ms = new CircleShape();
			ms.setRadius(26 / tool.PPM);
			fdef.shape = ms;
			funb [ i ].createFixture(fdef);
		}
    }

    //在绘图前更新相机，坐标，世界
    public void update ( )
	{
        cam.update();
        cam.position.x = mbody.getPosition().x;
		cam.position.y = mbody.getPosition().y;

        batch.setProjectionMatrix(cam.combined);

        world.step(1 / 60f, 3, 2);

        nx = mbody.getPosition().x - (52 / tool.PPM) / 2;
        ny = mbody.getPosition().y - (52 / tool.PPM) / 2;

	}

    @Override
    public void show ( )
	{
    }

    public void move ( )
	{
		if ( !stop )
		{
			if ( right )
			{
				if ( mbody.getLinearVelocity().x < speed )
				{
					mbody.applyForceToCenter(new Vector2(speed, 0), true);
				}
			}
			else
			{
				if ( mbody.getLinearVelocity().x > -speed )
				{
					mbody.applyForceToCenter(new Vector2(-speed, 0), true);
				}
			}
		}
		if ( jump )
		{
			if ( mbody.getLinearVelocity().y < 0.01f && mbody.getLinearVelocity().y > -0.01f )
			{
				mbody.applyForceToCenter(new Vector2(0, 300f), true);
			}
		}
    }

    @Override
    public void render ( float p1 )
	{
        this.update();
        this.move();
		time += p1 / 2;
		t += p1;

        tiledRender.setView(cam);
        tiledRender.render();

        batch.begin();

		for ( int i = 0; i < funb.length; i++ )
		{
			batch.draw(fun, funb [ i ].getPosition().x - (52 / tool.PPM) / 2, funb [ i ].getPosition().y - (52 / tool.PPM) / 2, 52 / tool.PPM, 52 / tool.PPM);
			let(funb [ i ]);
		}

      	batch.draw(msprite, nx, ny, 52 / tool.PPM, 52 / tool.PPM);

        batch.end();

		welcome.setColor(0, 0, time, 1);
		if ( time > 1 )
			time = 0;
		/*if(t>10f){
		 game.goMain();
		 }*/

        st.act();
        st.draw();

        //renderofb.render(world, cam.combined);
    }

	private void let ( Body funb )
	{
		if ( funb.getLinearVelocity().y == 0 )
			funb.applyForceToCenter(new Vector2(7f, 400f), true);
	}

    @Override
    public void resize ( int p1, int p2 )
	{
        Res.w = Gdx.graphics.getWidth();
        Res.h = Gdx.graphics.getHeight();
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
		msprite.getTexture().dispose();
        st.dispose();
		map.dispose();
        tiledRender.dispose();
        map.dispose();
    }

}
