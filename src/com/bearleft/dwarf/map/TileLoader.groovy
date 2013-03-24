package com.bearleft.dwarf.map
/**
 * User: Eric Siebeneich
 * Date: 3/24/13
 */
class TileLoader extends TileLoaderSupport {
	public void load() {
		"1" {
			flags(PASSABLE)
		}
	}

	public static void main(String[] args) {
		new TileLoader().load()

		println GameTile.tiles.toString()
	}
}
