package hhs.game.funny.games.contactListener;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class contact implements ContactListener
{
	public boolean is;
	@Override
	public void beginContact(Contact p1)
	{
		//Gdx.app.log("is touch","");

		Fixture fixa = p1.getFixtureA();
		Fixture fixb = p1.getFixtureB();
		Fixture zhu = null,lost = null;
		if( fixa.getUserData() == "zhu" )
		{
			zhu = fixa;
		}
		else if( fixb.getUserData() == "zhu" )
			zhu = fixb;
		if( fixa.getUserData() == "lost" )
		{
			lost = fixa;
		}
		else if( fixb.getUserData() == "lost" )
			lost = fixb;
		if( zhu != null && lost != null )
		{
			is = true;
		}
	}

	@Override
	public void endContact(Contact p1)
	{
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
