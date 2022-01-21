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
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.math.Rectangle;

public class PlatformActor implements RendererObject
{

	float left,right,ra;
	Texture text;
	World world;
	Body plat;
	Vector2 speed,pos,size;
	float w,h;
	int time = 0;

	public PlatformActor(Texture text, Vector2 pos, Shape shape, float left, float right, Vector2 speed, World world, Vector2 size, Vector2 r)
	{
		this.text = text;
		this.left = left;
		this.right = right;
		this.world = world;
		this.speed = speed;
		this.size = size;
		this.pos = pos;

		BodyDef bdef = new BodyDef();
		bdef.type = BodyDef.BodyType.KinematicBody;
		FixtureDef fdef = new FixtureDef();

		bdef.position.set(pos);
		fdef.shape = shape;

		fdef.density = 1;
		fdef.friction = 1;
		plat = world.createBody(bdef);

		plat.createFixture(fdef);

		ra = shape.getRadius();
		plat.setLinearVelocity(speed.x, 0);
		w = r.x;
		h = r.y;
	}
	@Override
	public void act(float p1)
	{
		pos.x = plat.getPosition().x - w / 2;
		pos.y = plat.getPosition().y - h / 2;

		if( left  > pos.x && pos.x > right )
		{
			time++;
		}
		if( time % 2 == 0 )
		{
			plat.setLinearVelocity(speed.x, 0);
		}
		else
		{
			plat.setLinearVelocity(-speed.x, 0);
		}
	}
	@Override
	public void draw(SpriteBatch batch)
	{
		batch.draw(text, pos.x, pos.y, size.x * 2, size.y * 2);
	}
	@Override
	public void setSize(float size)
	{

	}

}
