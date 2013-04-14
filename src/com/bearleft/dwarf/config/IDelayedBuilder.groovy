package com.bearleft.dwarf.config

/**
 * User: Eric Siebeneich
 * Date: 4/14/13
 */
public interface IDelayedBuilder<T extends IConfigurable> {

	void newItem(String type)

	void buildItem(String type)

	List<String> getItems()

	Class<T> getType()
}
