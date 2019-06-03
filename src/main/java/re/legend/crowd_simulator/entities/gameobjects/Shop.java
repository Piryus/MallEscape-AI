package re.legend.crowd_simulator.entities.gameobjects;

import com.badlogic.gdx.math.Vector2;

public class Shop extends GameObject{
	Vector2 origin;
	private float x;
	private float y;
	private float height;
	private float width;
	
	//"F" for female shop and "M" for Male shop
	final private String type;
	
	
	public Shop(float x, float y, float height, float width, String type) {
		super(x,y,0);
		this.height = height;
		this.width = width;
		this.type = type;
	}
	
	
	public Vector2 getOrigin()
	{
		return this.origin;
	}
	
	public void setOrigin(Vector2 origin)
	{
		this.origin = origin;
	}
	
	public float getHeight()
	{
		return this.height;
	}
	
	public void setHeight(float height)
	{
		this.height = height;
	}
	
	public float getWidth(float width)
	{
		return this.width;
	}
	
	public void setWidth(float width)
	{
		this.width = width;
	}
	
}
