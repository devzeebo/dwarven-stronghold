package com.bearleft.dwarf.ui.input

import org.lwjgl.input.Keyboard
/**
 * User: Eric Siebeneich
 * Date: 4/21/13
 */
class InputHandler {

	protected Map<String, Map<Integer, KeyEvent>> keyEvents = [:]
	protected String currentName

	public InputHandler() {
		Thread.startDaemon {
			while(true) {
				if (Keyboard.created) {
					handleInput()
				}
				sleep(10)
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

	private void onKeyDown(int keyCode, Closure clos) {
		addKeyEvent(keyCode, 'keyDown', clos)
	}
	private void onKeyUp(int keyCode, Closure clos) {
		addKeyEvent(keyCode, 'keyUp', clos)
	}
	private void addKeyEvent(int keyCode, String prop, Closure clos) {
		if (!keyEvents[currentName][keyCode]) {
			keyEvents[currentName][keyCode] = new KeyEvent(keycode: keyCode)
		}
		keyEvents[currentName][keyCode]."${prop}" = clos
	}


	public void handleInput() {
		while(Keyboard.next()) {
			KeyEvent ke = keyEvents[currentName][Keyboard.eventKey]
			if (ke) {
				if (Keyboard.eventKeyState) {
					ke.keyDown?.call()
				}
				else {
					ke.keyUp?.call()
				}
			}
		}
	}
}
