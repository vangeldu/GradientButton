package com.dagang.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by duweigang on 2017/10/8.
 * <p>
 * email : dagangxx@gmail.com
 * <p>
 * https://github.com/duweigang
 */

public class GradientButton extends FrameLayout {

    public static final int TOP_BOTTOM = 0;
    public static final int TR_BL = 1;
    public static final int RIGHT_LEFT = 2;
    public static final int BR_TL = 3;
    public static final int BOTTOM_TOP = 4;
    public static final int BL_TR = 5;
    public static final int LEFT_RIGHT = 6;
    public static final int TL_BR = 7;
    private final Paint paint;
    private final Canvas canvas;
    private final Rect bounds;
    private Bitmap bitmap;
    private boolean invalidateShadow;
    private boolean isShadowed;
    private int shadowColor;
    private int shadowAlpha;
    private float shadowRadius;
    private float shadowDistance;
    private float shadowAngle;
    private float shadowDx;
    private float shadowDy;

    private TextView button;


    private String buttonText;
    private float buttonSize;
    private int buttonTextColor;

    private int buttonStartColor;
    private int buttonEndColor;
    private int buttonPressStartColor;
    private int buttonPressEndColor;
    private int buttonGradientOrientation;
    private int buttonRadius;
    private Context context;


    public GradientButton(Context context) {
        this(context, null);
    }

