package info.schwark.androidjobdemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import com.evernote.android.job.DailyJob
import com.evernote.android.job.JobManager
import com.evernote.android.job.JobRequest
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*
import java.util.concurrent.TimeUnit


class DemoActivity : AppCompatActivity() {

    private val onJobComplete = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val executions = intent.getIntExtra("jobId", 0) - scheduledJobId
            line1.text = "Job ran " + executions.toString() + " times"
        }
    }

    private var scheduledJobId : Int
        get() = intent.getIntExtra("scheduledJobId", 0)
        set(value) { intent.putExtra("scheduledJobId",  value) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        scheduledJobId = scheduleJob(DemoDailyJob.TAG, scheduledTimeMs())
    }

    private fun scheduleJob(tag: String, time: Long) : Int {
        DailyJob.scheduleAsync(JobRequest.Builder(tag), time, time)
        return JobManager.instance().allJobRequests.toList()[0].jobId
    }

    private fun scheduledTimeMs() : Long {
        val scheduledTime = Calendar.getInstance().apply { add(Calendar.SECOND, 5) }
        return TimeUnit.HOURS.toMillis(scheduledTime.get(Calendar.HOUR_OF_DAY).toLong()) +
                TimeUnit.MINUTES.toMillis(scheduledTime.get(Calendar.MINUTE).toLong()) +
                TimeUnit.SECONDS.toMillis(scheduledTime.get(Calendar.SECOND).toLong())
    }

    override fun onStart() {
        super.onStart()

        LocalBroadcastManager.getInstance(this).registerReceiver(onJobComplete, IntentFilter("jobFinished"))
    }

    override fun onStop() {
        super.onStop()

        LocalBroadcastManager.getInstance(this).unregisterReceiver(onJobComplete)
    }

}
