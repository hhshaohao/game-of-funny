package hhs.game.funny.games.Screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import hhs.game.funny.games.MyGame;
import hhs.game.funny.games.Res;

public class TmxScreen extends GS
{

	public static int suo;
	public float ppm;
	public OrthoCachedTiledMapRenderer render;
	public OrthographicCamera cam;
	public TiledMap map;

    public TmxScreen(MyGame game, String file, float PPM)
	{
		super(game);
		ppm = PPM;

		cam = new OrthographicCamera();
		cam.setToOrtho(false, Res.w / (PPM + suo), Res.h / (PPM + suo));

		map = new TmxMapLoader().load(file);
		render = new OrthoCachedTiledMapRenderer(map, 1f / PPM);
	}

	@Override
	public void render(float p1)
	{

		render.setView(cam);
		render.render();
	}



}
