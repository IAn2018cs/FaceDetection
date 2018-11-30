package com.jdjr.courtcanteen.base;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Description:
 * Author:chenshuai
 * E-mail:chenshuai@amberweather.com
 * Date:2018/11/30
 */
public class BasePresenter<V extends IBaseView> {
    private V mProxyView;
    private WeakReference<V> mViewReference;

    public void attach(V paramV) {
        this.mViewReference = new WeakReference(paramV);
        this.mProxyView = ((IBaseView) Proxy.newProxyInstance(paramV.getClass().getClassLoader(), paramV.getClass().getInterfaces(), new InvocationHandler() {
            public Object invoke(Object paramObject, Method paramMethod, Object[] paramArrayOfObject)
                    throws Throwable {
                if ((BasePresenter.this.mViewReference != null) && (BasePresenter.this.mViewReference.get() != null))
                    try {
                        paramObject = paramMethod.invoke(BasePresenter.this.mViewReference.get(), paramArrayOfObject);
                        return paramObject;
                    } catch (InvocationTargetException paramObject) {
                        throw paramObject.getCause();
                    }
                return null;
            }
        }));
    }

    public void detach() {
        this.mViewReference = null;
        this.mProxyView = null;
    }

    public V getView() {
        return this.mProxyView;
    }
}
