package hhs.game.funny.games.Runnable;
//移动逻辑
public abstract class RoleLogic
{
	//左移动按钮被点击
    public abstract void leftAction();
    //右移动按钮被点击
	public abstract void rightAction();
	//跳跃按钮被点击
	public abstract void upAction();
}
