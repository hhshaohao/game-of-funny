package hhs.game.funny.games.Stage;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import hhs.game.funny.games.MyGame;
import hhs.game.funny.games.Res;
import hhs.game.funny.games.Runnable.RoleLogic;
import hhs.game.funny.games.funny;

public class UserInterface extends Stage
{

	MyGame game;
	Res r;

	ImageButton b0,b1,b2;
	RoleLogic logic;
	boolean u0,u1,u2;

	public UserInterface(final MyGame game, RoleLogic l,boolean hb)
	{
		this.game = game;

		r = new Res(game);
		b0 = r.b0;
		b1 = r.b1;
		b2 = r.b2;

		this.logic = l;
		this.addButtonAction();

		if(hb)
		{
			this.addActor(r.exit);
		}else
		{
			ImageButton exit = r.exit;
			exit.clearListeners();
			exit.addListener(new InputListener(){

					@Override
					public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
					{
						return true;
					}

					@Override
					public void touchUp(InputEvent event, float x, float y, int pointer, int button)
					{
						game.goThat();
						//super.touchUp(event, x, y, pointer, button);
					}

				});
			this.addActor(exit);
		}
		this.addActor(b0);
		this.addActor(b1);
		this.addActor(b2);
	}

	public void ac(funny role)
	{
		this.addActor(r.getChange(role));
	}

	private void addButtonAction()
	{
		b0.addListener(new InputListener()
			{
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
				{
					u0 = true;
					return true;
				}

				@Override
				public void touchUp(InputEvent event, float x, float y, int pointer, int button)
				{
					u0 = false;
				}
			});
		b1.addListener(new InputListener()
			{
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
				{
					u1 = true;
					return true;
				}
				@Override
				public void touchUp(InputEvent event, float x, float y, int pointer, int button)
				{
					u1 = false;
				}
			});
		b2.addListener(new InputListener()
			{
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
				{
					u2 = true;
					return true;
				}
				@Override
				public void touchUp(InputEvent event, float x, float y, int pointer, int button)
				{
					u2 = false;
				}
			});
	}

	@Override
	public void act()
	{
		if (u0)
		{
			logic.leftAction();
		}
		if (u1)
		{
			logic.rightAction();
		}
		if (u2)
		{
			logic.upAction();
		}
		super.act();
	}

	@Override
	public void draw()
	{
		super.draw();
	}

}
