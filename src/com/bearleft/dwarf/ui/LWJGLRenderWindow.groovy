package com.bearleft.dwarf.ui
import com.bearleft.dwarf.ui.support.LWJGLSupport
import org.lwjgl.input.Keyboard
/**
 * User: Eric Siebeneich
 * Date: 4/21/13
 */
class LWJGLRenderWindow extends LWJGLSupport {

	public LWJGLRenderWindow() {
		inputHandler << moveInputHandler
		inputHandler.name = 'Move'

		start()
	}

	public void render() {

	}

	public void handleInput() {
		if (inputHandler) {
			inputHandler.handleInput()
		}
	}

	def moveInputHandler = {
		name 'Move'
		onKeyDown(Keyboard.KEY_ESCAPE) {
			destroy()
		}
	}

	public static void main(String[] args) {
		new LWJGLRenderWindow()
	}
}
