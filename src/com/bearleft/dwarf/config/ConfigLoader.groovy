package com.bearleft.dwarf.config

import com.bearleft.dwarf.map.GameTile

/**
 * User: Eric Siebeneich
 * Date: 3/29/13
 */
class ConfigLoader<T> {

	public static void load(Class<Script> configFile) {

		configFile.metaClass.load = { Class<Script> script, Class type, Closure onLoad ->
			load(script, type, onLoad)
		}
		configFile.newInstance().run()
	}

	public static void load(Class<Script> script, Class<T> type, Closure onLoad) {

		script.metaClass.methodMissing = { String method, args ->

			T var = type.newInstance(method)

			Closure clos = (Closure)args[0]
			clos.delegate = var
			clos()

			onLoad(var)
		}

		script.newInstance().run()
	}

	public static void main(String[] args) {
		ConfigLoader.load(ConfigBootstrap)

		println GameTile.tiles
	}
}