    public GradientButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradientButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.paint = new Paint(1) {
            {
                this.setDither(true);
                this.setFilterBitmap(true);
            }
        };
        this.canvas = new Canvas();
        this.bounds = new Rect();
        this.invalidateShadow = true;
        this.setWillNotDraw(false);
        this.setLayerType(2, this.paint);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GradientButton);

        try {
            buttonText = (typedArray.getString(R.styleable.GradientButton_button_text));
            buttonSize = (typedArray.getDimension(R.styleable.GradientButton_button_size, 14));
            buttonTextColor = (typedArray.getColor(R.styleable.GradientButton_button_text_color, Color.BLACK));
            buttonStartColor = (typedArray.getColor(R.styleable.GradientButton_button_start_color, Color.BLACK));
            buttonEndColor = (typedArray.getColor(R.styleable.GradientButton_button_end_color, Color.WHITE));
            buttonPressStartColor = (typedArray.getColor(R.styleable.GradientButton_button_press_start_color, Color.BLACK));
            buttonPressEndColor = (typedArray.getColor(R.styleable.GradientButton_button_press_end_color, Color.WHITE));
            buttonGradientOrientation = (typedArray.getInt(R.styleable.GradientButton_button_gradient_orientation, 0));
            buttonRadius = ((int) typedArray.getDimension(R.styleable.GradientButton_button_radius, 0));

            this.setIsShadowed(typedArray.getBoolean(R.styleable.GradientButton_button_is_shadowed, true));
            this.setShadowRadius(typedArray.getDimension(R.styleable.GradientButton_button_shadow_radius, 30.0F));
            this.setShadowDistance(typedArray.getDimension(R.styleable.GradientButton_button_shadow_distance, 15.0F));
            this.setShadowAngle((float) typedArray.getInteger(R.styleable.GradientButton_button_shadow_angle, 45));
            this.setShadowColor(typedArray.getColor(R.styleable.GradientButton_button_shadow_color, Color.BLACK));
        } finally {
            typedArray.recycle();
        }
        addButtonText();
    }

    private void addButtonText() {
        button = new TextView(context);
        button.setClickable(true);
        button.setGravity(Gravity.CENTER);
        button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        button.setText(buttonText);
        button.setTextSize(buttonSize);
        button.setTextColor(buttonTextColor);
        addView(button);
    }

    private void setButtonBackground() {

        int[] defaultColors = {buttonStartColor, buttonEndColor};
        int[] pressColors = {buttonPressStartColor, buttonPressEndColor};

        GradientDrawable defaultDrawable = getGradientDrawable(defaultColors);
        GradientDrawable pressDrawable = getGradientDrawable(pressColors);

        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressDrawable);
        stateListDrawable.addState(new int[]{}, defaultDrawable);

        button.setBackgroundDrawable(stateListDrawable);
    }

    private GradientDrawable getGradientDrawable(int[] colors) {

        GradientDrawable backDrawable = new GradientDrawable();
        backDrawable.setCornerRadius(buttonRadius);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            backDrawable.setColors(colors);
            switch (buttonGradientOrientation) {
                case TOP_BOTTOM:
                    backDrawable.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
                    break;
                case TR_BL:
                    backDrawable.setOrientation(GradientDrawable.Orientation.TR_BL);
                    break;
                case RIGHT_LEFT:
                    backDrawable.setOrientation(GradientDrawable.Orientation.RIGHT_LEFT);
                    break;
                case BR_TL:
                    backDrawable.setOrientation(GradientDrawable.Orientation.BR_TL);
                    break;
                case BOTTOM_TOP:
                    backDrawable.setOrientation(GradientDrawable.Orientation.BOTTOM_TOP);
                    break;
                case BL_TR:
                    backDrawable.setOrientation(GradientDrawable.Orientation.BL_TR);
                    break;
                case LEFT_RIGHT:
                    backDrawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
                    break;
                case TL_BR:
                    backDrawable.setOrientation(GradientDrawable.Orientation.TL_BR);
                    break;
            }
        } else {
            backDrawable.setColor(colors[0]);
        }
        return backDrawable;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.bitmap != null) {
            this.bitmap.recycle();
            this.bitmap = null;
        }

    }

    private void resetShadow() {
        this.shadowDx = (float) ((double) this.shadowDistance * Math.cos((double) (this.shadowAngle / 180.0F) * 3.141592653589793D));
        this.shadowDy = (float) ((double) this.shadowDistance * Math.sin((double) (this.shadowAngle / 180.0F) * 3.141592653589793D));
        int padding = (int) (this.shadowDistance + this.shadowRadius);
        this.setPadding(padding, padding, padding, padding);
        this.requestLayout();
    }

    private int adjustShadowAlpha(boolean adjust) {
        return Color.argb(adjust ? 255 : this.shadowAlpha, Color.red(this.shadowColor), Color.green(this.shadowColor), Color.blue(this.shadowColor));
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.bounds.set(0, 0, MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
    }

    public void requestLayout() {
        this.invalidateShadow = true;
        super.requestLayout();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setButtonBackground();

        setButtonShadow(canvas);
    }

    private void setButtonShadow(Canvas canvas) {
        if (this.isShadowed) {
            if (this.invalidateShadow) {
                if (this.bounds.width() != 0 && this.bounds.height() != 0) {
                    this.bitmap = Bitmap.createBitmap(this.bounds.width(), this.bounds.height(), Bitmap.Config.ARGB_8888);
                    this.canvas.setBitmap(this.bitmap);
                    this.invalidateShadow = false;
                    super.dispatchDraw(this.canvas);
                    Bitmap extractedAlpha = this.bitmap.extractAlpha();
                    this.canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                    this.paint.setColor(this.adjustShadowAlpha(false));
                    this.canvas.drawBitmap(extractedAlpha, this.shadowDx, this.shadowDy, this.paint);
                    extractedAlpha.recycle();
                } else {
                    this.bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.RGB_565);
                }
            }

            this.paint.setColor(this.adjustShadowAlpha(true));
            if (this.canvas != null && this.bitmap != null && !this.bitmap.isRecycled()) {
                canvas.drawBitmap(this.bitmap, 0.0F, 0.0F, this.paint);
            }
        }
    }


    public boolean isShadowed() {
        return this.isShadowed;
    }

    public void setIsShadowed(boolean isShadowed) {
        this.isShadowed = isShadowed;
        this.postInvalidate();
    }

    public float getShadowDistance() {
        return this.shadowDistance;
    }

    public void setShadowDistance(float shadowDistance) {
        this.shadowDistance = shadowDistance;
        this.resetShadow();
    }

    public float getShadowAngle() {
        return this.shadowAngle;
    }

    public void setShadowAngle(float shadowAngle) {
        this.shadowAngle = Math.max(0.0F, Math.min(shadowAngle, 360.0F));
        this.resetShadow();
    }

    public float getShadowRadius() {
        return this.shadowRadius;
    }

    public void setShadowRadius(float shadowRadius) {
        this.shadowRadius = Math.max(0.1F, shadowRadius);
        if (!this.isInEditMode()) {
            this.paint.setMaskFilter(new BlurMaskFilter(this.shadowRadius, BlurMaskFilter.Blur.NORMAL));
            this.resetShadow();
        }
    }

    public int getShadowColor() {
        return this.shadowColor;
    }

    public void setShadowColor(int shadowColor) {
        this.shadowColor = shadowColor;
        this.shadowAlpha = Color.alpha(shadowColor);
        this.resetShadow();
    }

    public float getShadowDx() {
        return this.shadowDx;
    }

    public float getShadowDy() {
        return this.shadowDy;
    }

    public TextView getButton() {
        return button;
    }

    public void setButton(TextView mButton) {
        this.button = mButton;
    }

    public int getButtonStartColor() {
        return buttonStartColor;
    }

    public void setButtonStartColor(int buttonStartColor) {
        this.buttonStartColor = buttonStartColor;
        requestLayout();
    }

    public int getButtonEndColor() {
        return buttonEndColor;
    }

    public void setButtonEndColor(int buttonEndColor) {
        this.buttonEndColor = buttonEndColor;
        requestLayout();
    }

    public int getButtonPressStartColor() {
        return buttonPressStartColor;
    }

    public void setButtonPressStartColor(int buttonPressStartColor) {
        this.buttonPressStartColor = buttonPressStartColor;
        requestLayout();
    }

    public int getButtonPressEndColor() {
        return buttonPressEndColor;
    }

    public void setButtonPressEndColor(int buttonPressEndColor) {
        this.buttonPressEndColor = buttonPressEndColor;
        requestLayout();
    }

    public int getButtonGradientOrientation() {
        return buttonGradientOrientation;
    }

    public void setButtonGradientOrientation(int buttonGradientOrientation) {
        this.buttonGradientOrientation = buttonGradientOrientation;
        requestLayout();
    }

    public int getButtonRadius() {
        return buttonRadius;
    }

    public void setButtonRadius(int buttonRadius) {
        this.buttonRadius = buttonRadius;
        requestLayout();
    }
}
