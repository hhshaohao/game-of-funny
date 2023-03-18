package hhs.game.funny.games;

import com.badlogic.gdx.graphics.g2d.Batch;
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

    public funny(World world, Vector2 position, int filename, String name, float ra)
	{
		super(MyGame.emoji[filename]);
		this.world = world;
		defineBox(position, name, ra);
	}
	
	public funny(World world, Vector2 position, int filename, String name)
	{
		super(MyGame.emoji[filename]);
		this.world = world;
		defineBox(position, name, 8 / tool.le1);
	}
	
	public funny(World world, Vector2 position, int filename)
	{
		super(MyGame.emoji[filename]);
		this.world = world;
		defineBox(position, null, 8 / tool.le1);
	}
	
	public funny(World world, Vector2 position, int filename, float ra)
	{
		super(MyGame.emoji[filename]);
		this.world = world;
		defineBox(position, null, ra);
	}

	public funny(World world, Vector2 position, int filename, float ra, float re)
	{
		super(MyGame.emoji[filename]);
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
		setOrigin(ra,ra);
		this.ra = ra;
		CircleShape shape = new CircleShape();
		shape.setRadius(ra);

		BodyDef bdef = new BodyDef();
		bdef.type = BodyDef.BodyType.DynamicBody;
		bdef.position.set(position);
		b2body = world.createBody(bdef);
		
		FixtureDef fdef = new FixtureDef();
		fdef.density = 2;
		fdef.shape = shape;
		fdef.filter.categoryBits = 1;
		fix = b2body.createFixture(fdef);
		//fix.setUserData(name);
		
//		ChainShape sh = new ChainShape();
//		sh.createChain(new float[]{-0.01f * ra,-ra,0.01f * ra,-ra});
//		FixtureDef fdef2 = new FixtureDef();
//		fdef2.shape= sh;
//		fdef2.filter.categoryBits = tool.play;
//		Fixture fix2 = b2body.createFixture(fdef2);
//		fix2.setUserData(999);
		setSize(ra * 2, ra * 2);
	}

	@Override
	public void draw(Batch batch)
	{
		setPosition(b2body.getPosition().x - ra,b2body.getPosition().y - ra);
		setRotation(b2body.getAngle()*(180/3.14159265358979f));
		super.draw(batch);
	}

}
