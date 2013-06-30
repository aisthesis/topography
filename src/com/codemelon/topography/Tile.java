package com.codemelon.topography;

/**
 * @author Marshall Farrier
 * @my.created Jun 28, 2013
 * @my.edited Jun 28, 2013
 */
public class Tile implements Comparable<Tile> {
	private int height;
	private int x;

	private int y;
	private Color color;

	public Tile(int height, int x, int y) {
		this.height = height;
		this.x = x;
		this.y = y;
		color = Color.WHITE;
	}
	
	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	@Override
	public int compareTo(Tile tile) {
		return this.height - tile.height;
	}
}
