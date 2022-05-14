package suitmedia.com.dev.suitemedia.core.extension

import android.util.Patterns
import android.webkit.URLUtil
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun String.isValidEmail(): Boolean {
    return this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String?.isValidPhone(): Boolean {
    val regexPhone = "^(\\+62|62)?[\\s-]?0?8[1-9]{1}\\d{1}[\\s-]?\\d{4}[\\s-]?\\d{2,5}\$".toRegex()

    return (this?.length in 7..13) && (this?.matches(regexPhone) == true)
}

fun String?.isValidUrl(): Boolean {
    return URLUtil.isValidUrl(this)
}

fun Int.toRpCurrency(
    groupingSeparator: Char,
    decimalSeparator: Char,
    showRpSymbol: Boolean = true
): String {
    return this.toDouble().toRpCurrency(groupingSeparator, decimalSeparator, showRpSymbol)
}

fun Float.toRpCurrency(
    groupingSeparator: Char,
    decimalSeparator: Char,
    showRpSymbol: Boolean = true
): String {
    return this.toDouble().toRpCurrency(groupingSeparator, decimalSeparator, showRpSymbol)
}

fun Long.toRpCurrency(
    groupingSeparator: Char,
    decimalSeparator: Char,
    showRpSymbol: Boolean = true
): String {
    return this.toDouble().toRpCurrency(groupingSeparator, decimalSeparator, showRpSymbol)
}

fun Double.toRpCurrency(
    groupingSeparator: Char,
    decimalSeparator: Char,
    showRpSymbol: Boolean = true
): String {
    val symbols = DecimalFormatSymbols()
    symbols.groupingSeparator = groupingSeparator
    symbols.decimalSeparator = decimalSeparator
    if (showRpSymbol) {
        return DecimalFormat("Rp#,###.##", symbols).format(this)
    } else {
        return DecimalFormat("#,###.##", symbols).format(this)
    }
}

fun String.isMatchCurrencyRp(): Boolean {
    /**
     * This [regex] only match with example string like the following :
     * - Rp1.000.000
     * - Rp1.000.000,00
     * - Rp1,000,000
     * - Rp1,000,000.00
     *
     * or after Rp have a single space e.g Rp 1.000.000
     *
     * Notes : This [regex] supports less than 10 Billion (10.000.000.000).
     * If you need more, just increase the {14} value of [regex] as needed
     */
    val regex = ("^Rp[ ]?(?:" +
            "(?![,0-9]{14})\\d{1,3}(?:,\\d{3})*(?:\\.\\d{1,2})?" +
            "|(?![.0-9]{14})\\d{1,3}(?:\\.\\d{3})*(?:,\\d{1,2})" +
            "?)\$").toRegex()
    return this.matches(regex)
}

fun String.clearRpCurrency(): String = this.replace("[Rp,. ]".toRegex(), "")

fun String.currencyRptoDouble(): Double {
    return if (this.isMatchCurrencyRp()) {
        this.clearRpCurrency().toDouble()
    } else {
        throw IllegalArgumentException("An appropriate currency is required")
    }
}

fun String.toDate(pattern: String): Date {
    when (val date = SimpleDateFormat(pattern, Locale.getDefault()).parse(this)) {
        null -> throw IllegalArgumentException("Parse date failed, please attention to your pattern")
        else -> return date
    }
}

fun String.toDate(pattern: String, locale: Locale): Date {
    when (val date = SimpleDateFormat(pattern, locale).parse(this)) {
        null -> throw IllegalArgumentException("Parse date failed, please attention to your pattern")
        else -> return date
    }
}

fun String.toDateOptional(pattern: String, optionalPattern: String): Date {
    try {
        return this.toDate(pattern)
    } catch (e: ParseException) {
        return this.toDate(optionalPattern)
    }
}

fun String.phoneClearPrefix(vararg prefix: String, ignoreCase: Boolean = false): String {
    var value: String = this
    prefix.forEach {
        when {
            startsWith(it, ignoreCase) -> {
                value = substring(it.length, length)
            }
        }
    }
    return value
}

fun String.globalPhone(countryCode: String = "+62"): String {
    return when {
        startsWith(countryCode) -> this
        !startsWith(countryCode) && get(0) == '0' -> countryCode + this.substring(
            1,
            this.length
        )
        else -> countryCode + this
    }
}

fun String.capitalizeEachWord(locale: Locale): String {
    if (isNotEmpty()) {
        val words = split(" ")
        if (words.isNotEmpty()) {
            val builder = StringBuilder()
            words.forEachIndexed { index, s ->
                val result = s.toLowerCase(locale).capitalize(locale)
                if (index == 0) {
                    builder.append(result)
                } else {
                    builder.append(" $result")
                }
            }
            return builder.toString()
        } else {
            return this.toLowerCase(locale).capitalize(locale)
        }
    }
    return this
}

fun Int.toCurrency(
    groupingSeparator: Char,
    decimalSeparator: Char
): String {
    return this.toDouble().toCurrency(groupingSeparator, decimalSeparator)
}

fun Float.toCurrency(
    groupingSeparator: Char,
    decimalSeparator: Char
): String {
    return this.toDouble().toCurrency(groupingSeparator, decimalSeparator)
}

fun Long.toCurrency(
    groupingSeparator: Char,
    decimalSeparator: Char
): String {
    return this.toDouble().toCurrency(groupingSeparator, decimalSeparator)
}

fun Double.toCurrency(
    groupingSeparator: Char,
    decimalSeparator: Char
): String {
    val symbols = DecimalFormatSymbols()
    symbols.groupingSeparator = groupingSeparator
    symbols.decimalSeparator = decimalSeparator
    return DecimalFormat("#,###.##", symbols).format(this)

}
