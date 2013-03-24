package com.bearleft.dwarf.map

/**
 * User: Eric Siebeneich
 * Date: 3/24/13
 */
class GameMap {

	private class GameMapRow {
		GameTile[] tiles

		GameMapRow(int c) {
			tiles = new GameTile[c]
		}

		GameTile getAt(int c) {
			return tiles[c]
		}
	}

	GameMapRow[] rows

	public GameMap(int r, int c) {
		rows = new GameMapRow[r]
		(0..<r).each { rows[r] = new GameMapRow(c) }
	}

	GameMapRow getAt(int r) {
		return rows[r]
	}
}
