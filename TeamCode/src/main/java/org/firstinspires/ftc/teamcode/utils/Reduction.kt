package org.firstinspires.ftc.teamcode.utils

import android.icu.util.Measure

class Reduction(private val ratio: Double) {

    fun apply(value: Double) = value.div(ratio)
    
    fun unApply(value: Double) = value.times(ratio)

    fun uselessFun() {}
}

