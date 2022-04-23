package net.n4dev.treespot.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class DateConverter {

  companion object {
      private val DATE_UI_PRETTY = "MMM dd yyyy hh:mm a"
      private val DATE_STORED_FORMAT = "yyyy-MM-dd HH:mm:ss"
      private val df: DateFormat = SimpleDateFormat(DATE_STORED_FORMAT)

      fun toDate(stringdate: String?): Date {
          return df.parse(stringdate)
      }

      fun toDate(count : Long) : Date {
          return Date(count)
      }

      fun toString(date: Date?): String? {
          return if (date != null) {
              val dateTimeFormat = SimpleDateFormat(DATE_STORED_FORMAT, Locale.US)
              dateTimeFormat.format(date)
          } else {
              null
          }
      }

      fun toPrettyString(date: Date?): String? {
          if (date != null) {
              val dateTimeFormat = SimpleDateFormat(DATE_UI_PRETTY, Locale.US)
              return dateTimeFormat.format(date)
          }
          return null
      }

      @JvmStatic
      fun toPrettyString(count : Long) : String {
          val convertDate = toDate(count)
          val dateTimeFormat = SimpleDateFormat(DATE_UI_PRETTY, Locale.US)
          return dateTimeFormat.format(convertDate)
      }
  }
}