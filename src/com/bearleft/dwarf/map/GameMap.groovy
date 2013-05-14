package com.bearleft.dwarf.map

import com.bearleft.dwarf.ui.render.RenderConfiguration

/**
 * User: Eric Siebeneich
 * Date: 3/24/13
 */
class GameMap {

	int rows
	int cols
	GameTile[][] tiles

	public GameMap(int r, int c) {
		tiles = new GameTile[r][c]
		rows = r
		cols = c
	}

	GameTile getAt(int r, int c) {
		return tiles[r % rows][c % cols]
	}

	void putAt(def pos, GameTile tile) {
		tiles[pos[0] % rows][pos[1] % cols] = tile
	}

	public void each(int rMin, int cMin, int rMax, int cMax, Closure closure) {
		for (int r = rMin; r < rMax; r++) {
			for (int c = cMin; c < cMax; c++) {
				closure(r, c, this[r, c])
			}
		}
	}

	public void each(int cameraX, int cameraY, int screenWidth, int screenHeight, RenderConfiguration configuration, Closure closure) {
		double numCols = screenWidth / configuration.tileWidth
		double numRows = screenHeight / configuration.tileHeight

		int rMin = Math.floor(cameraY - numRows / 2.0) - 1
		int cMin = Math.floor(cameraX - numCols / 2.0) - 1
		int rMax = Math.ceil(cameraY + numRows / 2.0) + 1
		int cMax = Math.ceil(cameraX + numCols / 2.0) + 1

		for (int r = rMin; r < rMax; r++) {
			for (int c = cMin; c < cMax; c++) {
				closure(r, c, this[r, c],
						screenWidth / 2 + ((c - cameraX) * configuration.tileWidth) - configuration.tileWidth / 2,
						screenHeight / 2 + ((r - cameraY) * configuration.tileHeight) - configuration.tileHeight / 2)
			}
		}
	}
}
