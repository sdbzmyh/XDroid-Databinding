package cn.droidlover.xdroid.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;

import cn.droidlover.xdroid.event.BusFactory;

/**
 * Created by shihao on 2017/1/26.
 */

public abstract class XLazyFragment<V extends ViewDataBinding> extends LazyFragment implements UiCallback {

    private UiDelegate uiDelegate;

    private V binding;

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        if (getLayoutId() > 0) {
            setContentView(getLayoutId());
            bindUI(getRealRootView());
        }
        if (useEventBus()) {
            BusFactory.getBus().register(this);
        }
        setListener();
        initData(savedInstanceState);
    }

    protected V getBinding() {
        return binding;
    }

    public void bindUI(View rootView) {
        binding = DataBindingUtil.bind(rootView);
    }

    @Override
    protected void onDestoryLazy() {
        super.onDestoryLazy();
        if (useEventBus()) {
            BusFactory.getBus().unregister(this);
        }
        getUiDelegate().destory();
    }


    @Override
    public boolean useEventBus() {
        return false;
    }

    @Override
    public void setListener() {

    }

    protected UiDelegate getUiDelegate() {
        if (uiDelegate == null) {
            uiDelegate = UiDelegateBase.create(getContext());
        }
        return uiDelegate;
    }


}
