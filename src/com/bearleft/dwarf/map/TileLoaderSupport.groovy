package com.bearleft.dwarf.map

/**
 * User: Eric Siebeneich
 * Date: 3/24/13
 */
class TileLoaderSupport {

	def methodMissing(String methodName, args) {
		GameTile tile = new GameTile()
		tile.type = Integer.parseInt(methodName)

		Closure clos = (Closure)args[0]
		clos.delegate = tile
		clos()

		GameTile.tiles[tile.type] = tile
	}
}
