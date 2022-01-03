package hhs.game.funny.games;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.graphics.Color;

/*
一些快捷工具
*/
public class tool
{

    public static final float PPM = 70f;
	public static final float le1 = 20f;
	public static final double M_PI = 3.1415;
	static String TAG = "当你在注视代码时，代码也在注视你";
	static LabelStyle style = new LabelStyle(MyGame.font, Color.BLUE);

    public static ImageButton createButton ( String a, String b )
	{
        ImageButton c = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(a))),
                                        new TextureRegionDrawable(new TextureRegion(new Texture(b))));
        return c;
    }

	public static float d2r ( float d )
	{
		return (float)M_PI / 180 * d;
	}

    public static ImageButton createButton ( String a )
	{
        ImageButton c = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(a))));
        return c;

    }
	public static ImageButton createButton ( Texture a, Texture b )
	{
		return new ImageButton(new TextureRegionDrawable(new TextureRegion(a)),
							   new TextureRegionDrawable(new TextureRegion(b)));
	}
    public static ImageButton createButton ( Texture a )
	{
		return new ImageButton(new TextureRegionDrawable(new TextureRegion(a)));
	}
    public static TextureRegionDrawable createDrawable ( String a )
	{
        TextureRegionDrawable b = new TextureRegionDrawable(new TextureRegion(new Texture(a)));
        return b;
    }

	public static TextureRegionDrawable createDrawable ( Texture a )
	{
        TextureRegionDrawable b = new TextureRegionDrawable(new TextureRegion(a));
        return b;
    }

    public static TextureRegion createRegion ( String a )
	{
        TextureRegion b = new TextureRegion(new Texture(a));
        return b;
    }
	public static TextureRegion createRegion(Texture a)
	{
		return new TextureRegion(a);
	}

}
