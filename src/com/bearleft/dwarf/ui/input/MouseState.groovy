package com.bearleft.dwarf.ui.input

/**
 * User: Eric Siebeneich
 * Date: 6/1/13
 */
class MouseState {

	public static final int STATE_NONE = 0
	public static final int STATE_DRAG = 1

	int x
	int y

	int dragX
	int dragY

	int state

	public void startDrag(int x, int y) {
		if (state != STATE_DRAG) {
			dragX = x
			dragY = y
			state = STATE_DRAG
		}
	}

	public void endDrag(int x, int y) {
		if (state == STATE_DRAG) {
			state = STATE_NONE
		}
	}
}
