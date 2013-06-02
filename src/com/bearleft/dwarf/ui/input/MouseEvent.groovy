package com.bearleft.dwarf.ui.input

/**
 * User: Eric Siebeneich
 * Date: 6/1/13
 */
class MouseEvent {

	int button

	Closure buttonPressed
	Closure buttonReleased

	boolean isDown
	long lastInvoked
}
