package br.com.cursoandroid.tasklist.utils

import android.view.View
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText

object BindingUtils {
    @JvmStatic
    @BindingAdapter("requiredField")
    fun setRequiredField(view: TextInputEditText, error: String? = "") {
        view.error = error
    }

    @JvmStatic
    @BindingAdapter("visibleIf")
    fun setRequiredField(view: View, isEnabled: Boolean) {
        view.visibility = if(isEnabled) View.VISIBLE else View.GONE
    }
}