package com.bearleft.dwarf.map

/**
 * User: Eric Siebeneich
 * Date: 3/29/13
 */

staticTypes {
	type name: 'PASSABLE',  value: 0b00000001
	type name: 'BUILDABLE', value: 0b00000010
	type name: 'SWIMMABLE', value: 0b00000100
}

'1' {
	flags(PASSABLE, BUILDABLE)
	sprite 'grass'
	sprite 'grass2'
}