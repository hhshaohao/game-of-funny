package hhs.game.funny.games.Screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import hhs.game.funny.games.MyGame;
import hhs.game.funny.games.Res;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

public class EmojiViewScreen implements Screen
{

	public Stage st;
	SpriteBatch batch;
	
	Texture text;
	
	public EmojiViewScreen(MyGame game)
	{
		st = new Stage();
		
		Res r = new Res(game);
		
		st.addActor(r.exit);
		
		batch = MyGame.Misbatch;
		
		text = game.ass.get("p.png",Texture.class);
	}
	
	@Override
	public void show()
	{
	}

	@Override
	public void render(float p1)
	{
		batch.begin();
		batch.draw(text,0,0,Res.h / 3,Res.h / 3);
		batch.end();
		
		st.act();
		st.draw();
		
		MyGame.jump.act();
		MyGame.jump.draw();
	}

	@Override
	public void resize(int p1, int p2)
	{
	}

	@Override
	public void pause()
	{
	}

	@Override
	public void resume()
	{
	}

	@Override
	public void hide()
	{
	}

	@Override
	public void dispose()
	{
	}
	
    
    
    
}
