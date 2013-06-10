package com.bearleft.dwarf.ui.render

import java.awt.geom.Point2D

/**
 * User: Eric Siebeneich
 * Date: 5/20/13
 */
class Camera {

	float x
	float y

	float velocity
	float mouseVelocity

	RenderConfiguration config

	public Point2D.Float convertToWorldCoords(float x, float y) {

		Point2D.Float ret = new Point2D.Float()

		ret.x = (x - config.center.@x) / config.tileWidth + this.x
		ret.y = (y - config.center.@y) / config.tileHeight + this.y

		println "${x}, ${y}"
		println config.center
		println ret

		return ret
	}
	public Point2D.Float convertToWorldCoords(Point2D.Float coords) {
		return convertToWorldCoords(coords.@x, coords.@y)
	}

	public Point2D.Float convertToScreenCoords(float x, float y) {

		Point2D.Float ret = new Point2D.Float()

		ret.x = (x - this.x) * config.tileWidth + config.center.@x
		ret.y = (y - this.y) * config.tileHeight + config.center.@y

		return ret
	}
	public Point2D.Float convertToScreenCoords(Point2D.Float coords) {
		return convertToScreenCoords(coords.@x, coords.@y)
	}
}
