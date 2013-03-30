package com.bearleft.dwarf.ui
import com.bearleft.dwarf.actor.Actor
import com.bearleft.dwarf.config.ConfigBootstrap
import com.bearleft.dwarf.config.ConfigLoader
import com.bearleft.dwarf.map.GameMap
import com.bearleft.dwarf.map.GameTile

import javax.swing.*
import java.awt.*
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
/**
 * User: Eric Siebeneich
 * Date: 3/24/13
 */
class RenderWindow extends JPanel {

	Actor player

	RenderWindow() {
		JFrame frame = new JFrame()
		frame.contentPane = this
		frame.size = [1024, 768] as Dimension
		frame.locationRelativeTo = null
		frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

		ConfigLoader.load(ConfigBootstrap)

		map = new GameMap(10, 10)
		(0..<10).each { r ->
			(0..<10).each { c ->
				map[r][c] = new GameTile(Math.round(1 + Math.random() * 3) as int)
			}
		}

		player = new Actor()

		bindKeyListeners.delegate = frame
		bindKeyListeners()

		frame.visible = true
	}

	GameMap map

	public void paintComponent(Graphics g) {
		g.clearRect(0, 0, width, height)

		int tileWidth = 20
		int tileHeight = 20

		(0..<10).each { r ->
			(0..<10).each { c ->
				GameTile tile = map[r][c]
				g.setColor(tile.color)
				g.fillRect(r * tileWidth + 100 + r, c * tileHeight + 100 + c, tileWidth, tileHeight)
			}
		}

		GameTile tile = new GameTile(5)
		g.setColor(tile.color)
		g.fillRect(player.x * tileWidth + 100 + player.x, player.y * tileHeight + 100 + player.y, tileWidth, tileHeight)
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
