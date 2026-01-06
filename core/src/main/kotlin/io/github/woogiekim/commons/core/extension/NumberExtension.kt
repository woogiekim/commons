package io.github.woogiekim.commons.core.extension

import java.math.BigDecimal
import java.text.DecimalFormat

private val decimalFormatter = DecimalFormat("###,###.##################")

fun BigDecimal?.format(decimalFormat: DecimalFormat = decimalFormatter, defaultValue: String = "0"): String {
    return this?.run { decimalFormat.format(this) } ?: defaultValue
}

fun BigDecimal.notSame(target: BigDecimal): Boolean {
    return !this.same(target)
}

fun BigDecimal.same(target: BigDecimal): Boolean {
    return this.compareTo(target) == 0
}
