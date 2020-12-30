package com.aquatic.lucre.repositories

import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import com.aquatic.lucre.models.Entry
import com.aquatic.lucre.models.EntryType

internal class EntryStoreTest {

    private val id1: String = NanoIdUtils.randomNanoId()
    private val id2: String = NanoIdUtils.randomNanoId()
    private val id3: String = NanoIdUtils.randomNanoId()

    private val entry1 = mockk<Entry>()
    private val entry2 = mockk<Entry>()
    private val entry3 = Entry(
        4F,
        EntryType.EXPENSE,
        "Vodafone",
        "Internet",
        mockk(),
        "3",
        id3
    )

    private lateinit var store: EntryStore

    @Before
    fun setup() {
        every { entry1.id } returns id1
        every { entry2.id } returns id2

        store = spyk(EntryStore("test.json"))
        every { store["logAll"]() } returns "logging"
        store.addAll(listOf(entry1, entry2, entry3))
    }

    @Test
    fun all() {
        assertEquals(3, store.all().size)
    }

    @Test
    fun find() {
        assertEquals(entry1, store.find(id1))
    }

    @Test
    fun create() {
        val id4 = NanoIdUtils.randomNanoId()
        val entry4 = mockk<Entry>()
        every { entry4.id } returns id4
        store.create(entry4)
        assertTrue(store.all().contains(entry4))
    }

    @Test
    fun update() {
        val entry5 = Entry(
            3F,
            EntryType.EXPENSE,
            "Tesco",
            "Groceries",
            mockk(),
            "2",
            id3
        )
        store.update(entry5)
        val old = store.find(id3)
        assertEquals(old?.amount, entry5.amount)
        assertEquals(old?.type, entry5.type)
        assertEquals(old?.vendor, entry5.vendor)
        assertEquals(old?.description, entry5.description)
        assertEquals(old?.category, entry5.category)
        assertEquals(old?.vault, entry5.vault)
    }
}
