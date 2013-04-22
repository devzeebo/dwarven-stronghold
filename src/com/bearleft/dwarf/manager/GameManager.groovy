package com.bearleft.dwarf.manager

import org.newdawn.slick.AppGameContainer
import org.newdawn.slick.BasicGame
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import org.newdawn.slick.SlickException

/**
 * User: Michael John Slater
 * Date: 4/21/13
 */
class GameManager extends BasicGame {

	GameManager() {
		super("Dwarven Stronghold")
	}

	@Override
	public void init(GameContainer gc) throws SlickException {

	}

	@Override
	public void update(GameContainer gc, int delta) {

	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		g.drawString("Hello Dwarfs!",100,100)
	}


	public static void main(String[] args) {
		AppGameContainer app = new AppGameContainer(new GameManager())
		app.setDisplayMode(800, 600, false)
		app.start()
	}
}
