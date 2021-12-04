package hhs.game.funny.games;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Fixture;

public class funny extends Sprite
{

	public World world;
	public Body b2body;
	public Fixture fix;

    public funny(World world, Vector2 position, String filename, String name, float ra)
	{
		super(MyGame.ass.get(filename, Texture.class));
		this.world = world;
		defineBox(position, name, ra);
	}
	public funny(World world, Vector2 position, String filename, String name)
	{
		super(MyGame.ass.get(filename, Texture.class));
		this.world = world;
		defineBox(position, name, 8 / tool.le1);
	}
	public funny(World world, Vector2 position, String filename)
	{
		super(MyGame.ass.get(filename, Texture.class));
		this.world = world;
		defineBox(position, "", 8 / tool.le1);
	}
	public funny(World world, Vector2 position, String filename, float ra)
	{
		super(MyGame.ass.get(filename, Texture.class));
		this.world = world;
		defineBox(position, "", ra);
	}

	private void defineBox(Vector2 position, String name, float ra)
	{
		CircleShape shape = new CircleShape();
		shape.setRadius(ra);

		BodyDef bdef = new BodyDef();
		bdef.type = BodyDef.BodyType.DynamicBody;
		bdef.position.set(position);
		b2body = world.createBody(bdef);

		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fix = b2body.createFixture(fdef);
		fix.setUserData(name);
	}



}