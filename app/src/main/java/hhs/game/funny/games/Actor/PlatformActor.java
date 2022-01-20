package hhs.game.funny.games.Actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import hhs.game.funny.games.Tools.RendererObject;

public class PlatformActor implements RendererObject
{
	
	float left,right,ra;
	Texture text;
	World world;
	Body plat;
	Vector2 speed,pos,size;
	
	public PlatformActor(Texture text,Vector2 pos,Shape shape,float left,float right,Vector2 speed,World world,Vector2 size)
	{
		this.text = text;
		this.left = left;
		this.right = right;
		this.world = world;
		this.speed = speed;
		this.size = size;
		this.pos = pos;
		
		BodyDef bdef = new BodyDef();
		bdef.type = BodyDef.BodyType.DynamicBody;
		FixtureDef fdef = new FixtureDef();
		
		bdef.position.set(pos);
		fdef.shape = shape;
		
		plat = world.createBody(bdef);
		
		plat.createFixture(fdef);
		
		plat.setLinearVelocity(speed);
		
		ra = shape.getRadius();
	}
	@Override
	public void act(float p1)
	{
		pos.x = plat.getPosition().x - ra;
		pos.y = plat.getPosition().y - ra;
		
		if(pos.x < right && pos.x > left)
		{
			plat.setLinearVelocity(speed);
		}else
		{
			plat.setLinearVelocity(-speed.x,-speed.y);
		}
	}
	@Override
	public void draw(SpriteBatch batch)
	{
		batch.draw(text,pos.x,pos.y,size.x,size.y);
	}
	@Override
	public void setSize(float size)
	{
		
	}
    
}
