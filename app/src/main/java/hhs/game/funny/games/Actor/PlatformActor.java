package hhs.game.funny.games.Actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import hhs.game.funny.games.Tools.RendererObject;
//移动平台角色
public class PlatformActor implements RendererObject
{

	float left,right,ra;
	Texture text;
	World world;
	Body plat;
	Vector2 speed,pos,size;
	float w,h;
	float time = 0;

	boolean r = true;

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
		plat.setUserData("g");
		plat.createFixture(fdef);

		ra = shape.getRadius();
		plat.setLinearVelocity(speed.x, 0);
		w = r.x;
		h = r.y;
		//plat.setTransform(pos.x = left + 1, pos.y, plat.getAngle());
	}
	@Override
	public void act(float p1)
	{
		pos.x = plat.getPosition().x - w / 2;
		pos.y = plat.getPosition().y - h / 2;

		if (r)
		{
			plat.setLinearVelocity(speed);
			if (pos.x > right)
			{
				r = false;
			}
		}
		else
		{
			plat.setLinearVelocity(-speed.x, 0);
			if (pos.x < left)
			{
				r = true;
			}
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
