package com.bearleft.dwarf.ui.render

/**
 * User: Eric Siebeneich
 * Date: 4/28/13
 */
class RenderConfiguration {

	int tileWidth
	int tileHeight
	double zoom

	public int getTileWidth() {
		return tileWidth * zoom
	}
	public int getTileHeight() {
		return tileHeight * zoom
	}
}
