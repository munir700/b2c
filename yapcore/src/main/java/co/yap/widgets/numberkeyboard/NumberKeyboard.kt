package co.yap.widgets.numberkeyboard

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.AppCompatEditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import co.yap.yapcore.R

/**
 * Number keyboard (to enter pin or custom amount).
 */
@Suppress("MemberVisibilityCanBePrivate", "unused")
class NumberKeyboard : ConstraintLayout {

    @Dimension
    private var keyWidth: Int = 0
    @Dimension
    private var keyHeight: Int = 0
    @Dimension
    private var keyPadding: Int = 0
    @DrawableRes
    private var numberKeyBackground: Int = 0
    @ColorRes
    private var numberKeyTextColor: Int = 0
    @DrawableRes
    private var leftAuxBtnIcon: Int = 0
    @DrawableRes
    private var leftAuxBtnBackground: Int = 0
    @DrawableRes
    private var rightAuxBtnIcon: Int = 0
    @DrawableRes
    private var rightAuxBtnBackground: Int = 0

    var enableKeys: Boolean = true
        set(value) {
            field = value
            enableKeys(value)
        }

    private lateinit var numericKeys: MutableList<TextView>
    private lateinit var leftAuxBtn: ImageView
    private lateinit var rightAuxBtn: ImageView

    private var listener: NumberKeyboardListener? = null
    private var inputEditText: AppCompatEditText? = null

