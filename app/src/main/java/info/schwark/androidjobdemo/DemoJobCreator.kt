package info.schwark.androidjobdemo

import com.evernote.android.job.Job
import com.evernote.android.job.JobCreator



class DemoJobCreator : JobCreator {

    override fun create(tag: String): Job? {
        return when (tag) {
            DemoDailyJob.TAG -> DemoDailyJob()
            else -> throw IllegalArgumentException("Unknown tag $tag")
        }
    }

}
