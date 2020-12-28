package org.wit.lucre.services

import org.wit.lucre.models.Category
import org.wit.lucre.models.Entry
import org.wit.lucre.models.EntryType
import org.wit.lucre.repositories.CategoryStore
import java.util.function.Predicate

/**
 * IncomeService class for computing app business logic
 */
class IncomeService() {

    /** Category store for expenses */
    private val categoryStore = CategoryStore()

    /**
     * This method is used for calculating
     * the balance of an account. It
     * totals up the income and expenses
     * and returns the result
     */
    fun balance(entries: List<Entry>): Float {
        // validation
        if (entries.isEmpty()) return 0F
        return entries
            // get a list of the positive/negative values
            .map { it.signedAmount }
            // sum the amount
            .reduce { acc, it -> acc + it }
    }

    /**
     * The expense categories is used to break the
     * expenses up into their different categories
     * to then be displayed as a pie chart
     */
    fun expenseCategories(entries: List<Entry>): Map<Category, Double> {
        // validation
        if (entries.isEmpty()) return emptyMap()
        // filter the entries by expenses
        var predicate = Predicate<Entry> { it.type == EntryType.EXPENSE }
        return entries.filter { predicate.test(it) }
            // group expenses by category id
            .groupBy { it.category.id }
            // sum the amounts
            .mapValues { (_, value) ->
                value
                    // return a list of amount as a Double
                    .map { it.amount.toDouble() }
                    // sum the expenses in the category
                    .reduce { acc, it -> acc + it }
            }
            // convert the keys from id back to category
            .mapKeys { categoryStore.find(it.key)!! }
    }
}