    constructor(context: Context) : super(context) {
        inflateView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initializeAttributes(attrs)
        inflateView()
    }

    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initializeAttributes(attrs)
        inflateView()
    }

    /**
     * Sets keyboard listener.
     */
    fun setListener(listener: NumberKeyboardListener?) {
        this.listener = listener
    }

    /**
     * Sets input view.
     */
    fun setInputView(view: AppCompatEditText) {
        this.inputEditText = view
    }

    /**
     * Hides left auxiliary button.
     */
    fun hideLeftAuxButton() {
        leftAuxBtn.visibility = View.GONE
    }

    /**
     * Shows left auxiliary button.
     */
    fun showLeftAuxButton() {
        leftAuxBtn.visibility = View.VISIBLE
    }

    /**
     * Hides right auxiliary button.
     */
    fun hideRightAuxButton() {
        rightAuxBtn.visibility = View.GONE
    }

    /**
     * Shows right auxiliary button.
     */
    fun showRightAuxButton() {
        rightAuxBtn.visibility = View.VISIBLE
    }

    /**
     * Sets key width in px.
     */
    fun setKeyWidth(px: Int) {
        if (px == DEFAULT_KEY_WIDTH_DP) return
        for (key in numericKeys) {
            key.layoutParams.width = px
        }
        leftAuxBtn.layoutParams.width = px
        rightAuxBtn.layoutParams.width = px
        requestLayout()
    }

    /**
     * Sets enable/disable keys flag.
     */
    private fun enableKeys(enableKeys: Boolean) {
        if (this::numericKeys.isInitialized) {
            for (key in numericKeys) {
                key.isEnabled = enableKeys
            }
            leftAuxBtn.isEnabled = enableKeys
            rightAuxBtn.isEnabled = enableKeys
            requestLayout()
        }
    }

    /**
     * Sets key height in px.
     */
    fun setKeyHeight(px: Int) {
        if (px == DEFAULT_KEY_HEIGHT_DP) return
        for (key in numericKeys) {
            key.layoutParams.height = px
        }
        leftAuxBtn.layoutParams.height = px
        rightAuxBtn.layoutParams.height = px
        requestLayout()
    }

    /**
     * Sets key padding in px.
     */
    fun setKeyPadding(px: Int) {
        for (key in numericKeys) {
            key.setPadding(px, px, px, px)
            key.compoundDrawablePadding = -1 * px
        }
        leftAuxBtn.setPadding(px, px, px, px)
        rightAuxBtn.setPadding(px, px, px, px)
    }

    /**
     * Sets number keys background.
     */
    fun setNumberKeyBackground(@DrawableRes background: Int) {
        for (key in numericKeys) {
            key.background = ContextCompat.getDrawable(context, background)
        }
    }

    /**
     * Sets number keys text color.
     */
    fun setNumberKeyTextColor(@ColorRes color: Int) {
        for (key in numericKeys) {
            key.setTextColor(ContextCompat.getColorStateList(context, color))
        }
    }

    /**
     * Sets number keys text typeface.
     */
    fun setNumberKeyTypeface(typeface: Typeface) {
        for (key in numericKeys) {
            key.typeface = typeface
        }
    }

    /**
     * Sets left auxiliary button icon.
     */
    fun setLeftAuxButtonIcon(@DrawableRes icon: Int) {
        leftAuxBtn.setImageResource(icon)
    }

    /**
     * Sets right auxiliary button icon.
     */
    fun setRightAuxButtonIcon(@DrawableRes icon: Int) {
        rightAuxBtn.setImageResource(icon)
    }

    /**
     * Sets left auxiliary button background.
     */
    fun setLeftAuxButtonBackground(@DrawableRes bg: Int) {
        leftAuxBtn.background = ContextCompat.getDrawable(context, bg)
    }

    /**
     * Sets right auxiliary button background.
     */
    fun setRightAuxButtonBackground(@DrawableRes bg: Int) {
        rightAuxBtn.background = ContextCompat.getDrawable(context, bg)
    }

    /**
     * Initializes XML attributes.
     */
    private fun initializeAttributes(attrs: AttributeSet?) {
        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.NumberKeyboard, 0, 0)
        try {
            // Get keyboard type
            val type = array.getInt(R.styleable.NumberKeyboard_numberkeyboard_keyboardType, -1)
            if (type == -1) throw IllegalArgumentException("keyboardType attribute is required.")
            // Get key sizes
            keyWidth = array.getLayoutDimension(
                R.styleable.NumberKeyboard_numberkeyboard_keyWidth,
                DEFAULT_KEY_WIDTH_DP
            )
            keyHeight = array.getLayoutDimension(
                R.styleable.NumberKeyboard_numberkeyboard_keyHeight,
                DEFAULT_KEY_HEIGHT_DP
            )
            // Get key padding
            keyPadding = array.getDimensionPixelSize(
                R.styleable.NumberKeyboard_numberkeyboard_keyPadding,
                dpToPx(DEFAULT_KEY_PADDING_DP.toFloat())
            )
            // Get number key background
            numberKeyBackground = array.getResourceId(
                R.styleable.NumberKeyboard_numberkeyboard_numberKeyBackground,
                R.drawable.numberkeyboard_key_bg
            )
            // Get number key text color
            numberKeyTextColor = array.getResourceId(
                R.styleable.NumberKeyboard_numberkeyboard_numberKeyTextColor,
                R.drawable.numberkeyboard_key_text_color
            )
            // Get keys enable/disable
            enableKeys = array.getBoolean(
                R.styleable.NumberKeyboard_numberkeyboard_enableKeys,
                true
            )
            // Get auxiliary icons
            when (type) {
                0 -> { // integer
                    leftAuxBtnIcon = R.drawable.numberkeyboard_key_bg_transparent
                    rightAuxBtnIcon = R.drawable.numberkeyboard_ic_backspace
                    leftAuxBtnBackground = R.drawable.numberkeyboard_key_bg_transparent
                    rightAuxBtnBackground = R.drawable.numberkeyboard_key_bg_transparent
                }
                1 -> { // decimal
                    leftAuxBtnIcon = R.drawable.numberkeyboard_ic_comma
                    rightAuxBtnIcon = R.drawable.numberkeyboard_ic_backspace
                    leftAuxBtnBackground = R.drawable.numberkeyboard_key_bg
                    rightAuxBtnBackground = R.drawable.numberkeyboard_key_bg_transparent
                }
                2 -> { // fingerprint
                    leftAuxBtnIcon = R.drawable.numberkeyboard_ic_fingerprint
                    rightAuxBtnIcon = R.drawable.numberkeyboard_ic_backspace
                    leftAuxBtnBackground = R.drawable.numberkeyboard_key_bg_transparent
                    rightAuxBtnBackground = R.drawable.numberkeyboard_key_bg_transparent
                }
                3 -> { // custom
                    leftAuxBtnIcon = array.getResourceId(
                        R.styleable.NumberKeyboard_numberkeyboard_leftAuxBtnIcon,
                        R.drawable.numberkeyboard_key_bg_transparent
                    )
                    rightAuxBtnIcon = array.getResourceId(
                        R.styleable.NumberKeyboard_numberkeyboard_rightAuxBtnIcon,
                        R.drawable.numberkeyboard_key_bg_transparent
                    )
                    leftAuxBtnBackground = array.getResourceId(
                        R.styleable.NumberKeyboard_numberkeyboard_leftAuxBtnBackground,
                        R.drawable.numberkeyboard_key_bg_transparent
                    )
                    rightAuxBtnBackground = array.getResourceId(
                        R.styleable.NumberKeyboard_numberkeyboard_rightAuxBtnBackground,
                        R.drawable.numberkeyboard_key_bg_transparent
                    )
                }
                else -> {
                    leftAuxBtnIcon = R.drawable.numberkeyboard_key_bg_transparent
                    rightAuxBtnIcon = R.drawable.numberkeyboard_key_bg_transparent
                    leftAuxBtnBackground = R.drawable.numberkeyboard_key_bg
                    rightAuxBtnBackground = R.drawable.numberkeyboard_key_bg
                }
            }
        } finally {
            array.recycle()
        }
    }

    /**
     * Inflates layout.
     */
    private fun inflateView() {
        val view = View.inflate(context, R.layout.numberkeyboard_layout, this)
        // Get numeric keys
        numericKeys = ArrayList(10)
        numericKeys.add(view.findViewById(R.id.key0))
        numericKeys.add(view.findViewById(R.id.key1))
        numericKeys.add(view.findViewById(R.id.key2))
        numericKeys.add(view.findViewById(R.id.key3))
        numericKeys.add(view.findViewById(R.id.key4))
        numericKeys.add(view.findViewById(R.id.key5))
        numericKeys.add(view.findViewById(R.id.key6))
        numericKeys.add(view.findViewById(R.id.key7))
        numericKeys.add(view.findViewById(R.id.key8))
        numericKeys.add(view.findViewById(R.id.key9))
        // Get auxiliary keys
        leftAuxBtn = view.findViewById(R.id.leftAuxBtn)
        rightAuxBtn = view.findViewById(R.id.rightAuxBtn)

        // Set styles
        setStyles()
        // Set listeners
        setupListeners()
    }

    /**
     * Set styles.
     */
    private fun setStyles() {
        setKeyWidth(keyWidth)
        setKeyHeight(keyHeight)
        setKeyPadding(keyPadding)
        setNumberKeyBackground(numberKeyBackground)
        setNumberKeyTextColor(numberKeyTextColor)
        setLeftAuxButtonIcon(leftAuxBtnIcon)
        setLeftAuxButtonBackground(leftAuxBtnBackground)
        setRightAuxButtonIcon(rightAuxBtnIcon)
        setRightAuxButtonBackground(rightAuxBtnBackground)
    }

    /**
     * Setup on click listeners.
     */
    private fun setupListeners() {
        // Set number callbacks
        for (i in numericKeys.indices) {
            val key = numericKeys[i]
            key.setOnClickListener {
                inputEditText?.append(i.toString())
                listener?.onNumberClicked(i, inputEditText?.text.toString())
            }
        }
        // Set auxiliary key callbacks
        leftAuxBtn.setOnClickListener {
            listener?.onLeftAuxButtonClicked()
        }
        rightAuxBtn.setOnClickListener {
            inputEditText?.let {
                if (it.length() > 0) it.text?.delete(it.length() - 1, it.length())
            }
            listener?.onRightAuxButtonClicked(inputEditText?.text.toString())
        }
    }

    /**
     * Utility method to convert dp to pixels.
     */
    fun dpToPx(valueInDp: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            valueInDp,
            resources.displayMetrics
        ).toInt()
    }

    companion object {

        private const val DEFAULT_KEY_WIDTH_DP = -1 // match_parent
        private const val DEFAULT_KEY_HEIGHT_DP = -1 // match_parent
        private const val DEFAULT_KEY_PADDING_DP = 16

        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }

    interface NumberKeyboardListener {

        /**
         * Invoked when a number key is clicked.
         */
        fun onNumberClicked(number: Int, numbers: String)

        /**
         * Invoked when the left auxiliary button is clicked.
         */
        fun onLeftAuxButtonClicked()

        /**
         * Invoked when the right auxiliary button is clicked.
         */
        fun onRightAuxButtonClicked(numbers: String)
    }
}


