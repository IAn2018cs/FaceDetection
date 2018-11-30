package com.jdjr.courtcanteen.utils;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import android.widget.ImageView;

import com.jdjr.courtcanteen.MyApplication;

import java.lang.ref.SoftReference;


/**
 * Description:
 * Author:chenshuai
 * E-mail:chenshuai@amberweather.com
 * Date:2018/11/30
 */
public class AnimationsContainer {
    private static AnimationsContainer mInstance;
    public int FPS = 58;
    private Context mContext = MyApplication.getAppContext();
    private int resId = 0;

    private int[] getData(int paramInt) {
        TypedArray localTypedArray = this.mContext.getResources().obtainTypedArray(paramInt);
        int i = localTypedArray.length();
        int[] arrayOfInt = new int[localTypedArray.length()];
        paramInt = 0;
        while (paramInt < i) {
            arrayOfInt[paramInt] = localTypedArray.getResourceId(paramInt, 0);
            paramInt += 1;
        }
        localTypedArray.recycle();
        return arrayOfInt;
    }

    public static AnimationsContainer getInstance(int paramInt1, int paramInt2) {
        if (mInstance == null)
            mInstance = new AnimationsContainer();
        mInstance.setResId(paramInt1, paramInt2);
        return mInstance;
    }

    public FramesSequenceAnimation createProgressDialogAnim(ImageView paramImageView) {
        return new FramesSequenceAnimation(paramImageView, getData(this.resId), this.FPS);
    }

    public void setResId(int paramInt1, int paramInt2) {
        this.resId = paramInt1;
        this.FPS = paramInt2;
    }

    public class FramesSequenceAnimation {
        private Bitmap mBitmap = null;
        private BitmapFactory.Options mBitmapOptions;
        private int mDelayMillis;
        private int[] mFrames;
        private Handler mHandler = new Handler();
        private int mIndex;
        private boolean mIsRunning;
        private AnimationsContainer.OnAnimationStoppedListener mOnAnimationStoppedListener;
        private boolean mShouldRun;
        private SoftReference<ImageView> mSoftReferenceImageView;

        public FramesSequenceAnimation(ImageView paramArrayOfInt, int[] paramInt, int arg4) {
            this.mFrames = paramInt;
            this.mIndex = -1;
            this.mSoftReferenceImageView = new SoftReference(paramArrayOfInt);
            this.mShouldRun = false;
            this.mIsRunning = false;
            int i;
            this.mDelayMillis = (1000 / i);
            paramArrayOfInt.setImageResource(this.mFrames[0]);
            if (Build.VERSION.SDK_INT >= 11) {
                this$1 = ((BitmapDrawable) paramArrayOfInt.getDrawable()).getBitmap();
                this.mBitmap = Bitmap.createBitmap(AnimationsContainer.this.getWidth(), AnimationsContainer.this.getHeight(), AnimationsContainer.this.getConfig());
                this.mBitmapOptions = new BitmapFactory.Options();
                this.mBitmapOptions.inBitmap = this.mBitmap;
                this.mBitmapOptions.inMutable = true;
                this.mBitmapOptions.inSampleSize = 1;
            }
        }

        private int getNext() {
            this.mIndex += 1;
            if (this.mIndex >= this.mFrames.length)
                this.mIndex = 0;
            return this.mFrames[this.mIndex];
        }

        public void setOnAnimStopListener(AnimationsContainer.OnAnimationStoppedListener paramOnAnimationStoppedListener) {
            this.mOnAnimationStoppedListener = paramOnAnimationStoppedListener;
        }

        public void start() {
            monitorenter;
            try {
                this.mShouldRun = true;
                boolean bool = this.mIsRunning;
                if (bool)
                    return;
                1 local1 = new Runnable() {
                    public void run() {
                        ImageView localImageView = (ImageView) AnimationsContainer.FramesSequenceAnimation.this.mSoftReferenceImageView.get();
                        if ((AnimationsContainer.FramesSequenceAnimation.this.mShouldRun) && (localImageView != null)) {
                            AnimationsContainer.FramesSequenceAnimation.access$202(AnimationsContainer.FramesSequenceAnimation.this, true);
                            AnimationsContainer.FramesSequenceAnimation.this.mHandler.postDelayed(this, AnimationsContainer.FramesSequenceAnimation.this.mDelayMillis);
                            if (localImageView.isShown()) {
                                int i = AnimationsContainer.FramesSequenceAnimation.this.getNext();
                                if (AnimationsContainer.FramesSequenceAnimation.this.mBitmap != null) {
                                    Object localObject = null;
                                    try {
                                        Bitmap localBitmap = BitmapFactory.decodeResource(localImageView.getResources(), i, AnimationsContainer.FramesSequenceAnimation.this.mBitmapOptions);
                                        localObject = localBitmap;
                                    } catch (Exception localException) {
                                        localException.printStackTrace();
                                    }
                                    if (localObject != null) {
                                        localImageView.setImageBitmap(localObject);
                                    } else {
                                        localImageView.setImageResource(i);
                                        AnimationsContainer.FramesSequenceAnimation.this.mBitmap.recycle();
                                        AnimationsContainer.FramesSequenceAnimation.access$702(AnimationsContainer.FramesSequenceAnimation.this, null);
                                    }
                                    return;
                                }
                                localImageView.setImageResource(i);
                            }
                            return;
                        }
                        AnimationsContainer.FramesSequenceAnimation.access$202(AnimationsContainer.FramesSequenceAnimation.this, false);
                        if (AnimationsContainer.FramesSequenceAnimation.this.mOnAnimationStoppedListener != null)
                            AnimationsContainer.FramesSequenceAnimation.this.mOnAnimationStoppedListener.AnimationStopped();
                    }
                };
                this.mHandler.post(local1);
                return;
            } finally {
                monitorexit;
            }
            throw localObject;
        }

        public void stop() {
            monitorenter;
            try {
                this.mShouldRun = false;
                monitorexit;
                return;
            } finally {
                localObject = finally;
                monitorexit;
            }
            throw localObject;
        }
    }

    public static abstract interface OnAnimationStoppedListener {
        public abstract void AnimationStopped();
    }
}
