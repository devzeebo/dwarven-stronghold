package com.bearleft.dwarf.map
import com.bearleft.dwarf.config.CloneContainer
import com.bearleft.dwarf.config.IBuilder
import com.bearleft.dwarf.config.IConfigurable

import javax.imageio.ImageIO
import java.awt.*
import java.util.List
/**
 * User: Eric Siebeneich
 * Date: 3/24/13
 */
class GameTile implements IConfigurable {

	public GameTile(int type) {
		GameTile source = CloneContainer.clone(GameTile, type, this, ['images'])
		if (source.images) {
			image = source.images[(int)(Math.random() * source.images.size())]
		}
	}

	int type
	byte flags
	Color color
	String effect
	Image image

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

	List<Image> images

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

		void url(String url) {
			if (!gameTile.images) {
				gameTile.images = []
			}
			gameTile.images.add(ImageIO.read(new File(url)))
		}

		public GameTile getBuiltItem() {
			return gameTile
		}

		public Class<GameTile> getType() {
			return GameTile
		}
	}
}
