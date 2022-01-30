package hhs.game.funny.games.contactListener;

import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.Gdx;
import hhs.game.funny.MainActivity;
import hhs.game.funny.games.MyGame;
import com.badlogic.gdx.audio.Sound;

public class jumpConcat implements ContactListener
{
	public boolean is = false;
	@Override
	public void beginContact(Contact p1)
	{
		Fixture a = p1.getFixtureA();
		Fixture b = p1.getFixtureB();
		if( !is )
			if( a.getUserData() != null || b.getUserData() != null )
			{
				MyGame.ass.get("down.mp3", Sound.class).play();
				is = true;
				/*Fixture ground;
				 ground = a.getUserData() == "ground" ? a : b;*/
			}
	}

	@Override
	public void endContact(Contact p1)
	{
//		Fixture a = p1.getFixtureA();
//		Fixture b = p1.getFixtureB();
//		//MainActivity.use.showQuickTip("开始"+a.getUserData()+b.getUserData());
//		if( a.getUserData() != null || b.getUserData() != null )
//		{
//			is = false;
//			/*Fixture ground;
//			 ground = a.getUserData() == "ground" ? a : b;*/
//		}
	}

	@Override
	public void preSolve(Contact p1, Manifold p2)
	{
	}

	@Override
	public void postSolve(Contact p1, ContactImpulse p2)
	{

	}
}
