package com.bearleft.dwarf.model

/**
 * User: Eric Siebeneich
 * Date: 4/14/13
 */
class Loader {

	List<ILoadable> loadables
	IProgressListener listener

	public Loader() {
		loadables = []
	}

	public boolean load(Closure onComplete) {

		Thread.startDaemon {
			loadables.each {
				it.load(listener)
			}
			onComplete()
		}
	}
}
