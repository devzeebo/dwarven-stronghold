package com.bearleft.dwarf.ui.input
import com.badlogic.gdx.Gdx
import org.lwjgl.input.Keyboard
import org.lwjgl.input.Mouse
/**
 * User: Eric Siebeneich
 * Date: 4/21/13
 */
class InputHandler {

	protected Map<String, Map<Integer, KeyEvent>> keyEvents = [:]
	protected Map<String, Map<Integer, MouseEvent>> mouseListeners = [:]
	protected String currentName

	public static final int MOUSE_MOVED = -1

	public InputHandler() {
		Thread.startDaemon {
			while(true) {
				handleInput()
				sleep 10
			}
		}
	}

	public void leftShift(Closure c) {
		keyEvents.clear()
		c.delegate = this
		c()
	}

	private void name(String name) {
		keyEvents[name] = [:]
		mouseListeners[name] = [:]
		currentName = name
	}
	public void setName(String name) {
		currentName = name
	}

	private void onKeyDown(int keyCode, long repeatTime, Closure closure) {
		addKeyEvent(keyCode, repeatTime, 'keyDown', closure)
	}

	private void onKeyDown(int keyCode, Closure closure) {
		addKeyEvent(keyCode, 0, 'keyDown', closure)
	}

	private void onMouseMoved(Closure closure) {
		mouseListeners[currentName][MOUSE_MOVED] = new MouseEvent(button: MOUSE_MOVED, buttonPressed: closure)
	}

	private void onMousePressed(int button, Closure closure) {
		addMouseEvent(button)
		mouseListeners[currentName][button].buttonPressed = closure
	}

	private void onMouseReleased(int button, Closure closure) {
		addMouseEvent(button)
		mouseListeners[currentName][button].buttonReleased = closure
	}

	private void addMouseEvent(int button) {
		if (!mouseListeners[currentName][button]) {
			mouseListeners[currentName][button] = new MouseEvent(button: button)
		}
	}

	private void addKeyEvent(int keyCode, long repeatTime, String prop, Closure clos) {
		if (!keyEvents[currentName][keyCode]) {
			keyEvents[currentName][keyCode] = new KeyEvent(keycode: keyCode, repeatTime: repeatTime)
		}
		keyEvents[currentName][keyCode]."${prop}" = clos
	}

	public void handleInput() {
		if (Keyboard.created && currentName && keyEvents[currentName]) {
			keyEvents[currentName].each { int key, KeyEvent value ->
				if (Gdx.input.isKeyPressed(key)) {

					long curTime = System.currentTimeMillis()

					if (value.lastInvoked + value.repeatTime < curTime) {
						value.keyDown()
						value.lastInvoked = curTime
					}
				}
			}
		}
		if (Mouse.created && mouseListeners[currentName]) {
			mouseListeners[currentName].each { int key, MouseEvent event ->
				if (key == MOUSE_MOVED || !event.isDown && Gdx.input.isButtonPressed(key)) {
					if (event.buttonPressed) {
						event.buttonPressed(Gdx.input.x, Gdx.input.y)
					}
					event.isDown = true
				}
				if (event.isDown && !Gdx.input.isButtonPressed(key)) {
					if (event.buttonReleased) {
						event.buttonReleased(Gdx.input.x, Gdx.input.y)
					}
					event.lastInvoked = System.currentTimeMillis()
					event.isDown = false
				}
			}
		}
	}
}
