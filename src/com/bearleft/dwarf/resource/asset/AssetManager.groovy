package com.bearleft.dwarf.resource.asset
/**
 * User: Eric Siebeneich
 * Date: 6/1/13
 */
@Singleton
class AssetManager {

	@Delegate
	static com.badlogic.gdx.assets.AssetManager manager = new com.badlogic.gdx.assets.AssetManager(new ClasspathFileHandleResolver())

	static <T> T getAt(String name) {
		return manager.<T> get(name)
	}
	static <T> T getAt(String name, Class<T> type) {
		return manager.get(name, type)
	}

	static void dispose() {
		manager.dispose()
	}
}
