package com.bearleft.dwarf.ui.render

import java.awt.geom.Point2D

/**
 * User: Eric Siebeneich
 * Date: 4/28/13
 */
class RenderConfiguration {

	int width
	int height

	int tileWidth
	int tileHeight
	double zoom

	Point2D.Float center

	public RenderConfiguration(int screenWidth, int screenHeight, int tileWidth, int tileHeight, double zm = 1.0) {
		width = screenWidth
		height = screenHeight
		this.tileWidth = tileWidth
		this.tileHeight = tileHeight
		zoom = zm
		center = new Point2D.Float(screenWidth / 2 as float, screenHeight / 2 as float)
	}

	public int getTileWidth() {
		return tileWidth * zoom
	}
	public int getTileHeight() {
		return tileHeight * zoom
	}
}
