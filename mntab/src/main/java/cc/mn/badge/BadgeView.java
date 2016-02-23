package cc.mn.badge;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;

/**
 * User: 山野书生(1203596603@qq.com)
 * Date: 2015-12-03
 * Time: 19:10
 * Version 1.3
 * 本控件是在开源控件<a href="https://github.com/stefanjauker/BadgeView"></a>基础上修改完成，感谢！
 */

public class BadgeView extends TextView{

    private boolean mHideOnNull = true;

    //设置为true时，什么情况下都必须显示
    private boolean mMustShow = false;

    public BadgeView(Context context) {
        this(context, null);
    }

    public BadgeView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public BadgeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    /**
     * 初始化
     * */
    private void init(){
        if(!(getLayoutParams() instanceof LayoutParams)){
            LayoutParams layoutParams = new LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.RIGHT | Gravity.TOP);
            setLayoutParams(layoutParams);
        }

        //设置文字的默认属性
        setTextColor(Color.WHITE);
        setTypeface(Typeface.DEFAULT);
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);

        //设置默认背景
        setBackground(10, Color.parseColor("#e91e63"));

        setGravity(Gravity.CENTER);

        //设置默认数据
        setHideOnNull(true);
    }

    /**
     * 设置背景
     * @param  badgeColor 颜色
     * @param  dipRadius 圆角半径
     * */
    public void setBackground(int dipRadius, int badgeColor){
        int radius = dip2Px(dipRadius);
        float[] radiusArray = {radius, radius, radius, radius, radius, radius, radius, radius};

        RoundRectShape rectShape = new RoundRectShape(radiusArray, null, null);
        ShapeDrawable bgDrawable = new ShapeDrawable(rectShape);

        bgDrawable.getPaint().setColor(badgeColor);
        //兼容低版本
        setBackgroundDrawable(bgDrawable);
    }

    public boolean isHideOnNull(){
        return  mHideOnNull;
    }

    public void setHideOnNull(boolean hideOnNull){
        mHideOnNull = hideOnNull;
    }

    public boolean isMustShow() {
        return mMustShow;
    }

    public void setMustShow(boolean mustShow) {
        mMustShow = mustShow;
    }

    /**
     * 隐藏
     * */
    public void mustHide(){
        setHideOnNull(false);
        setMustShow(false);
        setVisibility(View.GONE);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if(isHideOnNull() && (text == null || text.toString().equalsIgnoreCase("0"))){
            setVisibility(View.GONE);
        } else {
            setVisibility(View.VISIBLE);
        }
        if(mMustShow){ //必须显示
            setVisibility(View.VISIBLE);
        }
        super.setText(text, type);
    }

    /**
     * 设置消息数量
     * */
    public void setBadgeCount(int count){
        setText(String.valueOf(count));
    }

    /**
     * 获取消息数量
     * */
    public Integer getBadgeCount(){
        if(TextUtils.isEmpty(getText())){
            return null; //整形对象，可以返回null
        }

        String text = getText().toString();
        try{
            return Integer.parseInt(text);
        } catch (Exception e){
            return null;
        }
    }

    /**
     * 设置Gravity
     * */
    public void setBadgeGravity(int gravity){
        FrameLayout.LayoutParams params = (LayoutParams) getLayoutParams();
        params.gravity = gravity;
        setLayoutParams(params);
    }

    public int getBadgeGravity(){
        FrameLayout.LayoutParams params = (LayoutParams)getLayoutParams();
        return params.gravity;
    }

    public void setBadgeWidthAndHeight(int width, int height){
        FrameLayout.LayoutParams params = (LayoutParams)getLayoutParams();
        params.width = width;
        params.height = height;
        setLayoutParams(params);
    }

    /**
     * 设置外边距
     * */
    public void setBadgeMargin(int dipMargin){
        setBadgeMargin(dipMargin, dipMargin, dipMargin, dipMargin);
    }

    /**
     * 设置外边距
     * */
    public void setBadgeMargin(int leftDipMargin, int topDipMargin, int rightDipMargin, int bottomDipMargin){
        FrameLayout.LayoutParams params = (LayoutParams)getLayoutParams();
        params.leftMargin = dip2Px(leftDipMargin);
        params.topMargin = dip2Px(topDipMargin);
        params.rightMargin = dip2Px(rightDipMargin);
        params.bottomMargin = dip2Px(bottomDipMargin);
        setLayoutParams(params);
    }

    /**
     * 设置内边距
     * */
    public void setBadgePadding(int dipPading){
        setBadgeMargin(dipPading, dipPading, dipPading, dipPading);
    }

    /**
     * 设置内边距
     * */
    public void setBadgePadding(int leftDipPadding, int topDipPadding, int rightDipPadding, int bottomDipPadding){
        setPadding(dip2Px(leftDipPadding), dip2Px(topDipPadding), dip2Px(rightDipPadding), dip2Px(bottomDipPadding));
    }

    public int[] getBadgeMargin(){
        FrameLayout.LayoutParams params = (LayoutParams)getLayoutParams();
        return new int[]{params.leftMargin, params.topMargin, params.rightMargin, params.bottomMargin};
    }

    /**
     * 累加消息数量
     * */
    public void incrementBadgeCount(int increment){
        Integer count = getBadgeCount();
        if(count == null){
            setBadgeCount(increment);
        } else {
            setBadgeCount(increment + count);
        }
    }

    /**
     * 减少消息数量
     * */
    public void decrementBadgeCount(int decrement){
        incrementBadgeCount(-decrement);
    }

    /**
     * 设置依靠的View
     * */
    public void setTargetView(View target){
        if(getParent() != null){
            ((ViewGroup)getParent()).removeView(this);
        }

        if(target == null){
            return;
        }

        if(target.getParent() instanceof FrameLayout){
            ((FrameLayout)target.getParent()).addView(this);
        } else if(target.getParent() instanceof ViewGroup){
            ViewGroup parentContainer = (ViewGroup)target.getParent();

            //返回目标视图在父视图中的位置
            int groupIndex = parentContainer.indexOfChild(target);

            //先移除视图，下面重新添加到新父视图中
            parentContainer.removeView(target);

            FrameLayout badgeContainer = new FrameLayout(getContext());
            ViewGroup.LayoutParams parentLayoutParams = target.getLayoutParams();

            badgeContainer.setLayoutParams(parentLayoutParams);

            target.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            ));
            //将移除的视图重新添加到新父视图中
            parentContainer.addView(badgeContainer, groupIndex, parentLayoutParams);
            badgeContainer.addView(target);

            badgeContainer.addView(this);
        } else if(target.getParent() == null){
            //父控件为空，可以抛出异常
        }
    }

    private int dip2Px(float dip){
        return (int)(dip*getContext().getResources().getDisplayMetrics().density + 5);
    }

}
