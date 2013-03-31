package com.bearleft.dwarf.config

import java.lang.reflect.Modifier
/**
 * User: Eric Siebeneich
 * Date: 3/30/13
 */
class CloneContainer {

	public Map<Class, Map<Object, Object>> clones = new HashMap<Class, Map<Object, Object>>()

	private static class Singleton {
		private static final CloneContainer instance = new CloneContainer()
	}

	private CloneContainer() {}

	protected static <T> void addClone(Class<T> type, def key, T value) {

		Singleton.instance.with {
			if (!clones[type]) {
				clones[type] = new HashMap<Object, Object>()
			}
			clones[type][key] = value
		}
	}

	public static <T> Map<Object, T> getAt(Class<T> type) {
		return Singleton.instance.clones[type]
	}

	public static <T> void clone(Class<T> type, Object key, T obj) {
		T clone = CloneContainer[type][key]
		type.declaredFields.findAll { !(it.modifiers & Modifier.FINAL) }.each {
			obj."${it.name}" = clone."${it.name}"
		}
	}
}