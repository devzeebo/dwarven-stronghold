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
	protected Map<String, Closure> mouseListeners = [:]
	protected String currentName

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
		currentName = name
	}
	public void setName(String name) {
		currentName = name
	}

	private void onKeyDown(int keyCode, long repeatTime, Closure clos) {
		addKeyEvent(keyCode, repeatTime, 'keyDown', clos)
	}

	private void onKeyDown(int keyCode, Closure clos) {
		addKeyEvent(keyCode, 0, 'keyDown', clos)
	}

	private void onMouseMoved(Closure clos) {
		mouseListeners[currentName] = clos
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
			mouseListeners[currentName](Gdx.input.x, Gdx.input.y)
		}
	}
}
