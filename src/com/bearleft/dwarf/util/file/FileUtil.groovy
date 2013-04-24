package com.bearleft.dwarf.util.file
/**
 * User: Eric Siebeneich
 * Date: 4/21/13
 */
class FileUtil {

	public static void bind() {
		File.metaClass.getExtension = { getExtension(delegate) }
	}

	public static String getExtension(File file) {
		if (file.exists()) {
			if (file.file) {
				return file.name.lastIndexOf('.') > 0 ? file.name.substring(file.name.lastIndexOf('.') + 1) : '[none]'
			}
			return '[directory]'
		}
		return '[file not found]'
	}
}
