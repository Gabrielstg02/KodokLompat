package com.Game;


public class BoxCollider {
	float x, y, width, height;
	
	public BoxCollider(float x, float y, float width, float height) {
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
	}
	
	boolean intersects(BoxCollider other) {
		float left = x;
		float right = x + width;
		float top = y;
		float bottom = y + height;
		
		float oLeft = other.x;
		float oRight = other.x + other.width;
		float oTop = other.y;
		float oBottom = other.y + other.height;
		
		return !(left >= oRight || right <= oLeft || top >= oBottom || bottom <= oTop);
	}
}