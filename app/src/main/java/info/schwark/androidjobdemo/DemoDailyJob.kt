package info.schwark.androidjobdemo

import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import com.evernote.android.job.DailyJob

class DemoDailyJob : DailyJob() {
    override fun onRunDailyJob(params: Params): DailyJobResult {
        LocalBroadcastManager.getInstance(context).sendBroadcast(Intent("jobFinished").apply { putExtra("jobId", params.id) })
        return DailyJobResult.SUCCESS
    }

    companion object {
        const val TAG = "DemoDailyJob"
    }

}
