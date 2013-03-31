package com.bearleft.dwarf.map
import com.bearleft.dwarf.config.CloneContainer
import com.bearleft.dwarf.config.IBuilder
import com.bearleft.dwarf.config.IConfigurable

import java.awt.*
/**
 * User: Eric Siebeneich
 * Date: 3/24/13
 */
class GameTile implements IConfigurable {

	public static final byte PASSABLE  = 0b00000001
	public static final byte BUILDABLE = 0b00000010
	public static final byte SWIMMABLE = 0b00000100

	public GameTile(int type) {
		CloneContainer.clone(GameTile, type, this)
	}

	protected GameTile() {}

	int type
	byte flags
	Color color
	String effect

	public Object getKey() {
		return type
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder('type:')
				.append(type)
		['PASSABLE', 'BUILDABLE', 'SWIMMABLE'].each {
			if (flags & GameTile."${it}") {
				sb.append(",${it}")
			}
		}
		return sb.toString()
	}

	//////////////////////
	// DSL BUILDER CODE //
	//////////////////////

	static class GameTileBuilder implements IBuilder<GameTile> {

		private GameTile gameTile

		public void newItem(String type) {
			gameTile = new GameTile()
			gameTile.type = Integer.parseInt(type)
		}

		void flags(byte... flags) {
			flags.each { gameTile.flags |= it }
		}

		void color(int color) {
			gameTile.color = new Color(color)
		}

		void effect(String effect) {
			gameTile.effect = effect
		}

		public GameTile getBuiltItem() {
			return gameTile
		}

		public Class<GameTile> getType() {
			return GameTile
		}
	}
}
