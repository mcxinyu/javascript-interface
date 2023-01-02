package com.mcxinyu.javascriptinterface.tbsx5

import androidx.annotation.UiThread
import com.tencent.smtt.sdk.WebView
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * 协程版本的 [WebView.evaluateJavascript]
 *
 * 支持 tbs 版本
 *
 * 在当前显示页面的上下文中异步运行 JavaScript。
 *
 * ```
 *   evaluate("javascript:funcSome('something or null');")
 * ```
 *
 * @receiver WebView
 * @param script String
 * @return String?
 *
 * @author <a href=mailto:mcxinyu@foxmail.com>yuefeng</a> in 2022/12/31.
 */
@UiThread
suspend inline fun WebView.evaluate(script: String): String? =
    suspendCancellableCoroutine { continuation ->
        evaluateScript(script) { continuation.resume(it) }
    }

@UiThread
fun WebView.evaluateScript(script: String, action: ((String?) -> Unit)? = null) =
    evaluateJavascript(script) { action?.invoke(it) }