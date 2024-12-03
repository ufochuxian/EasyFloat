package com.lzf.easyfloat.example.lockscreen

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log


class LockScreenService : Service() {

    private lateinit var appSwitchMonitor: AppSwitchMonitor
    private val handler = Handler(Looper.getMainLooper())
    private var lastCheckTime = 0L

    private val checkRunnable = object : Runnable {
        override fun run() {
            checkCurrentApp()
            handler.postDelayed(this, 1000)
        }
    }

    override fun onCreate() {
        super.onCreate()
        appSwitchMonitor = AppSwitchMonitor(this)
        startMonitoring()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun startMonitoring() {
        handler.post(checkRunnable)
    }

    private fun checkCurrentApp() {
        val now = System.currentTimeMillis()
        if (now - lastCheckTime < 500) return
        lastCheckTime = now

        val currentApp = appSwitchMonitor.getCurrentApp()
        if (shouldLock(currentApp)) {
            LockScreenManager.getInstance(this).showLockScreen()
        }
    }

    private fun shouldLock(packageName: String): Boolean {
        // 实现锁屏逻辑
        return packageName.contains("android")
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(checkRunnable)
        Log.i("LockScreenService", "Service destroyed")
    }
}