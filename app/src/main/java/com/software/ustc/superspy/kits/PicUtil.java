package com.software.ustc.superspy.kits;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class PicUtil {
    //从资源中获取Bitmap
    public static void UseBitmap(Context context, ImageView imageView, int drawableId) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                drawableId);
        imageView.setImageBitmap(bitmap);
    }
    //Bitmap ---> byte[]
    public byte[] BitmapToBytes(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
    //byte[] ---->bitmap
    public Bitmap BytesToBitmap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }
    //Bitmap 缩放方法
    public static Bitmap ZoomBitmap(Bitmap bitmap, int width, int heigh) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scalewidth = (float) width / w;
        float scaleheigh = (float) heigh / h;
        matrix.postScale(scalewidth, scaleheigh);
        Bitmap newBmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        return newBmp;
    }
    //Drawable----> Bitmap
    public static Bitmap DrawableToBitmap(Drawable drawable) {
        // 获取 drawable 长宽
        int width = drawable.getIntrinsicWidth();
        int heigh = drawable.getIntrinsicHeight();
        drawable.setBounds(0, 0, width, heigh);
        // 获取drawable的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 创建bitmap
        Bitmap bitmap = Bitmap.createBitmap(width, heigh, config);
        // 创建bitmap画布
        Canvas canvas = new Canvas(bitmap);
        // 将drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }
    //圆角图片
    public static Bitmap SetRoundCornerBitmap(Bitmap bitmap, float roundPx) {
        int width = bitmap.getWidth();
        int heigh = bitmap.getHeight();
        // 创建输出bitmap对象
        Bitmap outmap = Bitmap.createBitmap(width, heigh,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(outmap);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, width, heigh);
        final RectF rectf = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectf, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return outmap;
    }
    //获取带倒影的图片
    public static Bitmap CreateReflectionImageWithOrigin(Bitmap bitmap) {

        final int reflectionGap = 4;
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, h / 2, w,
                h / 2, matrix, false);

        Bitmap bitmapWithReflection = Bitmap.createBitmap(w, (h + h / 2),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(bitmap, 0, 0, null);
        Paint deafalutPaint = new Paint();
        canvas.drawRect(0, h, w, h + reflectionGap, deafalutPaint);

        canvas.drawBitmap(reflectionImage, 0, h + reflectionGap, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
                bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
                0x00ffffff, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        // Set the Transfer mode to be porter duff and destination in
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        // Draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0, h, w, bitmapWithReflection.getHeight()
                + reflectionGap, paint);
        return bitmapWithReflection;
    }
    //bitmap ---Drawable
    public static Drawable BitmapToDrawable(Bitmap bitmap, Context context) {
        BitmapDrawable drawbale = new BitmapDrawable(context.getResources(),
                bitmap);
        return drawbale;
    }
    //drawable进行缩放 ---> bitmap 然后比对bitmap进行缩放
    public static Drawable ZoomDrawable(Drawable drawable, int w, int h) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        // 调用5 中 drawable转换成bitmap
        Bitmap oldbmp = DrawableToBitmap(drawable);

        // 创建操作图片用的Matrix对象
        Matrix matrix = new Matrix();
        // 计算缩放比例
        float sx = ((float) w / width);
        float sy = ((float) h / height);
        // 设置缩放比例
        matrix.postScale(sx, sy);
        // 建立新的bitmap，其内容是对原bitmap的缩放后的图
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
                matrix, true);
        return new BitmapDrawable(newbmp);
    }
}
