package com.codemelon.topography;

import static org.junit.Assert.*;

import org.junit.Test;

public class PoolsTest {

	@Test
	public void testGetTile() {
		int[][] input = {{0, 1, 2}, {3, 4, 5}};
		Pools pools = new Pools(input);
		int val = 0;
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 3; j++) {
				assertEquals("Correct tile identified", val++, 
						pools.getTile(i, j).getHeight());
			}
		}
	}

	@Test
	public void testInitialTileColor() {
		int[][] input = {{0, 1, 2}, {3, 4, 5}};
		Pools pools = new Pools(input);
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 3; j++) {
				assertEquals("Tile color is WHITE", Color.WHITE, 
						pools.getTile(i, j).getColor());
			}
		}
	}

	@Test
	public void testBlackenEdgeTiles() {
		int[][] input = {{0, 0, 0}, {0, 1, 0}, {0, 1, 0}, {0, 0, 0}};
		Pools pools = new Pools(input);
		pools.blackenEdgeTiles();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 3; j++) {
				if (pools.getTile(i, j).getHeight() == 0) {
					assertEquals("Edge tiles are BLACK", Color.BLACK, 
						pools.getTile(i, j).getColor());
					
				} else {
					assertEquals("Inner tiles are WHITE", Color.WHITE, 
							pools.getTile(i, j).getColor());
				}
			}
		}
	}

	/**
	 * Pool in the middle
	 */
	@Test
	public void testFindPools01() {
		int[][] input = {
				{1, 1, 1}, 
				{1, 0, 2}, 
				{1, 0, 3}, 
				{1, 1, 1}};
		Pools pools = new Pools(input);
		assertEquals("Correct amount of water", 2, pools.findPools());
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 3; j++) {
				if (pools.getTile(i, j).getHeight() == 0) {
					assertEquals("Tile (" + i + ", " + j + ") is blue.", Color.BLUE,
							pools.getTile(i, j).getColor());
				} else {
					assertEquals("Tile (" + i + ", " + j + ") is black.", Color.BLACK,
							pools.getTile(i, j).getColor());
				}
			}
		}
	}
	
	/**
	 * No pool
	 */
	@Test
	public void testFindPools02() {
		int[][] input = {
				{0, 0, 1}, 
				{1, 0, 2}, 
				{1, 0, 3}, 
				{1, 1, 1}};
		Pools pools = new Pools(input);
		assertEquals("Correct amount of water", 0, pools.findPools());
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 3; j++) {
				assertEquals("Tile (" + i + ", " + j + ") is black.", Color.BLACK,
						pools.getTile(i, j).getColor());
			}
		}
	}
	
	/**
	 * complex topography
	 */
	@Test
	public void testFindPools03() {
		int[][] input = {
				{0, 0, 6, 5, 5, 5}, 
				{6, 2, 5, 5, 5, 5}, 
				{6, 1, 4, 5, 5, 5}, 
				{6, 6, 0, 4, 5, 5}, 
				{6, 6, 6, 1, 5, 5}, 
				{6, 5, 6, 2, 1, 5}, 
				{6, 6, 6, 5, 5, 5}};
		Pools pools = new Pools(input);
		assertEquals("Correct amount of water", 4 + 0 + 3 + 2 + 3 + 1 + 1, 
				pools.findPools());
		assertEquals("Tile (0, 1) is black", Color.BLACK, pools.getTile(0, 1)
				.getColor());
		assertEquals("Tile (1, 1) is black", Color.BLACK, pools.getTile(1, 1)
				.getColor());
		assertEquals("Tile (2, 1) is blue", Color.BLUE, pools.getTile(2, 1)
				.getColor());
		assertEquals("Tile (2, 2) is black", Color.BLACK, pools.getTile(2, 2)
				.getColor());
		assertEquals("Tile (3, 2) is blue", Color.BLUE, pools.getTile(3, 2)
				.getColor());
		assertEquals("Tile (3, 3) is black", Color.BLACK, pools.getTile(3, 3)
				.getColor());
		assertEquals("Tile (4, 3) is blue", Color.BLUE, pools.getTile(4, 3)
				.getColor());
		assertEquals("Tile (5, 3) is blue", Color.BLUE, pools.getTile(5, 3)
				.getColor());
		assertEquals("Tile (5, 4) is blue", Color.BLUE, pools.getTile(5, 4)
				.getColor());
		assertEquals("Tile (5, 1) is blue", Color.BLUE, pools.getTile(5, 1)
				.getColor());
	}
}
