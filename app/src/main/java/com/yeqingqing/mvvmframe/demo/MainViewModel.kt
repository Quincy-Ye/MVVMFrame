package com.yeqingqing.mvvmframe.demo


import com.yeqingqing.frame.base.vm.BaseViewModel
import com.yeqingqing.frame.network.ApiResponse
import com.yeqingqing.mvvmframe.demo.bd.MainDataBean
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 *
 * @Author: QCoder
 * @CreateDate: 2021/3/25
 * @Description: TODO
 * @Email: 526416248@qq.com
 */
@HiltViewModel
class MainViewModel @Inject constructor (private val mainRepository: MainRepository):
    BaseViewModel() {

    fun getLastData(block: (ApiResponse<List<MainDataBean>>) -> Unit) {
        scope.launch {
            val result =mainRepository.getLastData()
            block(result)
        }
    }
}