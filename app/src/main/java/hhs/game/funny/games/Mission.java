package hhs.game.funny.games;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

/*
一个弹窗API
使用方法：
Mission 变量名 = new Mission(title,main,font){
	@Override
	public void cilck(){
		按确定后执行...
	}
}
在 Render函数中
if(条件){
变量名.act();
变量名.draw();
}
*/
public class Mission extends Stage
{

 	public Dialog dialog;
	BitmapFont font;
	SpriteBatch batch;
	String title;
	Skin skin;

    public Mission(String title, String main, BitmapFont map)
	{
		this.title = title;
		skin = new Skin();
		batch = MyGame.Misbatch;
		map.setColor(Color.BLACK);
		font = map;

		WindowStyle windowStyle = new WindowStyle(new BitmapFont(), Color.BLACK, tool.createDrawable("s0.png"));
		TextButtonStyle textbuttonStyle = new TextButtonStyle(tool.createDrawable("s0.png"), tool.createDrawable("s1.png"), null, map);
		LabelStyle labelstyle = new LabelStyle();
		labelstyle.font = map;
		labelstyle.fontColor = Color.BLACK;

		skin.add("default", textbuttonStyle);
		skin.add("default", windowStyle);
		skin.add("default", labelstyle);

		dialog = new Dialog(title, skin, "default");
        //dialog.setTitleAlignment( Align.center | Align.top );
        dialog.setBounds(Res.w / 4, Res.h / 4, Res.w / 2, Res.h / 2);


        TextButton textbutton1 = new TextButton("确定", textbuttonStyle);
		textbutton1.addListener(new InputListener(){

				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
				{
					cilck(dialog);
					return true;
				}
			});
        textbutton1.setBounds(0, 0, dialog.getWidth() , 100);
        //dialog.addActor(button1);
        dialog.addActor(textbutton1);
		dialog.text(main);

        this.addActor(dialog);

		//Gdx.input.setInputProcessor(this);
	}

	@Override
	public void draw()
	{

		super.draw();

		batch.begin();
		font.draw(batch, title, Res.w / 2 - font.getScaleX() * 80, 3 * Res.h / 4);
		batch.end();
	}

	public void cilck(Dialog dialog)
	{
		dialog.remove();
		Level1.show = false;
		Gdx.input.setInputProcessor(Level1.st);
	}

}


/**
 * <b>类名称：</b>ExitDialog
 * <b>类描述：</b>退出游戏对话框
 * <b>创建人：</b>wanglong
 * <b>修改人：</b>wanglong
 * <b>修改时间：</b>2014-12-14 上午3:54:18
 * <b>修改备注：</b>类初始化
 * @version 1.0.0<br/>
 */

