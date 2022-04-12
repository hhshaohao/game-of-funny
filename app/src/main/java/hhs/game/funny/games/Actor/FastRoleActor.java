package hhs.game.funny.games.Actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import hhs.game.funny.games.Tools.Controler;
//快捷演员
public class FastRoleActor extends Actor
{

    World world;
	public Body b2body;
	Texture texture;
	float sx,sy,radius;	//self X,self Y
	Controler con;

	public FastRoleActor(World w, Texture text, int radius, Vector2 ve)
	{
		this.world = w;
		this.texture = text;
		this.radius = radius;

		BodyDef bdef = new BodyDef();
		bdef.type = BodyDef.BodyType.DynamicBody;

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(radius, radius);

		FixtureDef fdef = new FixtureDef();
		fdef.shape  = shape;

		bdef.position.set(ve);
		b2body = world.createBody(bdef);
		b2body.createFixture(fdef);

		con = null;
	}

	@Override
	public void act(float delta)
	{
		sx = b2body.getPosition().x - radius;
		sy = b2body.getPosition().y - radius;
		if (con != null)
			con.frameCall(b2body);
	}

	@Override
	public void draw(Batch batch, float parentAlpha)
	{
		batch.draw(texture, sx, sy, radius, radius);
	}

	public void addControler(Controler con)
	{
		this.con = con;
	}

}
