package hhs.game.funny;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import hhs.game.funny.games.MyGame;
import hhs.game.funny.games.Tools.NativeUse;

public class MainActivity extends AndroidApplication
{ 

	public static NativeUse use;

    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
		AlertDialog dialog = new AlertDialog.Builder(this)
			.setTitle("提示")
			.setMessage("本游戏完全免费，请不要在非官方途径下载\n联系方式：\nQQ:1265177365\n微信：quququqqq")
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dia, int which)
				{
					Toast.makeText(MainActivity.this, "感谢游玩", Toast.LENGTH_SHORT).show();
				}
			})
			.create();
		dialog.show();
		use = new NativeUse(this);
        AndroidApplicationConfiguration conf = new AndroidApplicationConfiguration();
		initialize(new MyGame(), conf);

    }

} 
