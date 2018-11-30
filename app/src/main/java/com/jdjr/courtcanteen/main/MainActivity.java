package com.jdjr.courtcanteen.main;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.percent.PercentRelativeLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jd.zteam.lib.det.entity.ByteData;
import com.jd.zteam.lib.det.mtcnn.MTCNNDet;
import com.jd.zteam.lib.det.mtcnn.MTNCNNDetRet;
import com.jdjr.courtcanteen.base.BaseActivity;
import com.jdjr.courtcanteen.db.FaceDatabase;
import com.jdjr.courtcanteen.utils.AnimationsContainer;
import com.jdjr.courtcanteen.utils.AnimationsContainer.FramesSequenceAnimation;
import com.jdjr.courtcanteen.utils.BitmapUtil;
import com.jdjr.courtcanteen.utils.NetworkUtil;
import com.jdjr.courtcanteen.utils.SpUtil;
import com.jdjr.courtcanteen.utils.TimeUtil;
import com.jdjr.courtcanteen.view.CameraTextureView;
import com.jdjr.courtcanteen.view.CameraTextureView.OnPreviewCallbackLister;

import java.io.File;

/**
 * Description:
 * Author:chenshuai
 * E-mail:chenshuai@amberweather.com
 * Date:2018/11/30
 */
public class MainActivity extends BaseActivity<MainPresenter>
        implements MainContract.IMainView, CameraTextureView.OnPreviewCallbackLister {
    public static volatile boolean isFirstIn = true;
    public static volatile boolean lostFace;
    private static long mRequestStartTime;
    private static long mStartTime = 0L;
    private Animation addNumberAnimation;
    private AnimationsContainer.FramesSequenceAnimation animationScanning;
    private CameraTextureView camerapreview;
    private ScaleAnimation faceSuccessAnimation;
    private PercentRelativeLayout interface_error_bg;
    private volatile boolean isInterfaceError = false;
    private volatile boolean isNoNetwork = false;
    private boolean isTouchIn = false;
    private ImageView iv_check_face;
    private ImageView iv_middle_icon;
    private ImageView iv_scan_success;
    private ImageView iv_scan_success_bg;
    private ImageView iv_scanning;
    private LinearLayout ll_check_card;
    private FaceDatabase mDatabase;
    private int mEatNumber;
    private long mEndTouchTime = 0L;
    private MediaPlayer mFaceMediaPlayer;
    private volatile Rect mFaceRect;
    private Handler mHandler = new Handler() {
        private long endShowScanSuccessStartTime;
        private long endShowSuccessTime;
        private long startShowScanSuccessTime;
        private long startShowSuccessTime;

        public void handleMessage(Message paramMessage) {
            super.handleMessage(paramMessage);
            StringBuilder localStringBuilder;
            switch (paramMessage.what) {
                case 5:
                default:
                    return;
                case 11:
                    MainActivity.access$402(MainActivity.this, MainActivity.this.mDatabase.getEatNumber());
                    paramMessage = MainActivity.this.tv_eat_num;
                    localStringBuilder = new StringBuilder();
                    localStringBuilder.append(MainActivity.this.mEatNumber);
                    localStringBuilder.append("");
                    paramMessage.setText(localStringBuilder.toString());
                    return;
                case 10:
                    if ((MainActivity.this.mFaceMediaPlayer != null) && (MainActivity.this.mFaceMediaPlayer.isPlaying())) {
                        MainActivity.this.mFaceMediaPlayer.stop();
                        MainActivity.this.mFaceMediaPlayer.release();
                        MainActivity.access$902(MainActivity.this, null);
                    }
                    MainActivity.this.dismissScanSuccessNow();
                    MainActivity.this.dismissCardNow();
                    MainActivity.this.clearFaceRect();
                    MainActivity.this.iv_middle_icon.setImageResource(2131165289);
                    MainActivity.this.pl_bg.setClickable(false);
                    MainActivity.this.tv_touch.setVisibility(4);
                    MainActivity.this.pl_bg.setVisibility(0);
                    return;
                case 9:
                    if ((MainActivity.this.mFaceMediaPlayer != null) && (MainActivity.this.mFaceMediaPlayer.isPlaying())) {
                        MainActivity.this.mFaceMediaPlayer.stop();
                        MainActivity.this.mFaceMediaPlayer.release();
                        MainActivity.access$902(MainActivity.this, null);
                    }
                    MainActivity.this.dismissScanSuccessNow();
                    MainActivity.this.dismissCardNow();
                    MainActivity.this.clearFaceRect();
                    MainActivity.this.iv_middle_icon.setImageResource(2131165287);
                    MainActivity.this.pl_bg.setClickable(true);
                    MainActivity.this.tv_touch.setVisibility(0);
                    MainActivity.this.pl_bg.setVisibility(0);
                    return;
                case 8:
                    MainActivity.this.camerapreview.setAlpha(0.0F);
                    MainActivity.this.rl_before.setVisibility(4);
                    return;
                case 7:
                    MainActivity.access$402(MainActivity.this, MainActivity.this.mEatNumber + 1);
                    paramMessage = MainActivity.this.tv_eat_num;
                    localStringBuilder = new StringBuilder();
                    localStringBuilder.append(MainActivity.this.mEatNumber);
                    localStringBuilder.append("");
                    paramMessage.setText(localStringBuilder.toString());
                    if (MainActivity.this.addNumberAnimation == null)
                        MainActivity.access$602(MainActivity.this, AnimationUtils.loadAnimation(MainActivity.this, 2130771980));
                    MainActivity.this.tv_eat_num.startAnimation(MainActivity.this.addNumberAnimation);
                    return;
                case 6:
                    MainActivity.this.dismissCardNow();
                    MainActivity.this.dismissScanSuccessNow();
                    MainActivity.this.clearFaceRect();
                    return;
                case 4:
                    this.endShowSuccessTime = System.currentTimeMillis();
                    if (this.endShowSuccessTime - this.startShowSuccessTime <= 1000L)
                        break;
                    MainActivity.this.dismissCardNow();
                    return;
                case 3:
                    this.startShowSuccessTime = System.currentTimeMillis();
                    MainActivity.this.clearFaceRect();
                    MainActivity.this.showError(paramMessage.getData(), (Rect) paramMessage.obj);
                    return;
                case 2:
                    this.startShowSuccessTime = System.currentTimeMillis();
                    MainActivity.this.clearFaceRect();
                    MainActivity.this.showSuccess(paramMessage.getData(), (Rect) paramMessage.obj);
                    return;
                case 1:
                    this.endShowScanSuccessStartTime = System.currentTimeMillis();
                    if (this.endShowScanSuccessStartTime - this.startShowScanSuccessTime <= 1000L)
                        break;
                    MainActivity.this.dismissScanSuccessNow();
                    return;
                case 0:
                    this.startShowScanSuccessTime = System.currentTimeMillis();
                    MainActivity.this.clearFaceRect();
                    MainActivity.this.showScanSuccess();
            }
        }
    };
    private MTCNNDet mMtcnnDet;
    private long mStartTouchTime = 0L;
    private PercentRelativeLayout no_network_bg;
    private volatile boolean openFaceCheck = false;
    private PercentRelativeLayout pl_bg;
    private PercentRelativeLayout prl_text_bg;
    private RelativeLayout rl_before;
    private FrameLayout scan_success;
    private TextView tv_all;
    private TextView tv_eat_num;
    private TextView tv_msg;
    private TextView tv_name;
    private TextView tv_touch;

    static {
        mRequestStartTime = 0L;
        lostFace = true;
    }

    private void dismissScanSuccessNow() {
        this.scan_success.setVisibility(4);
    }

    private void initCardTip() {
        this.faceSuccessAnimation = new ScaleAnimation(0.0F, 1.0F, 0.0F, 1.0F, 1, 0.5F, 1, 0.5F);
        this.faceSuccessAnimation.setDuration(100L);
        this.ll_check_card = ((LinearLayout) findViewById(2131230811));
        this.ll_check_card.setVisibility(4);
        this.iv_check_face = ((ImageView) findViewById(2131230795));
        this.tv_name = ((TextView) findViewById(2131230892));
        this.tv_msg = ((TextView) findViewById(2131230891));
        this.prl_text_bg = ((PercentRelativeLayout) findViewById(2131230829));
    }

    private void initScanSuccess() {
        this.scan_success = ((FrameLayout) findViewById(2131230839));
        this.iv_scan_success = ((ImageView) findViewById(2131230802));
        this.iv_scan_success_bg = ((ImageView) findViewById(2131230803));
    }

    private void initZTEAMSDK() {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(Environment.getExternalStorageDirectory().getPath());
        localStringBuilder.append(File.separator);
        localStringBuilder.append("jdjr");
        localStringBuilder.append(File.separator);
        localStringBuilder.append("faceEntrance");
        this.mMtcnnDet = new MTCNNDet(localStringBuilder.toString());
        this.mMtcnnDet.setThreadNumber(1);
    }

    private void liveFaceLost() {
        this.mEndTouchTime = System.currentTimeMillis();
        Log.d("liveFace", "失去人脸");
        if (this.openFaceCheck)
            runOnUiThread(new Runnable() {
                public void run() {
                    MainActivity.this.clearFaceRect();
                    MainActivity.this.dismissCardNow();
                    MainActivity.this.dismissScanSuccessNow();
                }
            });
        int i = TimeUtil.getFormalEatTime();
        int k = 0;
        if (i == 3)
            i = 1;
        else
            i = 0;
        int j;
        if (this.mEndTouchTime - this.mStartTouchTime > 60000L)
            j = 1;
        else
            j = 0;
        if (TimeUtil.getIntervalEatTime() > 60000L)
            k = 1;
        if ((this.isTouchIn) && (i != 0) && (j != 0) && (k != 0)) {
            hiddenCamerapreview();
            stopFaceCheck();
            showTouchDefault();
        }
    }

    private void liveFaceSuccess(Bitmap paramBitmap) {
        mRequestStartTime = System.currentTimeMillis();
        boolean bool;
        if ((mRequestStartTime != 0L) && (Math.abs(MainPresenter.endTime - mRequestStartTime) <= 2000L))
            bool = false;
        else
            bool = true;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("isNoNetwork：");
        localStringBuilder.append(this.isNoNetwork);
        Log.d("request", localStringBuilder.toString());
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("MainPresenter.isRequest：");
        localStringBuilder.append(MainPresenter.isRequest);
        Log.d("request", localStringBuilder.toString());
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("requestTime：");
        localStringBuilder.append(bool);
        Log.d("request", localStringBuilder.toString());
        if ((!this.isNoNetwork) && (!MainPresenter.isRequest) && (bool)) {
            Log.d("liveFace", "请求服务器");
            BitmapUtil.saveFace(paramBitmap);
            ((MainPresenter) getPresenter()).requestFaceClock(BitmapUtil.bitmapToBase64(paramBitmap));
        }
    }

    private void liveFaceSuccessYouTobe(Rect paramRect) {
        this.mStartTouchTime = System.currentTimeMillis();
        Log.d("liveFace", "实时检测");
        if (!this.isInterfaceError)
            runOnUiThread(new Runnable(paramRect) {
                public void run() {
                    MainActivity.this.showCamerapreview();
                    MainActivity.this.dismissCard();
                    MainActivity.this.dismissScanSuccess();
                    MainActivity.this.drawFaceRect(this.val$faceRect.right, this.val$faceRect.top, this.val$faceRect.left - this.val$faceRect.right, this.val$faceRect.bottom - this.val$faceRect.top);
                }
            });
    }

    private void showCardLocation(Rect paramRect) {
        monitorenter;
        try {
            int i = paramRect.bottom;
            int k = paramRect.top;
            int j = Math.abs(paramRect.left - paramRect.right);
            j = paramRect.right - j / 4;
            int m = paramRect.top - this.iv_check_face.getHeight() - (i - k) / 3;
            Object localObject1 = new StringBuilder();
            ((StringBuilder) localObject1).append("限制前  x:");
            ((StringBuilder) localObject1).append(j);
            ((StringBuilder) localObject1).append("y:");
            ((StringBuilder) localObject1).append(m);
            Log.d("CardLocation", ((StringBuilder) localObject1).toString());
            localObject1 = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics((DisplayMetrics) localObject1);
            int n = ((DisplayMetrics) localObject1).widthPixels;
            i = j;
            if (j < 30)
                i = 30;
            j = i;
            if (this.ll_check_card.getWidth() + i > n - 30)
                j = n - 30 - this.ll_check_card.getWidth();
            i = m;
            if (m < 130)
                i = paramRect.bottom + 10;
            if (j <= 30)
                this.ll_check_card.setX(30.0F);
            else
                this.ll_check_card.setX(j);
            this.ll_check_card.setY(i);
            this.ll_check_card.setVisibility(0);
            this.ll_check_card.startAnimation(this.faceSuccessAnimation);
            localObject1 = new StringBuilder();
            ((StringBuilder) localObject1).append("限制后  x:");
            ((StringBuilder) localObject1).append(this.ll_check_card.getX());
            ((StringBuilder) localObject1).append("y:");
            ((StringBuilder) localObject1).append(this.ll_check_card.getY());
            Log.d("CardLocation", ((StringBuilder) localObject1).toString());
            return;
        } finally {
            monitorexit;
        }
        throw localObject2;
    }

    private void showError(Bundle paramBundle, Rect paramRect) {
        String str1 = paramBundle.getString("name");
        String str2 = paramBundle.getString("msg");
        paramBundle = paramBundle.getString("url");
        if (!paramBundle.equals(""))
            Glide.with(this).load(paramBundle).asBitmap().placeholder(2131165278).listener(new RequestListener() {
                public boolean onException(Exception paramException, String paramString, Target<Bitmap> paramTarget, boolean paramBoolean) {
                    MainActivity.this.iv_check_face.setImageResource(2131165278);
                    return false;
                }

                public boolean onResourceReady(Bitmap paramBitmap, String paramString, Target<Bitmap> paramTarget, boolean paramBoolean1, boolean paramBoolean2) {
                    if (paramBitmap != null) {
                        if (paramBitmap.getWidth() > paramBitmap.getHeight()) {
                            paramBitmap = BitmapUtil.rotateBitmap(paramBitmap, -90.0F);
                            MainActivity.this.iv_check_face.setImageBitmap(paramBitmap);
                        } else {
                            MainActivity.this.iv_check_face.setImageBitmap(paramBitmap);
                        }
                    } else
                        MainActivity.this.iv_check_face.setImageResource(2131165278);
                    return false;
                }
            }).into(this.iv_check_face);
        else
            this.iv_check_face.setImageResource(2131165358);
        this.tv_name.setText(str1);
        this.tv_msg.setText(str2);
        this.prl_text_bg.setBackgroundResource(2131165274);
        this.iv_check_face.setBackgroundResource(2131165273);
        showCardLocation(paramRect);
    }

    private void showScanSuccess() {
        Object localObject = this.iv_scan_success.getLayoutParams();
        int i = this.mFaceRect.bottom - this.mFaceRect.top;
        ((ViewGroup.LayoutParams) localObject).height = (i + 60);
        ((ViewGroup.LayoutParams) localObject).width = (i + 60);
        this.iv_scan_success.setLayoutParams((ViewGroup.LayoutParams) localObject);
        localObject = this.iv_scan_success_bg.getLayoutParams();
        ((ViewGroup.LayoutParams) localObject).height = (i + 20);
        ((ViewGroup.LayoutParams) localObject).width = (i + 20);
        this.iv_scan_success_bg.setLayoutParams((ViewGroup.LayoutParams) localObject);
        localObject = new ScaleAnimation(1.0F, 0.75F, 1.0F, 0.8F, 1, 0.5F, 1, 0.5F);
        ((ScaleAnimation) localObject).setDuration(500L);
        ((ScaleAnimation) localObject).setFillAfter(true);
        this.iv_scan_success.startAnimation((Animation) localObject);
        this.scan_success.setX(this.mFaceRect.right - 60);
        this.scan_success.setY(this.mFaceRect.top - 40);
        this.scan_success.setVisibility(0);
    }

    @TargetApi(17)
    private void showSuccess(Bundle paramBundle, Rect paramRect) {
        String str1 = paramBundle.getString("url");
        String str2 = paramBundle.getString("name");
        paramBundle = paramBundle.getString("msg");
        if (!isDestroyed())
            Glide.with(this).load(str1).asBitmap().placeholder(2131165278).listener(new RequestListener() {
                public boolean onException(Exception paramException, String paramString, Target<Bitmap> paramTarget, boolean paramBoolean) {
                    MainActivity.this.iv_check_face.setImageResource(2131165278);
                    return false;
                }

                public boolean onResourceReady(Bitmap paramBitmap, String paramString, Target<Bitmap> paramTarget, boolean paramBoolean1, boolean paramBoolean2) {
                    if (paramBitmap != null) {
                        if (paramBitmap.getWidth() > paramBitmap.getHeight()) {
                            paramBitmap = BitmapUtil.rotateBitmap(paramBitmap, -90.0F);
                            MainActivity.this.iv_check_face.setImageBitmap(paramBitmap);
                        } else {
                            MainActivity.this.iv_check_face.setImageBitmap(paramBitmap);
                        }
                    } else
                        MainActivity.this.iv_check_face.setImageResource(2131165278);
                    return false;
                }
            }).into(this.iv_check_face);
        this.tv_name.setText(str2);
        this.tv_msg.setText(paramBundle);
        this.prl_text_bg.setBackgroundResource(2131165277);
        this.iv_check_face.setBackgroundResource(2131165276);
        showCardLocation(paramRect);
    }

    public void addEatNumber() {
        this.mHandler.sendEmptyMessageDelayed(7, 400L);
    }

    public void clearFaceRect() {
        this.mHandler.post(new Runnable() {
            public void run() {
                if (MainActivity.this.animationScanning != null)
                    MainActivity.this.animationScanning.stop();
                MainActivity.this.iv_scanning.setVisibility(4);
            }
        });
    }

    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    public void dismissCard() {
        this.mHandler.sendEmptyMessage(4);
    }

    public void dismissCardNow() {
        this.ll_check_card.setVisibility(4);
    }

    public void dismissScanSuccess() {
        this.mHandler.sendEmptyMessage(1);
    }

    public void drawFaceRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        this.iv_scanning.setX(paramInt1 - 40);
        this.iv_scanning.setY(paramInt2 + 8);
        ViewGroup.LayoutParams localLayoutParams = this.iv_scanning.getLayoutParams();
        localLayoutParams.width = paramInt3;
        localLayoutParams.height = paramInt4;
        this.iv_scanning.setLayoutParams(localLayoutParams);
        this.iv_scanning.setVisibility(0);
        if (this.animationScanning != null)
            this.animationScanning.start();
    }

    public boolean getInterfaceState() {
        return this.isInterfaceError;
    }

    public boolean getNoNetworkState() {
        return this.isNoNetwork;
    }

    public void hiddenCamerapreview() {
        this.mHandler.sendEmptyMessage(8);
    }

    public void hiddenInterfaceError() {
        this.isInterfaceError = false;
        this.interface_error_bg.setVisibility(4);
        if (!this.openFaceCheck) {
            restartFaceCheck();
            showCamerapreview();
        }
    }

    public void hiddenNoNetwork() {
        this.isNoNetwork = false;
        this.no_network_bg.setVisibility(4);
        if (!this.openFaceCheck)
            restartFaceCheck();
    }

    protected void hideBottomUIMenu() {
        if ((Build.VERSION.SDK_INT > 11) && (Build.VERSION.SDK_INT < 19)) {
            getWindow().getDecorView().setSystemUiVisibility(8);
            return;
        }
        if (Build.VERSION.SDK_INT >= 19)
            getWindow().getDecorView().setSystemUiVisibility(4102);
    }

    protected void initData() {
        this.mDatabase = FaceDatabase.getInstance();
        initZTEAMSDK();
        ((MainPresenter) getPresenter()).setLaunchAlarm(this);
        ((MainPresenter) getPresenter()).checkNetwork(this);
        ((MainPresenter) getPresenter()).connectSocket();
        ((MainPresenter) getPresenter()).checkServiceLive();
    }

    protected void initView() {
        getWindow().addFlags(128);
        hideBottomUIMenu();
        this.pl_bg = ((PercentRelativeLayout) findViewById(2131230828));
        this.iv_middle_icon = ((ImageView) findViewById(2131230801));
        this.tv_touch = ((TextView) findViewById(2131230898));
        this.pl_bg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                MainActivity.this.updateEatNumber();
                MainActivity.this.showCamerapreview();
                MainActivity.this.restartFaceCheck();
                MainActivity.access$1402(MainActivity.this, true);
                MainActivity.access$1502(MainActivity.this, System.currentTimeMillis());
            }
        });
        this.camerapreview = ((CameraTextureView) findViewById(2131230812));
        this.camerapreview.setOnPreviewCallbackLister(this);
        this.rl_before = ((RelativeLayout) findViewById(2131230837));
        this.no_network_bg = ((PercentRelativeLayout) findViewById(2131230818));
        this.interface_error_bg = ((PercentRelativeLayout) findViewById(2131230791));
        this.tv_all = ((TextView) findViewById(2131230885));
        this.tv_eat_num = ((TextView) findViewById(2131230886));
        TextView localTextView = this.tv_all;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(SpUtil.getInt("eat_number", 30));
        localStringBuilder.append("");
        localTextView.setText(localStringBuilder.toString());
        this.iv_scanning = ((ImageView) findViewById(2131230804));
        initCardTip();
        initScanSuccess();
    }

    protected void onDestroy() {
        super.onDestroy();
        MainPresenter.checkTime = false;
        MainPresenter.checkInterface = false;
        ((MainPresenter) getPresenter()).detach();
        dismissCardNow();
        dismissScanSuccessNow();
        clearFaceRect();
        if ((this.mFaceMediaPlayer != null) && (this.mFaceMediaPlayer.isPlaying())) {
            this.mFaceMediaPlayer.stop();
            this.mFaceMediaPlayer.release();
            this.mFaceMediaPlayer = null;
        }
        stopFaceCheck();
    }

    protected void onPause() {
        super.onPause();
        stopFaceCheck();
        dismissCardNow();
        dismissScanSuccessNow();
        clearFaceRect();
        if ((this.mFaceMediaPlayer != null) && (this.mFaceMediaPlayer.isPlaying())) {
            this.mFaceMediaPlayer.stop();
            this.mFaceMediaPlayer.release();
            this.mFaceMediaPlayer = null;
        }
    }

    public void onPreviewFrame(byte[] paramArrayOfByte) {
        mStartTime = System.currentTimeMillis();
        boolean bool;
        if ((MainPresenter.endTime - mStartTime != 0L) && (Math.abs(MainPresenter.endTime - mStartTime) <= 1700L))
            bool = false;
        else
            bool = true;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("检测状态：");
        localStringBuilder.append(this.openFaceCheck);
        localStringBuilder.append("   time:");
        localStringBuilder.append(bool);
        Log.d("onPreviewFrame", localStringBuilder.toString());
        if ((this.openFaceCheck) && (bool))
            new Thread(paramArrayOfByte) {
                public void run() {
                    super.run();
                    Object localObject1 = MainActivity.this.mMtcnnDet.detectMaxFace(this.val$data);
                    if (localObject1 != null) {
                        MainActivity.lostFace = false;
                        int k = ((MTNCNNDetRet) localObject1).getLeft() * 2;
                        int i = ((MTNCNNDetRet) localObject1).getTop() * 2;
                        int m = ((MTNCNNDetRet) localObject1).getRight();
                        int j = ((MTNCNNDetRet) localObject1).getBottom() * 2;
                        m = k - m * 2;
                        int n = j - i;
                        Object localObject2 = new StringBuilder();
                        ((StringBuilder) localObject2).append("宽：");
                        ((StringBuilder) localObject2).append(m);
                        ((StringBuilder) localObject2).append("高：");
                        ((StringBuilder) localObject2).append(n);
                        Log.d("WH", ((StringBuilder) localObject2).toString());
                        if (Math.abs(Math.abs(m) - n) < 30) {
                            Log.d("WH", "检测人脸");
                            localObject2 = new DisplayMetrics();
                            MainActivity.this.getWindowManager().getDefaultDisplay().getMetrics((DisplayMetrics) localObject2);
                            k = ((DisplayMetrics) localObject2).widthPixels - k;
                            MainActivity.access$1702(MainActivity.this, new Rect(k, i, k + m, j));
                            MainActivity.this.liveFaceSuccessYouTobe(MainActivity.this.mFaceRect);
                            if (((MTNCNNDetRet) localObject1).getAbgr() != null) {
                                localObject1 = MainActivity.this.mMtcnnDet.getMaxFace((MTNCNNDetRet) localObject1, 0.5F, 0.0F);
                                localObject2 = ((ByteData) localObject1).getFaceBitmap();
                                MainActivity.this.liveFaceSuccess((Bitmap) localObject2);
                                ((Bitmap) localObject2).recycle();
                                ((ByteData) localObject1).release();
                            }
                        } else {
                            MainActivity.this.liveFaceLost();
                            MainActivity.lostFace = true;
                        }
                        return;
                    }
                    MainActivity.this.liveFaceLost();
                    MainActivity.lostFace = true;
                }
            }
                    .start();
    }

    protected void onResume() {
        super.onResume();
        this.mEatNumber = this.mDatabase.getEatNumber();
        TextView localTextView = this.tv_eat_num;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(this.mEatNumber);
        localStringBuilder.append("");
        localTextView.setText(localStringBuilder.toString());
        MainPresenter.checkInterface = true;
        MainPresenter.checkTime = true;
        ((MainPresenter) getPresenter()).checkTime();
        if (!NetworkUtil.isNetworkAvalible(this))
            stopFaceCheck();
        this.animationScanning = AnimationsContainer.getInstance(2130837504, 204).createProgressDialogAnim(this.iv_scanning);
    }

    public void playSound(int paramInt) {
        if ((this.mFaceMediaPlayer != null) && (this.mFaceMediaPlayer.isPlaying())) {
            this.mFaceMediaPlayer.stop();
            this.mFaceMediaPlayer.release();
            this.mFaceMediaPlayer = null;
        }
        this.mFaceMediaPlayer = MediaPlayer.create(this, paramInt);
        this.mFaceMediaPlayer.setAudioStreamType(3);
        this.mFaceMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer paramMediaPlayer) {
                MainActivity.this.mFaceMediaPlayer.stop();
                MainActivity.this.mFaceMediaPlayer.release();
                MainActivity.access$902(MainActivity.this, null);
            }
        });
        this.mFaceMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            public boolean onError(MediaPlayer paramMediaPlayer, int paramInt1, int paramInt2) {
                MainActivity.this.mFaceMediaPlayer.stop();
                MainActivity.this.mFaceMediaPlayer.release();
                MainActivity.access$902(MainActivity.this, null);
                return true;
            }
        });
        this.mFaceMediaPlayer.start();
    }

    public void restartFaceCheck() {
        this.openFaceCheck = true;
    }

    public void setAllEatNumber(int paramInt) {
        TextView localTextView = this.tv_all;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(paramInt);
        localStringBuilder.append("");
        localTextView.setText(localStringBuilder.toString());
    }

    protected void setContentView() {
        setContentView(2131361821);
    }

    public void showCamerapreview() {
        runOnUiThread(new Runnable() {
            public void run() {
                MainActivity.this.camerapreview.setAlpha(1.0F);
                MainActivity.this.rl_before.setVisibility(0);
            }
        });
    }

    public void showErrorPopWindow(String paramString1, String paramString2, String paramString3) {
        Message localMessage = Message.obtain();
        localMessage.what = 3;
        Bundle localBundle = new Bundle();
        localBundle.putString("name", paramString1);
        localBundle.putString("msg", paramString2);
        localBundle.putString("url", paramString3);
        localMessage.obj = this.mFaceRect;
        localMessage.setData(localBundle);
        this.mHandler.sendMessageDelayed(localMessage, 400L);
    }

    public void showFaceDefault() {
        this.mHandler.sendEmptyMessage(10);
    }

    public void showInterfaceError() {
        if ((this.mFaceMediaPlayer != null) && (this.mFaceMediaPlayer.isPlaying())) {
            this.mFaceMediaPlayer.stop();
            this.mFaceMediaPlayer.release();
            this.mFaceMediaPlayer = null;
        }
        this.isInterfaceError = true;
        stopFaceCheck();
        dismissCardNow();
        dismissScanSuccessNow();
        clearFaceRect();
        hiddenCamerapreview();
        this.interface_error_bg.setVisibility(0);
    }

    public void showNoNetwork() {
        if ((this.mFaceMediaPlayer != null) && (this.mFaceMediaPlayer.isPlaying())) {
            this.mFaceMediaPlayer.stop();
            this.mFaceMediaPlayer.release();
            this.mFaceMediaPlayer = null;
        }
        this.isNoNetwork = true;
        stopFaceCheck();
        dismissCardNow();
        dismissScanSuccessNow();
        clearFaceRect();
        this.interface_error_bg.setVisibility(4);
        this.no_network_bg.setVisibility(0);
        this.mHandler.sendEmptyMessageDelayed(6, 1000L);
    }

    public void showScanSuccessPopWindow() {
        Message localMessage = Message.obtain();
        localMessage.what = 0;
        this.mHandler.sendMessageDelayed(localMessage, 400L);
    }

    public void showSuccessPopWindow(String paramString1, String paramString2, String paramString3) {
        Message localMessage = Message.obtain();
        localMessage.what = 2;
        Bundle localBundle = new Bundle();
        localBundle.putString("url", paramString1);
        localBundle.putString("name", paramString2);
        localBundle.putString("msg", paramString3);
        localMessage.obj = this.mFaceRect;
        localMessage.setData(localBundle);
        this.mHandler.sendMessageDelayed(localMessage, 400L);
    }

    public void showTouchDefault() {
        this.mHandler.sendEmptyMessage(9);
    }

    public void stopFaceCheck() {
        this.openFaceCheck = false;
    }

    public void updateEatNumber() {
        this.mHandler.sendEmptyMessage(11);
    }
}
