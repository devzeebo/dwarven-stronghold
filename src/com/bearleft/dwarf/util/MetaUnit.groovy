package com.bearleft.dwarf.util

/**
 * User: Eric Siebeneich
 * Date: 3/30/13
 */
final class MetaUnit {

	Number value
	String type

	static Map conversions = [
			'kg' : 1000,
			'milli' : -1000,
			'min' : 60,
			'hr' : 3600,
			'km' : 1000,
			'cm' : -10,
			'mm' : -1000
	]

	static Map types = [
	        'g' : ['g', 'kg'],
			'sec' : ['sec', 'milli', 'min', 'hr'],
			'm' : ['m', 'km', 'cm', 'mm']
	]

	public static final String typeOf(String units) {
		return types.find { it.value.contains(units) }.key
	}

	public static final Number normalize(Number num, String units, boolean invert = false) {
		if (conversions[units]) {
			int mult = conversions[units]
			if (mult > 0 ^ invert) {
				num = num * mult * (invert ? -1 : 1)
			}
			else {
				num = num / -mult * (invert ? -1 : 1)
			}
		}

		return num
	}

	public static final MetaUnit valueOf(Number num, String units) {
		return new MetaUnit(normalize(num, units), typeOf(units))
	}

	public static final void bind() {
		Number.metaClass.propertyMissing = { String propertyName ->
			return MetaUnit.valueOf(delegate, propertyName)
		}
	}

	public String toString() {
		return "${value} ${type}"
	}

	public MetaUnit(Number value, String type) {
		this.value = value
		this.type = type
	}

	public MetaUnit plus(MetaUnit other) {
		assert type == other.type
		return new MetaUnit(value + other.value, type)
	}
	public MetaUnit plus(Number scalar) {
		return new MetaUnit(value + scalar, type)
	}

	public MetaUnit minus(MetaUnit other) {
		assert type == other.type
		return new MetaUnit(value - other.value, type)
	}
	public MetaUnit minus(Number scalar) {
		return new MetaUnit(value - scalar, type)
	}

	public MetaUnit multipy(MetaUnit other) {
		assert type == other.type
		return new MetaUnit(value * other.value, type)
	}
	public MetaUnit multiply(Number scalar) {
		return new MetaUnit(value * scalar, type)
	}

	public MetaUnit divide(MetaUnit other) {
		assert type == other.type
		return new MetaUnit(value / other.value, type)
	}
	public MetaUnit divide(Number scalar) {
		return new MetaUnit(value / scalar, type)
	}

	public Number getAt(String units) {
		assert type == typeOf(units)
		return normalize(value, units, true)
	}
}
