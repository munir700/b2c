package co.yap.yapcore.helpers.validation

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.R
import co.yap.yapcore.helpers.validation.rule.Rule
import co.yap.yapcore.helpers.validation.util.ViewTagHelper.filterViewWithTag
import co.yap.yapcore.helpers.validation.util.ViewTagHelper.filterViewsWithTag
import co.yap.yapcore.helpers.validation.util.ViewTagHelper.getViewsByTag
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created irfan arshad on 10/6/2020.
 */
class Validator(private val target: ViewDataBinding?) {
    private val disabledViews: MutableSet<View>
    private var validationListener: ValidationListener? = null
    private var mode =
        FORM_VALIDATION_MODE
    private var msgValidationMode =
        VALIDATION_WITHOUT_ERROR_MESSAGES

    @JvmField
    var isValidate =
        MutableLiveData(false)
    private val textChangedListener: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(
            s: CharSequence,
            start: Int,
            count: Int,
            after: Int
        ) {
        }

        override fun onTextChanged(
            s: CharSequence,
            start: Int,
            before: Int,
            count: Int
        ) {
            if (msgValidationMode == VALIDATION_WITHOUT_ERROR_MESSAGES) isValidate.value =
                validate()
        }

        override fun afterTextChanged(s: Editable) {}
    }

    private fun applyTextWatcherOnAllViews() {
        val viewWithValidations =
            getViewsWithValidation()
        for (view in viewWithValidations) {
            if (view is TextView) {
                view.addTextChangedListener(textChangedListener)
            }
        }
    }

    private fun removeTextWatcherOnAllViews() {
        val viewWithValidations =
            getViewsWithValidation()
        for (view in viewWithValidations) {
            if (view is TextView) {
                view.removeTextChangedListener(textChangedListener)
            }
        }
    }

    fun setValidationListener(validationListener: ValidationListener?) {
        this.validationListener = validationListener
    }

    fun toValidate() {
        requireNotNull(validationListener) { "Validation listener should not be null." }
        if (validate()) {
            validationListener?.onValidationSuccess()
        } else {
            validationListener?.onValidationError()
        }
    }

    fun validate(): Boolean {
        val viewWithValidations =
            getViewsWithValidation()
        return isAllViewsValid(viewWithValidations)
    }

    fun validate(view: View): Boolean {
        val viewWithValidations =
            getViewsWithValidation(view)
        return isAllViewsValid(viewWithValidations)
    }

    fun <ViewType : View?> validate(views: List<ViewType>): Boolean {
        val viewWithValidations =
            getViewsWithValidation(views)
        return isAllViewsValid(viewWithValidations)
    }

    private fun isAllViewsValid(viewWithValidations: List<View>): Boolean {
        var allViewsValid = true
        for (viewWithValidation in viewWithValidations) {
            var viewValid = true
            val rules: List<Rule<*, *>> =
                viewWithValidation.getTag(R.id.validator_rule) as List<Rule<*, *>>
            for (rule in rules) {
                viewValid = viewValid && isRuleValid(rule)
                allViewsValid = allViewsValid && viewValid
            }
            if (mode == FIELD_VALIDATION_MODE && !viewValid) {
                break
            }
        }
        return allViewsValid
    }

    private fun isRuleValid(rule: Rule<*, *>): Boolean {
        return disabledViews.contains(rule.view) || rule.validate()
    }

    fun disableValidation(view: View) {
        disabledViews.add(view)
    }

    fun enableValidation(view: View) {
        disabledViews.remove(view)
    }

    fun enableFormValidationMode() {
        mode = FORM_VALIDATION_MODE
    }

    fun enableWithoutErrorMessageValidation() {
        if (msgValidationMode == VALIDATION_WITH_ERROR_MESSAGES) {
            applyTextWatcherOnAllViews()
            msgValidationMode =
                VALIDATION_WITHOUT_ERROR_MESSAGES
        }
    }

    fun enableWithErrorMessageValidation() {
        if (msgValidationMode == VALIDATION_WITHOUT_ERROR_MESSAGES) {
            removeTextWatcherOnAllViews()
            msgValidationMode =
                VALIDATION_WITH_ERROR_MESSAGES
        }
    }

    fun enableFieldValidationMode() {
        mode = FIELD_VALIDATION_MODE
    }

    private fun getViewsWithValidation(): List<View> {
        if (target == null) return ArrayList()
        if (target.root is ViewGroup) {
            return getViewsByTag(
                (target.root as ViewGroup),
                R.id.validator_rule
            )
        }
        return listOf(target.root)
    }

    private fun <ViewType : View?> getViewsWithValidation(views: List<ViewType>): List<View> {
        return filterViewsWithTag(R.id.validator_rule, views)
    }

    private fun getViewsWithValidation(view: View): List<View> {
        return filterViewWithTag(R.id.validator_rule, view)
    }

    interface ValidationListener {
        fun onValidationSuccess()
        fun onValidationError()
    }

    companion object {
        private const val FIELD_VALIDATION_MODE = 0
        private const val FORM_VALIDATION_MODE = 1
        private const val VALIDATION_WITHOUT_ERROR_MESSAGES = 10
        private const val VALIDATION_WITH_ERROR_MESSAGES = 20
    }

    init {
        disabledViews = HashSet()
        target?.let {
            if (msgValidationMode == VALIDATION_WITHOUT_ERROR_MESSAGES) applyTextWatcherOnAllViews()
        }
    }
}