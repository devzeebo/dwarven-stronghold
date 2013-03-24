package com.bearleft.dwarf.map

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
		clone.properties.keySet().each {
			this."${it}" = clone."${it}"
		}
	}

	protected GameTile() {}

	int type
	byte flags

	public void flags(byte... flags) {
		flags.each { this.flags |= it }
	}
}
