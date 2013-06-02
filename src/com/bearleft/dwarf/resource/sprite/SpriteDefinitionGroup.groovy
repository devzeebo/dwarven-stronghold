package com.bearleft.dwarf.resource.sprite
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.bearleft.dwarf.config.CloneContainer
import com.bearleft.dwarf.config.IConfigurable
import com.bearleft.dwarf.config.ICustomBuilder
import com.bearleft.dwarf.resource.asset.AssetManager
/**
 * User: Eric Siebeneich
 * Date: 6/1/13
 */
class SpriteDefinitionGroup implements IConfigurable, ICustomBuilder {

	@Override
	Object getKey() {
		return null
	}

	def sprites = []
	def params

	void coords(Object[] dims) {
		params[1] = dims as int[]
	}

	void textureGroup(Object[] args) {

		sprites.clear()

		args[1]()

		String url = args[0]

		AssetManager.instance.load(url, Texture)
		AssetManager.instance.finishLoading()

		sprites.each { String name, int[] coords ->
			TextureRegion texture = new TextureRegion(AssetManager.getAt(url, Texture), coords[0], coords[1], coords[2], coords[3])

			CloneContainer.addClone(TextureRegion, name, texture)
		}
	}

	def methodMissing(String method, args) {
		params = ['', null]
		params[0] = method
		args[0][0]()

		sprites << params
	}
}
