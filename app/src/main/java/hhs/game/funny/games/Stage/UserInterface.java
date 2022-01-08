package hhs.game.funny.games.Stage;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import hhs.game.funny.games.MyGame;
import hhs.game.funny.games.Res;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import hhs.game.funny.games.Runnable.RoleLogic;

public class UserInterface extends Stage
{

	MyGame game;

	ImageButton b0,b1,b2;
	RoleLogic logic;

	public UserInterface(final MyGame game,RoleLogic l)
	{
		this.game = game;

		Res r = new Res(game);
		b0 = r.b0;
		b1 = r.b1;
		b2 = r.b2;
		
		this.logic = l;
		this.addButtonAction();

		this.addActor(r.exit);
		this.addActor(b0);
		this.addActor(b1);
		this.addActor(b2);
	}

	private void addButtonAction()
	{
		b0.addListener(new InputListener()
			{
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
				{
					logic.leftAction();
					return true;
				}
			});
		b1.addListener(new InputListener()
			{
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
				{
					logic.rightAction();
					return true;
				}
			});
		b2.addListener(new InputListener()
			{
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
				{
					logic.upAction();
					return true;
				}
			});
	}

	@Override
	public void act()
	{
		super.act();
	}

	@Override
	public void draw()
	{
		super.draw();
	}

}
