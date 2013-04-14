package com.bearleft.dwarf.actor

import com.bearleft.dwarf.actor.effect.ActorEffect
import com.bearleft.dwarf.actor.skill.ActorSkill

/**
 * User: Eric Siebeneich
 * Date: 3/30/13
 */
class Actor {

	List<ActorEffect> effects
	Map<String, ActorSkill> skills

	int x
	int y

	public Actor() {

		effects = []
		skills = [:]

		x = 0
		y = 0
	}
}