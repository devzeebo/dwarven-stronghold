package com.bearleft.dwarf.resource
/**
 * User: Eric Siebeneich
 * Date: 4/23/13
 */
class ResourceBundler {

	List<String> resourceTypes
	List<File> resources = []

	public ResourceBundler(List<String> types = []) {
		resourceTypes = types
	}

	public File bundle(String output, String base) {
		def includes = resources.collect { it.absolutePath.replace('\\', '/').replace(base.replace('\\', '/'), '').replaceAll('^(/|\\\\)', '') }.join(',')

		new AntBuilder().with {
			jar destfile: output,
				basedir: base,
				includes: includes
		}
		return new File(output)

	}

	public void leftShift(String resource) {
		this << new File(resource)
	}

	public void leftShift(File file) {
		if (file.exists()) {
			if (file.directory) {
				file.eachFile {
					this << it
				}
			}
			else if (file.file) {
				if (file.absolutePath ==~ /^.*?\.(?:${resourceTypes.join('|')})$/) {
					resources << file
				}
			}
		}
	}
}
