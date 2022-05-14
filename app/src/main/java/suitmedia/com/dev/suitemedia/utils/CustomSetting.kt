package suitmedia.com.dev.suitemedia.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.text.InputType
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatAutoCompleteTextView


/**
 * Created by Andri Dwi Utomo on 4/11/2021.
 * Mallsampah Indonesia
 * andri@mallsampah.com
 */
object CustomSetting {
    fun changeColorActionBar(activity: Activity, @ColorInt color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = activity?.window!!
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = color
        }
    }

    //function to check string is palindrome or not
    fun isPalindromeString(inputStr: String): Boolean {
        val sb = StringBuilder(inputStr)

        //reverse the string
        val reverseStr = sb.reverse().toString()

        //compare reversed string with input string
        return inputStr.equals(reverseStr, ignoreCase = true)
    }
}