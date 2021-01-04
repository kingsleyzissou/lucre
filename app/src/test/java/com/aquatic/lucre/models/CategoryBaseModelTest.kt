package com.aquatic.lucre.models

import org.junit.Assert.* // ktlint-disable no-wildcard-imports
import org.junit.Before
import org.junit.Test

internal class CategoryBaseModelTest {

    lateinit var category: Category

    private final val EXPECTED_NAME = "Takeout"
    private final val EXPECTED_DESCRIPTION = "Dine-in and takeaways"
    private final val EXPECTED_COlOR = "#ff0000"

    @Before
    internal fun setup() {
        category = Category(
            "Takeout",
            "Dine-in and takeaways",
            "#ff0000"
        )
    }

    @Test
    fun getName() {
        assertEquals(EXPECTED_NAME, category.name)
    }

    @Test
    fun setName() {
        val newName = "Bills"
        category.name = newName
        assertEquals(newName, category.name)
    }

    @Test
    fun getDescription() {
        assertEquals(EXPECTED_DESCRIPTION, category.description)
    }

    @Test
    fun setDescription() {
        val newDescription = "Utilities and lights"
        category.description = newDescription
        assertEquals(newDescription, category.description)
    }

    @Test
    fun getColor() {
        assertEquals(EXPECTED_COlOR, category.color)
    }

    @Test
    fun setColor() {
        val newColor = "#00ff00"
        category.color = newColor
        assertEquals(newColor, category.color)
    }
}
