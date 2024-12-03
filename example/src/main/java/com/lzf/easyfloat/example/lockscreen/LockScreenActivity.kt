package com.lzf.easyfloat.example.lockscreen

import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lzf.easyfloat.example.R
import com.lzf.easyfloat.permission.PermissionUtils
import com.lzf.easyfloat.interfaces.OnPermissionResult

class LockScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock_screen)

        // 启动锁屏功能
        findViewById<Button>(R.id.btnStartLock).setOnClickListener {
            checkPermissionAndStart()
        }

        // 停止锁屏功能
        findViewById<Button>(R.id.btnStopLock).setOnClickListener {
            LockScreenManager.getInstance(this).stopLockScreen()
        }
    }

    private fun checkPermissionAndStart() {
        // 检查悬浮窗权限
        if (!PermissionUtils.checkPermission(this)) {
            PermissionUtils.requestPermission(this, object : OnPermissionResult {
                override fun permissionResult(isOpen: Boolean) {
                    if (isOpen) {
                        checkUsagePermission()
                    } else {
                        Toast.makeText(this@LockScreenActivity, "需要悬浮窗权限", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        } else {
            checkUsagePermission()
        }
    }

    private fun checkUsagePermission() {
        // 检查使用情况访问权限
        if (!hasUsageStatsPermission()) {
            startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        } else {
            startLockScreen()
        }
    }

    private fun hasUsageStatsPermission(): Boolean {
        val appOps = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.checkOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            android.os.Process.myUid(),
            packageName
        )
        return mode == AppOpsManager.MODE_ALLOWED
    }

    private fun startLockScreen() {
        // 启动锁屏服务
        LockScreenManager.getInstance(this).startLockScreen()
    }
}