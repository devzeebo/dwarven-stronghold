package com.bearleft.dwarf.manager
import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.bearleft.dwarf.config.CloneContainer
import com.bearleft.dwarf.config.ConfigBootstrap
import com.bearleft.dwarf.map.GameMap
import com.bearleft.dwarf.map.GameTile
import com.bearleft.dwarf.resource.ResourceLoader
import com.bearleft.dwarf.resource.asset.ClasspathFileHandleResolver
import com.bearleft.dwarf.ui.render.RenderConfiguration
import com.bearleft.dwarf.util.MetaUtility
/**
 * User: Eric Siebeneich
 * Date: 4/22/13
 */
class LibgdxGameManager implements ApplicationListener {

	static RenderConfiguration configuration = new RenderConfiguration(tileWidth: 128, tileHeight: 128, zoom: 0.5)
	GameMap map
	AssetManager am
	SpriteBatch batch
	ShapeRenderer shapes
	BitmapFont font

	boolean debug = true

	int cameraX
	int cameraY

	@Override
	void create() {
		ResourceLoader.load(ConfigBootstrap)

		map = new GameMap(100, 100)
		(0..<map.rows).each { r ->
			(0..<map.cols).each { c ->
				map[r, c] = new GameTile(1)
			}
		}

		batch = new SpriteBatch()
		batch.projectionMatrix.setToOrtho2D(0, Gdx.graphics.height, Gdx.graphics.width, -Gdx.graphics.height)

		font = new BitmapFont(true)

		shapes = new ShapeRenderer()
		shapes.projView.setToOrtho2D(0, Gdx.graphics.height, Gdx.graphics.width, -Gdx.graphics.height)

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
		batch.begin()
		map.each(
				cameraX, cameraY,
				Gdx.graphics.width, Gdx.graphics.height, configuration)
			{ int r, int c, GameTile tile, rLoc, cLoc ->
				batch.draw(am.get(tile.image), rLoc, cLoc, configuration.tileWidth, configuration.tileHeight)
				if (debug) {
					font.draw(batch, "(${r}, ${c})", rLoc, cLoc)
				}
			}
		batch.end()

		if (debug) {
			shapes.color = Color.BLACK
			shapes.begin(ShapeRenderer.ShapeType.Line)
			map.each(cameraX, cameraY,
					Gdx.graphics.width, Gdx.graphics.height, configuration)
				{ int r, int c, GameTile tile, rLoc, cLoc ->
					shapes.box(rLoc, cLoc, 1, configuration.tileWidth, configuration.tileHeight, 1)
				}
			shapes.line(0, Gdx.graphics.height / 2, Gdx.graphics.width, Gdx.graphics.height / 2)
			shapes.line(Gdx.graphics.width / 2, 0, Gdx.graphics.width / 2, Gdx.graphics.height)
			shapes.end()
		}
	}

	@Override
	void pause() {

	}

	@Override
	void resume() {

	}

	@Override
	void dispose() {
		am.dispose()
	}

	public static void main(String[] args) {
		MetaUtility.bind()

		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration()
		cfg.with {
			title = 'Dwarven Stronghold'
			useGL20 = true
			width = 1920
			height = 1080
			resizable = false
		}
		System.setProperty("org.lwjgl.opengl.Window.undecorated", "true")
		new LwjglApplication(new LibgdxGameManager(), cfg)
	}
}
