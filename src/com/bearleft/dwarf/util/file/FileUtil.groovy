package com.bearleft.dwarf.util.file

/**
 * User: Eric Siebeneich
 * Date: 4/21/13
 */
class FileUtil {

	public static String getExtension(File file) {
		return file.name.substring(file.name.lastIndexOf('.') + 1)
	}
}
