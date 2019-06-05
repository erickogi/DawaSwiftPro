package com.dev.common.utils

import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Pattern


class Validator {
    companion object {
        // Default validation messages
        private val PASSWORD_POLICY = """Password should be minimum 8 characters long,
            |should contain at least one capital letter,
            |at least one small letter,
            |at least one number and
            |at least one special character among ~!@#$%^&*()-_=+|[]{};:'\",<.>/?""".trimMargin()
        private val NAME_VALIDATION_MSG = "Enter a valid name"
        private val EMAIL_VALIDATION_MSG = "Enter a valid email address"
        private val PHONE_VALIDATION_MSG = "Enter a valid phone number"
        /**
         * Retrieve string data from the parameter.
         * @param data - can be EditText or String
         * @return - String extracted from EditText or data if its data type is Strin.
         */
        fun getText(data: Any): String {
            var str = ""
            if (data is EditText) {
                str = data.text.toString()
            } else if (data is String) {
                str = data
            }
            return str
        }

        private fun getText(a: Int, data: Any): String {
            var str = ""
            if (data is EditText) {
                str = data.text.toString().replace(" ", "").trim()
            } else if (data is String) {
                str = data
            }
            return str
        }

        /**
         * Checks if the name is valid.
         * @param data - can be EditText or String
         * @param updateUI - if true and if data is EditText, the function sets error to the EditText or its TextInputLayout
         * @return - true if the name is valid.
         */
        fun isValidName(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            val valid = str.trim().length > 2
            // Set error if required
            if (updateUI) {
                val error: String? = if (valid) null else NAME_VALIDATION_MSG
                setError(data, error)
            }

            return valid
        }

        /**
         * Checks if the email is valid.
         * @param data - can be EditText or String
         * @param updateUI - if true and if data is EditText, the function sets error to the EditText or its TextInputLayout
         * @return - true if the email is valid.
         */
        fun isValidEmail(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            val valid = Patterns.EMAIL_ADDRESS.matcher(str).matches()
            // Set error if required
            if (updateUI) {
                val error: String? = if (valid) null else EMAIL_VALIDATION_MSG
                setError(data, error)
            }

            return valid
        }


        fun isNull(s: TextInputEditText?): Boolean {
            if (s == null) {
                return true
            }
            if (TextUtils.isEmpty(s.text)) {
                setError(s, "Required")

                return true
            }
            if (s.text == null) {
                setError(s, "Required")

                return true
            }
            if (isNull(s.text.toString())) {
                setError(s, "Required")


                return true
            }
            return false
        }

        fun isNull(s: String?): Boolean {
            if (s == null) {
                return true
            }
            if (s == "") {
                return true
            }
            return false
        }

        /**
         * Checks if the phone is valid.
         * @param data - can be EditText or String
         * @param updateUI - if true and if data is EditText, the function sets error to the EditText or its TextInputLayout
         * @return - true if the phone is valid.
         */
        fun isValidPhone(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            val valid = Patterns.PHONE.matcher(str).matches()
            // Set error if required
            if (updateUI) {
                val error: String? = if (valid) null else PHONE_VALIDATION_MSG
                setError(data, error)
            }

            return valid
        }

        fun isValidPhone(a: Int, data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(a, data)
            val valid = Patterns.PHONE.matcher(str).matches()
            // Set error if required
            if (updateUI) {
                val error: String? = if (valid) null else PHONE_VALIDATION_MSG
                setError(data, error)
            }

            return valid
        }

        fun passwordsMatch(edt_password: TextInputEditText, edt_confirm_password: TextInputEditText): Boolean {

            val pass1 = getText(edt_password)
            val pass2 = getText(edt_confirm_password)

            val st = pass1 == pass2
            setError(edt_password, null)
            setError(edt_confirm_password, null)

            if (!st) {
                setError(edt_password, "Passwords do not match")
                setError(edt_confirm_password, "Passwords do not match")
            }
            return st
        }

        fun getPhoneNumber(field: TextInputEditText): String {
            return getText(field).replace(" ", "").trim()


        }

        fun getPhoneNumber(field: TextInputEditText, code: String): String {
            return "254" + getText(field).replace(" ", "").trim()


        }

        fun isValidPhoneNumber(mobile: TextInputEditText): Boolean {
            var no = getPhoneNumber(mobile)

            return if (isValidPhoneNumber(no)) {
                true
            } else {
                val error: String? = PHONE_VALIDATION_MSG
                setError(mobile, error)

                false
            }
        }

        fun isValidPhoneNumber(mobile: String): Boolean {
            val regEx = "^[0-9]{9}$"
            return mobile.matches(regEx.toRegex())
        }


        fun isValidPhone(data: String, updateUI: Boolean = true): Boolean {
            return if (data != null && data.length > 9) {
                val str = data
                val valid = Patterns.PHONE.matcher(str).matches()
                // Set error if required
                if (updateUI) {
                    val error: String? = if (valid) null else PHONE_VALIDATION_MSG
                    setError(data, error)
                }


                valid
            } else {
                false
            }
        }

        /**
         * Checks if the password is valid as per the following password policy.
         * Password should be minimum minimum 8 characters long.
         * Password should contain at least one number.
         * Password should contain at least one capital letter.
         * Password should contain at least one small letter.
         * Password should contain at least one special character.
         * Allowed special characters: "~!@#$%^&*()-_=+|/,."';:{}[]<>?"
         *
         * @param data - can be EditText or String
         * @param updateUI - if true and if data is EditText, the function sets error to the EditText or its TextInputLayout
         * @return - true if the password is valid as per the password policy.
         */
        fun isValidPassword(data: Any, updateUI: Boolean = true): Boolean {
            val str = getText(data)
            var valid = true
            // Password policy check
            // Password should be minimum minimum 8 characters long
            if (str.length < 8) {
                valid = false
            }
            // Password should contain at least one number
            var exp = ".*[0-9].*"
            var pattern = Pattern.compile(exp, Pattern.CASE_INSENSITIVE)
            var matcher = pattern.matcher(str)
            if (!matcher.matches()) {
                valid = false
            }
            // Password should contain at least one capital letter
            exp = ".*[A-Z].*"
            pattern = Pattern.compile(exp)
            matcher = pattern.matcher(str)
            if (!matcher.matches()) {
                valid = false
            }
            // Password should contain at least one small letter
            exp = ".*[a-z].*"
            pattern = Pattern.compile(exp)
            matcher = pattern.matcher(str)
            if (!matcher.matches()) {
                valid = false
            }
            // Password should contain at least one special character
            // Allowed special characters : "~!@#$%^&*()-_=+|/,."';:{}[]<>?"
            exp = ".*[~!@#\$%\\^&*()\\-_=+\\|\\[{\\]};:'\",<.>/?].*"
            pattern = Pattern.compile(exp)
            matcher = pattern.matcher(str)
            if (!matcher.matches()) {
                valid = false
            }
            // Set error if required
            if (updateUI) {
                val error: String? = if (valid) null else PASSWORD_POLICY
                setError(data, error)
            }

            return valid
        }

        /**
         * Sets error on EditText or TextInputLayout of the EditText.
         * @param data - Should be EditText
         * @param error - Message to be shown as error, can be null if no error is to be set
         */
        private fun setError(data: Any, error: String?) {
            if (data is TextInputEditText) {
                if (data.parent.parent is TextInputLayout) {
                    (data.parent.parent as TextInputLayout).error = error
                } else {
                    data.error = error
                }
            }
        }

        fun isValidCode(t: TextInputEditText, excper: String?): Boolean {
            val str = getText(t)
            if (str.length == 4) {
                if (str == excper) {
                    return true
                }
                setError(t, "Code doesn't match")

                return false
            }
            setError(t, "Enter a valid 4 digit code")

            return false
        }

        fun isValidDateOfBirth(edt_dob: TextInputEditText): Boolean {
            //TODO ADD DATE VALIDATION


            val date = getText(edt_dob)
            if (date != null && date != "") {
                return true
            }
            return false
        }

        fun isValidSex(radio_grp: RadioGroup): Boolean = if (radio_grp.checkedRadioButtonId > -1) {
            true
        } else {
            val lastChildPos = radio_grp.childCount - 1
            (radio_grp.getChildAt(lastChildPos) as RadioButton).error = "Select Gender"
            false
        }

        fun isValidJobId(field: TextInputEditText): Boolean {
            val id = getText(field)
            if (id.length >= 5) return true
            else

                setError(field, "Invalid Job Id")
            return false

        }

        fun isRadioGroupdSelected(radioGroup: RadioGroup?): Boolean {
            return radioGroup?.checkedRadioButtonId != -1
        }

        fun isSpinnerSelected(spinner: Spinner?): Boolean {

            if (spinner?.selectedItem != null && spinner.selectedItemPosition > 0) {
                return true
            }
            return false
        }

        fun getRadioChecked(radioGroup: RadioGroup?, view: View): String? {
            val selectedId = radioGroup?.checkedRadioButtonId

            // find the radiobutton by returned id
            val radioButton = view.findViewById(selectedId!!) as RadioButton


            return radioButton.text.toString()

        }

        fun getSpinerChecked(spinner: Spinner?): String? {

            return spinner?.selectedItem.toString()

        }

    }
}