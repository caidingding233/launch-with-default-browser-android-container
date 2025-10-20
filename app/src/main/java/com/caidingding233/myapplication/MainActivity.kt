package com.caidingding233.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.caidingding233.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    companion object {
        private const val TAG = "BrowserForwarder"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 处理浏览器Intent
        if (handleBrowserIntent(intent)) {
            // 如果是浏览器Intent，不显示UI，直接处理
            return
        }

        // 不是浏览器Intent，显示正常UI
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "浏览器转发器",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        // 处理新的Intent
        handleBrowserIntent(intent)
    }

    private fun handleBrowserIntent(intent: Intent?): Boolean {
        intent?.let {
            val action = it.action
            val data = it.data

            Log.d(TAG, "Received intent: action=$action, data=$data")
            Log.d(TAG, "Intent extras: ${it.extras}")
            Log.d(TAG, "Intent flags: ${it.flags}")

            // 检查是否是浏览器Intent
            if (action == Intent.ACTION_VIEW && data != null) {
                Log.d(TAG, "Forwarding browser intent to default browser")
                // 直接转发Intent
                forwardIntent(it)
                // 关闭当前Activity
                finish()
                return true
            }

            Log.d(TAG, "Not a browser intent, ignoring")
        } ?: Log.d(TAG, "Intent is null")
        
        return false
    }

    private fun forwardIntent(originalIntent: Intent) {
        try {
            // 复制原始Intent的所有信息
            val forwardIntent = Intent(originalIntent).apply {
                // 清除当前应用的包名，让系统选择真正的浏览器
                setPackage(null)
                // 清除component
                component = null
                // 确保有NEW_TASK标记
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }

            Log.d(TAG, "Forwarding intent: action=${forwardIntent.action}, data=${forwardIntent.data}")

            // 获取所有能处理这个Intent的应用
            val resolveInfos = packageManager.queryIntentActivities(forwardIntent, 0)
            Log.d(TAG, "Found ${resolveInfos.size} apps that can handle this intent")

            // 打印所有可用的浏览器
            resolveInfos.forEach { resolveInfo ->
                Log.d(TAG, "Available browser: ${resolveInfo.activityInfo.packageName}")
            }

            // 过滤掉自己
            val filteredResolveInfos = resolveInfos.filter { 
                it.activityInfo.packageName != packageName 
            }

            if (filteredResolveInfos.isEmpty()) {
                Log.e(TAG, "No other browser found to handle the intent")
                return
            }

            Log.d(TAG, "After filtering, ${filteredResolveInfos.size} browsers available")

            // 直接使用选择器，让用户选择或使用系统默认
            val chooser = Intent.createChooser(forwardIntent, "选择浏览器").apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            Log.d(TAG, "Showing browser chooser")
            startActivity(chooser)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to forward intent", e)
            e.printStackTrace()
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}