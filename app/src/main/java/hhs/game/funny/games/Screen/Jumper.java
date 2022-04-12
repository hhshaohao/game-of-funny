package hhs.game.funny.games.Screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import hhs.game.funny.games.Res;
import hhs.game.funny.games.funny;
import hhs.game.funny.games.tool;

public class Jumper extends BackDraw
{
	SpriteBatch batch;
	World world;
	OrthographicCamera cam;
	funny role[];

	public Jumper()
	{
		batch = new SpriteBatch();

		cam = new OrthographicCamera();
		cam.setToOrtho(false, Res.w / tool.le1, Res.h / tool.le1);

		this.initBox2d();
	}

	void initBox2d()
	{
		world = new World(new Vector2(0, -9.81f), true);

		BodyDef bdef = new BodyDef();
		FixtureDef fdef= new FixtureDef();
		Body body;

		bdef.type = BodyDef.BodyType.StaticBody;

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(Res.w / tool.le1, 1 / tool.le1);
		//下层框
		fdef.shape = shape;
		bdef.position.set(shape.getRadius() / tool.le1, shape.getRadius() / tool.le1);
		body = world.createBody(bdef);
		body.createFixture(fdef);
		//上层框
		bdef.position.set(shape.getRadius() / tool.le1, (Res.h + shape.getRadius()) / tool.le1);
		body = world.createBody(bdef);
		body.createFixture(fdef);
		//左层框
		shape.setAsBox(1 / tool.le1, Res. h / tool.le1);
		fdef.shape = shape;
		bdef.position.set(shape.getRadius() / tool.le1, shape.getRadius() / tool.le1);
		body = world.createBody(bdef);
		body.createFixture(fdef);
		//右层框
		bdef.position.set((Res.w + shape.getRadius()) / tool.le1, shape.getRadius() / tool.le1);
		body = world.createBody(bdef);
		body.createFixture(fdef);

		role = new funny[15];
		for (int i = 0; i < role.length; ++i)
		{
			role[i] = new funny(world, new Vector2(MathUtils.random(8 / tool.le1, Res.w / tool.le1), MathUtils.random(8 / tool.le1, Res.h / tool.le1)),
								"w" + MathUtils.random(0, 2) + ".png", 32 / tool.le1, 1.0f);
			role[i].b2body.setLinearVelocity(MathUtils.random(-50, 50), 0);
		}
	}

	@Override
	public void act()
	{
		batch.setProjectionMatrix(cam.combined);
		world.step(1 / 30f, 1, 1);
		this.move();
	}

	void move()
	{
		for (int i = 0; i < role.length; i++)
		{
			if (role[i].b2body.getLinearVelocity().y == 0)
			{
				role[i].b2body.applyForceToCenter(MathUtils.random(-500, 500), 0, true);
			}
		}
	}

	@Override
	public void draw()
	{
		batch.begin();
		for (int i = 0; i < role.length; i++)
		{
			batch.draw(role[i], role[i].b2body.getPosition().x - ((64 / tool.le1) / 2), role[i].b2body.getPosition().y - ((64 / tool.le1) / 2), 64 / tool.le1, 64 / tool.le1);
		}
		batch.end();
	}

	@Override
	public void dispose()
	{
		batch.dispose();
		world.dispose();
	}

}
