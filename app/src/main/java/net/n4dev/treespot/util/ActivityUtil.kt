package net.n4dev.treespot.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.Nullable

class ActivityUtil {

    companion object {


        fun startActivityWithBundle(@Nullable bundle: Bundle, clas : Class<Any>, context: Context) {
            var intent = Intent(context, clas)
            intent.putExtras(bundle)
            context.startActivity(intent)

        }

        fun startActivity(klass: Class<*>, context: Context) {
            var intent = Intent(context, klass)
            context.startActivity(intent)
        }

        fun getAppImagesDirectory(activity: Activity): String {
            return activity.getFilesDir().toString() + "/TS_Images/"
        }

        fun getAppVideosDirectory(activity: Activity) : String {
            return activity.filesDir.toString() + "/TS_Videos/"
        }
    }

}