package me.jp.indicator;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Tabs indicator
 * <p/>
 * 这里需要注意的是继承ViewGroup的自定义控件，onDraw()执行的前提是设置了背景，所以代码中设置了默认的背景
 * <p/>
 * Created by JiangPing on 2015/7/8.
 */
public class TabsIndicator extends LinearLayout implements View.OnClickListener, OnPageChangeListener {
    private final int BASE_ID = 0xffff00;

    private ColorStateList mTextColor;
    private int mTextSizeNormal = 12;
    private int mTextSizeSelected = 15;

    private int mLineColor = android.R.color.white;
    private int mLineHeight = 5;
    private int mLineMarginTab = 20;

    private boolean mHasDivider = true;
    private int mDividerColor = android.R.color.black;
    private int mDividerWidth = 3;
    private int mDividerVerticalMargin = 10;
    private int mLinePosition = 1;

    private boolean mIsAnimation = true;

    private Paint mPaintLine;
    private LayoutInflater mInflater;

    private ViewPager mViewPager;

    private int mTabCount = 0;
    private int mCurrentTabIndex = 0;

    private int mLineMarginX = 0;

    private Path mPath = new Path();
    private int mTabWidth;
    private Context mContext;

    private OnPageChangeListener mOnPageChangeListener;

    private List<StateListDrawable> mTabIcons;

    public TabsIndicator(Context context) {
        this(context, null);
    }

