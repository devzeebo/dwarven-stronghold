package com.bearleft.dwarf.model

import com.bearleft.dwarf.actor.Actor
import com.bearleft.dwarf.map.GameMap
import com.bearleft.dwarf.map.GameTile

/**
 * User: Eric Siebeneich
 * Date: 4/14/13
 */
class GameModel implements ILoadable {

	Map<String, Actor> actors
	GameMap map

	int w
	int h

	double percentComplete

	public GameModel(int w, int h) {
		actors = [:]

		this.w = w
		this.h = h
	}

	public boolean load(IProgressListener listener) {
		map = new GameMap(w, h)
		(0..<w).each { r ->
			(0..<h).each { c ->
				map[r][c] = new GameTile(1)
				listener.progressPercent = ((double)r * h + c) / (w * h)
			}
		}

		actors['player'] = new Actor()
		listener.progressPercent = 1
	}
}
