@file:JvmName("BundleUtils")

package com.yap.core.extensions

import android.os.Bundle


/**
 * Adds the contents of the specified [Bundle] into
 * the current [Bundle].
 *
 * @return the current [Bundle] with appended content
 */
operator fun Bundle.plus(other: Bundle): Bundle {
    return this.also { it.putAll(other) }
}