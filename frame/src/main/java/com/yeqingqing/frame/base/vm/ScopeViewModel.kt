package com.yeqingqing.frame.base.vm

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.*
import java.io.Serializable

/**
 * 具有 Scope 协程 CoroutineScope 特性管理的 ViewModel
 *
 * scope 用于启动一个协程
 * Scope 关键的两个知识点：
 * 1：我们开启一个协程的时候，总在一个 CoroutineScope 里
 * 2：Scope用来管理不同协程之间的父子关系和结构
 *
 * 协程的父子关系有以下两个特性
 * 1：父协程被取消时，所有的子协程都会被取消
 * 2：父协程永远会等待所有的子协程结束
 *
 */
open class ScopeViewModel : ViewModel(), Serializable{

    /**
     * Job 是作为协程身份唯一标识的存在，每一个协程内部都会有一个唯一的标识。通过 Job 对可以控制协程的生命周期，比如：
     * 可以判断协程是否正在运行
     * 可以判断协程是否已经被取消
     * 可以判断协程是否运行结束
     */
    private val job = SupervisorJob()

    /*协程提供了一种全局捕获异常的方式：CoroutineExceptionHandler*/
    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        onError(throwable)
    }

    //⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️
    //Dispatchers.Main - 使用此调度程序可在 Android 主线程上运行协程。此调度程序只能用于与界面交互和执行快速工作。
    //示例包括调用 suspend 函数、运行 Android 界面框架操作，以及更新 LiveData 对象
    //ViewModel里面大多数都是和View打交道，所以说直接在Main上比较方便

    //得到一个 CoroutineScope 对象之后，接下来就可以调用 scope.launch 函数去创建一个协程，创建了一个协程之后，
    //launch 会赋予一个协程的作用域，有了协程作用域，就可以在作用域里去调用协程函数。

    //CoroutineScope翻译过来就是“协程范围”，指的是协程内的代码运行的时间周期范围，如果超出了指定的协程范围，协程会被取消执行。
    protected val scope = CoroutineScope(Dispatchers.Main + job + errorHandler)

    //Dispatchers.IO - 此调度程序经过了专门优化，适合在主线程之外执行磁盘或网络 I/O。
    //示例包括使用 Room 组件、从文件中读取数据或向文件中写入数据，以及运行任何网络操作
    protected val ioScope = CoroutineScope(Dispatchers.IO + job + errorHandler)

    //Dispatchers.Default - 此调度程序经过了专门优化，适合在主线程之外执行占用大量 CPU 资源的工作。
    //用例示例包括对列表排序和解析 JSON。
    //protected val scope = CoroutineScope(Dispatchers.Default + job + errorHandler)
    //⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️
    //当前scope工作调度默认在Dispatchers.Main，若遇到读写IO操作的，请使用withContext切换线程

    protected open fun onError(throwable: Throwable) {
        throwable.printStackTrace()
        Log.e("ScopeViewModel","ViewModel onError message:"+throwable.message+"\nthrowable:$throwable")
    }

    fun Job.onError(block: (Throwable) -> Unit):Job {
        this.invokeOnCompletion {
            it?.also(block)
        }
        return this
    }

    fun Job.onCompletion(block: () -> Unit) :Job{
        this.invokeOnCompletion {
            if (it == null) {
                block()
            }
        }
        return this
    }

    fun launchMain( block: suspend CoroutineScope.() -> Unit){
        scope.launch(Dispatchers.Main) {
            block()
        }
    }

}

//下面三个方法放那么都可以。没有影响
inline fun <reified T : ScopeViewModel> LifecycleOwner.getViewModel(): T {
    return T::class.java.getConstructor(LifecycleOwner::class.java).newInstance(this)
}

inline fun <reified T : ScopeViewModel> FragmentActivity.getViewModel(): T {
    return ViewModelProvider(this).get(T::class.java)
}


inline fun <reified T : ScopeViewModel> Fragment.getViewModel(): T {
    return ViewModelProvider(this).get(T::class.java)
}

