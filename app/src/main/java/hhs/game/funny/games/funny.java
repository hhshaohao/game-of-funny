package hhs.game.funny.games;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
//游戏内基本角色初始化类
public class funny extends Sprite
{

	public World world;
	public Body b2body;
	public Fixture fix;
	public float ra;

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
		defineBox(position, null, 8 / tool.le1);
	}
	public funny(World world, Vector2 position, String filename, float ra)
	{
		super(MyGame.ass.get(filename, Texture.class));
		this.world = world;
		defineBox(position, null, ra);
	}

	public funny(World world, Vector2 position, String filename, float ra, float re)
	{
		super(MyGame.ass.get(filename, Texture.class));
		this.world = world;
		CircleShape shape = new CircleShape();
		shape.setRadius(ra);

		BodyDef bdef = new BodyDef();
		bdef.type = BodyDef.BodyType.DynamicBody;
		bdef.position.set(position);
		b2body = world.createBody(bdef);

		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.restitution = re;
		fix = b2body.createFixture(fdef);
		fix.setUserData("");
	}

	private void defineBox(Vector2 position, String name, float ra)
	{
		this.ra = ra;
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
		ChainShape sh = new ChainShape();

		sh.createChain(new float[]{-0.1f * ra,-ra,0.1f * ra,-ra});
		fix = b2body.createFixture(sh, 1);
		fix.setUserData("p");
	}



}
