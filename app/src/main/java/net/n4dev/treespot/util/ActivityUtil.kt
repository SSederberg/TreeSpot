package net.n4dev.treespot.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import net.n4dev.treespot.R
import net.n4dev.treespot.core.api.ITreeSpot
import java.io.File

class ActivityUtil {

    companion object {

         fun startActivity(@Nullable bundle: Bundle, klass : Class<*>, context: Context, internetRequired : Boolean) {
             if(internetRequired) {
                 if(DeviceConnectionHelper.isConnected(context)) {
                     val intent = Intent(context, klass)
                     intent.putExtras(bundle)
                     context.startActivity(intent)
                 }
             } else {
                 val intent = Intent(context, klass)
                 intent.putExtras(bundle)
                 context.startActivity(intent)
             }

        }

        fun startActivity(@Nullable bundle: Bundle, klass : Class<*>, activity: Activity, internetRequired: Boolean) {
            if(internetRequired) {
                if(DeviceConnectionHelper.isConnected(activity)) {
                    val intent = Intent(activity, klass)
                    intent.putExtras(bundle)
                    activity.startActivity(intent)
                }
            } else {
                val intent = Intent(activity, klass)
                intent.putExtras(bundle)
                activity.startActivity(intent)
            }
        }


        fun startActivity(klass: Class<*>, activity: Activity) {
            val intent = Intent(activity, klass)
            activity.startActivity(intent)
        }

        fun getAppImagesDirectory(activity: Activity): String {
            return activity.filesDir.toString() + "/TS_Images/"
        }

        fun getAppImagesDirectoryAsFile(activity: Activity) : File {
            return File(getAppImagesDirectory(activity))
        }

        fun getAppVideosDirectory(activity: Activity) : String {
            return activity.filesDir.toString() + "/TS_Videos/"
        }

        fun getAppFriendImagesDirectory(activity: Activity) : String {
            return activity.filesDir.toString() + "/TS_Friends/"
        }

        fun getAppFriendImagesDirectory(context: Context) : String {
            return context.filesDir.toString() + "/TS_Friends/"
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

        fun toast(context: Context, string: String, error: Boolean) {
            Toast.makeText(context, string, Toast.LENGTH_LONG).show()
        }

         fun forwardToGMaps(entity: ITreeSpot, context: Context) {
            var formatted = ""
            var uri: Uri? = null
            formatted = entity.getLatNorth() + "," + entity.getLongWest() + "(" + entity.getDescription() + ")"
            uri = Uri.parse("geo:0,0?q=$formatted")
            goToGoogleMaps(context, uri)
        }

        fun goToGoogleMaps(context : Context, uri : Uri) {
            val mapsIntent = Intent(Intent.ACTION_VIEW, uri)
            mapsIntent.setPackage("com.google.android.apps.maps")

            if(mapsIntent.resolveActivity(context.packageManager) != null) {
                context.startActivity(mapsIntent)
            } else {
                ActivityUtil.toast(context, "Failed to find Google Maps on device!", true)
            }
        }
    }

}