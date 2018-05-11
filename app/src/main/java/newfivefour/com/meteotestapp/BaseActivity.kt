package newfivefour.com.meteotestapp

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

import net.hockeyapp.android.CrashManager
import net.hockeyapp.android.UpdateManager
import android.content.pm.PackageManager
import android.R.attr.versionName
import android.content.pm.PackageInfo
import android.widget.TextView


open class BaseActivity : AppCompatActivity() {

    val TAG: String = "BaseActivity"
    var versionCode: String = "Unknown"

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkForUpdates()
    }

    public override fun onResume() {
        super.onResume()
        try {
            val pInfo = this.packageManager.getPackageInfo(packageName, 0)
            val version = pInfo.versionName
            val versionCode = pInfo.versionCode
            var tv = findViewById<TextView>(R.id.version_code)
            tv.setText("$versionCode");
        } catch (e: Exception) {
            e.printStackTrace()
        }
        checkForCrashes()
        checkForUpdates()
    }

    public override fun onPause() {
        super.onPause()
        unregisterManagers()
    }

    public override fun onDestroy() {
        super.onDestroy()
        unregisterManagers()
    }

    private fun checkForCrashes() {
        CrashManager.register(this)
    }

    private fun checkForUpdates() {
        // Remove this for store builds!
        Log.d(TAG, "Looking for updates");
        UpdateManager.register(this)
    }

    private fun unregisterManagers() {
        UpdateManager.unregister()
    }

}