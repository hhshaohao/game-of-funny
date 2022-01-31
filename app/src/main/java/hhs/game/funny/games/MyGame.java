package hhs.game.funny.games;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import hhs.game.funny.MainActivity;
import hhs.game.funny.games.MainLineLevel.level1;
import hhs.game.funny.games.Screen.ChooseCustomsScreen;
import hhs.game.funny.games.Screen.Jumper;
import hhs.game.funny.games.Screen.SettingScreen;

public class MyGame extends Game
{

	Stage st;

	startScreen ss;
	mainScreen m;
	lernSkill stu;
	Mario mario;
	so so;
	MagicLand magic;
	SettingScreen settingscreen;

	hscreen h;
	Level1 lev;
	ChooseCustomsScreen ccs;
	level1 l;

	public static Label fps;					//帧率显示
	public static SpriteBatch batch,Misbatch;	//公共资源：画笔
	public static BitmapFont font;				//中文支持
	public static AssetManager ass;				//图像资源
	public static Preferences archive,setting;	//存档读取（暂未使用)
	public static Jumper jump;					//滑稽跳舞
	public static int zoom;						//屏幕缩放
	public static Color clearColor;				//清屏颜色
	public static Image image;					//场景过渡
	public static Animation boom;				//爆炸效果

	public void finish()
	{
		jump = new Jumper();

		archive = Gdx.app.getPreferences("data");
		setting = Gdx.app.getPreferences("setting");

		if( setting.contains("zoom") )
		{
			zoom = setting.getInteger("zoom");
		}
		else
		{
			zoom = 0;
		}

		font = ass.get("font.fnt", BitmapFont.class);	

		h = new hscreen(this, batch);
		settingscreen = new SettingScreen(this);
		so = new so(this);
		ccs = new ChooseCustomsScreen(this);

		/*TextureRegion[] tr = new TextureRegion[14];

		 for (int i = 1; i <= 14; i++) 
		 {
		 tr[i - 1] =new TextureRegion(ass.get("anim/e" + i + ".png",Texture.class));
		 }
		 boom = new Animation(0.1f,tr);*/

		goMain();
		//Gdx.input.setInputProcessor(l.ui);
	}

	/*public <T extends UniversalScreen> void goClass(Stage st)
	 {
	 Gdx.input.setInputProcessor(st);	
	 setScreen();
	 }*/

	public void goChooser()
	{
		transition();
		transition();
		font.getData().setScale(1);
		font.setColor(Color.BLACK);
		Gdx.input.setInputProcessor(ccs.st);
		this.setScreen(ccs);
	}

	public void goMainLine()
	{
		transition();
		l = new level1(this, batch);
		Gdx.input.setInputProcessor(l.ui);
		setScreen(l);
	}

	public void goScreen(Screen s, Stage st)
	{
		transition();
		Gdx.input.setInputProcessor(st);
		setScreen(s);
	}

	public void goLevel1()
	{
		transition();
		if( lev == null )
			lev = new Level1(batch, this);
		Gdx.input.setInputProcessor(lev.st);
		setScreen(lev);
	}

	public void goLevel2()
	{
		transition();
		if( stu == null )
			stu = new lernSkill(this, batch);
		Gdx.input.setInputProcessor(stu.st);
		setScreen(stu);
	}

	public void goMario()
	{
		transition();
		if( mario == null )
			mario = new Mario(this, batch);
		Gdx.input.setInputProcessor(mario.st);
		setScreen(mario);
	}

	public void goSo()
	{
		transition();
		font.setColor(Color.BLACK);
		Gdx.input.setInputProcessor(so.st);
		font.getData().setScale(1.5f);
		setScreen(so);
	}

	public void goGame()
	{
		transition();
		font.setColor(Color.BLACK);
		if( m == null )
			m = new mainScreen(this, batch);
		Gdx.input.setInputProcessor(m.st);
		font.getData().setScale(1);
		setScreen(m);
	}

	public void goMain()
	{
		transition();
		font.setColor(Color.BLACK);
		Gdx.input.setInputProcessor(h.st);
		font.getData().setScale(4);
		setScreen(h);
	}

	public void goMagicLand()
	{
		transition();
		MainActivity.use.showQuickTip("加载中哦……");
		if( magic == null )
			magic = new MagicLand(this, batch);
		Gdx.input.setInputProcessor(magic.st);
		setScreen(magic);
	}
	public void goSetting()
	{
		transition();
		font.setColor(Color.BLACK);
		Gdx.input.setInputProcessor(settingscreen.st);
		font.getData().setScale(1.5f);
		setScreen(settingscreen);
	}

	@Override
	public void create()
	{
		clearColor = Color.WHITE;
		batch = new SpriteBatch();	//初始化画笔
		Misbatch = new SpriteBatch();
		ass = new AssetManager();	//资源管理器

		ss = new startScreen(batch);

		setScreen(ss);	//初始场景设置(加载资源时展示的场景)

		ass.load("font.fnt", BitmapFont.class);	//加载资源

		ass.load("s0.png", Texture.class);
		ass.load("s1.png", Texture.class);
		ass.load("f0.jpg", Texture.class);
		ass.load("f1.jpg", Texture.class);
		ass.load("ui3.png", Texture.class);
		ass.load("w0.png", Texture.class);
		ass.load("w1.png", Texture.class);
		ass.load("w2.png", Texture.class);
		ass.load("jump.mp3", Sound.class);
		ass.load("skill.png", Texture.class);
		ass.load("background/dead.jpg", Texture.class);
		ass.load("ui5.png", Texture.class);
		ass.load("ui8.png", Texture.class);
		ass.load("down.mp3", Sound.class);
		/*for (int i = 1; i <= 14; i++) {
		 ass.load("anim/e" + i + ".gif",Texture.class);
		 }*/
		//ass.load("anim/GameOver.png",Texture.class);

		st = new Stage();

		Label.LabelStyle s = new Label.LabelStyle();
		s.font = new BitmapFont();
		s.font.getData().setScale(4);
		s.fontColor = Color.BLUE;

		fps = new Label("fps:", s);

		st.addActor(fps);
		image = new Image(new Texture("tran.jpg"));
		image.setSize(st.getWidth(), st.getHeight()); 
		image.setOrigin(st.getWidth() / 2, st.getHeight() / 2); 
		image.setColor(Color.CLEAR); 

		st.addActor(image); 


	}

	@Override
	public void render()
	{
		tool.clearScreen(clearColor);
		fps.setText("fps:" + Gdx.graphics.getFramesPerSecond());	//帧率显示

		super.render();

		st.act();
		st.draw();

		if( ss.isOk )	//加载完成
		{
			finish();
			ss.isOk = false;
			ss.dispose();
		}
	}

	public void transition(Color c, float t)
	{
		image.setColor(c);
		image.addAction(Actions.color(Color.CLEAR, t));
	}
	public void transition()
	{
		image.setColor(Color.BLACK);
		image.addAction(Actions.color(Color.CLEAR, 0.5f));
	}

	@Override
	public void dispose()
	{
		archive.flush();
		if( m != null )
			m.dispose();
		if( h != null )
			h.dispose();
		if( lev != null )
			lev.dispose();
		if( batch != null )
			batch.dispose();
		if( Misbatch != null )
			Misbatch.dispose();
		if( jump != null )
			jump.dispose();
		if( ass != null )
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
		MainActivity.use.showQuickTip("暂停");
		super.pause();
	}

	@Override
	public void resume()
	{
		MainActivity.use.showQuickTip("返回");
		super.resume();
	}

}
