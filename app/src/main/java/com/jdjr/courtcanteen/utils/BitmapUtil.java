package com.jdjr.courtcanteen.utils;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.os.Environment;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;

import com.jdjr.courtcanteen.MyApplication;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Description:
 * Author:chenshuai
 * E-mail:chenshuai@amberweather.com
 * Date:2018/11/30
 */
public class BitmapUtil {
    public static byte[] Bitmap2Bytes(Bitmap paramBitmap, Bitmap.CompressFormat paramCompressFormat, int paramInt) {
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
        paramBitmap.compress(paramCompressFormat, paramInt, localByteArrayOutputStream);
        return localByteArrayOutputStream.toByteArray();
    }

    // ERROR //
    public static String bitmapToBase64(Bitmap paramBitmap) {
        // Byte code:
        //   0: aconst_null
        //   1: astore 5
        //   3: aconst_null
        //   4: astore_1
        //   5: aconst_null
        //   6: astore 4
        //   8: aconst_null
        //   9: astore_2
        //   10: aconst_null
        //   11: astore_3
        //   12: aconst_null
        //   13: astore 6
        //   15: aload_0
        //   16: ifnull +132 -> 148
        //   19: aload 6
        //   21: astore_1
        //   22: new 13	java/io/ByteArrayOutputStream
        //   25: dup
        //   26: invokespecial 14	java/io/ByteArrayOutputStream:<init>	()V
        //   29: astore_3
        //   30: aload_3
        //   31: astore_1
        //   32: aload_3
        //   33: astore_2
        //   34: aload_0
        //   35: getstatic 34	android/graphics/Bitmap$CompressFormat:JPEG	Landroid/graphics/Bitmap$CompressFormat;
        //   38: bipush 100
        //   40: aload_3
        //   41: invokevirtual 20	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
        //   44: pop
        //   45: aload_3
        //   46: astore_1
        //   47: aload_3
        //   48: astore_2
        //   49: aload_3
        //   50: invokevirtual 37	java/io/ByteArrayOutputStream:flush	()V
        //   53: aload_3
        //   54: astore_1
        //   55: aload_3
        //   56: astore_2
        //   57: aload_3
        //   58: invokevirtual 40	java/io/ByteArrayOutputStream:close	()V
        //   61: aload_3
        //   62: astore_1
        //   63: aload_3
        //   64: astore_2
        //   65: aload_3
        //   66: invokevirtual 24	java/io/ByteArrayOutputStream:toByteArray	()[B
        //   69: iconst_0
        //   70: invokestatic 46	android/util/Base64:encodeToString	([BI)Ljava/lang/String;
        //   73: astore_0
        //   74: aload_0
        //   75: astore_1
        //   76: goto +72 -> 148
        //   79: astore_0
        //   80: goto +46 -> 126
        //   83: astore_0
        //   84: aload_2
        //   85: astore_1
        //   86: aload_0
        //   87: invokevirtual 49	java/io/IOException:printStackTrace	()V
        //   90: aload 5
        //   92: astore_0
        //   93: aload_2
        //   94: ifnull +30 -> 124
        //   97: aload 4
        //   99: astore_0
        //   100: aload_2
        //   101: invokevirtual 37	java/io/ByteArrayOutputStream:flush	()V
        //   104: aload 4
        //   106: astore_0
        //   107: aload_2
        //   108: invokevirtual 40	java/io/ByteArrayOutputStream:close	()V
        //   111: aload 5
        //   113: astore_0
        //   114: goto +10 -> 124
        //   117: astore_1
        //   118: aload_1
        //   119: invokevirtual 49	java/io/IOException:printStackTrace	()V
        //   122: aload_0
        //   123: areturn
        //   124: aload_0
        //   125: areturn
        //   126: aload_1
        //   127: ifnull +19 -> 146
        //   130: aload_1
        //   131: invokevirtual 37	java/io/ByteArrayOutputStream:flush	()V
        //   134: aload_1
        //   135: invokevirtual 40	java/io/ByteArrayOutputStream:close	()V
        //   138: goto +8 -> 146
        //   141: astore_1
        //   142: aload_1
        //   143: invokevirtual 49	java/io/IOException:printStackTrace	()V
        //   146: aload_0
        //   147: athrow
        //   148: aload_1
        //   149: astore_0
        //   150: aload_3
        //   151: ifnull -27 -> 124
        //   154: aload_1
        //   155: astore_0
        //   156: aload_3
        //   157: invokevirtual 37	java/io/ByteArrayOutputStream:flush	()V
        //   160: aload_1
        //   161: astore_0
        //   162: aload_3
        //   163: invokevirtual 40	java/io/ByteArrayOutputStream:close	()V
        //   166: aload_1
        //   167: areturn
        //
        // Exception table:
        //   from	to	target	type
        //   22	30	79	finally
        //   34	45	79	finally
        //   49	53	79	finally
        //   57	61	79	finally
        //   65	74	79	finally
        //   86	90	79	finally
        //   22	30	83	java/io/IOException
        //   34	45	83	java/io/IOException
        //   49	53	83	java/io/IOException
        //   57	61	83	java/io/IOException
        //   65	74	83	java/io/IOException
        //   100	104	117	java/io/IOException
        //   107	111	117	java/io/IOException
        //   156	160	117	java/io/IOException
        //   162	166	117	java/io/IOException
        //   130	138	141	java/io/IOException
    }

