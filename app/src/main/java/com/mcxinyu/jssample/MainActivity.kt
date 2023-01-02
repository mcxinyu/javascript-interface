package com.mcxinyu.jssample

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mcxinyu.javascriptinterface.JavaScriptInterface
import com.mcxinyu.javascriptinterface.SampleMessage
import com.mcxinyu.javascriptinterface.SamplePayload
import com.mcxinyu.javascriptinterface.addJavascriptInterface
import com.mcxinyu.javascriptinterface.evaluate
import com.mcxinyu.javascriptinterface.evaluateScript
import com.mcxinyu.jssample.databinding.ActivityMainBinding
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var httpClient: OkHttpClient
    private val gson = Gson()

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        httpClient = OkHttpClient().newBuilder().build()

        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE)

        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.displayZoomControls = false
        binding.webView.addJavascriptInterface(JavaScriptInterface("jsapi") {
            val message = gson.fromJson(it, SampleMessage::class.java)

            when (message.func) {
                "toast" -> toast(message.payload as? String)
                "whatTimeIsIt" -> return@JavaScriptInterface dateFormat.format(Date())
                "getWeather" -> {
                    val aMessage = gson.fromJson<SampleMessage<ExtSamplePayload>>(
                        it,
                        object : TypeToken<SampleMessage<ExtSamplePayload>>() {}.type
                    )
                    getWeather(aMessage)
                }
            }
            return@JavaScriptInterface null
        })

        binding.webView.loadUrl("file:///android_asset/index.html")
    }

    private fun getWeather(message: SampleMessage<ExtSamplePayload>) {
        if (message.payload?.code.isNullOrEmpty()) {
            runOnUiThread {
                val s = "javascript:${message.payload?.onFailure}('code cannot be empty')"
                binding.webView.evaluateScript(s)
            }
            return
        }

        val url = "http://www.weather.com.cn/data/cityinfo/${message.payload?.code}.html"
        val request: Request = Request.Builder()
            .url(url)
            .get()
            .build()
        httpClient.newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    runOnUiThread {
                        val s = "javascript:${message.payload?.onFailure}(${e.message})"
                        binding.webView.evaluateScript(s)
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    val string = response.body?.string()
                    runOnUiThread {
                        val s = "javascript:${message.payload?.onSuccess}(${gson.toJson(string)})"
                        binding.webView.evaluateScript(s)

                        val ss = "javascript:ssss(${
                            mapOf(
                                "params" to "...",
                                "onSuccess" to "xxxonSuccess(any)",
                                "onFailure" to "xxxonFailure(any)",
                            )
                        })"
                        binding.webView.evaluateScript(ss)
                    }
                }
            })
    }

    private fun toast(s: String?) {
        Toast.makeText(this, s ?: "^-^", Toast.LENGTH_SHORT).show()
    }
}

data class ExtSamplePayload(
    val code: String?,
    val onSuccess: String? = null,
    val onFailure: String? = null
) : Serializable