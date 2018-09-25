package com.cprieto.kronstache

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class VariableDictionaryTests {
    @Test
    fun `With empty variables and not variable dictionary it returns same dictionary`() {
        val dictionary = VariableDictionary()
        val input = mapOf("var1" to "foo", "var2" to 2)
        val response = dictionary.evaluate(input)

        assertEquals(input, response)
    }

    @Test
    fun `With variables and not variable dictionary it returns sum of both dictionaries`() {
        val existing = mapOf("var2" to "foo")

        val dictionary = VariableDictionary(existing)
        val input = mapOf("var1" to "bar", "var3" to 2)
        val response = dictionary.evaluate(input)

        assertEquals(input.plus(existing), response)
    }

    @Test
    fun `With overlap variables, input has priority`() {
        val dictionary = VariableDictionary(mapOf("var1" to "foo"))
        val input = mapOf("var1" to "bar")
        val response = dictionary.evaluate(input)

        assertEquals(input, response)
    }

    @Test
    fun `We discard variables we cannot evaluate`() {
        val dictionary = VariableDictionary(mapOf("var1" to "foo"))

        val input = mapOf("var2" to "#{ bar }")
        val response = dictionary.evaluate(input)

        assertEquals(1, response.size)
        assertEquals("foo", response["var1"])
    }

    @Test
    fun `We can evaluate integers`() {
        val dictionary = VariableDictionary(mapOf())
        val response = dictionary.evaluate(mapOf("var1" to "#{ 1 }"))

        assertEquals("1", response["var1"])
    }

    @Test
    fun `We can evaluate strings`() {
        val dictionary = VariableDictionary(mapOf())
        val response = dictionary.evaluate(mapOf("var1" to "#{ 'foo' }"))

        assertEquals("foo", response["var1"])
    }

    @Test
    fun `We can replace variables`() {
        val dictionary = VariableDictionary(mapOf("foo" to "bar"))
        val response = dictionary.evaluate(mapOf("var1" to "#{ foo }"))

        assertEquals("bar", response["var1"])
    }

    @Test
    fun `We can mix expression with text`() {
        val dictionary = VariableDictionary(mapOf("foo" to "bar"))
        val response = dictionary.evaluate(mapOf("var1" to "foo #{ foo }"))

        assertEquals("foo bar", response["var1"])
    }
}