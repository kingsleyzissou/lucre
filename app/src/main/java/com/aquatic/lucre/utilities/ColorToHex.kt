package com.aquatic.lucre.utilities

import android.graphics.Color
import kotlin.math.roundToInt

/**
 * Method for returning a color channel
 * to a hex string, reference:
 *
 * https://stackoverflow.com/a/56733608
 */
private fun format(value: Float): String {
    val integer = Integer.toHexString((value * 255).roundToInt())
    if (integer.length == 1) "0$integer".toUpperCase()
    return integer.toUpperCase()
}

/**
 * This method converts a JavaFX color
 * into a hex string, reference:
 *
 * https://stackoverflow.com/a/56733608
 */
fun toHexString(value: Color): String? {
    val red = format(value.red())
    val green = format(value.green())
    val blue = format(value.blue())
    return "#$red$green$blue"
}
