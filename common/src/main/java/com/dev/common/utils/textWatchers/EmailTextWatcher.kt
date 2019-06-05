package com.dev.common.utils.textWatchers

import android.text.Editable
import android.text.TextWatcher
import com.dev.common.utils.Validator
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class EmailTextWatcher(field: TextInputEditText) : TextWatcher {


    val field = field


    override fun afterTextChanged(s: Editable?) {
        (field.parent.parent as TextInputLayout).error = null



        if (s != null) {
            if (s.isNotEmpty()) {
                if (Validator.isValidEmail(field)) {
                    (field.parent.parent as TextInputLayout).error = null

                } else {
                    (field.parent.parent as TextInputLayout).error = "In Complete"


                }
            }
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }
}