package com.bearleft.dwarf.ui.input

/**
 * User: Eric Siebeneich
 * Date: 4/21/13
 */
class KeyEvent {

	int keycode
	Closure keyDown
	Closure keyUp

	long lastInvoked
	long repeatTime
}
