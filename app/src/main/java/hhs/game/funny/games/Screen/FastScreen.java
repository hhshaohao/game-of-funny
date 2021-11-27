package hhs.game.funny.games.Screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import hhs.game.funny.games.MyGame;
import hhs.game.funny.games.Res;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.math.Vector2;

/*
快速实现一个场景
*/
public class FastScreen {
	
	SpriteBatch batch;
	MyGame game;
	float speed;
	int jumpH;
	
	TiledMap map;
	OrthogonalTiledMapRenderer render;
	OrthographicCamera cam;
	float ppm;
	
	ImageButton b0,b1,b2;
	
	boolean up,left,stop;
	
	World world;
	
    public FastScreen(SpriteBatch batch,MyGame game, String TmxName,float speed,int jumpSpeed,float ppm,String[] ceng)
	{
		this.ppm = ppm;
		this.game  = game;
		this.batch = batch;
		this.speed = speed;
		this.jumpH = jumpSpeed;
		
		map = new TmxMapLoader().load(TmxName);
		cam = new OrthographicCamera();
		cam.setToOrtho(false,Res.w / ppm ,Res.h / ppm);
		render = new OrthogonalTiledMapRenderer(map,1 / 60f);
		
		Res r = new Res();
		b0 = r.b0;
		b1 = r.b1;
		b2 = r.b2;
		this.addLIstener();
		
		this.initBox2d();
	}
    
	void initBox2d(){
		world = new World(new Vector2(0,9.81f),true);
		
	}
	
	void addLIstener(){
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
