package com.samikb.assignment

import android.app.Application
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

private const val LAST_CACHE_DELETE_TIMESTAMP = "com.samikb.assignment.cache_delete_timestamp"
class App: Application() {
    val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    override fun onCreate() {
        super.onCreate()
        clearImageCache()
    }
    private fun calculateHoursSpent(time: Long): Int{
        val diff = System.currentTimeMillis() - time
        return (diff / 1000 / 60 / 60).toInt()
    }

    private fun clearImageCache(){
        applicationScope.launch {
            val sharedPreferences = getSharedPreferences(App::class.qualifiedName, MODE_PRIVATE)
            val lastDeletedTimeStamp = sharedPreferences.getLong(LAST_CACHE_DELETE_TIMESTAMP, 0)
            if(lastDeletedTimeStamp > 0){
                val hours = calculateHoursSpent(lastDeletedTimeStamp)
                if(hours >= 24){
                    Glide.get(applicationContext).clearDiskCache()
                    sharedPreferences
                        .edit()
                        .putLong(LAST_CACHE_DELETE_TIMESTAMP, System.currentTimeMillis())
                        .commit()
                }
            }
            else{
                sharedPreferences
                    .edit()
                    .putLong(LAST_CACHE_DELETE_TIMESTAMP, System.currentTimeMillis())
                    .commit()
            }
        }
    }
}