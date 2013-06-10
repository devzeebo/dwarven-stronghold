package com.bearleft.dwarf.ui.input

import java.awt.geom.Point2D

/**
 * User: Eric Siebeneich
 * Date: 6/1/13
 */
class MouseState {

	public static final int STATE_NONE = 0
	public static final int STATE_DRAG = 1

	Point2D.Float coords = new Point2D.Float()

	Point2D.Float drag = new Point2D.Float()

	int state

	public void startDrag(int x, int y) {
		if (state != STATE_DRAG) {
			startDrag(new Point2D.Float(x, y))
		}
	}
	public void startDrag(Point2D.Float coords) {
		if (state != STATE_DRAG) {
			drag = coords
			state = STATE_DRAG
		}
	}

	public void endDrag(int x, int y) {
		if (state == STATE_DRAG) {
			state = STATE_NONE
		}
	}

	public void setX(float x) {
		coords.x = x
	}
	public void setY(float y) {
		coords.y = y
	}
	public float getX() {
		return coords.@x
	}
	public float getY() {
		return coords.@y
	}
}
