package com.jdjr.courtcanteen.view;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.view.WindowManager;

import com.jd.zteam.lib.det.yuv.ImageYuv;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Description:
 * Author:chenshuai
 * E-mail:chenshuai@amberweather.com
 * Date:2018/11/30
 */
public class CameraTextureView extends TextureView
        implements TextureView.SurfaceTextureListener {
    public static Camera.Size pictureSize;
    private String TAG = CameraTextureView.class.getSimpleName();
    private boolean isSupportAutoFocus;
    private Camera mCamera;
    private Context mContext;
    private OnPreviewCallbackLister onPreviewCallbackLister;
    private Camera.Size previewSize;
    private int screenHeight;
    private int screenWidth;

    public CameraTextureView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        init(paramContext);
        setSurfaceTextureListener(this);
    }

    private float calcPreviewPercent() {
        return this.screenHeight / this.screenWidth;
    }

    private Camera.Size findSizeFromList(List<Camera.Size> paramList, Camera.Size paramSize) {
        if ((paramList != null) && (!paramList.isEmpty())) {
            paramList = paramList.iterator();
            while (paramList.hasNext()) {
                Camera.Size localSize = (Camera.Size) paramList.next();
                if ((paramSize.width == localSize.width) && (paramSize.height == localSize.height))
                    return localSize;
            }
        }
        return null;
    }

    private Camera.Size getPictureMaxSize(List<Camera.Size> paramList, Camera.Size paramSize) {
        Object localObject2 = null;
        int i = 0;
        while (i < paramList.size()) {
            Object localObject1 = localObject2;
            if (((Camera.Size) paramList.get(i)).width >= paramSize.width) {
                localObject1 = localObject2;
                if (((Camera.Size) paramList.get(i)).height >= paramSize.width) {
                    localObject1 = localObject2;
                    if (((Camera.Size) paramList.get(i)).height != ((Camera.Size) paramList.get(i)).width)
                        if (localObject2 == null) {
                            localObject1 = (Camera.Size) paramList.get(i);
                        } else {
                            localObject1 = localObject2;
                            if (localObject2.height * localObject2.width > ((Camera.Size) paramList.get(i)).width * ((Camera.Size) paramList.get(i)).height)
                                localObject1 = (Camera.Size) paramList.get(i);
                        }
                }
            }
            i += 1;
            localObject2 = localObject1;
        }
        return (Camera.Size) localObject2;
    }

    private Camera.Size getPreviewMaxSize(List<Camera.Size> paramList, float paramFloat) {
        int i1 = 0;
        int m = 0;
        float f2 = 100.0F;
        int i = 0;
        while (i < paramList.size()) {
            int n = ((Camera.Size) paramList.get(i)).width;
            int j = ((Camera.Size) paramList.get(i)).height;
            int k;
            float f1;
            if (n * j < this.screenHeight * this.screenWidth) {
                j = i1;
                k = m;
                f1 = f2;
            } else {
                float f3 = Math.abs(n / j - paramFloat);
                if (f3 < f2) {
                    j = i;
                    f1 = f3;
                    k = n;
                } else {
                    j = i1;
                    k = m;
                    f1 = f2;
                    if (f3 == f2) {
                        j = i1;
                        k = m;
                        f1 = f2;
                        if (n > m) {
                            j = i;
                            f1 = f3;
                            k = n;
                        }
                    }
                }
            }
            i += 1;
            i1 = j;
            m = k;
            f2 = f1;
        }
        return (Camera.Size) paramList.get(i1);
    }

    private void init(Context paramContext) {
        this.mContext = paramContext;
        DisplayMetrics localDisplayMetrics = getResources().getDisplayMetrics();
        this.screenWidth = localDisplayMetrics.widthPixels;
        this.screenHeight = localDisplayMetrics.heightPixels;
        this.isSupportAutoFocus = paramContext.getPackageManager().hasSystemFeature("android.hardware.camera.autofocus");
    }

    private void setCameraDisplayOrientation(Activity paramActivity) {
        Camera.CameraInfo localCameraInfo = new Camera.CameraInfo();
        boolean bool = false;
        Camera.getCameraInfo(0, localCameraInfo);
        int j = paramActivity.getWindowManager().getDefaultDisplay().getRotation();
        int i = 0;
        switch (j) {
            default:
                break;
            case 3:
                i = 270;
                break;
            case 2:
                i = 180;
                break;
            case 1:
                i = 90;
                break;
            case 0:
                i = 0;
        }
        if (localCameraInfo.facing == 1)
            i = (360 - (localCameraInfo.orientation + i) % 360) % 360;
        else
            i = (localCameraInfo.orientation - i + 360) % 360;
        this.mCamera.setDisplayOrientation(i);
        if (localCameraInfo.facing == 1)
            i = 360 - i;
        if (i == 270)
            bool = true;
        ImageYuv.initImageYuv(1280, 720, 0.5F, i, bool);
    }

    private void setCameraParms() {
        Camera.Parameters localParameters = this.mCamera.getParameters();
        List localList = localParameters.getSupportedFlashModes();
        String str = localParameters.getFlashMode();
        if (localList == null)
            return;
        if ((!"off".equals(str)) && (localList.contains("off")))
            localParameters.setFlashMode("off");
        float f = calcPreviewPercent();
        this.previewSize = getPreviewMaxSize(localParameters.getSupportedPreviewSizes(), f);
        localList = localParameters.getSupportedPictureSizes();
        pictureSize = findSizeFromList(localList, this.previewSize);
        if (pictureSize == null)
            pictureSize = getPictureMaxSize(localList, this.previewSize);
        localParameters.setPictureSize(pictureSize.width, pictureSize.height);
        localParameters.setPreviewSize(this.previewSize.width, this.previewSize.height);
        localParameters.setJpegQuality(70);
        this.mCamera.setParameters(localParameters);
    }

    public void onSurfaceTextureAvailable(SurfaceTexture paramSurfaceTexture, int paramInt1, int paramInt2) {
        try {
            this.mCamera = Camera.open(0);
            if (this.mCamera == null)
                return;
            setCameraDisplayOrientation((Activity) this.mContext);
            this.mCamera.setPreviewTexture(paramSurfaceTexture);
            setCameraParms();
            this.mCamera.setPreviewCallback(new Camera.PreviewCallback() {
                public void onPreviewFrame(byte[] paramArrayOfByte, Camera paramCamera) {
                    if (CameraTextureView.this.onPreviewCallbackLister != null)
                        CameraTextureView.this.onPreviewCallbackLister.onPreviewFrame(paramArrayOfByte);
                }
            });
            this.mCamera.startPreview();
            this.mCamera.cancelAutoFocus();
            requestLayout();
            setMirrorView((Activity) this.mContext);
            return;
        } catch (IOException paramSurfaceTexture) {
            paramSurfaceTexture.printStackTrace();
        }
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture paramSurfaceTexture) {
        if (this.mCamera != null) {
            this.mCamera.setPreviewCallback(null);
            this.mCamera.stopPreview();
            this.mCamera.release();
            this.mCamera = null;
        }
        return true;
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture paramSurfaceTexture, int paramInt1, int paramInt2) {
    }

    public void onSurfaceTextureUpdated(SurfaceTexture paramSurfaceTexture) {
    }

    public void setMirrorView(Activity paramActivity) {
        Camera.CameraInfo localCameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(0, localCameraInfo);
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        paramActivity.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        int i = localDisplayMetrics.widthPixels;
        if (localCameraInfo.facing != 1) {
            paramActivity = new Matrix();
            paramActivity.setScale(-1.0F, 1.0F, i / 2, 0.0F);
            setTransform(paramActivity);
        }
    }

    public void setOnPreviewCallbackLister(OnPreviewCallbackLister paramOnPreviewCallbackLister) {
        this.onPreviewCallbackLister = paramOnPreviewCallbackLister;
    }

    public static abstract interface OnPreviewCallbackLister {
        public abstract void onPreviewFrame(byte[] paramArrayOfByte);
    }
}
