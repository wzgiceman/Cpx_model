package com.base.project.webview

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.http.SslError
import android.os.Build
import android.support.annotation.ColorInt
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import com.base.library.R
import com.base.project.base.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_web_play.*
import java.util.regex.Pattern


/**
 * webView 加载网页的页面
 * bundle 传入两个参数 url,title
 * @param url {String} 必填，加载网页的url
 * @param title {String} 可选，如果标题为空，则标题栏隐藏，如果不为空，则标题栏显示
 *
 * @author xuechao
 * @date 2018/9/6 上午11:05
 * @copyright cpx
 */
open class WebPlayActivity : BaseActivity() {

    private var url: String = ""
    private var title: String = ""
    private var exitUrlRegexSet: MutableSet<String> = mutableSetOf() //拦截url，当拦截到时，退出Activity

    companion object {
        const val URL: String = "url"
        const val TITLE: String = "title"
    }

    override fun initActivity() {
        super.initActivity()
        setTitle(title)
        loadUrl(url)
        initErrorPageEvent()
    }

    override fun layoutId() = R.layout.activity_web_play

    override fun initData() {
        val bundle = this.bundle ?: return
        if (bundle.containsKey(URL)) {
            url = bundle.getString(URL)
        }
        if (bundle.containsKey(TITLE)) {
            title = bundle.getString(TITLE)
        }
    }

    override fun initView() {
        initToolbar()
        setWebSettings()
        setWebViewClient()
        setWebViewChromeClient()
        initWidgetVisibility()

    }


    /**
     * 初始化Toolbar
     */
    private fun initToolbar() {
        web_toolbar.visibility = View.GONE
        setSupportActionBar(web_toolbar)
        web_toolbar.setNavigationOnClickListener { this@WebPlayActivity.finish() }
    }

    /**
     * 初始化页面控件的显示状态
     */
    private fun initWidgetVisibility() {
        progress_bar.visibility = View.VISIBLE
        web_view.visibility = View.VISIBLE
        ll_error_page.visibility = View.GONE
    }

    /**
     * 设置标题
     */
    private fun setTitle(title: String) {
        if (!TextUtils.isEmpty(title)) {
            web_toolbar.visibility = View.VISIBLE
            web_toolbar.title = title
        }
    }

    /**
     * webview 加载url
     */
    private fun loadUrl(url: String) {
        web_view.loadUrl(url)
    }

    /**
     * 设置进度条颜色
     */
    fun setProgressColor(@ColorInt color: Int) {
        setProgressDrawable(ColorDrawable(color))
    }

    /**
     * 设置进度条图片
     */
    fun setProgressDrawable(drawable: Drawable) {
        progress_bar.progressDrawable = drawable
    }

    /**
     * 设置错误页面
     */
    fun setErrorPage(errorView: View) {
        ll_error_page.removeAllViews()
        ll_error_page.addView(errorView)
    }

    /**
     * webview 加载url
     */
    private fun loadUrl(data: String, mimeType: String, encoding: String) {
        web_view.loadData(data, mimeType, encoding)
    }

    /**
     * 初始化错误页的点击事件
     */
    private fun initErrorPageEvent() {
        ll_error_page.setOnClickListener { view ->
            run {
                initWidgetVisibility()
                web_view.reload()
            }
        }
    }

    /**
     * 设置WebView Settings
     */
    @SuppressLint("SetJavaScriptEnabled")
    private fun setWebSettings() {
        val settings: WebSettings = web_view.settings
        //允许使用js
        settings.javaScriptEnabled = true
        //设置自适应屏幕，两者合用
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        //支持缩放
        settings.setSupportZoom(true)
        settings.builtInZoomControls = true
        //隐藏原生的缩放控件
        settings.displayZoomControls = false
        //H5页面使用DOM storage API导致的页面加载问题
        settings.domStorageEnabled = true
        //允许Https + Http的混合使用
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
    }

    /**
     * 设置webview 的client
     *
     */
    private fun setWebViewClient() {
        web_view.webViewClient = object : WebViewClient() {

            override fun shouldOverrideKeyEvent(view: WebView?, event: KeyEvent?): Boolean {
                //处理返回事件
                if (KeyEvent.KEYCODE_BACK == event?.keyCode && view!!.canGoBack()) {
                    return true
                }
                return super.shouldOverrideKeyEvent(view, event)
            }

            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                handler?.proceed()
//                val builder: AlertDialog.Builder = AlertDialog.Builder(this@WebPlayActivity)
//                builder.setMessage(getString(R.string.ssl_cert_invalid))
//                builder.setPositiveButton(getString(R.string.continue_text)) { dialog, which ->
//                    dialog?.dismiss()
//                    handler?.proceed()
//                }
//                builder.setNegativeButton(getString(R.string.cancel)) { dialog, which ->
//                    dialog?.dismiss()
//                    handler?.cancel()
//                }
//                val dialog = builder.create()
//                dialog.show()
            }

            @TargetApi(Build.VERSION_CODES.M)
            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                if (error != null) {
                    handleError(error.errorCode, error.description.toString())
                }
            }

            override fun onReceivedError(view: WebView?, errorCode: Int, description: String?, failingUrl: String?) {
                super.onReceivedError(view, errorCode, description, failingUrl)
                handleError(errorCode, description!!)
            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                return super.shouldOverrideUrlLoading(view, url)
            }

            override fun shouldInterceptRequest(view: WebView?, url: String): WebResourceResponse? {
                return handleInterceptUrl(url)

            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest): WebResourceResponse? {
                return handleInterceptUrl(request.url.toString())
            }
        }
    }

    //是否处理拦截的url
    private var isHandleUrl = false

    /**
     * 处理拦截的url
     */
    private fun handleInterceptUrl(url: String): WebResourceResponse? {
        if (exitUrlRegexSet.isEmpty()) {
            return null
        }
        var isFind = false
        for (regex in exitUrlRegexSet) {
            var pattern = Pattern.compile(regex)
            isFind = pattern.matcher(url).find()
            if (isFind) {
                break
            }
        }
        if (isFind) {
            if (!isHandleUrl) {
                isHandleUrl = true
                finish()
            }
        }
        return null

    }

    /**
     * 添加退出url的正则
     */
    fun addExitUrlRegex(regex: String) {
        this.exitUrlRegexSet.add(regex)
    }

    /**
     * 添加退出url的正则
     */
    fun addExitUrlRegexs(regexList: MutableList<String>) {
        regexList.forEach {
            addExitUrlRegex(it)
        }
    }

    /**
     * 删除退出url的正则
     */
    fun removeExitUrlRegex(regex: String) {
        this.exitUrlRegexSet.remove(regex)
    }

    /**
     * 处理请求错误
     * 只是显示空的页面
     */
    fun handleError(errorCode: Int, description: String) {
        ll_error_page.visibility = View.VISIBLE
        web_view.visibility = View.GONE

    }

    /**
     * 设置webview的ChromeClient
     */
    private fun setWebViewChromeClient() {
        web_view.webChromeClient = object : WebChromeClient() {

            /**
             * 加载的进度条
             */
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                if (newProgress == 100) {
                    progress_bar.visibility = View.GONE
                } else {
                    progress_bar.progress = newProgress
                }
                super.onProgressChanged(view, newProgress)
            }
        }
    }


    /**
     * 处理返回事件
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && web_view.canGoBack()) {
            web_view.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (web_view != null) {
            web_view.clearHistory()
            val parent: ViewGroup = web_view.parent as ViewGroup
            parent.removeView(this.web_view)
            this.web_view.removeAllViews()
            this.web_view.destroy()
        }
    }


}
