package com.bearleft.dwarf.actor.effect

/**
 * User: Eric Siebeneich
 * Date: 3/29/13
 */
class ActorEffect {

	protected String effectName

	protected int triggerSpeed
	protected Closure applyEffect

	protected ActorEffect(String effectName) {

	}

	String getEffectName() {
		return effectName
	}


}
