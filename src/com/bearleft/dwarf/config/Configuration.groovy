package com.bearleft.dwarf.config

/**
 * User: Eric Siebeneich
 * Date: 4/1/13
 */
class Configuration {

	private static Map<String, String> configurationSettings = [:]

	public static void loadConfigFile(String fileName) {

		File file = new File(fileName)

		file.withReader { Reader reader ->
			reader.eachLine { it ->
				String[] prop = it.split('=')
				configurationSettings[prop[0]] = prop[1]
			}
		}
	}

	public static String getAt(String key) {
		return configurationSettings[key]
	}
}
