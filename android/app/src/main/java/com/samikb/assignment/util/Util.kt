package com.samikb.assignment.util

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class Util {
    companion object {
        fun getFormattedRuntime(runTime: Int) =
            if (runTime > 60) {
                "${runTime / 60}h ${runTime % 60}m"
            } else {
                "${runTime}m"
            }

        fun getFormattedDate(dateString: String?): String {
            if (dateString == null) {
                return ""
            }
            return try{
                val date = SimpleDateFormat("yyyy-MM-dd").parse(dateString)
                val month = SimpleDateFormat("LLLL", Locale.getDefault()).format(date)
                val day = SimpleDateFormat("dd", Locale.getDefault()).format(date)
                val year = SimpleDateFormat("yyyy", Locale.getDefault()).format(date)
                "$month ${day}, $year"
            }catch (e: Exception){
                ""
            }

        }
    }
}