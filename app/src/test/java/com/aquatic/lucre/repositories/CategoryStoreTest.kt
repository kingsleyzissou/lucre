package com.aquatic.lucre.repositories

import com.aqautic.lucre.repositories.CategoryStore
import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import com.aquatic.lucre.models.Category

internal class CategoryStoreTest {

    private val id1: String = NanoIdUtils.randomNanoId()
    private val id2: String = NanoIdUtils.randomNanoId()
    private val id3: String = NanoIdUtils.randomNanoId()

    private val category1 = mockk<Category>()
    private val category2 = mockk<Category>()
    private val category3 = mockk<Category>()

    private lateinit var store: CategoryStore

    @Before
    fun setup() {
        every { category1.id } returns id1
        every { category2.id } returns id2
        every { category3.id } returns id3

        store = spyk(CategoryStore("test.json"))
        every { store["logAll"]() } returns "logging"
        store.addAll(listOf(category1, category2, category3))
    }

    @Test
    fun all() {
        assertEquals(3, store.all().size)
    }

    @Test
    fun find() {
        assertEquals(category1, store.find(id1))
    }

    @Test
    fun create() {
        val id4 = NanoIdUtils.randomNanoId()
        val category4 = mockk<Category>()
        every { category4.id } returns id4
        store.create(category4)
        assertTrue(store.all().contains(category4))
    }

    @Test
    fun update() {
        val category5 = mockk<Category>()
        every { category5.id } returns id3
        every { category5.name } returns "Income"
        store.update(category5)
        assertEquals(category5, store.find(id3))
    }
}
