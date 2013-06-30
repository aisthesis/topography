package com.codemelon.topography;

/**
 * @author Marshall Farrier
 * @my.created Jun 28, 2013
 * @my.edited Jun 28, 2013
 */
public enum Color {
	WHITE, // unexplored
	GRAY, // currently being explored
	BLACK, // effective edges that aren't part of local discovery
	BLUE, // containing water
	GREEN	// effective edges that are part of local discovery
}
