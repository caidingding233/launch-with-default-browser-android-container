package com.caidingding233.myapplication

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager

/**
 * 应用可见性控制辅助类
 * 用于动态控制应用在启动器中的显示/隐藏
 */
object AppVisibilityHelper {
    
    /**
     * 隐藏应用图标（从启动器中移除）
     */
    fun hideAppIcon(context: Context) {
        val componentName = ComponentName(
            context.packageName,
            "${context.packageName}.MainLauncher"
        )
        context.packageManager.setComponentEnabledSetting(
            componentName,
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )
    }
    
    /**
     * 显示应用图标（在启动器中显示）
     */
    fun showAppIcon(context: Context) {
        val componentName = ComponentName(
            context.packageName,
            "${context.packageName}.MainLauncher"
        )
        context.packageManager.setComponentEnabledSetting(
            componentName,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    }
    
    /**
     * 检查应用图标是否可见
     */
    fun isAppIconVisible(context: Context): Boolean {
        val componentName = ComponentName(
            context.packageName,
            "${context.packageName}.MainLauncher"
        )
        val state = context.packageManager.getComponentEnabledSetting(componentName)
        return state == PackageManager.COMPONENT_ENABLED_STATE_ENABLED ||
               state == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT
    }
}