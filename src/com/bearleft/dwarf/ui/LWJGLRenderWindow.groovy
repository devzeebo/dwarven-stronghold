package com.bearleft.dwarf.ui

import com.bearleft.dwarf.ui.support.LWJGLSupport
import org.lwjgl.input.Keyboard
import org.lwjgl.opengl.Display
/**
 * User: Eric Siebeneich
 * Date: 4/21/13
 */
class LWJGLRenderWindow extends LWJGLSupport {

	public LWJGLRenderWindow() {
		Display.setDisplayMode(Display.desktopDisplayMode)
		Display.create()

		inputHandler << moveInputHandler
		inputHandler.name = 'Move'
		while(Display.created && !Display.closeRequested) {
			if (Display.created) {
				Display.update()
				gameLoop()
			}
		}

		destroy()
	}

	public void handleInput() {
		if (inputHandler) {
			inputHandler.handleInput()
		}
	}

	def moveInputHandler = {
		delegate.name 'Move'
		delegate.onKeyDown(Keyboard.KEY_ESCAPE) {
			destroy()
		}
	}

	public static void main(String[] args) {
		new LWJGLRenderWindow()
	}
}
