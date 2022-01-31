package hhs.game.funny.games.Tools;

import com.badlogic.gdx.physics.box2d.Body;

public interface Controler
{
    //每一帧执行
    public void frameCall(Body body);
}
