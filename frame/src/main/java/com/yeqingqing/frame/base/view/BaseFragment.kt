package com.yeqingqing.frame.base.view
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.yeqingqing.frame.base.vm.BaseViewModel

/**
 *
 * @Author: QCoder
 * @CreateDate: 2021/3/25
 * @Description: 基类 Fragment
 * @Email: 526416248@qq.com
 */
abstract class BaseFragment<BVM: BaseViewModel>:Fragment() {
    lateinit var viewModel: BVM
    lateinit var activity: Activity
    private var isFirstVisible = true
    private var isViewCreated = false

    lateinit var root: View

    @LayoutRes
    protected abstract fun bindLayout(): Int

    protected abstract fun bindViewModel(): BVM

    protected open fun onViewCreated(root: View){}

    protected open fun onFragmentResume(){}

    protected open fun onFragmentPause(){}

    protected open fun onFragmentDestroy(){}

    protected open fun onFragmentFirstVisible() {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(bindLayout(), container, false)
        activity = requireActivity()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = bindViewModel()
        onViewCreated(root)
        isViewCreated = true
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        isFragmentVisible = !hidden
        dispatcherHiddenChanged(hidden)
        if (isActivityVisible) {
            if (hidden) {
                onFragmentPause()
            } else {
                onFragmentResume()
            }
        }
    }

    private fun dispatcherHiddenChanged(hidden: Boolean) {
        for (fragment in childFragmentManager.fragments) {
            if (fragment is BaseFragment<*> && !fragment.isHidden) {
                fragment.onHiddenChanged(hidden)
            }
        }
    }

    private var isActivityVisible = false
    private var isFragmentVisible = true
    override fun onResume() {
        super.onResume()
        isActivityVisible = true
        if (isFragmentVisible) {
            onFragmentResume()
        }
        if (isActivityVisible && isFragmentVisible && isFirstVisible) {
            isFirstVisible = false
            onFragmentFirstVisible()
        }

    }

    override fun onPause() {
        super.onPause()
        isActivityVisible = false
        isFirstVisible = false
        if (isFragmentVisible) {
            onFragmentPause()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        isViewCreated = false
        isFirstVisible = true
        onFragmentDestroy()
    }
}