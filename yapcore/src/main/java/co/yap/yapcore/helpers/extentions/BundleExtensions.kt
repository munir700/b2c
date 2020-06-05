@file:JvmName("BundleUtils")

package co.yap.yapcore.helpers.extentions

import android.os.Bundle


/**
 * Adds the contents of the specified [Bundle] into
 * the current [Bundle].
 *
 * @return the current [Bundle] with appended content
 */
operator fun Bundle.plus(other : Bundle) : Bundle {
    return this.also { it.putAll(other) }
}
