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
	url 'resources/images/gametile/grass.jpg'
	url 'resources/images/gametile/grass2.jpg'
}

'2' {
	flags PASSABLE
	color 0x121212
}

'3' {
	flags PASSABLE
	color 0x00CC00
}

'4' {
	flags(PASSABLE, SWIMMABLE)
	color 0x0000CC
	effect 'swimming'
}

'5' {
	color 0xCCCC00
}