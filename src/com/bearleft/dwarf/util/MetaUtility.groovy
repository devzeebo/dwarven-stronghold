package com.bearleft.dwarf.util

import com.bearleft.dwarf.util.file.FileUtil

/**
 * User: Eric Siebeneich
 * Date: 4/23/13
 */
class MetaUtility {

	private static boolean bound = false

	public static void bind() {
		synchronized (bound) {
			if (!bound) {
				MetaUnit.bind()
				FileUtil.bind()

				bound = true
			}
		}
	}
}