    public static Bitmap byte2Bitmap(byte[] paramArrayOfByte) {
        Object localObject = MyApplication.getAppContext().getResources().getDisplayMetrics();
        new BitmapFactory.Options().inJustDecodeBounds = true;
        paramArrayOfByte = new YuvImage(paramArrayOfByte, 17, ((DisplayMetrics) localObject).widthPixels, ((DisplayMetrics) localObject).heightPixels, null);
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
        paramArrayOfByte.compressToJpeg(new Rect(0, 0, ((DisplayMetrics) localObject).widthPixels, ((DisplayMetrics) localObject).heightPixels), 80, localByteArrayOutputStream);
        paramArrayOfByte = localByteArrayOutputStream.toByteArray();
        localObject = new BitmapFactory.Options();
        ((BitmapFactory.Options) localObject).inPreferredConfig = Bitmap.Config.RGB_565;
        return (Bitmap) BitmapFactory.decodeByteArray(paramArrayOfByte, 0, paramArrayOfByte.length, (BitmapFactory.Options) localObject);
    }

    public static Bitmap cropBitmap(Bitmap paramBitmap, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("bitmap with =");
        localStringBuilder.append(paramBitmap.getWidth());
        Log.e("cropBitmap", localStringBuilder.toString());
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("bitmap  Height=");
        localStringBuilder.append(paramBitmap.getHeight());
        Log.e("cropBitmap", localStringBuilder.toString());
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("x =");
        localStringBuilder.append(paramFloat1);
        Log.e("cropBitmap", localStringBuilder.toString());
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("y =");
        localStringBuilder.append(paramFloat2);
        Log.e("cropBitmap", localStringBuilder.toString());
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("cropWidth =");
        localStringBuilder.append(paramFloat3);
        Log.e("cropBitmap", localStringBuilder.toString());
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("cropHeight =");
        localStringBuilder.append(paramFloat4);
        Log.e("cropBitmap", localStringBuilder.toString());
        return Bitmap.createBitmap(paramBitmap, (int) paramFloat1, (int) paramFloat2, (int) paramFloat3, (int) paramFloat4, null, false);
    }

    public static void deleteFaceImage() {
        Object localObject = new StringBuilder();
        ((StringBuilder) localObject).append(Environment.getExternalStorageDirectory().getAbsolutePath());
        ((StringBuilder) localObject).append("/courtCanteenFaceImage");
        localObject = new File(((StringBuilder) localObject).toString());
        if (((File) localObject).isFile()) {
            ((File) localObject).delete();
            return;
        }
        if (((File) localObject).isDirectory()) {
            File[] arrayOfFile = ((File) localObject).listFiles();
            if ((arrayOfFile != null) && (arrayOfFile.length != 0)) {
                int i = 0;
                while (i < arrayOfFile.length) {
                    arrayOfFile[i].delete();
                    i += 1;
                }
                ((File) localObject).delete();
                return;
            }
            ((File) localObject).delete();
            return;
        }
    }

    public static Bitmap getViewBitmap(View paramView) {
        paramView.setDrawingCacheEnabled(true);
        paramView.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        paramView.layout(0, 0, paramView.getMeasuredWidth(), paramView.getMeasuredHeight());
        paramView.buildDrawingCache();
        return Bitmap.createBitmap(paramView.getDrawingCache());
    }

