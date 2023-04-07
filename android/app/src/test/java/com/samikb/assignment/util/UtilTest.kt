package com.samikb.assignment.util

import com.samikb.assignment.util.Util

import org.junit.Assert.*
import org.junit.Test

class UtilTest {

    @Test
    fun test_getFormattedRuntime(){
        val runtime = 126
        val formattedRuntime = Util.getFormattedRuntime(runtime)
        assertEquals(formattedRuntime, "2h 6m")
    }

    @Test
    fun test_getFormattedRuntime1(){
        val runtime = 59
        val formattedRuntime = Util.getFormattedRuntime(runtime)
        assertEquals(formattedRuntime, "59m")
    }

    @Test
    fun test_getFormattedDate(){
        val date = "1980-12-09"
        val formattedDate = Util.getFormattedDate(date)
        assertEquals(formattedDate, "December 09, 1980")
    }
}