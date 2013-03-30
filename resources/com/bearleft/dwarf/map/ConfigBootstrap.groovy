package com.bearleft.dwarf.map
/**
 * User: Eric Siebeneich
 * Date: 3/29/13
 */

load (TileSet, GameTile) { GameTile tile ->
	GameTile.tiles[tile.type] = tile
}