package hhs.game.funny.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.scenes.scene2d.Group;
import hhs.game.funny.MainActivity;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.graphics.Color;
//游戏内静态变量储存
public class Res
{
	//手机固定变量
    public static final int w = Gdx.graphics.getWidth();		//手机宽度像素
	public static final int h = Gdx.graphics.getHeight();		//手机高度像素
    static float tx = w / tool.PPM;
    static float ty = h / tool.PPM;

	//静态资源
	public ImageButton b0,b1,b2,exit;
	public Group cSkin;
	public funny role;
	public int cv;
	public Label point;
	public MyGame game;

	public Res(final MyGame game)
	{
		this.game = game;
		cv = 0;
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

		b0.setPosition(0, 0);
        b1.setPosition(1.5f * b0.getWidth(), 0);
        b2.setPosition(Res.w - b2.getWidth(), 0);

		exit = tool.createButton(MyGame.ass.get("ui8.png", Texture.class));
		exit.setPosition(0, Res.h - exit.getHeight());
		exit.addListener(new InputListener()
			{

				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
				{
					Res.this.exit();
					return true;
				}
			});


	}
	public void exit()
	{
		game.goMain();
	}
	//角色表情变化
	public Group getChange(final funny role)
	{
		cSkin = new Group();
		cSkin.setPosition(Res.w - 250, Res.h / 2 - 100);

		Label.LabelStyle style = new Label.LabelStyle();
		style.font = MyGame.font;
		style.fontColor = Color.BLACK;
		
		point = new Label(1 + "/" + MyGame.emoji.length,style);

		this.role = role;
		ImageButton b0 = tool.createButton("ui17.png", "s0.png");
		b0.setPosition(w - cSkin.getWidth(), h / 2 - cSkin.getHeight() / 2);
		b0.addListener(new InputListener()
			{
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
				{
					if (cv < MyGame.emoji.length - 1)
					{
						cv += 1;
						point.setText((cv + 1) + "/" + MyGame.emoji.length);
						tool.changeSkin(role, cv);
					}
					else
					{
						MainActivity.use.showQuickTip("已经是最后一个表情");
					}
					return true;
				}
			});

		ImageButton b1 = tool.createButton("ui16.png", "s0.png");
		b1.addListener(new InputListener()
			{
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
				{
					if (cv > 0)
					{
						cv -= 1;
						point.setText((cv + 1) + "/" + MyGame.emoji.length);
						tool.changeSkin(role, cv);
					}
					else
					{
						MainActivity.use.showQuickTip("已经是第一个表情");
					}
					return true;
				}
			});
		b0.setPosition(0, 0);
		cSkin.addActor(b0);
		b1.setPosition(0, 100);
		cSkin.addActor(b1);
		point.setPosition(0,200);
		cSkin.addActor(point);

		return cSkin;
	}


}
enum sprite
{
    left,right,stop,up,down;
}
