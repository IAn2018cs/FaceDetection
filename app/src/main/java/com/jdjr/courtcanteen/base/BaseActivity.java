package com.jdjr.courtcanteen.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Author:chenshuai
 * E-mail:chenshuai@amberweather.com
 * Date:2018/11/30
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity
        implements IBaseView {
    private P mPresenter;

    protected abstract P createPresenter();

    public P getPresenter() {
        return this.mPresenter;
    }

    protected abstract void initData();

    protected abstract void initView();

    protected void onCreate(@Nullable Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView();
        this.mPresenter = createPresenter();
        this.mPresenter.attach(this);
        requestPermission(this);
        initView();
        initData();
    }

    protected void onDestroy() {
        super.onDestroy();
        this.mPresenter.detach();
    }

    public void onRequestPermissionsResult(int paramInt, @NonNull String[] paramArrayOfString, @NonNull int[] paramArrayOfInt) {
        super.onRequestPermissionsResult(paramInt, paramArrayOfString, paramArrayOfInt);
        if (paramInt != 1)
            return;
        if (paramArrayOfInt.length > 0) {
            int i = paramArrayOfInt.length;
            paramInt = 0;
            while (paramInt < i) {
                if (paramArrayOfInt[paramInt] != 0) {
                    Toast.makeText(this, "请允许使用摄像头来进行人脸识别", 0).show();
                    return;
                }
                paramInt += 1;
            }
        }
    }

    public void requestPermission(Activity paramActivity) {
        ArrayList localArrayList = new ArrayList();
        if (ContextCompat.checkSelfPermission(paramActivity, "android.permission.CAMERA") != 0)
            localArrayList.add("android.permission.CAMERA");
        if (ContextCompat.checkSelfPermission(paramActivity, "android.permission.WRITE_EXTERNAL_STORAGE") != 0)
            localArrayList.add("android.permission.WRITE_EXTERNAL_STORAGE");
        if (ContextCompat.checkSelfPermission(paramActivity, "android.permission.READ_PHONE_STATE") != 0)
            localArrayList.add("android.permission.READ_PHONE_STATE");
        if (!localArrayList.isEmpty())
            ActivityCompat.requestPermissions(paramActivity, (String[]) localArrayList.toArray(new String[localArrayList.size()]), 1);
    }

    protected abstract void setContentView();
}
