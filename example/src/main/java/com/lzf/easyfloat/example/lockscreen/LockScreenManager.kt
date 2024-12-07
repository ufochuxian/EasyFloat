package com.lzf.easyfloat.example.lockscreen

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.View
import com.lzf.easyfloat.EasyFloat
import com.lzf.easyfloat.enums.ShowPattern
import com.lzf.easyfloat.example.R

const val FLOAT_VIEW_ID = "lock_screen"

class LockScreenManager private constructor(private val context: Context) {

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var instance: LockScreenManager? = null

        fun getInstance(context: Context): LockScreenManager {
            if (instance == null) {
                synchronized(LockScreenManager::class.java) {
                    if (instance == null) {
                        instance = LockScreenManager(context.applicationContext)
                    }
                }
            }
            return instance!!
        }
    }

    fun startLockScreen() {
        context.startService(Intent(context, LockScreenService::class.java))
    }

    fun stopLockScreen() {
        context.stopService(Intent(context, LockScreenService::class.java))
        EasyFloat.dismiss(FLOAT_VIEW_ID)
    }

    fun showLockScreen(lockType: LockType = LockType.LOCK_SCREEN_BY_ACTIVITY) {
        when(lockType) {
            LockType.LOCK_SCREEN_BY_WINDOW -> {
                EasyFloat.with(context)
                    .setTag("lock_screen")
                    .setLayout(R.layout.layout_lock_screen)
                    .setMatchParent(true, true)
                    .setDragEnable(false)
                    .setShowPattern(ShowPattern.ALL_TIME)
                    .show()
            }
            LockType.LOCK_SCREEN_BY_ACTIVITY -> {
                // 启动 Activity，传递视图 ID
                val intent = Intent(context, ScreenLockViewActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(intent)
            }
        }
    }
}

enum class LockType {
    LOCK_SCREEN_BY_WINDOW,
    LOCK_SCREEN_BY_ACTIVITY
}
