package com.codemelon.topography;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * @author Marshall Farrier
 * @my.created Jun 28, 2013
 * @my.edited Jun 28, 2013
 */
public class Pools {
	private ArrayList<Tile> tiles;
	private final int ROWS;
	private final int COLUMNS;
	
	PriorityQueue<Tile> globalQueue;
	PriorityQueue<Tile> localQueue;
	LinkedList<Tile> redQueue;
	LinkedList<Tile> blackAndBlueQueue;
	
	private int waterTotal;
	
	public Pools(int[][] input) {
		ROWS = input.length;
		COLUMNS = input[0].length;
		tiles = new ArrayList<Tile>(ROWS * COLUMNS);
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				tiles.add(new Tile(input[i][j], i, j));
			}
		}
		globalQueue = new PriorityQueue<Tile>();
		localQueue = new PriorityQueue<Tile>();
		redQueue = new LinkedList<Tile>();
		blackAndBlueQueue = new LinkedList<Tile>();
	}
	/**
	 * After calling this method, the topography will be colored BLUE wherever
	 * water is retained and BLACK everywhere else. The value returned is
	 * the amount of water retained by the topography.
	 * Assumes that all tiles are colored WHITE when the method is called.
	 * @return amount of water retained by the topography.
	 */
	public int findPools() {
		blackenEdgeTiles();
		enqueueWhiteTiles();
		while (!globalQueue.isEmpty()) {
			explore(globalQueue.poll());
		}
		return waterTotal;
	}
	
	public Tile getTile(int x, int y) {
		return tiles.get(COLUMNS * x + y);
	}
	
	void blackenEdgeTiles() {
		// blacken 1st and last row
		for (int j = 0; j < COLUMNS; j++) {
			getTile(0, j).setColor(Color.BLACK);
			getTile(ROWS - 1, j).setColor(Color.BLACK);
		}
		// blacken remainder of 1st and last column
		for (int i = 1; i < ROWS - 1; i++) {
			getTile(i, 0).setColor(Color.BLACK);
			getTile(i, COLUMNS - 1).setColor(Color.BLACK);
		}
	}
	
	void enqueueWhiteTiles() {
		for (Tile tile : tiles) {
			if (tile.getColor() == Color.WHITE) {
				globalQueue.add(tile);
			}
		}
	}
	
	private void explore(Tile tile) {
		if (tile.getColor() != Color.WHITE) {
			return;
		}
		tile.setColor(Color.GREEN);
		enqueueAdjacencies(tile);
		while (!localQueue.isEmpty()) {
			exploreLocal(localQueue.poll());
		}
	}
	
	private void exploreLocal(Tile tile) {
		switch (tile.getColor()) {
		case RED:
			// we've found an edge
			finishLocal(tile);
			break;
		case GRAY:
			tile.setColor(Color.GREEN);
			enqueueAdjacencies(tile);
			break;
		default:
			break;
		}
	}
	
	/**
	 * Add adjacencies to local priority queue unless they are already in it.
	 * Note that in the grid
	 * 0 1 2
	 * 3 4 5
	 * 6 7 8
	 * The adjacencies for 4 are 1, 3, 5 and 7 but NOT 0, 2, 6, and 8
	 * @param tile
	 */
	void enqueueAdjacencies(Tile tile) {
		// above
		if (tile.getX() > 0) {
			enqueueAndColor(this.getTile(tile.getX() - 1, tile.getY()));
		}
		// below
		if (tile.getX() + 1 < ROWS) {
			enqueueAndColor(this.getTile(tile.getX() + 1, tile.getY()));
		}
		// left
		if (tile.getY() > 0) {
			enqueueAndColor(this.getTile(tile.getX(), tile.getY() - 1));
		}
		// right
		if (tile.getY() + 1 < COLUMNS) {
			enqueueAndColor(this.getTile(tile.getX(), tile.getY() + 1));
		}
	}
	
	/**
	 * Add tile to local queue only if white or black
	 * @param tile
	 */
	private void enqueueAndColor(Tile tile) {
		switch (tile.getColor()) {
		case WHITE:
			tile.setColor(Color.GRAY);
			localQueue.add(tile);
			break;
		case BLACK:
			tile.setColor(Color.RED);
			localQueue.add(tile);
			break;
		default:
			break;
		}
	}
	
	/**
	 * At this point everything colored GRAY won't hold water, so it can be 
	 * colored BLACK.
	 * The GREEN tiles may or may not hold water because a 0-height path to
	 * the edge is possible. Such as
	 * 1 1 1
	 * 1 0 0
	 * 1 1 1
	 * where the middle tile will be colored GREEN.
	 * We also have to deal with possibly complicated paths to the edge, as in
	 * 3 3 3 3 
	 * 3 0 2 1
	 * 3 3 3 3
	 * Here, the tile with 0 height will be GREEN, the tile with height 2 will be
	 * GREEN, and the tile with height 1 will be RED when finishLocal() is called.
	 * @param edgeTile
	 */
	private void finishLocal(Tile edgeTile) {
		// everything still in the local queue can be colored black (some are
		// red, others are gray)
		while (!localQueue.isEmpty()) {
			localQueue.poll().setColor(Color.BLACK);
		}
		redQueue.addLast(edgeTile);
		while (!redQueue.isEmpty()) {
			exploreFromEdge(redQueue.pollFirst());
		}
	}
	
	private void exploreFromEdge(Tile edgeTile) {
		int localPoolHeight = edgeTile.getHeight();
		edgeTile.setColor(Color.BLACK);
		enqueueAdjacenciesFromEdge(edgeTile, localPoolHeight);
		while (!blackAndBlueQueue.isEmpty()) {
			enqueueAdjacenciesFromEdge(blackAndBlueQueue.pollFirst(),
					localPoolHeight);
		}
	}
	
	private void enqueueAdjacenciesFromEdge(Tile tile, int localPoolHeight) {
		// above
		if (tile.getX() > 0) {
			enqueueAndColorFromEdge(this.getTile(tile.getX() - 1, tile.getY()),
					localPoolHeight);
		}
		// below
		if (tile.getX() + 1 < ROWS) {
			enqueueAndColorFromEdge(this.getTile(tile.getX() + 1, tile.getY()),
					localPoolHeight);
		}
		// left
		if (tile.getY() > 0) {
			enqueueAndColorFromEdge(this.getTile(tile.getX(), tile.getY() - 1),
					localPoolHeight);
		}
		// right
		if (tile.getY() + 1 < COLUMNS) {
			enqueueAndColorFromEdge(this.getTile(tile.getX(), tile.getY() + 1),
					localPoolHeight);
		}
		
	}
	
	private void enqueueAndColorFromEdge(Tile tile, int localPoolHeight) {
		if (tile.getColor() == Color.GREEN) {
			if (tile.getHeight() < localPoolHeight) {
				tile.setColor(Color.BLUE);
				blackAndBlueQueue.addLast(tile);
				waterTotal += localPoolHeight - tile.getHeight();
			} else if (tile.getHeight() == localPoolHeight) {
				tile.setColor(Color.BLACK);
				blackAndBlueQueue.addLast(tile);
			} else {
				tile.setColor(Color.RED);
				redQueue.addLast(tile);
			}
		}
	}
}
