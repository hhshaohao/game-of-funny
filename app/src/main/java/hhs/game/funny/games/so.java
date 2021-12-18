package hhs.game.funny.games;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class so implements Screen
{

	MyGame m;
	Label s1,s2,s3,s4,s5,s6;
	Stage st;
	Table ta;
	ImageButton back;
	int t;

	public so ( MyGame g )
	{
		m = g;

		back = new ImageButton(new TextureRegionDrawable(new TextureRegion(MyGame.ass.get("ui3.png", Texture.class))));
		back.setX(0);
		back.setY(Res.h - 100);
		back.addListener(new InputListener(){
				@Override
				public boolean touchDown ( InputEvent event, float x, float y, int pointer, int button )
				{
					m.goMain();
					return true;
				}


			});

		ta = new Table();

		Label.LabelStyle style = new Label.LabelStyle();
		style.font = MyGame.ass.get("font.fnt", BitmapFont.class);
		style.font.getData().setScale(1);
		style.fontColor = Color.BLACK;
		s1 = new Label("千年一遇的滑稽风暴来到了沙雕界,使沙雕界遭遇大灾难", style);
		s2 = new Label("沙雕之神和其他表情为了使沙雕界回归正常", style);
		s3 = new Label("使用了沙雕界神器'沙雕神符',简称'氵隹符'", style);
		s4 = new Label("使用沙雕神符后,这件宝物消失于世间,'", style);
		s5 = new Label("众表情们为了纪念沙雕神符,举办沙雕比拼,谁能胜利谁就是'沙雕之主", style);
		s6 = new Label("除了滑稽一族外,还有其他表情参加(待开发)", style);

		ta.setFillParent(true);
		ta.center();
		ta.add(s1);
		ta.row();
		ta.add(s2);
		ta.row();
		ta.add(s3);
		ta.row();
		ta.add(s4);
		ta.row();
		ta.add(s5);
		ta.row();
		ta.add(s6);

		st = new Stage();
		st.addActor(ta);
		st.addActor(back);
	}

	@Override
	public void show ( )
	{
	}

	@Override
	public void render ( float p1 )
	{
		MyGame.jump.act();
		MyGame.jump.draw();
		
		st.act();
		st.draw();
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
	}




}
