package com.bearleft.dwarf.map
import java.awt.*
import java.lang.reflect.Modifier
/**
 * User: Eric Siebeneich
 * Date: 3/24/13
 */
class GameTile {

	public static final byte PASSABLE  = 0b00000001
	public static final byte BUILDABLE = 0b00000010
	public static final byte SWIMMABLE = 0b00000100

	protected static final Map<Integer, GameTile> tiles = new HashMap<Integer, GameTile>()

	public GameTile(int type) {
		GameTile clone = tiles[type]
		GameTile.declaredFields.findAll { !(it.modifiers & Modifier.FINAL) }.each {
			this."${it.name}" = clone."${it.name}"
		}
	}

	protected GameTile(String type) {
		this.type = type as int
	}
	protected GameTile() {}

	int type
	byte flags
	Color color

	public void flags(byte... flags) {
		flags.each { this.flags |= it }
	}

	public void color(int color) {
		this.color = new Color(color)
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
}
