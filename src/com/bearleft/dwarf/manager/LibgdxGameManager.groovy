package com.bearleft.dwarf.manager
import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.bearleft.dwarf.config.CloneContainer
import com.bearleft.dwarf.config.ConfigBootstrap
import com.bearleft.dwarf.map.GameMap
import com.bearleft.dwarf.map.GameTile
import com.bearleft.dwarf.resource.ResourceLoader
import com.bearleft.dwarf.resource.asset.ClasspathFileHandleResolver
import com.bearleft.dwarf.util.MetaUtility
/**
 * User: Eric Siebeneich
 * Date: 4/22/13
 */
class LibgdxGameManager implements ApplicationListener {

	GameMap map
	AssetManager am

	@Override
	void create() {
		ResourceLoader.load(ConfigBootstrap)

		am = new AssetManager(new ClasspathFileHandleResolver())

		CloneContainer[GameTile].values().each { GameTile tile ->
			if (tile.images) {
				tile.images.each { am.load(it, Texture) }
			}
		}

		am.finishLoading()
	}

	@Override
	void resize(int i, int i1) {

	}

	@Override
	void render() {
		SpriteBatch batch = new SpriteBatch()

		GameTile gt = new GameTile(1)

		batch.begin()
		batch.draw(am.get(gt.image), 100, 100)
		batch.end()
	}

	@Override
	void pause() {

	}

	@Override
	void resume() {

	}

	@Override
	void dispose() {

	}

	public static void main(String[] args) {
		MetaUtility.bind()

		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration()
		cfg.with {
			title = 'Dwarven Stronghold'
			useGL20 = true
			width = 800
			height = 600
		}
		new LwjglApplication(new LibgdxGameManager(), cfg)
	}
}
