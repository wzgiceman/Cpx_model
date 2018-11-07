package com.prog.zhigangwei.cpx_model.rxjava

import com.base.library.retrofit_rx.observer.AbsObserver
import com.base.library.utils.AbLogUtil
import com.base.muslim.base.activity.BaseFragmentActivity
import com.prog.zhigangwei.cpx_model.R
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_rx_java.*
import org.reactivestreams.Subscription
import java.util.concurrent.TimeUnit


/**
 *
 * RxJava相关的Demo的Activity
 * read <a href="https://maxwell-nc.github.io/android/rxjava2-1.html">RxJava2入门</a>
 * @author xuechao
 * @date 2018/11/6 下午3:39
 * @copyright cpx
 *
 */
class RxJavaActivity : BaseFragmentActivity() {


    override fun setContentViews() {
        super.setContentViews()
        setContentView(R.layout.activity_rx_java)
    }

    override fun initResource() {

    }

    override fun initWidget() {

        //默认的使用方式
        btn_rx_default.setOnClickListener {
            defaultUse()
        }

        //背压的使用方式
        btn_rx_flowable.setOnClickListener {
            testFlowable()
        }

        //线程调度
        btn_rx_scheduler.setOnClickListener {
            testScheduler()
        }

        //操作符
        btn_rx_operator.setOnClickListener{
            testOperator()
        }
    }

    /**
     * 测试背压模式的观察者
     */
    private fun testFlowable() {
        //1.创建Flowable
        var flowable = Flowable.create(object:FlowableOnSubscribe<Int>{
            override fun subscribe(emitter: FlowableEmitter<Int>) {
                emitter.onNext(1)
                emitter.onNext(2)
                emitter.onNext(3)
                emitter.onNext(4)
                emitter.onNext(5)
                emitter.onComplete()
            }

        },BackpressureStrategy.BUFFER)

        //2.创建Subscriber
        var subscriber = object:FlowableSubscriber<Int>{
            override fun onComplete() {
                AbLogUtil.d("xc","onComplete")
            }

            override fun onSubscribe(s: Subscription) {
                AbLogUtil.d("xc","onSubscribe")
                //订阅时候的操作,请求多少事件
                s.request(Long.MAX_VALUE)
            }

            override fun onNext(t: Int?) {
                AbLogUtil.d("xc","onNext $t")
            }

            override fun onError(t: Throwable?) {
                AbLogUtil.d("xc","onNext ${t?.message}")
            }

        }
        //3.订阅
        flowable.subscribe(subscriber)

    }


    /**
     * 默认的使用方式
     * @see AbsObserver
     */
    private fun defaultUse() {
        //1.创建Observable
        var observable = Observable.create(object : ObservableOnSubscribe<Int> {
            override fun subscribe(emitter: ObservableEmitter<Int>) {
                emitter.onNext(1)
                emitter.onNext(2)
                emitter.onNext(3)
                emitter.onNext(4)
                emitter.onNext(5)
                emitter.onNext(6)
                emitter.onComplete()
//                emitter.onError(NullPointerException("空指针"))
            }

        });
        //2.创建Observer
        var observer: Observer<Int> = object : Observer<Int> {
            private var mDisposable:Disposable?=null
            private var i:Int = 0

            override fun onComplete() {
                AbLogUtil.d("xc","onComplete")
            }

            override fun onSubscribe(d: Disposable) {
                AbLogUtil.d("xc","onSubscribe")
                mDisposable = d
            }

            override fun onNext(t: Int) {
                AbLogUtil.d("xc","onNext $t")
                i++
                if(i == 4){
                    //切断的操作，让Observer观察者不再接收上游事件
//                    mDisposable?.dispose()
                }
            }

            override fun onError(e: Throwable) {
                AbLogUtil.d("xc","onError:${e.message}")
            }

        }
        //自定义的封装的Observer
        var myObserver:AbsObserver<Int> = object:AbsObserver<Int>(){
            override fun onNext(t: Int) {
                AbLogUtil.d("xc","onNext $t")
                if(t == 4){
                    //取消订阅
                    unsubscribe()
                }
            }

        }
        //订阅
        observable.subscribe(myObserver)


    }

