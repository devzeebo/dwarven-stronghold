package com.bearleft.dwarf.resource

import com.bearleft.dwarf.config.*
import com.bearleft.dwarf.util.MetaUtility
/**
 * User: Eric Siebeneich
 * Date: 3/29/13
 */
class ResourceLoader<T> {

	public static void load(Class<Script> configFile) {

		configFile.metaClass.load = { Class<Script> script, Class type ->
			load(script, type)
		}
		configFile.metaClass.loadDelayed = { Class<Script> script, Class type ->
			loadDelayed(script, type)
		}
		configFile.metaClass.loadCustom = { Class<Script> script, Class type ->
			loadCustom(script, type)
		}
		configFile.newInstance().run()
	}

	public static <S extends IBuilder<T>, T extends IConfigurable> void load(Class<Script> script, Class<S> type) {

		S builder = type.newInstance()

		definePropertyMissing(script, builder)

		defineMethodMissing(script, builder, false)

		script.newInstance().run()
	}

	public static <S extends IDelayedBuilder<T>, T extends IConfigurable> void loadDelayed(Class<Script> script, Class<S> type) {

		S builder = type.newInstance()

		definePropertyMissing(script, builder)

		defineMethodMissing(script, builder, true)

		script.newInstance().run()

		builder.items.each {
			T item = builder.buildItem(it)
			CloneContainer.addClone(builder.type, item.key, item)
		}
	}

	public static <S extends ICustomBuilder> void loadCustom(Class<Script> script, Class<S> type) {

		S builder = type.newInstance()

		script.metaClass.methodMissing = { String method, args ->
			builder."${method}"(args)
		}

		script.newInstance().run()
	}

	private static void defineMethodMissing(Class<Script> script, def builder, boolean delayed) {

		String currentType = null

		script.metaClass.methodMissing = { String method, args ->

			if (currentType != null && method.equals(currentType.substring(0, currentType.length() - 1))) {
				builder.type."${currentType}"[args['name'][0]] = args['value'][0]
			}
			else {
				if (method.startsWith('static')) {
					currentType = "${method.charAt(6).toLowerCase()}${method.substring(7)}"

					Closure clos = (Closure)args[0]
					clos.delegate = builder
					clos()
				}
				else {
					builder.newItem(method)

					Closure clos = (Closure)args[0]
					clos.delegate = builder
					clos()

					if (!delayed) {
						CloneContainer.addClone(builder.type, builder.builtItem.key, builder.builtItem)
					}
				}
			}
		}
	}

	private static void definePropertyMissing(Class<Script> script, def builder) {
		script.metaClass.propertyMissing = { String name -> builder.type."${name}" }
	}

	public static void main(String[] args) {
		MetaUtility.bind()
		ResourceLoader.load(ConfigBootstrap)

		println CloneContainer.Singleton.instance.clones
	}
}
