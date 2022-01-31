package hhs.game.funny.games.Stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import hhs.game.funny.games.Mission;
import java.util.ArrayList;

public class MissionStage extends Stage
{

	private ArrayList<Mission> mis;
	public int total;

	public MissionStage(int value)
	{
		mis = new ArrayList<Mission>(value);
	}

	public MissionStage()
	{
		mis = new ArrayList<Mission>();
	}

	public void addMission(Mission m)
	{
		mis.add(m);
		total++;
	}

	@Override
	public void act()
	{
		super.act();
	}

	@Override
	public void draw()
	{
		for( Mission m : mis )
		{
			if( m.isShow )
			{
				Gdx.input.setInputProcessor(m);
				m.act();
				m.draw();
			}
		}
	}


}
