package com.lzf.easyfloat.example.lockscreen

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import com.lzf.easyfloat.EasyFloat
import com.lzf.easyfloat.enums.ShowPattern
import com.lzf.easyfloat.example.R

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
        EasyFloat.dismiss("lock_screen")
    }

    fun showLockScreen() {
        EasyFloat.with(context)
            .setTag("lock_screen")
            .setLayout(R.layout.layout_lock_screen)
            .setMatchParent(true, true)
            .setDragEnable(false)
            .setShowPattern(ShowPattern.ALL_TIME)
            .show()
    }
}