    public static Bitmap rotateBitmap(Bitmap paramBitmap, float paramFloat) {
        if (paramBitmap == null)
            return null;
        int i = paramBitmap.getWidth();
        int j = paramBitmap.getHeight();
        Object localObject = new Matrix();
        ((Matrix) localObject).setRotate(paramFloat);
        localObject = Bitmap.createBitmap(paramBitmap, 0, 0, i, j, (Matrix) localObject, false);
        if (localObject.equals(paramBitmap))
            return localObject;
        paramBitmap.recycle();
        return (Bitmap) localObject;
    }

    public static Bitmap rotateToDegrees(Bitmap paramBitmap, float paramFloat) {
        Matrix localMatrix = new Matrix();
        localMatrix.reset();
        localMatrix.setRotate(paramFloat);
        localMatrix.postScale(-1.0F, 1.0F);
        return Bitmap.createBitmap(paramBitmap, 0, 0, paramBitmap.getWidth(), paramBitmap.getHeight(), localMatrix, true);
    }

    // ERROR //
    public static String saveFace(Bitmap paramBitmap) {
        // Byte code:
        //   0: new 118	java/lang/StringBuilder
        //   3: dup
        //   4: invokespecial 119	java/lang/StringBuilder:<init>	()V
        //   7: astore_1
        //   8: aload_1
        //   9: invokestatic 170	android/os/Environment:getExternalStorageDirectory	()Ljava/io/File;
        //   12: invokevirtual 175	java/io/File:getAbsolutePath	()Ljava/lang/String;
        //   15: invokevirtual 125	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   18: pop
        //   19: aload_1
        //   20: ldc 177
        //   22: invokevirtual 125	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   25: pop
        //   26: new 172	java/io/File
        //   29: dup
        //   30: aload_1
        //   31: invokevirtual 137	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   34: invokespecial 180	java/io/File:<init>	(Ljava/lang/String;)V
        //   37: astore_1
        //   38: aload_1
        //   39: invokevirtual 190	java/io/File:isDirectory	()Z
        //   42: ifne +16 -> 58
        //   45: aload_1
        //   46: invokevirtual 262	java/io/File:mkdir	()Z
        //   49: pop
        //   50: goto +8 -> 58
        //   53: astore_2
        //   54: aload_2
        //   55: invokevirtual 263	java/lang/Exception:printStackTrace	()V
        //   58: new 118	java/lang/StringBuilder
        //   61: dup
        //   62: invokespecial 119	java/lang/StringBuilder:<init>	()V
        //   65: astore_2
        //   66: aload_2
        //   67: aload_1
        //   68: invokevirtual 266	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   71: pop
        //   72: aload_2
        //   73: ldc_w 268
        //   76: invokevirtual 125	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   79: pop
        //   80: aload_2
        //   81: invokestatic 273	com/jdjr/courtcanteen/utils/TimeUtil:getDayString	()Ljava/lang/String;
        //   84: invokevirtual 125	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   87: pop
        //   88: aload_2
        //   89: ldc_w 275
        //   92: invokevirtual 125	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   95: pop
        //   96: aload_2
        //   97: invokevirtual 137	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   100: astore 4
        //   102: aconst_null
        //   103: astore_2
        //   104: aconst_null
        //   105: astore_1
        //   106: new 277	java/io/FileOutputStream
        //   109: dup
        //   110: aload 4
        //   112: invokespecial 278	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
        //   115: astore_3
        //   116: aload_3
        //   117: ifnull +34 -> 151
        //   120: aload_3
        //   121: astore_1
        //   122: aload_3
        //   123: astore_2
        //   124: aload_0
        //   125: getstatic 281	android/graphics/Bitmap$CompressFormat:PNG	Landroid/graphics/Bitmap$CompressFormat;
        //   128: bipush 80
        //   130: aload_3
        //   131: invokevirtual 20	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
        //   134: pop
        //   135: aload_3
        //   136: astore_1
        //   137: aload_3
        //   138: astore_2
        //   139: aload_3
        //   140: invokevirtual 282	java/io/FileOutputStream:flush	()V
        //   143: aload_3
        //   144: astore_1
        //   145: aload_3
        //   146: astore_2
        //   147: aload_3
        //   148: invokevirtual 283	java/io/FileOutputStream:close	()V
        //   151: aload_3
        //   152: ifnull +60 -> 212
        //   155: aload_3
        //   156: invokevirtual 282	java/io/FileOutputStream:flush	()V
        //   159: aload_3
        //   160: invokevirtual 283	java/io/FileOutputStream:close	()V
        //   163: goto +14 -> 177
        //   166: astore_0
        //   167: ldc_w 284
        //   170: ldc_w 286
        //   173: invokestatic 289	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
        //   176: pop
        //   177: aload 4
        //   179: areturn
        //   180: astore_0
        //   181: goto +34 -> 215
        //   184: astore_0
        //   185: aload_2
        //   186: astore_1
        //   187: ldc_w 284
        //   190: ldc_w 291
        //   193: invokestatic 289	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
        //   196: pop
        //   197: aload_2
        //   198: ifnull +14 -> 212
        //   201: aload_2
        //   202: invokevirtual 282	java/io/FileOutputStream:flush	()V
        //   205: aload_2
        //   206: invokevirtual 283	java/io/FileOutputStream:close	()V
        //   209: aload 4
        //   211: areturn
        //   212: aload 4
        //   214: areturn
        //   215: aload_1
        //   216: ifnull +25 -> 241
        //   219: aload_1
        //   220: invokevirtual 282	java/io/FileOutputStream:flush	()V
        //   223: aload_1
        //   224: invokevirtual 283	java/io/FileOutputStream:close	()V
        //   227: goto +14 -> 241
        //   230: astore_1
        //   231: ldc_w 284
        //   234: ldc_w 286
        //   237: invokestatic 289	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
        //   240: pop
        //   241: aload_0
        //   242: athrow
        //
        // Exception table:
        //   from	to	target	type
        //   45	50	53	java/lang/Exception
        //   155	163	166	java/io/IOException
        //   201	209	166	java/io/IOException
        //   106	116	180	finally
        //   124	135	180	finally
        //   139	143	180	finally
        //   147	151	180	finally
        //   187	197	180	finally
        //   106	116	184	java/lang/Exception
        //   124	135	184	java/lang/Exception
        //   139	143	184	java/lang/Exception
        //   147	151	184	java/lang/Exception
        //   219	227	230	java/io/IOException
    }

