package hhs.game.funny.games;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import hhs.game.funny.MainActivity;
import hhs.game.funny.games.MainLineLevel.MainLineLevelLoader;
import hhs.game.funny.games.Screen.ChooseCustomsScreen;
import hhs.game.funny.games.Screen.EmojiViewScreen;
import hhs.game.funny.games.Screen.Jumper;
import hhs.game.funny.games.Screen.LocalMapEntrance;
import hhs.game.funny.games.Screen.PractiseScreen;
import hhs.game.funny.games.Screen.SettingScreen;
import java.util.HashMap;
import java.util.Iterator;
//游戏入口类
public class MyGame extends Game
{

	Stage st;	//最上层舞台

	startScreen ss;					//开始场景
	mainScreen m;					//教学场景
	Mario mario;					//马里奥场景
	so so;							//背景故事场景
	MagicLand magic;				//胜利宫殿
	SettingScreen settingscreen;	//设置界面
	//lernSkill stu;

	public hscreen h;					//选择页
	public Level1 lev;					//第一关页面
	public ChooseCustomsScreen ccs;		//选关页面
	public PractiseScreen  ps;			//练习关卡页面
	public LocalMapEntrance lmp;		//本地地图入口
	public PractiseScreen teampScreen;	//当前场景
	public HashMap<String,PractiseScreen> map = new HashMap<>();//优化目录读取
	public EmojiViewScreen evs;			//表情一览
	//公众资源
	public static Label fps,heap;						//帧率显示，内存占用显示
	public static SpriteBatch batch,Misbatch;			//渲染器
	public static BitmapFont font;						//中文支持
	public static AssetManager ass;						//图像资源
	public static Preferences archive,setting,emojiF;	//本地数据读取
	public static Jumper jump;							//滑稽跳舞
	public static int zoom;								//屏幕缩放
	public static Color clearColor;						//清屏颜色
	public static Image image;							//场景过渡
	public static TextureRegion emoji[];				//表情包

	//当主要静态资源加载完毕后
	public void finish()
	{
		MainActivity.use.showQuickTip(Gdx.files.isExternalStorageAvailable() ? "本地文件可读取" : "失败");

		//初始化本地存储对象
		archive = Gdx.app.getPreferences("data");
		setting = Gdx.app.getPreferences("setting");

		if (setting.contains("zoom"))
		{
			zoom = setting.getInteger("zoom");
		}
		else
		{
			zoom = 0;
		}

		//初始化所有表情
		TextureRegion temp = new TextureRegion(ass.get("p0.png", Texture.class));
		TextureRegion temp1 = new TextureRegion(ass.get("p1.png", Texture.class));
		int shu = 49;
		TextureRegion temp2[][] = temp.split(128, 128);
		TextureRegion temp3[][] = temp1.split(107, 108);
		emoji = new TextureRegion[shu];
		int zh = 0;
		for (int i = 0; i < temp2.length; i++)
		{
			for (int j = 0; j < temp2[0].length; j++)
			{
				emoji[zh] = temp2[i][j];
				++zh;
			}
		}
		for (int i = 0; i < temp3.length; i++)
		{
			for (int j = 0; j < temp3[0].length; j++)
			{
				emoji[zh] = temp3[i][j];
				++zh;
			}
		}

		jump = new Jumper();

		//初始化中文字体
		font = ass.get("font.fnt", BitmapFont.class);	
		//加载一些场景
		h = new hscreen(this, batch);
		settingscreen = new SettingScreen(this);
		so = new so(this);
		ccs = new ChooseCustomsScreen(this);
		ps = new PractiseScreen(this, "tmx/practise/", false);
		lmp = new LocalMapEntrance(this);
		evs = new EmojiViewScreen(this);

		//去到主场景
		//这里也可以换成该类中'go'前缀的函数
		goMain();
	}
	//去关卡选择
	public void goChooser()
	{
		transition();
		font.getData().setScale(1);
		font.setColor(Color.BLACK);
		Gdx.input.setInputProcessor(ccs.st);
		this.setScreen(ccs);
	}
	//去主线关卡
	public void goMainLine()
	{
		font.getData().setScale(1);
		String s = "tmx/0.tmx";
		FileHandle fh =  Gdx.files.internal(s);
		if (fh.exists())
		{
			transition();
			MainLineLevelLoader mll = new MainLineLevelLoader(this, s, 1);
			Gdx.input.setInputProcessor(mll.ui);
			setScreen(mll);
		}
		else
		{
			MainActivity.use.showQucikDialog("恭喜", "你通关了。\n你成功当上了沙雕之主\n你可以去往你的沙雕宫殿了", new Runnable()
				{
					@Override
					public void run()
					{							
						archive.putBoolean("WIN", true);
						archive.flush();
						h = new hscreen(MyGame.this, batch);
						goMagicLand();
					}
				});
		}
	}
	//去一个场景
	public void goScreen(Screen s, Stage st)
	{
		transition();
		font.getData().setScale(1);
		Gdx.input.setInputProcessor(st);
		setScreen(s);
	}
	//去第一关
	public void goLevel1()
	{
		transition();
		font.getData().setScale(1);
		lev = new Level1(batch, this);
		Gdx.input.setInputProcessor(lev.st);
		setScreen(lev);
	}

