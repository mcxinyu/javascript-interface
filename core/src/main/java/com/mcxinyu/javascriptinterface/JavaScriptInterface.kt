package com.mcxinyu.javascriptinterface

import android.webkit.JavascriptInterface
import android.webkit.WebView
import androidx.annotation.WorkerThread
import java.io.Serializable

/**
 * 快速实现一个可绑定到 JavaScript 的对象
 *
 * 注意：绑定到 JavaScript 的对象在另一个线程中运行，而不是在构造它的线程中运行。
 *
 * @property interfaceName String. 这会为在 WebView 中运行的 JavaScript 创建名为 [interfaceName] 的接口。
 * 使用 [addJavascriptInterface] 扩展方法，会自动向 WebView 注册此接口。
 * @property onMessage Function1<String, Unit>
 * @constructor
 * @author <a href=mailto:mcxinyu@foxmail.com>yuefeng</a> in 2022/3/24.
 */
open class JavaScriptInterface(val interfaceName: String, private val onMessage: (String?) -> String?) {

    /**
     * 该方法暴露给 javaScript 调用。
     * 此时，javaScript 应用可以通过 [interfaceName] 访问 [JavaScriptInterface] 类。
     *
     * 假如 [interfaceName] = Android
     *```html
     *  <input type="button" value="Say hello" onClick="showAndroidToast('Hello Android!')" />
     *
     *  <script type="text/javascript">
     *      function showAndroidToast(toast) {
     *          Android.postMessage(JSON.stringify(
     *              {
     *                  func: 'showToast',
     *                  payload: toast
     *              }
     *          ));
     *      }
     *  </script>
     *```
     * 你甚至可以将处理后的数据直接返回给 JavaScript 调用方。
     *
     * 注意：绑定到 JavaScript 的对象在另一个线程中运行，而不是在构造它的线程中运行。
     * 因此，[onMessage] 如果需要修改 UI，应该在主线程中运行。
     *
     * @param message String 请求信息，可以使用预定义的 [SampleMessage]，也可以自定义消息体。建议内容序列化为 json 字符串。
     * @return String 你甚至可以将处理后的数据直接返回给 JavaScript 调用方。
     */
    @JavascriptInterface
    @WorkerThread
    open fun postMessage(message: String?): String? = onMessage.invoke(message)
}

/**
 * 快速与 JavaScript 交互的方法，详见 [JavaScriptInterface]
 *
 * @receiver WebView
 * @param javaScriptInterface [JavaScriptInterface]
 */
fun WebView.addJavascriptInterface(javaScriptInterface: JavaScriptInterface) =
    addJavascriptInterface(javaScriptInterface, javaScriptInterface.interfaceName)

/**
 * 一个消息传递的示例
 *
 * @param func String 要调用 Android 的“方法名”
 * @param payload Any? 传递给 Android 的数据，这里用 Any，因为可以传递基础数据类型。建议用 json 格式字符串，这样可以扩展开，例如 [SamplePayload]。
 * @constructor
 */
data class SampleMessage<T : Serializable>(val func: String, val payload: T? = null) : Serializable

/**
 * 一个 payload 消息传递的示例
 *
 * 内置两个参数用于传递 Android 回调 JavaScript 的方法名，
 *
 * 假如 onSuccess 的值为 `onXXXSuccess`，onFailure 的值为 `onXXXFailure`
 * ```kotlin
 *  evaluate("javascript:$onXXXSuccess('something or null');")
 *  evaluate("javascript:$onXXXFailure('something or null');")
 * ```
 *
 * @param onSuccess String? 成功时 Android 回调 JavaScript 的方法名
 * @param onFailure String? 失败时 Android 回调 JavaScript 的方法名
 * @constructor
 */
data class SamplePayload(val onSuccess: String? = null, val onFailure: String? = null) : Serializable
