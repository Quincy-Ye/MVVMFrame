package com.yeqingqing.frame.base.view

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.yeqingqing.frame.base.vm.BaseViewModel
import com.yeqingqing.frame.ext.ktxRunOnUi
import com.yeqingqing.frame.utils.ActivityManager
import com.yeqingqing.swipeback.SwipeBackActivityBase
import com.yeqingqing.swipeback.SwipeBackActivityHelper
import com.yeqingqing.swipeback.SwipeBackLayout
import com.yeqingqing.swipeback.Utils


/**
 *
 * @Author: QCoder
 * @CreateDate: 2021/3/25
 * @Description: 基类 Activity ，可配置【右滑返回】;
 * 遵循：非必要，不抽象原则，尽可能减少耦合度
 * @param BVM 绑定对应 Activity 的 ViewModel
 * @Email: 526416248@qq.com
 */
abstract class BaseActivity<BVM : BaseViewModel> : AppCompatActivity(), SwipeBackActivityBase,
    BaseViewModel.LoadingEvent {
    companion object {
        const val TAG = "BaseActivity"
    }

    override fun showLoading(message: String) {
    }

    override fun showMessage(message: String, delay: Int) {
    }

    override fun dismissLoading(delay: Int) {
    }

    lateinit var viewModel: BVM
    lateinit var activity: Activity
    lateinit var context: Context

    lateinit var  connManager : ConnectivityManager
    /**************************************** 右滑返回-Begin **************************************/
    private lateinit var mSwipeBackLayout: SwipeBackLayout
    lateinit var helper: SwipeBackActivityHelper
    override fun getSwipeBackLayout(): SwipeBackLayout {
        return helper.swipeBackLayout
    }

    override fun setSwipeBackEnable(enable: Boolean) {
        mSwipeBackLayout.setEnableGesture(enable)
    }

    override fun scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this)
        mSwipeBackLayout.scrollToFinishActivity()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        helper.onPostCreate()
    }

    /**
     * @param enableSwipeBack
     */
    fun swipeBack(enableSwipeBack:Boolean){
        setSwipeBackEnable(enableSwipeBack)
    }
    /**
     * @param enableSwipeBack
     * @param edgeFlags 设置滑动方向，可设置 EDGE_LEFT, EDGE_RIGHT, EDGE_ALL, EDGE_BOTTOM
     */
     fun swipeBack(enableSwipeBack: Boolean, edgeFlags: Int) {
        mSwipeBackLayout.setEdgeTrackingEnabled(edgeFlags)
        setSwipeBackEnable(enableSwipeBack)
    }
    /**************************************** 右滑返回-End **************************************/

    @LayoutRes
    abstract fun provideLayoutId(): Int
    abstract fun provideViewModel(): BVM

    //LifeCycle
    protected abstract fun onViewCreated(savedInstanceState: Bundle?)
    protected abstract fun onActivityResume()
    protected abstract fun onActivityPause()
    protected abstract fun onActivityDestroy()

    //wifi status
    // open 关键字的作用，不强制 重写以下方法
    open fun onNetworkLost(){}
    open fun onNetworkAvailable(){}
    open fun onNetworkUnAva(){}
//    open fun onNetwork(){} 其他网络情况，自定义

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate: ")
        setContentView(provideLayoutId())
        activity = this
        context = this
        ActivityManager.instance.addActivity(this)
        viewModel = provideViewModel()

        initSwipeBack()
        onViewCreated(savedInstanceState)
        connManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        registerNetworkListener()
    }

   // 初始化 【右滑返回】
    private fun initSwipeBack(){
        helper = SwipeBackActivityHelper(this)
        helper.onActivityCreate()
        mSwipeBackLayout = swipeBackLayout
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT)
    }
    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume: ")
        onActivityResume()
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause: ")
        onActivityPause()
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityManager.instance.finishActivity(this)
        connManager.unregisterNetworkCallback(connectCallback)
        Log.i(TAG, "onDestroy: ")
        onActivityDestroy()
    }


    private fun registerNetworkListener(){
        val builder = NetworkRequest.Builder()
        val request = builder.build()
        connManager.registerNetworkCallback(request,connectCallback)
    }
    private val connectCallback = object :ConnectivityManager.NetworkCallback(){
        override fun onLost(network: Network) {
            super.onLost(network)
            //网络已断开
            Log.i(TAG, "onLost: 网络已断开")
            this.ktxRunOnUi { onNetworkLost() }
        }

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            //网络可用
            Log.i(TAG, "onAvailable: 网络可用")
            this.ktxRunOnUi {
                onNetworkAvailable()
            }
        }

        override fun onUnavailable() {
            super.onUnavailable()
            //网络不可用
            Log.i(TAG, "onUnavailable: 网络不可用")
            this.ktxRunOnUi {
                onNetworkUnAva()
            }
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)){
                //wifi
                Log.i(TAG, "onCapabilitiesChanged: wifi")
            }else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
            {
                //数据流量
                Log.i(TAG, "onCapabilitiesChanged: 数据流量")
            }else{
                //其他网络
                Log.i(TAG, "onCapabilitiesChanged: 其他网络")
            }
        }
    }

}