package net.n4dev.treespot.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import net.n4dev.treespot.R

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

        fun snack(view : View, string: String, error : Boolean) {
            val snackbar = Snackbar.make(view, string, Snackbar.LENGTH_LONG)
            val snackView = snackbar.view

            snackbar.setTextColor(ContextCompat.getColor(snackView.context, R.color.md_white_1000))

            if(error) {
                snackView.setBackgroundColor(ContextCompat.getColor(snackView.context, R.color.md_red_900))
            } else {
                snackView.setBackgroundColor(ContextCompat.getColor(snackView.context, R.color.primaryDarkColor))
            }

            snackbar.show()
        }
    }

}