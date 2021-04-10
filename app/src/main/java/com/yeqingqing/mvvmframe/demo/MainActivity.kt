package com.yeqingqing.mvvmframe.demo

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.yeqingqing.frame.base.vm.getViewModel
import com.yeqingqing.frame.base.view.BaseActivity
import com.yeqingqing.mvvmframe.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * 注意不要忘了 @AndroidEntryPoint 不然创建 ViewModel 时会出现问题。
 */
@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel>() {
    companion object{
        const val TAG = "MainActivity"
    }

    override fun provideLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun provideViewModel(): MainViewModel {
        return getViewModel()
    }

    override fun onViewCreated(savedInstanceState: Bundle?) {
        viewModel.getLastData{
            if (it.errorCode==0){
                Toast.makeText(this,"数据请求成功，请前往日志中查看",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,it.errorMsg,Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResume() {
    }

    override fun onActivityPause() {
    }

    override fun onActivityDestroy() {
    }

    override fun onNetworkLost() {
        Toast.makeText(this,"网络已断开",Toast.LENGTH_LONG).show()
    }

    var firstTime: Long = 0
    override fun onBackPressed() {
        val secondTime = System.currentTimeMillis()
        if (secondTime - firstTime > 2000) {
            Log.i(TAG, "onBackPressed: 再按一次，将退出" + getString(R.string.app_name))
            firstTime = secondTime
            return
        }
        super.onBackPressed()
    }

}