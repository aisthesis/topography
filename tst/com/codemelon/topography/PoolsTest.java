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
}
