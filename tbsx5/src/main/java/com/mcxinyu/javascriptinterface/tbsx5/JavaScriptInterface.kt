package com.mcxinyu.javascriptinterface.tbsx5

import com.mcxinyu.javascriptinterface.JavaScriptInterface
import com.tencent.smtt.sdk.WebView

/**
 * 支持 tbs 的快速与 JavaScript 交互的方法，详见 [JavaScriptInterface]
 *
 * @receiver WebView
 * @param javaScriptInterface [JavaScriptInterface]
 *
 * @author <a href=mailto:mcxinyu@foxmail.com>yuefeng</a> in 2023/1/2.
 */
fun WebView.addJavascriptInterface(javaScriptInterface: JavaScriptInterface) =
    addJavascriptInterface(javaScriptInterface, javaScriptInterface.interfaceName)
