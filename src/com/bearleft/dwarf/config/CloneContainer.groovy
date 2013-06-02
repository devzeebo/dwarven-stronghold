package com.bearleft.dwarf.config

import java.lang.reflect.Modifier
/**
 * User: Eric Siebeneich
 * Date: 3/30/13
 */
@Singleton
class CloneContainer {

	public Map<Class, Map<Object, ?>> clones = [:]

	private CloneContainer() {}

	protected static <T> void addClone(Class<T> type, def key, T value) {

		CloneContainer.instance.with {
			if (!clones[type]) {
				clones[type] = new HashMap<Object, Object>()
			}
			clones[type][key] = value
		}
	}

	public static <T> Map<Object, T> getAt(Class<T> type) {
		return CloneContainer.instance.clones[type]
	}

	public static <T> T clone(Class<T> type, Object key, T obj) {
		T clone = CloneContainer[type][key]
		type.declaredFields.findAll { !(it.modifiers & Modifier.FINAL) }.each {
			obj."${it.name}" = clone."${it.name}"
		}
		return clone
	}
	public static <T> T clone(Class<T> type, Object key, T obj, List<String> exclude) {
		T clone = CloneContainer[type][key]
		type.declaredFields.findAll { !((it.modifiers & Modifier.FINAL) || exclude.contains(it.name) || it.synthetic) }.each {
			obj."${it.name}" = clone."${it.name}"
		}
		return clone
	}
}