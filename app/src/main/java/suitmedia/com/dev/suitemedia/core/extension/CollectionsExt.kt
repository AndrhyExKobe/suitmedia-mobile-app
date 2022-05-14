package suitmedia.com.dev.suitemedia.core.extension

/**
 * Returns the first Pair index with element matching the given [predicate], or `null` if element was not found.
 */
public inline fun <T> Iterable<T>.firstWithIndexOrNull(predicate: (T) -> Boolean): Pair<Int,T>? {
    for ((index, element) in this.withIndex()) {
        if (predicate(element)) return index to element
    }
    return null
}