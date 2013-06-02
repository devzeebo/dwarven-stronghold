package com.bearleft.dwarf.map

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.bearleft.dwarf.config.CloneContainer
import com.bearleft.dwarf.config.IBuilder
import com.bearleft.dwarf.config.IConfigurable

import java.awt.*
import java.util.List
/**
 * User: Eric Siebeneich
 * Date: 3/24/13
 */
class GameTile implements IConfigurable {

	public GameTile(int type) {
		GameTile source = CloneContainer.clone(GameTile, type, this, ['sprites'])
		if (source.sprites) {
			texture = CloneContainer[TextureRegion][source.sprites[(int)(Math.random() * source.sprites.size())]]
		}
	}

	int type
	byte flags
	Color color
	String effect
	TextureRegion texture

	public Object getKey() {
		return type
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder('type:')
				.append(type)
		types.keySet().each {
			if (flags & GameTile."${it}") {
				sb.append(",${it}")
			}
		}
		return sb.toString()
	}

	//////////////////////
	// DSL BUILDER CODE //
	//////////////////////

	protected static final Map<String, Byte> types = [:]

	static {
		GameTile.metaClass.static.propertyMissing = { String name ->
			return types[name]
		}
	}

	List<String> sprites

	protected GameTile() {}

	static class GameTileBuilder implements IBuilder<GameTile> {

		private GameTile gameTile

		public void newItem(String type) {
			gameTile = new GameTile()
			gameTile.type = Integer.parseInt(type)
		}

		void flags(Object... flags) {
			flags.each { gameTile.flags |= it }
		}

		void color(int color) {
			gameTile.color = new Color(color)
		}

		void effect(String effect) {
			gameTile.effect = effect
		}

		void sprite(String url) {
			if (!gameTile.sprites) {
				gameTile.sprites = []
			}
			gameTile.sprites.add(url)
		}

		public GameTile getBuiltItem() {
			return gameTile
		}

		public Class<GameTile> getType() {
			return GameTile
		}
	}
}
