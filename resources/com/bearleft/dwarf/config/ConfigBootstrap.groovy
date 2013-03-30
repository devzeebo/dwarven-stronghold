package com.bearleft.dwarf.config

import com.bearleft.dwarf.map.GameTile
import com.bearleft.dwarf.map.TileSet

/**
 * User: Eric Siebeneich
 * Date: 3/29/13
 */

load (TileSet, GameTile) { GameTile tile ->
	GameTile.tiles[tile.type] = tile
}