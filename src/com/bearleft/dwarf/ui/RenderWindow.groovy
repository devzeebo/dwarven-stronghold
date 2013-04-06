package com.bearleft.dwarf.ui

import com.bearleft.dwarf.actor.Actor
import com.bearleft.dwarf.config.ConfigBootstrap
import com.bearleft.dwarf.config.Configuration
import com.bearleft.dwarf.config.ResourceLoader
import com.bearleft.dwarf.map.GameMap
import com.bearleft.dwarf.map.GameTile

import javax.swing.*
import java.awt.*
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.image.BufferStrategy

/**
 * User: Eric Siebeneich
 * Date: 3/24/13
 */
class RenderWindow extends JPanel {

	Actor player

	int w = 100
	int h = 100

	Renderer renderer

	JFrame frame

	boolean debug = true

	RenderWindow() {

		Configuration.loadConfigFile('settings.ini')

		frame = new JFrame()
		frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
		frame.undecorated = true
		frame.ignoreRepaint = true
		GraphicsEnvironment ge = GraphicsEnvironment.localGraphicsEnvironment
		GraphicsDevice gd = ge.screenDevices.find { it.IDstring == Configuration['screenDevice'] }
		gd.fullScreenWindow = frame

		frame.visible = true

		renderLoop(frame)

		double percentComplete = 0.0

		renderer = [render: { Graphics g ->
			g.clearRect(0, 0, frame.width, frame.height)
			g.drawString("${String.format("%.2f", percentComplete)}%", 600, 500)
		}] as Renderer

		ResourceLoader.load(ConfigBootstrap)

		map = new GameMap(w, h)
		(0..<w).each { r ->
			(0..<h).each { c ->
				map[r][c] = new GameTile(1)
				percentComplete = ((double)r * h + c) / (w * h) * 100
				frame.repaint()
			}
		}

		player = new Actor()

		bindKeyListeners.delegate = frame
		bindKeyListeners()

		renderer = [render: { Graphics g ->
			int tileWidth = 64
			int tileHeight = 64

			int numTilesX = (int)(frame.width / tileWidth + 2)
			int numTilesY = (int)(frame.height / tileHeight + 2)

			(player.x - numTilesX / 2 ..< player.x + numTilesX / 2).each { r ->
				(player.y - numTilesY / 2 ..< player.y + numTilesY / 2).each { c ->
					drawTile(g, map[(int)r][(int)c], (int)r, (int)c, tileWidth, tileHeight, player.x, player.y)
				}
			}
			drawTile(g, new GameTile(5), player.x, player.y, tileWidth, tileHeight, player.x, player.y)
			if (debug) {
				g.drawLine((int)frame.width / 2, 0, (int)frame.width / 2, (int)frame.height)
				g.drawLine(0, (int)frame.height / 2, (int)frame.width, (int)frame.height / 2)
			}
		}] as Renderer
	}

	GameMap map

	interface Renderer {
		public void render(Graphics g)
	}

	public void renderLoop(JFrame frame) {

//		BufferCapabilities cap = new BufferCapabilities(new ImageCapabilities(true), new ImageCapabilities(true), BufferCapabilities.FlipContents.COPIED)
		frame.createBufferStrategy(2)
		BufferStrategy strat = frame.bufferStrategy

		Thread.startDaemon {
			while(true) {
				if (!strat.contentsLost()) {
					Graphics g = strat.drawGraphics
					render(g)
					g.dispose()
					strat.show()
				}
			}
		}
	}

	public void render(Graphics g) {
		renderer?.render(g)
	}

	protected void drawTile(Graphics g, GameTile tile, int r, int c, int tileWidth, int tileHeight, int rOff, int cOff) {

		int cx = frame.width / 2 - tileWidth / 2
		int cy = frame.height / 2 - tileHeight / 2

		int x = cx + (r - rOff) * tileWidth
		int y = cy + (c - cOff) * tileHeight

		if (tile.image) {
			g.drawImage(tile.image, x, y, tileWidth, tileHeight, null)
		}
		else {
			g.color = tile.color
			g.fillRect(x, y, tileWidth, tileHeight)
		}
		if (debug) {
			g.color = Color.black
			g.drawString("($r,$c)", x + 5, y + 15)
		}
	}

	protected bindKeyListeners = {

		delegate.addKeyListener([
			keyPressed: { KeyEvent e ->
				switch (e.keyCode) {
					case KeyEvent.VK_LEFT:
					case KeyEvent.VK_A:
						player.x--
						delegate.repaint()
						break
					case KeyEvent.VK_RIGHT:
					case KeyEvent.VK_D:
						player.x++
						delegate.repaint()
						break
					case KeyEvent.VK_UP:
					case KeyEvent.VK_W:
						player.y--
						delegate.repaint()
						break
					case KeyEvent.VK_DOWN:
					case KeyEvent.VK_S:
						player.y++
						delegate.repaint()
						break
				}
			}]
		as KeyAdapter)
	}

	public static void main(String[] args) {
		new RenderWindow()
	}
}
