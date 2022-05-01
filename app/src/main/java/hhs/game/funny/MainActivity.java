package hhs.game.funny;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import hhs.game.funny.games.MyGame;
import hhs.game.funny.games.Tools.NativeUse;

//android程序入口类
public class MainActivity extends AndroidApplication
{ 

	public static NativeUse use;

    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
		Toast.makeText(this,"感谢游玩",Toast.LENGTH_SHORT).show();
		use = new NativeUse(this);
        AndroidApplicationConfiguration conf = new AndroidApplicationConfiguration();
		initialize(new MyGame(), conf);	//进入游戏
		//setContentView(R.layout.community);
    }

} 
