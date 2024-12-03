package com.lzf.easyfloat.example.lockscreen

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.FrameLayout
import com.lzf.easyfloat.EasyFloat

@SuppressLint("ClickableViewAccessibility")
class LockScreenView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN,
                MotionEvent.ACTION_MOVE,
                MotionEvent.ACTION_UP -> true
            }
            true
        }
    }

    fun unlock() {
        EasyFloat.dismiss("lock_screen")
        Log.i("LockScreenView", "Screen unlocked")
    }
}