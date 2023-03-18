package hhs.game.funny;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;
import com.badlogic.gdx.Gdx;
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
		Toast.makeText(this, "感谢游玩", Toast.LENGTH_SHORT).show();
		use = new NativeUse(this);
		//requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
		if (Build.VERSION.SDK_INT >= 23)
		{

			if (this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || 
				this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
			{

				this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

			}
		}
		//this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
		AndroidApplicationConfiguration conf = new AndroidApplicationConfiguration();
		initialize(new MyGame(), conf);	//进入游戏
		//setContentView(R.layout.community);
    }

} 
