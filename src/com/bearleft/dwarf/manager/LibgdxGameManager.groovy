package com.bearleft.dwarf.manager
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.bearleft.dwarf.config.CloneContainer
import com.bearleft.dwarf.config.ConfigBootstrap
import com.bearleft.dwarf.map.GameMap
import com.bearleft.dwarf.map.GameTile
import com.bearleft.dwarf.resource.ResourceLoader
import com.bearleft.dwarf.resource.asset.AssetManager
import com.bearleft.dwarf.ui.input.InputHandler
import com.bearleft.dwarf.ui.input.MouseState
import com.bearleft.dwarf.ui.render.Camera
import com.bearleft.dwarf.ui.render.RenderConfiguration
import com.bearleft.dwarf.util.MetaUtility
import org.lwjgl.input.Keyboard
import org.lwjgl.input.Mouse

import java.awt.geom.Point2D

/**
 * User: Eric Siebeneich
 * Date: 4/22/13
 */
class LibgdxGameManager extends ApplicationAdapter {

	static RenderConfiguration configuration = new RenderConfiguration(1600, 900, 128, 128, 0.5)
	GameMap map
	SpriteBatch batch
	ShapeRenderer shapes
	BitmapFont font
	InputHandler handler

	boolean debug = true

	Camera camera
	MouseState mouse

	@Override
	void create() {
		camera = new Camera(velocity: 0.05f, mouseVelocity: 0.1f, config: configuration)
		mouse = new MouseState()

		handler = new InputHandler()
		handler << {

			int bounds = 25
			def camLeft = { camera.x -= it ?: camera.velocity }
			def camRight = { camera.x += it ?: camera.velocity }
			def camUp = { camera.y -= it ?: camera.velocity }
			def camDown = { camera.y += it ?: camera.velocity }

			name 'camera'
			onKeyDown Input.Keys.LEFT, camLeft
			onKeyDown Input.Keys.RIGHT, camRight
			onKeyDown Input.Keys.UP, camUp
			onKeyDown Input.Keys.DOWN, camDown
			onMouseMoved { int x, int y ->
				if (x < bounds) {
					camLeft(camera.mouseVelocity)
				}
				if (y < bounds) {
					camUp(camera.mouseVelocity)
				}
				if (x > Gdx.graphics.width - bounds) {
					camRight(camera.mouseVelocity)
				}
				if (y > Gdx.graphics.height - bounds) {
					camDown(camera.mouseVelocity)
				}
				mouse.x = x
				mouse.y = y
			}
			onMousePressed Input.Buttons.LEFT, { int x, int y ->
				mouse.startDrag(camera.convertToWorldCoords(x, y))
			}
			onMouseReleased Input.Buttons.LEFT, { int x, int y ->
				mouse.endDrag(x, y)
			}
		}

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

		Keyboard.enableRepeatEvents(true)

		Mouse.grabbed = true
		Mouse.clipMouseCoordinatesToWindow = true
	}

	@Override
	void render() {

		float cameraX = camera.x
		float cameraY = camera.y

		Gdx.graphics.getGL20().glClearColor(1, 0, 0, 1);
		Gdx.graphics.getGL20().glClear(Gdx.gl20.GL_COLOR_BUFFER_BIT | Gdx.gl20.GL_DEPTH_BUFFER_BIT);
		batch.begin()
		map.each(cameraX, cameraY,
				Gdx.graphics.width, Gdx.graphics.height, configuration)
				{ int r, int c, GameTile tile, rLoc, cLoc ->
					batch.draw(tile.texture, (float) rLoc, (float) cLoc, configuration.tileWidth, configuration.tileHeight)

					if (debug) {
						font.draw(batch, "(${c}, ${r})", (int) rLoc, (int) cLoc)
					}
				}

		batch.draw(CloneContainer[TextureRegion]['cursorBlue'], mouse.x, mouse.y)

		batch.end()

		shapes.begin(ShapeRenderer.ShapeType.Line)

		if (mouse.state == MouseState.STATE_DRAG) {
			Point2D.Float screenDrag = camera.convertToScreenCoords(mouse.drag)
			shapes.box(screenDrag.@x, screenDrag.@y, 1, (mouse.x - screenDrag.@x) as float, (mouse.y - screenDrag.@y) as float, 1)
		}

		shapes.end()
	}

	@Override
	void dispose() {
		AssetManager.dispose()
	}

	public static void main(String[] args) {
		MetaUtility.bind()

		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration()
		cfg.with {
			title = 'Dwarven Stronghold'
			useGL20 = true
			width = configuration.width
			height = configuration.height
			resizable = false
		}
		System.setProperty("org.lwjgl.opengl.Window.undecorated", "true")
		new LwjglApplication(new LibgdxGameManager(), cfg)
	}
}
