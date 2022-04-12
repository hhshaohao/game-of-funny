package hhs.game.funny.games.Tools;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import hhs.game.funny.MainActivity;

public class NativeUse implements PlatformResolver
{
	Context _context;
	Handler hd;
	public NativeUse(Context context)
	{
		_context = context;
		hd = new Handler();
	}

	public boolean isNetEnable()
	{
		// TODO Auto-generated method stub
		ConnectivityManager connectivityManager = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connectivityManager == null)
		{
			return false;
		}
		else
		{
			NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

			if (networkInfo != null && networkInfo.length > 0)
			{
				for (int i = 0; i < networkInfo.length; i++)
				{
					System.out.println(i + "==net state==" + networkInfo[i].getState());
					System.out.println(i + "===net type===" + networkInfo[i].getTypeName());
					if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED)
					{
						return true;
					}
				}
			}
		}
		return false;
	}


	public void showQuickTip(final String context)
	{
		// TODO Auto-generated method stub
		hd.post(new Runnable() {

				@Override
				public void run()
				{
					// TODO Auto-generated method stub
					Toast.makeText(_context, context, Toast.LENGTH_LONG).show();
				}
			});


	}

	public BitmapFont getFont(String characters)
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void callPay(final String payee, final int money)
	{
		// TODO Auto-generated method stub
		hd.post(new Runnable() {
				public void run()
				{
					Intent intent=new Intent(_context, MainActivity.class);

					Bundle bundle=new Bundle();
					bundle.putString("payee", payee);
					bundle.putInt("money", money);

					intent.putExtras(bundle);
					_context.startActivity(intent);
				}
			});




	}
	@Override
	public void showQucikDialog(final String title, final String context, final Runnable callback)
	{
		// TODO Auto-generated method stub
		hd.post(new Runnable() {

				@Override
				public void run()
				{
					// TODO Auto-generated method stub
					new  AlertDialog.Builder(_context)    
						.setTitle(title)  
						.setMessage(context)  
						.setPositiveButton("确定" , new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								// TODO Auto-generated method stub
								Gdx.app.postRunnable(callback);

							}
						})  
						.show();
				}
			});

	}

	public void showDialog(final String title, final String context, final Runnable ok, final Runnable cancel)
	{
		hd.post(new Runnable() {

				@Override
				public void run()
				{
					// TODO Auto-generated method stub
					new  AlertDialog.Builder(_context)    
						.setTitle(title)  
						.setMessage(context)
						.setNegativeButton("取消", new OnClickListener(){
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								// TODO Auto-generated method stub
								Gdx.app.postRunnable(cancel);

							}
						})
						.setPositiveButton("确定" , new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								// TODO Auto-generated method stub
								Gdx.app.postRunnable(ok);

							}
						})  
						.show();
				}
			});

	}

}

