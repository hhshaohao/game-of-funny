package hhs.game.funny.games;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Preferences;
import hhs.game.funny.games.Screen.Jumper;

public class MyGame extends Game
{

	startScreen ss;
	static mainScreen m;
	static SpriteBatch batch;
	lernSkill stu;
	Mario mario;
	so so;
	static hscreen h;
	static Level1 lev;
	Stage st;
	static Label fps;
	public static BitmapFont font;
	public static AssetManager ass;
	public static Preferences pre;
	public static Jumper jump;

	public void finish()
	{
		jump = new Jumper();

		pre = Gdx.app.getPreferences("data");

		font = ass.get("font.fnt", BitmapFont.class);	

		h = new hscreen(this, batch);
		lev = new Level1(batch, this);
		stu = new lernSkill(this, batch);
		mario = new Mario(this, batch);
		m = new mainScreen(this, batch);
		so = new so(this);

		goMain();
	}

	public void goLevel1()
	{
		Gdx.input.setInputProcessor(lev.st);
		setScreen(lev);
	}

	public void goLevel2()
	{
		
		Gdx.input.setInputProcessor(stu.st);
		setScreen(stu);
	}

	public void goMario()
	{
		
		Gdx.input.setInputProcessor(mario.st);
		setScreen(mario);
	}

	public void goSo()
	{
		Gdx.input.setInputProcessor(so.st);
		font.getData().setScale(1.5f);
		setScreen(so);
	}

	public void goGame()
	{
		
		Gdx.input.setInputProcessor(m.st);
		font.getData().setScale(1);
		setScreen(m);
	}

	public void goMain()
	{
		Gdx.input.setInputProcessor(h.st);
		font.getData().setScale(4 / tool.le1);
		setScreen(h);
	}

	public void goMagicLand()
	{
		MagicLand l = new MagicLand(this, batch);
		Gdx.input.setInputProcessor(l.st);
		setScreen(l);
	}

	@Override
	public void create()
	{
		batch = new SpriteBatch();
		ass = new AssetManager();

		ss = new startScreen(batch);

		setScreen(ss);
		ass.load("font.fnt", BitmapFont.class);

		ass.load("s0.png", Texture.class);
		ass.load("s1.png", Texture.class);
		ass.load("f0.jpg", Texture.class);
		ass.load("f1.jpg", Texture.class);
		ass.load("ui3.png", Texture.class);
		ass.load("anim0.png", Texture.class);
		ass.load("anim1.png", Texture.class);
		ass.load("w0.png", Texture.class);
		ass.load("w1.png", Texture.class);
		ass.load("w2.png", Texture.class);
		ass.load("jump.mp3", Sound.class);
		ass.load("skill.png", Texture.class);
		ass.load("background/dead.jpg", Texture.class);
		ass.load("ui5.png", Texture.class);

		st = new Stage();

		Label.LabelStyle s = new Label.LabelStyle();
		s.font = new BitmapFont();
		s.font.getData().setScale(4);
		s.fontColor = Color.BLUE;

		fps = new Label("fps:", s);

		st.addActor(fps);

	}

	@Override
	public void render()
	{
	    Gdx.gl.glClearColor(1, 1, 1, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		fps.setText("fps:" + Gdx.graphics.getFramesPerSecond());

		super.render();

		st.act();
		st.draw();

		if( ss.isOk )
		{
			finish();
			ss.isOk = false;
			ss.dispose();
		}
	}

	@Override
	public void dispose()
	{
		pre.flush();
		if( m != null )
			m.dispose();
		if( h != null )
			h.dispose();
		if( lev != null )
			lev.dispose();
		if( batch != null )
			batch.dispose();
		ass.dispose();

	}

	@Override
	public void resize(int width, int height)
	{
		super.resize(width, height);
	}

	@Override
	public void pause()
	{
		super.pause();
	}

	@Override
	public void resume()
	{
		super.resume();
	}

}
