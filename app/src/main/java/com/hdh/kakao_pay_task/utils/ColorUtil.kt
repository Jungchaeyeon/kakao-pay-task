package com.hdh.kakao_pay_task.utils

import kotlin.math.roundToInt

object ColorUtil {
    fun fadeColor(start: Int, end: Int, ratio: Float): Int {
        val sr = start and 0xff0000 shr 16
        val sg = start and 0x00ff00 shr 8
        val sb = start and 0x0000ff
        val dr = end and 0xff0000 shr 16
        val dg = end and 0x00ff00 shr 8
        val db = end and 0x0000ff
        val r = ((1 - ratio) * sr + ratio * dr).toInt()
        val g = ((1 - ratio) * sg + ratio * dg).toInt()
        val b = ((1 - ratio) * sb + ratio * db).toInt()
        return -0x1000000 or (r shl 16) or (g shl 8) or b
    }

    fun fadeAlphaColor(color: Int, alpha: Int): Int {
        val a = alpha and -0x1000000 shr 24
        val r = color and 0xff0000 shr 16
        val g = color and 0x00ff00 shr 8
        val b = color and 0x0000ff
        return a shl 24 or (r shl 16) or (g shl 8) or b
    }

    fun getHexAlpha(alpha: Float): Int {
        var hex = Integer.toHexString((alpha * 255).roundToInt())
        if (hex.length == 1) hex = "0$hex"
        val a = hex.toInt(16)
        return a shl 24
    }
}