    public TabsIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        handlerAttrs(context, attrs);
        initPaint();
    }


    private void handlerAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TabsIndicator);

        //indicator line
        mLineColor = ta.getColor(R.styleable.TabsIndicator_lineColor, mLineColor);
        mLineMarginTab = ta.getDimensionPixelOffset(R.styleable.TabsIndicator_lineMarginTab, mLineMarginTab);
        mLineHeight = ta.getDimensionPixelOffset(R.styleable.TabsIndicator_lineHeight, mLineHeight);
        mLinePosition = ta.getInt(R.styleable.TabsIndicator_linePosition, 1);

        //tabs text
        mTextColor = ta.getColorStateList(R.styleable.TabsIndicator_textColor);
        mTextSizeNormal = ta.getDimensionPixelOffset(R.styleable.TabsIndicator_textSizeNormal, mTextSizeNormal);
        mTextSizeSelected = ta.getDimensionPixelOffset(R.styleable.TabsIndicator_textSizeSelected, mTextSizeSelected);

        //divider between tabs
        mHasDivider = ta.getBoolean(R.styleable.TabsIndicator_hasDivider, mHasDivider);
        mDividerColor = ta.getColor(R.styleable.TabsIndicator_dividerColor, mDividerColor);
        mDividerWidth = ta.getDimensionPixelOffset(R.styleable.TabsIndicator_dividerWidth, mDividerWidth);
        mDividerVerticalMargin = ta.getDimensionPixelOffset(R.styleable.TabsIndicator_dividerVerticalMargin, mDividerVerticalMargin);

        ta.recycle();
    }

    private void initPaint() {
        mPaintLine = new Paint();
        mPaintLine.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaintLine.setColor(mLineColor);
    }

    public void setViewPager(int currentTab, ViewPager viewPager) {
        this.removeAllViews();
        mViewPager = viewPager;
        mTabCount = mViewPager.getAdapter().getCount();
        mViewPager.addOnPageChangeListener(this);

        initTabs(currentTab);
        postInvalidate();
    }


    private void initTabs(int currentTab) {
        if (getBackground() == null) {
            setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
        }

        for (int index = 0; index < mTabCount; index++) {
            TextView tvTab = new TextView(mContext);
            tvTab.setId(BASE_ID + index);
            tvTab.setOnClickListener(this);

            tvTab.setGravity(Gravity.CENTER);

            if (null != mViewPager.getAdapter().getPageTitle(index)) {
                if (null != mTextColor) {
                    tvTab.setTextColor(mTextColor);
                }
                tvTab.setText(mViewPager.getAdapter().getPageTitle(index));
            }

            if (mTabIcons != null && mTabIcons.size() > index) {
                StateListDrawable drawable = mTabIcons.get(index);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tvTab.setCompoundDrawables(null, drawable, null, null);
                tvTab.setCompoundDrawablePadding(0);
                tvTab.setPadding(0, 10, 0, 0);
            }

            LayoutParams tabLp = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1);
            tabLp.gravity = Gravity.CENTER;
            tvTab.setLayoutParams(tabLp);
            this.addView(tvTab);

            if (index == 0) {
                resetTab(tvTab, true);
            }

            if (index != mTabCount - 1 && mHasDivider) {
                LayoutParams dividerLp = new LayoutParams(mDividerWidth, LayoutParams.MATCH_PARENT);
                dividerLp.setMargins(0, mDividerVerticalMargin, 0, mDividerVerticalMargin);
                View vLine = new View(getContext());
                vLine.setBackgroundColor(mDividerColor);
                vLine.setLayoutParams(dividerLp);
                this.addView(vLine);
            }
        }
        setCurrentTab(currentTab);
    }

    private void setCurrentTab(int index) {
        if (mCurrentTabIndex != index && index > -1 && index < mTabCount) {
            TextView oldTab = (TextView) findViewById(BASE_ID + mCurrentTabIndex);
            resetTab(oldTab, false);

            mCurrentTabIndex = index;
            TextView newTab = (TextView) findViewById(BASE_ID + mCurrentTabIndex);
            resetTab(newTab, true);

            if (mViewPager.getCurrentItem() != mCurrentTabIndex) {
                mViewPager.setCurrentItem(mCurrentTabIndex, mIsAnimation);
            }
            postInvalidate();
        }
    }

    private void resetTab(TextView tvTab, boolean isSelected) {
        tvTab.setTextSize(TypedValue.COMPLEX_UNIT_PX, isSelected ? mTextSizeSelected : mTextSizeNormal);
        tvTab.setSelected(isSelected);
        tvTab.setPressed(isSelected);
    }

    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        mOnPageChangeListener = onPageChangeListener;
    }

    @Override
    public void onClick(View v) {
        int currentTabIndex = v.getId() - BASE_ID;
        setCurrentTab(currentTabIndex);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPath.rewind();

        float leftX = mLineMarginTab + mLineMarginX;
        float rightX = leftX + mTabWidth - 2 * mLineMarginTab;
        float topY = 0;
        float bottomY = 0;
        switch (mLinePosition) {
            case 0:
                topY = 0;
                bottomY = mLineHeight;
                break;
            case 1:
                topY = getHeight() - mLineHeight;
                bottomY = getHeight();
                break;
        }

        mPath.moveTo(leftX, topY);
        mPath.lineTo(rightX, topY);
        mPath.lineTo(rightX, bottomY);
        mPath.lineTo(leftX, bottomY);
        mPath.close();

        canvas.drawPath(mPath, mPaintLine);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTabWidth = getWidth();
        if (mTabCount != 0) {
            mTabWidth = getWidth() / mTabCount;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mLineMarginX = (int) (mTabWidth * (position + positionOffset));

        postInvalidate();

        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        setCurrentTab(position);

        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageSelected(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrollStateChanged(state);
        }
    }

    public void setAnimationWithTabChange(boolean isAnimation) {
        mIsAnimation = isAnimation;
    }




    public void addTabIcon(int idNormal, int idSelected) {
        StateListDrawable sld = new StateListDrawable();
        Drawable normal = idNormal == -1 ? null : mContext.getResources().getDrawable(idNormal);
        Drawable select = idSelected == -1 ? null : mContext.getResources().getDrawable(idSelected);
        sld.addState(new int[]{android.R.attr.state_selected}, select);
        sld.addState(new int[]{}, normal);
        if (mTabIcons == null) {
            mTabIcons = new ArrayList<>();
        }
        mTabIcons.add(sld);
    }


}