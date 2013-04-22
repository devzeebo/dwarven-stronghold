package com.bearleft.dwarf.ui.support
import com.bearleft.dwarf.ui.input.InputHandler
import org.lwjgl.opengl.Display
import org.newdawn.slick.Image

/**
 * User: Eric Siebeneich
 * Date: 4/21/13
 */
abstract class LWJGLSupport {

	Image grass

	InputHandler inputHandler = new InputHandler()
	Thread currentThread

	boolean running

	public void destroy() {
		running = false
	}

	public boolean isShouldDie() {
		if (!running) {
			return true
		}
		return !(Display.created && !Display.closeRequested)
	}

	public abstract void render()

	public void start() {

		currentThread = Thread.currentThread()
		currentThread.name = 'Main Thread'
		running = true

		Thread.startDaemon('Render') {

			Display.displayMode = Display.desktopDisplayMode
			Display.create()

			grass = new Image('resources/images/gametile/grass.jpg')

			while(!shouldDie) {
				if (Display.created) {
					render()
					Display.update()
				}
			}

			Display.destroy()
			synchronized (currentThread) {
				currentThread.notify()
			}
		}
		inputHandler.start()

		synchronized (currentThread) {
			currentThread.wait()
		}
	}
}
