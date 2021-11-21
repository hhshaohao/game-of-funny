package hhs.game.funny;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import hhs.game.funny.games.MyGame;
import android.widget.Toast;
import android.app.Activity;
 
public class MainActivity extends AndroidApplication { 
     
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration conf = new AndroidApplicationConfiguration();
		initialize(new MyGame(),conf);
		
    }
	
} 
