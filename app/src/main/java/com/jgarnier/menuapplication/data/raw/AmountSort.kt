package com.jgarnier.menuapplication.data.raw

import com.jgarnier.menuapplication.R

enum class AmountSort(val shortNameId: Int) {
    LITRE(R.string.amount_sort_litre_short),
    CENTILITRE(R.string.amount_sort_centilitre_short),
    MILLILITRE(R.string.amount_sort_mililitre_short),
    GRAMME(R.string.amount_sort_gram_short),
    KILOGRAMME(R.string.amount_sort_kilogram_short),
    UNITE(R.string.amount_sort_unit_short);

    companion object {

        fun retrieveAmountSortFrom(shortNameId: Int): AmountSort? {
            return values().firstOrNull { amountSort -> amountSort.shortNameId == shortNameId }
        }

    }
}