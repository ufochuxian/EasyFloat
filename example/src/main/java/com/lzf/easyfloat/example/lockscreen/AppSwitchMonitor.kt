package com.lzf.easyfloat.example.lockscreen

import android.app.usage.UsageStatsManager
import android.content.Context


class AppSwitchMonitor(private val context: Context) {
    private val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

    fun getCurrentApp(): String {
        val time = System.currentTimeMillis()
        val stats = usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            time - 1000 * 60,
            time
        )
        return stats.maxByOrNull { it.lastTimeUsed }?.packageName ?: ""
    }
}