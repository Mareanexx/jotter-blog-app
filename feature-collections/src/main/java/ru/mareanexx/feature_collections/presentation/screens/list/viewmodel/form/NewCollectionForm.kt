package ru.mareanexx.feature_collections.presentation.screens.list.viewmodel.form

import androidx.annotation.StringRes
import ru.mareanexx.feature_collections.R

data class NewCollectionForm(
    val id: Int? = null,
    val name: String = "",
    val nameValidationError: NameValidationError? = null
)

enum class NameValidationError(@StringRes val textErr: Int) {
    CANT_ADD(R.string.cant_add_collection),
    SAME_NAME(R.string.error_same_collection_name),
    TOO_SHORT_NAME(R.string.error_too_short_collection_name)
}