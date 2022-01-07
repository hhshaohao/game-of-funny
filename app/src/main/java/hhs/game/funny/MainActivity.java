package hhs.game.funny;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import hhs.game.funny.games.MyGame;
import android.widget.Toast;
import android.app.Activity;
import hhs.game.funny.games.Tools.NativeUse;
import android.content.Context;
import com.simple.spiderman.SpiderMan;
 
public class MainActivity extends AndroidApplication { 
     
	public static NativeUse use;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		use = new NativeUse(this);
        AndroidApplicationConfiguration conf = new AndroidApplicationConfiguration();
		initialize(new MyGame(),conf);
		
    }
	
} 
