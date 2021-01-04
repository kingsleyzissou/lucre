package com.aquatic.lucre.models

import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import org.junit.Assert.* // ktlint-disable no-wildcard-imports
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

internal class EntryBaseModelTest {

    lateinit var expense: Entry

    private val id: String = NanoIdUtils.randomNanoId()
    private val date: LocalDate = LocalDate.now()
    private val vault: Vault = Vault(
        "HSBC",
        "GBP Account",
        "£"
    )
    private val category: Category = Category(
        "Takeout",
        "Dine-in and takeaways",
        "#ff0000"
    )

    private final val EXPECTED_ID: String = id
    private final val EXPECTED_VENDOR: String = "Tesco"
    private final val EXPECTED_DESCRIPTION: String = "Dinner"
    private final val EXPECTED_AMOUNT: Float = 10F
    private final val EXPECTED_TYPE: String = EntryType.EXPENSE.toString()
    private final val EXPECTED_DATE: String = date.toString()
    private final val EXPECTED_CATEGORY: String? = category.id
    private final val EXPECTED_VAULT: String? = vault.id

    @Before
    internal fun setup() {
        expense = Entry(
            10F,
            EntryType.EXPENSE.toString(),
            "Tesco",
            "Dinner",
            category.id,
            vault.id,
            id,
            date.toString()
        )
    }

    @Test
    fun getId() {
        assertEquals(EXPECTED_ID, expense.id)
    }

    @Test
    fun setId() {
        val newId: String = NanoIdUtils.randomNanoId()
        expense.id = newId
        assertEquals(newId, expense.id)
    }

    @Test
    fun getAmount() {
        assertEquals(EXPECTED_AMOUNT, expense.amount)
    }

    @Test
    fun setAmount() {
        val newAmount = 15F
        expense.amount = newAmount
        assertEquals(newAmount, expense.amount)
    }

    @Test
    fun getType() {
        assertEquals(EXPECTED_TYPE, expense.type)
    }

    @Test
    fun setType() {
        val newType = EntryType.INCOME
        expense.type = newType.toString()
        assertEquals(newType.toString(), expense.type)
    }

    @Test
    fun getVendor() {
        assertEquals(EXPECTED_VENDOR, expense.vendor)
    }

    @Test
    fun setVendor() {
        val newVendor = "Spar"
        expense.vendor = newVendor
        assertEquals(newVendor, expense.vendor)
    }

    @Test
    fun getDescription() {
        assertEquals(EXPECTED_DESCRIPTION, expense.description)
    }

    @Test
    fun setDescription() {
        val newDescription = "Refund"
        expense.description = newDescription
        assertEquals(newDescription, expense.description)
    }

    @Test
    fun getCategory() {
        assertEquals(EXPECTED_CATEGORY, expense.category)
    }

    @Test
    fun setCategory() {
        val newCategory = Category(
            "Bills",
            "Household bills",
            "#ffffff"
        )
        expense.category = newCategory.id
        assertEquals(newCategory.id, expense.category)
    }

    @Test
    fun getVault() {
        assertEquals(EXPECTED_VAULT, expense.vault)
    }

    @Test
    fun setVault() {
        val newVault = Vault(
            "AIB",
            "Euro account",
            "€"
        )
        expense.vault = newVault.id
        assertEquals(newVault.id, expense.vault)
    }

    @Test
    fun getDate() {
        assertEquals(EXPECTED_DATE, expense.date)
    }

    @Test
    fun setDate() {
        val newDate: LocalDate = LocalDate.now()
        expense.date = newDate.toString()
        assertEquals(newDate.toString(), expense.date)
    }
}
