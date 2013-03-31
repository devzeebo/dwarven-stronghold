package com.bearleft.dwarf.actor.effect
import com.bearleft.dwarf.config.CloneContainer
import com.bearleft.dwarf.config.IBuilder
import com.bearleft.dwarf.config.IConfigurable
import com.bearleft.dwarf.config.MetaUnit
/**
 * User: Eric Siebeneich
 * Date: 3/29/13
 */
class ActorEffect implements IConfigurable {

	protected String effectName

	protected long triggerSpeed
	protected Closure applyEffect

	private ActorEffect() {}

	public ActorEffect(String effect) {
		CloneContainer.clone(ActorEffect, effect, this)
	}

	public Object getKey() {
		return effectName.toLowerCase()
	}

	public String getEffectName() {
		return effectName
	}

	//////////////////////
	// DSL BUILDER CODE //
	//////////////////////

	static class ActorEffectBuilder implements IBuilder<ActorEffect> {

		ActorEffect effect

		void newItem(String type) {
			effect = new ActorEffect()
			effect.effectName = type
		}

		def effect(Closure applyEffect) {
			effect.applyEffect = applyEffect
		}

		def trigger(Number millis) {
			effect.triggerSpeed = millis
		}

		long every(MetaUnit time) {
			return time['milli']
		}

		ActorEffect getBuiltItem() {
			return effect
		}

		Class<ActorEffect> getType() {
			return ActorEffect
		}
	}
}