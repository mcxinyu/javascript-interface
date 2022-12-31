package com.mcxinyu.javascriptinterface

import android.webkit.WebView
import androidx.annotation.UiThread
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * @author <a href=mailto:mcxinyu@foxmail.com>yuefeng</a> in 2022/12/31.
 */

/**
 * 协程版本的 [WebView.evaluateJavascript]
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
 */
@UiThread
suspend inline fun WebView.evaluate(script: String): String? =
    suspendCancellableCoroutine { continuation ->
        evaluateScript(script) { continuation.resume(it) }
    }

@UiThread
fun WebView.evaluateScript(script: String, action: ((String?) -> Unit)? = null) =
    evaluateJavascript(script) { action?.invoke(it) }