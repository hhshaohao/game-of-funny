package hhs.game.funny.games.Tools;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

public interface PlatformResolver {
	/**
	 * 返回网络是否连接
	 * @return
	 */
	public boolean isNetEnable();
	/**
	 * 弹出文字信息
	 * @param context 要显示的信息
	 */
	public void showQuickTip(String context);
	/**
	 * 弹出一个带按钮的窗口
	 * @param title 窗口标题文字
	 * @param context 窗口内容文字
	 * @param callback 按钮按下的在游戏线程中的回调，
	 */
	public void showQucikDialog(String title,String context,Runnable callback);
	public BitmapFont getFont(String characters);
	public void callPay(String payee,int money);
}

