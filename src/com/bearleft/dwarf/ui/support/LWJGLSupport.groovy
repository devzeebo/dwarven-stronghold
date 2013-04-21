package com.bearleft.dwarf.ui.support
import com.bearleft.dwarf.ui.input.InputHandler
import org.lwjgl.opengl.Display
/**
 * User: Eric Siebeneich
 * Date: 4/21/13
 */
class LWJGLSupport {

	InputHandler inputHandler = new InputHandler()

	boolean shouldDie

	public void destroy() {
		shouldDie = true
	}

	public void gameLoop() {
		if (shouldDie) {
			Display.destroy()
		}
	}
}
