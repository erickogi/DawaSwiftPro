package com.dev.common.utils.textWatchers

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.TextView
import com.dev.common.utils.Validator
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class PhoneTextWatcher(edtphone: TextInputEditText, txtke: TextView) : TextWatcher {
    val space = ' '
    val edt_phone = edtphone
    val txt_ke = txtke


    override fun afterTextChanged(s: Editable?) {
        (edt_phone.parent.parent as TextInputLayout).error = null
        try {
            if (s != null && s.isNotEmpty()) {
                if (s[0] == '0') {
                    s.delete(s.length - 1, s.length)
                    txt_ke.text = "+254-0"
                } else if (s.length < 1) {
                    txt_ke.text = "+254"

                }
            } else {
                txt_ke.text = "+254"

            }


        } catch (nm: Exception) {
            nm.printStackTrace()
        }

        if (s!!.isNotEmpty() && s.length % 4 == 0) {
            val c = s[s.length - 1]
            if (space == c) {
                s.delete(s.length - 1, s.length)
            }

        }
        if (s.isNotEmpty() && s.length % 4 == 0) {
            val c = s[s.length - 1]
            if (Character.isDigit(c) && TextUtils.split(s.toString(), space.toString()).size <= 3) {
                s.insert(s.length - 1, space.toString())
            }

        }
        if (s.isNotEmpty()) {
            if (Validator.isValidPhoneNumber(
                    edt_phone.text.toString().replace(
                        " ",
                        ""
                    ).trim()
                )
            ) {
                (edt_phone.parent.parent as TextInputLayout).error = null

            } else {
                (edt_phone.parent.parent as TextInputLayout).error = "In Complete"


            }
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }
}