    public static Bitmap scaleBitmap(Bitmap paramBitmap, float paramFloat) {
        if (paramBitmap == null)
            return null;
        int i = paramBitmap.getWidth();
        int j = paramBitmap.getHeight();
        Object localObject = new Matrix();
        ((Matrix) localObject).preScale(paramFloat, paramFloat);
        localObject = Bitmap.createBitmap(paramBitmap, 0, 0, i, j, (Matrix) localObject, false);
        if (localObject.equals(paramBitmap))
            return localObject;
        paramBitmap.recycle();
        return (Bitmap) localObject;
    }

    public static Bitmap scaleBitmap(Bitmap paramBitmap, int paramInt1, int paramInt2) {
        if (paramBitmap == null)
            return null;
        int i = paramBitmap.getHeight();
        int j = paramBitmap.getWidth();
        float f1 = paramInt1 / j;
        float f2 = paramInt2 / i;
        Object localObject = new Matrix();
        ((Matrix) localObject).postScale(f1, f2);
        localObject = Bitmap.createBitmap(paramBitmap, 0, 0, j, i, (Matrix) localObject, true);
        if (!paramBitmap.isRecycled())
            paramBitmap.recycle();
        return (Bitmap) localObject;
    }

    public static Bitmap skewBitmap(Bitmap paramBitmap) {
        if (paramBitmap == null)
            return null;
        int i = paramBitmap.getWidth();
        int j = paramBitmap.getHeight();
        Object localObject = new Matrix();
        ((Matrix) localObject).postSkew(-0.6F, -0.3F);
        localObject = Bitmap.createBitmap(paramBitmap, 0, 0, i, j, (Matrix) localObject, false);
        if (localObject.equals(paramBitmap))
            return localObject;
        paramBitmap.recycle();
        return (Bitmap) localObject;
    }

    public static Bitmap stringToBitmap(String paramString) {
        paramString = Base64.decode(paramString, 0);
        return BitmapFactory.decodeByteArray(paramString, 0, paramString.length);
    }
}
