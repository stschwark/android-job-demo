package info.schwark.androidjobdemo

import android.app.Application
import com.evernote.android.job.JobManager



class DemoApp : Application() {

    override fun onCreate() {
        super.onCreate()

        JobManager.create(this).addJobCreator(DemoJobCreator())
    }

}