	/*public void goLevel2()
	 {
	 transition();
	 if( stu == null )
	 stu = new lernSkill(this, batch);
	 Gdx.input.setInputProcessor(stu.st);
	 setScreen(stu);
	 }*/
	//去马里奥关
	public void goMario()
	{
		transition();
		font.getData().setScale(1);
		if (mario == null)
			mario = new Mario(this, batch);
		Gdx.input.setInputProcessor(mario.st);
		setScreen(mario);
	}
	//去背景故事
	public void goSo()
	{
		transition();
		font.setColor(Color.BLACK);
		Gdx.input.setInputProcessor(so.st);
		font.getData().setScale(1.5f);
		setScreen(so);
	}
	//去到学习界面
	public void goGame()
	{
		transition();
		font.setColor(Color.BLACK);
		if (m == null)
			m = new mainScreen(this, batch);
		Gdx.input.setInputProcessor(m.st);
		font.getData().setScale(1);
		setScreen(m);
	}
	//去主场景
	public void goMain()
	{
		transition();
		font.setColor(Color.BLACK);
		Gdx.input.setInputProcessor(h.st);
		font.getData().setScale(4);
		setScreen(h);
	}
	//去开放世界
	public void goMagicLand()
	{
		transition();
		font.getData().setScale(1);
		MainActivity.use.showQuickTip("加载中哦……");
		if (magic == null)
			magic = new MagicLand(this, batch);
		Gdx.input.setInputProcessor(magic.st);
		setScreen(magic);
	}
	//去到设置
	public void goSetting()
	{
		transition();
		font.setColor(Color.BLACK);
		Gdx.input.setInputProcessor(settingscreen.st);
		font.getData().setScale(1.5f);
		setScreen(settingscreen);
	}
	//去到练习界面
	public void goPractise()
	{
		transition();
		font.getData().setScale(1);
		font.setColor(Color.BLACK);
		Gdx.input.setInputProcessor(ps.st);
		this.setScreen(ps);

	}
	//去到本地地图场景
	public void goLocal()
	{
		transition();
		font.getData().setScale(1.5f);
		font.setColor(Color.BLACK);
		Gdx.input.setInputProcessor(lmp.st);
		this.setScreen(lmp);
	}
	//去到表情一览
	public void goViewEmoji()
	{
		transition();
		font.getData().setScale(1.0f);
		font.setColor(Color.BLACK);
		Gdx.input.setInputProcessor(evs.st);
		setScreen(evs);
	}
	//重开
	public void reStart()
	{
		transition();
		font.getData().setScale(1);
		if (lev != null)
		{
			lev.dispose();
			lev = null;
		}
		h = new hscreen(this, batch);
		lev = new Level1(batch, this);
		goLevel1();
	}
	//去到TeampScreen
	public void goThat()
	{
		transition();
		font.getData().setScale(1);
		font.setColor(Color.BLACK);
		Gdx.input.setInputProcessor(teampScreen.st);
		this.setScreen(teampScreen);
	}
	//初始化线程
	@Override
	public void create()
	{
		clearColor = Color.WHITE;
		batch = new SpriteBatch();	//初始化画笔
		Misbatch = new SpriteBatch();
		ass = new AssetManager();	//资源管理器

		ss = new startScreen(batch);

		setScreen(ss);	//初始场景设置(加载资源时展示的场景)

		ass.load("font.fnt", BitmapFont.class);	//加载文字资源
		//加载图片和声音资源
		ass.load("s0.png", Texture.class);
		ass.load("s1.png", Texture.class);
		ass.load("bg.png",Texture.class);
		ass.load("f0.jpg", Texture.class);
		ass.load("ui3.png", Texture.class);
		ass.load("p0.png", Texture.class);
		ass.load("p1.png", Texture.class);
		ass.load("jump.mp3", Sound.class);
		ass.load("skill.png", Texture.class);
		ass.load("background/dead.jpg", Texture.class);
		ass.load("ui5.png", Texture.class);
		ass.load("ui8.png", Texture.class);
		ass.load("down.mp3", Sound.class);

		st = new Stage();
		//初始化帧率显示和内存占用显示
		Label.LabelStyle s = new Label.LabelStyle();
		s.font = new BitmapFont();
		s.font.getData().setScale(4);
		s.fontColor = Color.BLUE;

		fps = new Label("fps:", s);
		heap = new Label("", s);
		heap.setPosition(0, fps.getHeight());

		st.addActor(fps);
		st.addActor(heap);
		//初始化过渡黑场
		image = new Image(new Texture("tran.jpg"));
		image.setSize(st.getWidth(), st.getHeight()); 
		image.setOrigin(st.getWidth() / 2, st.getHeight() / 2); 
		image.setColor(Color.CLEAR); 

		st.addActor(image); 


	}
	//主渲染线程
	@Override
	public void render()
	{
		tool.clearScreen(clearColor);
		fps.setText("fps:" + Gdx.graphics.getFramesPerSecond());	//帧率显示
		heap.setText(((Gdx.app.getJavaHeap() / 1048576) + (Gdx.app.getNativeHeap() / 1048576)) + "MB");

		//渲染游戏
		super.render();
		//渲染帧率、内存占用还有过渡黑场
		st.act();
		st.draw();

		if (ss.isOk)	//加载完成
		{
			finish();
			ss.isOk = false;
			ss.dispose();
		}
	}
	//游戏过渡动作
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
	//游戏生命周期
	//当游戏退出时，释放内存，防止内存泄露
	@Override
	public void dispose()
	{
		archive.flush();
		if (m != null)
			m.dispose();
		if (h != null)
			h.dispose();
		if (lev != null)
			lev.dispose();
		if (batch != null)
			batch.dispose();
		if (Misbatch != null)
			Misbatch.dispose();
		if (jump != null)
			jump.dispose();
		if (ass != null)
			ass.dispose();
		if(map != null){
			Iterator<String> it=map.keySet().iterator();
			String key;
			while(it.hasNext()){
				key=it.next();
				map.get(key).dispose();
			}
		}
	}
	//当手机分辨率变化时
	@Override
	public void resize(int width, int height)
	{
		super.resize(width, height);
	}
	//当游戏进入后台
	@Override
	public void pause()
	{
		MainActivity.use.showQuickTip("暂停");
		super.pause();
	}
	//当游戏进程回复时
	@Override
	public void resume()
	{
		MainActivity.use.showQuickTip("返回");
		super.resume();
	}

}
