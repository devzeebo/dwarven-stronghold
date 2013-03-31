package com.bearleft.dwarf.actor

import com.bearleft.dwarf.actor.effect.ActorEffect

/**
 * User: Eric Siebeneich
 * Date: 3/30/13
 */
class Actor {

	List<ActorEffect> effects

	int x
	int y

	public Actor() {

		effects = []

		x = 0
		y = 0
	}
}