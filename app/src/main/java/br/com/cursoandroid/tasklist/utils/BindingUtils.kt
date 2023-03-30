package br.com.cursoandroid.tasklist.utils

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText

object BindingUtils {
    @JvmStatic
    @BindingAdapter("requiredField")
    fun setRequiredField(view: TextInputEditText, error: String? = "") {
        view.error = error
    }
}