package com.bearleft.dwarf.ui

import com.bearleft.dwarf.map.GameMap
import com.bearleft.dwarf.map.GameTile
import com.bearleft.dwarf.map.TileLoader

import javax.swing.*
import java.awt.*

/**
 * User: Eric Siebeneich
 * Date: 3/24/13
 */
class RenderWindow extends JPanel {

	RenderWindow() {
		JFrame frame = new JFrame()
		frame.contentPane = this
		frame.size = [1024, 768] as Dimension
		frame.locationRelativeTo = null
		frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

		new TileLoader().load()

		map = new GameMap(10, 10)
		(0..<10).each { r ->
			(0..<10).each { c ->
				map[r][c] = new GameTile(Math.round(1 + Math.random()) as int)
			}
		}

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
	}

	public static void main(String[] args) {
		new RenderWindow()
	}
}
