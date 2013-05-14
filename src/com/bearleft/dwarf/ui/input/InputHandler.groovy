package com.bearleft.dwarf.ui.input
import com.badlogic.gdx.InputAdapter
/**
 * User: Eric Siebeneich
 * Date: 4/21/13
 */
class InputHandler extends InputAdapter {

	protected Map<String, Map<Integer, KeyEvent>> keyEvents = [:]
	protected String currentName

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

	public boolean keyDown(int keycode) {
		Closure closure = keyEvents[currentName][keycode]?.keyDown
		if (closure) {
			closure()
		}
	}
	public boolean keyUp(int keycode) {
		Closure closure = keyEvents[currentName][keycode]?.keyUp
		if (closure) {
			closure()
		}
	}
}
