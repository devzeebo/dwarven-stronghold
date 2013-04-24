package com.bearleft.dwarf.resource.asset

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.loaders.FileHandleResolver
import com.badlogic.gdx.files.FileHandle

/**
 * User: Eric Siebeneich
 * Date: 4/23/13
 */
class ClasspathFileHandleResolver implements FileHandleResolver {

	@Override
	FileHandle resolve(String resource) {
		return Gdx.files.classpath(resource)
	}
}
