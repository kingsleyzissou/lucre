package com.aquatic.lucre.repositories

import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import com.aquatic.lucre.models.Vault

internal class VaultStoreTest {

    private val id1: String = NanoIdUtils.randomNanoId()
    private val id2: String = NanoIdUtils.randomNanoId()
    private val id3: String = NanoIdUtils.randomNanoId()

    private val vault1 = mockk<Vault>(relaxed = true)
    private val vault2 = mockk<Vault>(relaxed = true)
    private val vault3 = Vault(
        "HSBC",
        "Pound",
        "£",
        id3
    )

    private lateinit var store: VaultStore

    @Before
    fun setup() {
        every { vault1.id } returns id1
        every { vault2.id } returns id2

        store = spyk(VaultStore("test.json"))
        every { store["logAll"]() } returns "logging"
        store.addAll(listOf(vault1, vault2, vault3))
    }

    @Test
    fun all() {
        assertEquals(3, store.all().size)
    }

    @Test
    fun find() {
        assertEquals(vault1, store.find(id1))
    }

    @Test
    fun create() {
        val id4 = NanoIdUtils.randomNanoId()
        val vault4 = mockk<Vault>()
        every { vault4.id } returns id4
        store.create(vault4)
        assertTrue(store.all().contains(vault4))
    }

    @Test
    fun update() {
        val vault5 = mockk<Vault>()
        every { vault5.id } returns id3
        every { vault5.name } returns "AIB"
        every { vault5.description } returns "Euro"
        every { vault5.currency } returns "€"
        store.update(vault5)
        val old = store.find(id3)
        assertEquals(vault5.name, old?.name)
        assertEquals(vault5.description, old?.description)
        assertEquals(vault5.currency, old?.currency)
    }
}
