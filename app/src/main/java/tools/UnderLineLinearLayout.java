package tools;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.example.ygh.falltestprogram.R;

/**
 * Created by ygh on 2016/2/26.
 * ���״���ʱ�����linearlayout
 */
public class UnderLineLinearLayout extends LinearLayout {
    //=============================================================Ԫ�ض���
    private Bitmap mIcon;
    //line location
    private int lineMarginSide;
    private int lineDynamicDimen;
    //line property
    private int lineStrokeWidth;
    private int lineColor;
    //point property
    private int pointSize;
    private int pointColor;

    //=============================================================paint
    private Paint linePaint;
    private Paint pointPaint;
    //=============================================================������������
    //��һ�����λ��
    private int firstX;
    private int firstY;
    //���һ��ͼ��λ��
    private int lastX;
    private int lastY;
    //Ĭ�ϴ�ֱ
    private int curOrientation = VERTICAL;
    private Context mContext;
    //����
    private boolean drawLine = true;

    public UnderLineLinearLayout(Context context) {
        this(context, null);
    }

    public UnderLineLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UnderLineLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.UnderLineLinearLayout);
        lineMarginSide = attr.getDimensionPixelOffset(R.styleable.UnderLineLinearLayout_line_margin_side, 10);
        lineDynamicDimen = attr.getDimensionPixelOffset(R.styleable.UnderLineLinearLayout_line_dynamic_dimen, 0);
        lineStrokeWidth = attr.getDimensionPixelOffset(R.styleable.UnderLineLinearLayout_line_stroke_width, 2);
        lineColor = attr.getColor(R.styleable.UnderLineLinearLayout_line_color, 0xff3dd1a5);
        pointSize = attr.getDimensionPixelSize(R.styleable.UnderLineLinearLayout_point_size, 8);
        pointColor = attr.getDimensionPixelOffset(R.styleable.UnderLineLinearLayout_point_color, 0xff3dd1a5);

        int iconRes = attr.getResourceId(R.styleable.UnderLineLinearLayout_icon_src, R.drawable.ic_ok);
        BitmapDrawable temp = (BitmapDrawable) context.getResources().getDrawable(iconRes);
        if (temp != null) mIcon = temp.getBitmap();

        curOrientation = getOrientation();
        attr.recycle();
        initView(context);
    }

    private void initView(Context context) {
    	this.mContext = context;
        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setDither(true);
        linePaint.setColor(lineColor);
        linePaint.setStrokeWidth(lineStrokeWidth);
        linePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        pointPaint = new Paint();
        pointPaint.setAntiAlias(true);
        pointPaint.setDither(true);
        pointPaint.setColor(pointColor);
        pointPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (drawLine) {
            drawTimeLine(canvas);
        }
    }

    private void drawTimeLine(Canvas canvas) {
        int childCount = getChildCount();

        if (childCount > 0) {
            //����1��֤��������2����Ҳ���ǵ�һ���͵ڶ���֮�������ߣ���һ�������һ���ֱ��е�/icon
            if (childCount > 1) {
                switch (curOrientation) {
                    case VERTICAL:
                        drawFirstChildViewVertical(canvas);
                        drawLastChildViewVertical(canvas);
                        drawBetweenLineVertical(canvas);
                        break;
                    case HORIZONTAL:
                        break;
                    default:
                        break;
                }
            }
            else if (childCount == 1) {
                switch (curOrientation) {
                    case VERTICAL:
                        drawFirstChildViewVertical(canvas);
                        break;
                    case HORIZONTAL:
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void drawFirstChildViewVertical(Canvas canvas) {
        if (getChildAt(0) != null) {
            int top = getChildAt(0).getTop();
            //��¼ֵ
            firstX = lineMarginSide;
            firstY = top + getChildAt(0).getPaddingTop() + lineDynamicDimen;
            //��һ��Բ
            canvas.drawCircle(firstX, firstY, pointSize, pointPaint);
        }
    }

    private void drawLastChildViewVertical(Canvas canvas) {
        if (getChildAt(getChildCount() - 1) != null) {
            int top = getChildAt(getChildCount() - 1).getTop();
            //��¼ֵ
            lastX = lineMarginSide - (mIcon.getWidth() >> 1);
            lastY = top + getChildAt(getChildCount() - 1).getPaddingTop() + lineDynamicDimen;
            //��һ��ͼ
            canvas.drawBitmap(mIcon, lastX, lastY, null);
        }
    }

    private void drawBetweenLineVertical(Canvas canvas) {
        for (int i = 0; i < getChildCount() - 1; i++) {
            //��ʣ�µ�
            canvas.drawLine(lineMarginSide, firstY, lineMarginSide, lastY, linePaint);
            //�����ߣ��ͻ�Բ
            if (getChildAt(i) != null && i != 0) {
                int top = getChildAt(i).getTop();
                //��¼ֵ
                int Y = top + getChildAt(i).getPaddingTop() + lineDynamicDimen;
                canvas.drawCircle(lineMarginSide, Y, pointSize, pointPaint);
            }
        }
    }
}

