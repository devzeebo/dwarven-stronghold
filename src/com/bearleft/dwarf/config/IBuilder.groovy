package com.bearleft.dwarf.config

/**
 * User: Eric Siebeneich
 * Date: 3/30/13
 */
interface IBuilder<T extends IConfigurable> {

	public T getBuiltItem()

	public void newItem(String type)

	public Class<T> getType()
}