    /**
     * 测试线程调度
     * subscribeOn() 指定的就是发射事件的线程，多次调用 subscribeOn() 只有第一次的有效，其余的会被忽略。
     * observerOn 指定的就是订阅者接收事件的线程,每调用一次 observerOn()，下游的线程就会切换一次。
     *
     * Schedulers.io() 代表io操作的线程, 通常用于网络,读写文件等io密集型的操作；
     * Schedulers.computation() 代表CPU计算密集型的操作, 例如需要大量计算的操作；
     * Schedulers.newThread() 代表一个常规的新线程；
     * AndroidSchedulers.mainThread() 代表Android的主线程
     *
     * @see Schedulers
     * @see AndroidSchedulers
     */
    private fun testScheduler() {
        Observable
            .create<Int> {
                it.onNext(1)
                it.onNext(2)
                it.onNext(3)
                it.onNext(4)
                it.onNext(5)
                it.onNext(6)
                it.onNext(7)
            }
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.io())
            .doOnNext {
                AbLogUtil.d("xc","value:$it observeOn(io) : ${Thread.currentThread().name}")
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                AbLogUtil.d("xc","value:$it observeOn(mainThread) : ${Thread.currentThread().name}")
            }
    }

    /**
     * 测试运算符
     */
    private fun testOperator() {

//        testCreate()
//        testJust()
//        testDistinct()
//        testFilter()
//        testTimer()
//        testInterval()
//        testMap()
//        testFlatMap()
//        testZip()
//        testConcat()
//        testBuffer()
//        testSkip()
//        testThrottleFirst()
//        testDebounce()
        testLast()
    }




    /**
     * create 操作符，主要用于产生一个 Obserable 被观察者对象
     * just
     */
    private fun testCreate() {
        Observable
            .create<Int> {
                AbLogUtil.d("xc","emitter 1")
                it.onNext(1)
                AbLogUtil.d("xc","emitter 2")
                it.onNext(2)
                AbLogUtil.d("xc","emitter 3")
                it.onNext(3)
                AbLogUtil.d("xc","emitter 4")
                it.onNext(4)
                AbLogUtil.d("xc","emitter complete")
                it.onComplete()
                AbLogUtil.d("xc","emitter 5")
                it.onNext(5)
                AbLogUtil.d("xc","emitter 6")
                it.onNext(6)
                AbLogUtil.d("xc","emitter 7")
                it.onNext(7)

            }.subscribe {
                AbLogUtil.d("xc","onNext $it")
            }
    }

    /**
     * distinct 去重
     */
    private fun testDistinct(){
        Observable.just("1","1","2","2","4","5")
            .distinct()
            .subscribe {
                AbLogUtil.d("xc","onNext $it")
            }
    }

    /**
     * filter 过滤
     */
    private fun testFilter(){
        Observable.just(1,2,3,4,5,6,7,8)
            .filter {
               return@filter it % 2 == 0
            }
            .subscribe {
                AbLogUtil.d("xc","onNext $it")
            }
    }

    /**
     * timer 延时
     */
    private fun testTimer(){
        AbLogUtil.d("xc","timer")
        Observable.timer(2,TimeUnit.SECONDS)
            .subscribe {
                AbLogUtil.d("xc","onNext:$it")
            }
    }

    /**
     * interval 定时任务
     * take 表示至多接收 count 个数据。
     * 常用作倒计时
     */
    private fun testInterval(){
        AbLogUtil.d("xc","interval")
        Observable.interval(1,TimeUnit.SECONDS)
            .take(11)
            .subscribe {
                AbLogUtil.d("xc","onNext:$it")
            }
    }

    /**
     * just 一个简单的发射器依次调用 onNext() 方法
     */
    private fun testJust() {
        Observable.just("1","3","5","7")
            .subscribe {
                AbLogUtil.d("xc","onNext $it")
            }
    }

    /**
     * map 操作符，将一个 Observable 通过某种函数关系，转换为另一种 Observable
     */
    private fun testMap(){
        Observable
            .create<Int> {
                it.onNext(1)
                it.onNext(2)
                it.onNext(3)
                it.onNext(4)

            }.map {
                return@map "${it*2}"
            }.subscribe {
                AbLogUtil.d("xc","onNext $it")
            }
    }

    /**
     * flatMap 操作符，把一个发射器 Observable 通过某种方法转换为多个 Observables，然后再把这些分散的 Observables装进一个单一的发射器 Observable
     * flatMap 是无序的，而concatMap是有序的
     * @see concatMap
     */
    private fun testFlatMap(){
        Observable
            .create<Int> {
                it.onNext(1)
                it.onNext(2)
                it.onNext(3)
                it.onNext(4)

            }.flatMap(object :Function<Int,ObservableSource<String>>{
                override fun apply(t: Int): ObservableSource<String> {
                    AbLogUtil.d("xc","applay:$t")
                    var list = mutableListOf<String>()
                    for (i in 1..3) {
                        list.add("I am value ${i*t}")
                    }

                    return Observable.fromIterable(list).delay(10L,TimeUnit.MILLISECONDS)
                }

            }).subscribe {
                AbLogUtil.d("xc","onNext $it")
            }
    }

    /**
     * zip 合并事件 两两配对，也就意味着，最终配对出的 Observable 发射事件数目只和少的那个相同
     */
    private fun testZip() {
        Observable
            .zip(Observable.just(1,2,3,4,5),
                Observable.just("10","11"),
                object:BiFunction<Int,String,String>{
                    override fun apply(t1: Int, t2: String): String {
                        return "$t1 -> $t2"
                    }

                })
            .subscribe {
                AbLogUtil.d("xc","onNext $it")
            }

    }

    /**
     * concat 把两个发射器连接成一个发射器
     */
    private fun testConcat(){
        Observable
            .concat(Observable.just(1,2,3,4,5),
                Observable.just("10","11")
                )
            .subscribe {
                AbLogUtil.d("xc","onNext $it")
            }
    }

    /**
     * buffer buffer(count,skip)，作用是将 Observable 中的数据按 skip (步长) 分成最大不超过 count 的 buffer，
     * 然后生成一个  Observable
     */
    private fun testBuffer(){
        Observable.just(1,2,3,4,5,6,7,8,9)
            .buffer(4,2)
            .subscribe {
                AbLogUtil.d("xc","onNext $it")
            }
    }

    /**
     * skip 代表跳过 count 个数目开始接收
     */
    private fun testSkip(){
        Observable.just(1,2,3,4,5,6,7,8,9)
            .skip(2)
            .subscribe {
                AbLogUtil.d("xc","onNext $it")
            }
    }

    /**
     * throttleFirst 允许设置一个时间长度，之后它会发送固定时间长度内的第一个事件，而屏蔽其它事件，在间隔达到设置的时间后，可以再发送下一个事件。
     * 一般和RxBinding配合使用,用于按钮去重
     */
    private fun testThrottleFirst(){
        Observable.create<Int> {
            it.onNext(1)
            Thread.sleep(100)
            it.onNext(2)
            Thread.sleep(100)
            it.onNext(3)
            Thread.sleep(100)
            it.onNext(4)
            Thread.sleep(100)
            it.onNext(5)
            Thread.sleep(100)
            it.onNext(6)
            it.onComplete()
        }.throttleFirst(200,TimeUnit.MILLISECONDS)
            .subscribe {
                AbLogUtil.d("xc","onNext $it")
            }
    }

    /**
     * debounce 去除发送频率过快的项
     */
    private fun testDebounce(){
        Observable.create<Int> {
            it.onNext(1)
            Thread.sleep(100)
            it.onNext(2)
            Thread.sleep(200)
            it.onNext(3)
            Thread.sleep(300)
            it.onNext(4)
            Thread.sleep(400)
            it.onNext(5)
            Thread.sleep(500)
            it.onNext(6)
            it.onComplete()
        }.debounce(300,TimeUnit.MILLISECONDS)
            .subscribe {
                AbLogUtil.d("xc","onNext $it")
            }
    }

    /**
     * last 仅取出可观察到的最后一个值，或者是满足某些条件的最后一项
     */
    private fun testLast(){
        Observable.just(1,2,3,4)
            .last(6)
            .subscribe { t-> AbLogUtil.d("xc","onNext $t") }

    }

}
