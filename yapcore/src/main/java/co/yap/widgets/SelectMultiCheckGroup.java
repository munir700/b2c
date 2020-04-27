package co.yap.widgets;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import co.yap.yapcore.R;
import co.yap.yapcore.helpers.extentions.DimensionUtilsKt;


public class SelectMultiCheckGroup extends LinearLayout {

    private boolean isSingleSelected;
    private int column;
    private int row;
    private int firstSingleSelectedPosition;
    private int itemHorizontalSpace;
    private int itemVerticalSpace;
    private int itemHorizontalPadding;
    private int itemVerticalPadding;
    private int itemTextSize;
    private int checkedTextColor;
    private int unCheckedTextColor;

    private List<AppCompatRadioButton> radioButtonList;
    private List<CheckBox> checkBoxList;

    private int mSelected;
    private List<Integer> mSelectedList;
    private OnItemSelectedListener listener;
    private List<String> mData;

    private final int DEFAULT_SELECTED_POSITION = -1;

    public SelectMultiCheckGroup(Context context) {
        this(context, null);
    }

    public SelectMultiCheckGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectMultiCheckGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
        initView();
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SelectMultiCheckGroup);
            isSingleSelected = ta.getBoolean(R.styleable.SelectMultiCheckGroup_isSingleSelected, false);
            column = ta.getInteger(R.styleable.SelectMultiCheckGroup_column, 1);
            row = ta.getInteger(R.styleable.SelectMultiCheckGroup_row, 1);
            firstSingleSelectedPosition = ta.getInteger(R.styleable.SelectMultiCheckGroup_firstSingleSelectedPosition, DEFAULT_SELECTED_POSITION);
            itemHorizontalSpace = ta.getDimensionPixelSize(R.styleable.SelectMultiCheckGroup_item_horizontal_space,
                    getResources().getDimensionPixelSize(R.dimen.margin_medium));
            itemVerticalSpace = ta.getDimensionPixelSize(R.styleable.SelectMultiCheckGroup_item_vertical_space,
                    getResources().getDimensionPixelSize(R.dimen.margin_medium));
            itemHorizontalPadding = ta.getDimensionPixelSize(R.styleable.SelectMultiCheckGroup_item_horizontal_padding,
                    getResources().getDimensionPixelSize(R.dimen.margin_small));
            itemVerticalPadding = ta.getDimensionPixelSize(R.styleable.SelectMultiCheckGroup_item_vertical_padding,
                    getResources().getDimensionPixelSize(R.dimen.margin_small));
            itemTextSize = ta.getDimensionPixelSize(R.styleable.SelectMultiCheckGroup_item_text_size, 12);
            checkedTextColor = ta.getColor(R.styleable.SelectMultiCheckGroup_checked_text_color, ContextCompat.getColor(getContext(), android.R.color.white));
            unCheckedTextColor = ta.getColor(R.styleable.SelectMultiCheckGroup_unChecked_text_color, ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
            CharSequence[] entries = ta.getTextArray(R.styleable.SelectMultiCheckGroup_android_entries);
            mData = new ArrayList<>();
            for (CharSequence c : entries) {
                mData.add(c.toString());
            }
            ta.recycle();
        } else {
            column = 1;
            row = 1;
            firstSingleSelectedPosition = DEFAULT_SELECTED_POSITION;
            itemHorizontalSpace = DimensionUtilsKt.dip(getContext(), 14); //ScreenUtil.dip2px(getContext(), 14f);
            itemVerticalSpace = DimensionUtilsKt.dip(getContext(), 6);
            itemHorizontalPadding = DimensionUtilsKt.dip(getContext(), 8);
            itemVerticalPadding = DimensionUtilsKt.dip(getContext(), 6);
            itemTextSize = 12;
        }

        if (isSingleSelected) {
            radioButtonList = new ArrayList<>();
        } else {
            mSelectedList = new ArrayList<>();
            checkBoxList = new ArrayList<>();
        }
    }

    private void initView() {
        setOrientation(VERTICAL);
        for (int i = 0; i < row; i++) {
            RadioGroup rg = null;
            LinearLayout rowLL = null;
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, itemVerticalSpace, itemHorizontalSpace, 0);
            if (isSingleSelected) {
                rg = new RadioGroup(getContext());
                rg.setLayoutParams(lp);
                rg.setOrientation(HORIZONTAL);
            } else {// 多选
                rowLL = new LinearLayout(getContext());
                rowLL.setLayoutParams(lp);
                rowLL.setOrientation(HORIZONTAL);
            }

            for (int j = 0; j < column; j++) {
                final int finalI = i;
                final int finalJ = j;

                if (isSingleSelected) {
                    RadioGroup.LayoutParams rglp = new RadioGroup.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
                    rglp.leftMargin = itemHorizontalSpace;
                    rglp.bottomMargin = 5;
                    AppCompatRadioButton rb = new AppCompatRadioButton(getContext());
                    rb.setLayoutParams(rglp);
                    rb.setBackgroundResource(R.drawable.select_single_cb_selector);
                    rb.setTextColor(getTextColorStateList());
                    rb.setButtonDrawable(android.R.color.transparent);
                    rb.setGravity(Gravity.CENTER);
                    rb.setPadding(itemHorizontalPadding, itemVerticalPadding, itemHorizontalPadding, itemVerticalPadding);
                    rb.setTextSize(TypedValue.COMPLEX_UNIT_SP, itemTextSize);
                    rb.setSingleLine(true);
                    rb.setEllipsize(TextUtils.TruncateAt.END);
                    //rb.setTypeface(ResourcesCompat.getFont(getContext(), R.font.roboto_regular));
                    rb.setOnCheckedChangeListener((buttonView, isChecked) -> {
                        if (isChecked) {
                            mSelected = finalI * column + finalJ;
                            for (int k = 0; k < row; k++) {
                                if (radioButtonList.get(mSelected).getParent() != getChildAt(k)) {
                                    ((RadioGroup) getChildAt(k)).clearCheck();
                                }
                            }
                            if (listener != null) {
                                listener.checked(buttonView, mSelected, true);
                            }
                        }
                    });

                    if (rg != null) {
                        rg.addView(rb);
                        radioButtonList.add(rb);
                    }
                } else {
                    LinearLayout.LayoutParams lllp = new LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
                    lllp.leftMargin = itemHorizontalSpace;
                    lllp.bottomMargin = 5;
                    CheckBox cb = new CheckBox(getContext());
                    cb.setLayoutParams(lllp);
                    cb.setBackgroundResource(R.drawable.select_single_cb_selector);
                    cb.setTextColor(getTextColorStateList());
                    cb.setButtonDrawable(android.R.color.transparent);
                    cb.setGravity(Gravity.CENTER);
                    cb.setPadding(itemHorizontalPadding, itemVerticalPadding, itemHorizontalPadding, itemVerticalPadding);
                    cb.setTextSize(TypedValue.COMPLEX_UNIT_SP, itemTextSize);
                    cb.setSingleLine(true);
                    ///   cb.setTypeface(ResourcesCompat.getFont(getContext(), R.font.roboto_regular));
                    cb.setEllipsize(TextUtils.TruncateAt.END);

                    cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
                        mSelected = finalI * column + finalJ;
                        if (isChecked) {
                            if (!mSelectedList.contains(mSelected)) {
                                mSelectedList.add(mSelected);
                            }
                        } else {
                            if (mSelectedList.contains(mSelected)) {
                                mSelectedList.remove(mSelectedList.indexOf(mSelected));
                            }
                        }

                        if (listener != null) {
                            listener.checked(buttonView, mSelected, isChecked);
                        }
                    });

                    if (rowLL != null) {
                        rowLL.addView(cb);
                        checkBoxList.add(cb);
                    }
                }
            }
            if (isSingleSelected) {
                addView(rg);
            } else {
                addView(rowLL);
            }
        }
        initData(mData);
    }

    private ColorStateList getTextColorStateList() {
        return new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_checked},
                        new int[]{}
                }, new int[]{
                checkedTextColor,
                unCheckedTextColor});
    }

    public void initData(List<String> data) {
        if (data == null || data.isEmpty()
                || data.size() > ((isSingleSelected) ? radioButtonList.size() : checkBoxList.size())) {
            throw new IllegalArgumentException("setData() invalid Param");
        }
        mData = new ArrayList<>();
        mData.addAll(data);

        setData();
    }

    private void setData() {
        if (isSingleSelected) {
            if (mData.size() < radioButtonList.size()) {
                int cout1 = mData.size() - 1;
                int cout2 = radioButtonList.size() - 1;
                for (int i = cout2; i > cout1; i--) {
                    radioButtonList.get(i).setVisibility(INVISIBLE);
                    radioButtonList.remove(i);
                }
            }
            for (int i = 0; i < mData.size(); i++) {
                radioButtonList.get(i).setText(mData.get(i));
            }
        } else {
            if (mData.size() < checkBoxList.size()) {
                int cout1 = mData.size() - 1;
                int cout2 = checkBoxList.size() - 1;
                for (int i = cout2; i > cout1; i--) {
                    checkBoxList.get(i).setVisibility(INVISIBLE);
                    checkBoxList.remove(i);
                }
            }
            for (int i = 0; i < mData.size(); i++) {
                checkBoxList.get(i).setText(mData.get(i));
            }
        }
        setSeleted(firstSingleSelectedPosition);
    }


    public int getSelectedOne() {
        if (isSingleSelected) {
            return mSelected;
        } else {
            throw new IllegalStateException("Use for radio，app:isSingleSelected=\"true\"");
        }
    }


    public List<Integer> getSelectedAll() {
        if (isSingleSelected) {
            throw new IllegalStateException("Use for multiple selection，app:isSingleSelected=\"false\"");
        } else {
            return mSelectedList;
        }
    }

    public List<CheckBox> getSelectedAllView() {
        return checkBoxList;
    }

    public int getDataSize() {
        return mData == null ? 0 : mData.size();
    }

    public void setSeleted(int position) {
        if (position < 0 || position >= mData.size()) {
            return;
        }
        mSelected = position;
        if (isSingleSelected) {
            radioButtonList.get(position).setChecked(true);
        } else {
            checkBoxList.get(position).setChecked(true);
        }
    }

    public void resetSelect() {
        if (!isSingleSelected) {
            for (int i = 1; i < checkBoxList.size(); i++) {
                checkBoxList.get(i).setChecked(false);
            }
        }
        setSeleted(firstSingleSelectedPosition);
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.listener = listener;
    }


    public interface OnItemSelectedListener {
        void checked(View view, int position, boolean isChecked);
    }

}