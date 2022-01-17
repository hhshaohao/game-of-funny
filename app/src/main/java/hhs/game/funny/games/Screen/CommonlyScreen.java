package hhs.game.funny.games.Screen;

import com.badlogic.gdx.Screen;
import hhs.game.funny.games.Stage.UserInterface;
import hhs.game.funny.games.MyGame;
import hhs.game.funny.games.Runnable.RoleLogic;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class CommonlyScreen extends UniversalScreen
{
	public UserInterface ui;
	public MyGame game;
	public Stage gs;
	public boolean showUI = true;

	public CommonlyScreen(MyGame game, RoleLogic r)
	{
		ui = new UserInterface(game, r);
		gs = new Stage();
	}
	
	public CommonlyScreen(MyGame game,Stage gs, RoleLogic r)
	{
		ui = new UserInterface(game, r);
		this.gs = gs;
	}

	@Override
	public void show()
	{
	}

	@Override
	public void render(float p1)
	{
		//gs.act();
		//gs.draw();

		if( showUI )
		{
			ui.act();
			ui.draw();
		}
	}

	@Override
	public void resize(int p1, int p2)
	{
	}

	@Override
	public void pause()
	{
	}

	@Override
	public void resume()
	{
	}

	@Override
	public void hide()
	{
	}

	@Override
	public void dispose()
	{
	}

	public void setStage(Stage st)
	{
		gs = st;
	}
	
	public void setShowUI(boolean b)
	{
		showUI = b;
	}
}
