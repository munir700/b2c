package co.yap.yapcore.helpers.jwtparser

import androidx.annotation.Keep
import java.util.*
import kotlin.Throws

/**
 * The Claim class holds the value in a generic way so that it can be recovered in many representations.
 */
@Keep
interface Claim {
    /**
     * Get this Claim as a Boolean.
     * If the value isn't of type Boolean or it can't be converted to a Boolean, null will be returned.
     *
     * @return the value as a Boolean or null.
     */
    fun asBoolean(): Boolean?

    /**
     * Get this Claim as an Integer.
     * If the value isn't of type Integer or it can't be converted to an Integer, null will be returned.
     *
     * @return the value as an Integer or null.
     */
    fun asInt(): Int?

    /**
     * Get this Claim as an Long.
     * If the value isn't of type Long or it can't be converted to an Long, null will be returned.
     *
     * @return the value as an Long or null.
     */
    fun asLong(): Long?

    /**
     * Get this Claim as a Double.
     * If the value isn't of type Double or it can't be converted to a Double, null will be returned.
     *
     * @return the value as a Double or null.
     */
    fun asDouble(): Double?

    /**
     * Get this Claim as a String.
     * If the value isn't of type String or it can't be converted to a String, null will be returned.
     *
     * @return the value as a String or null.
     */
    fun asString(): String?

    /**
     * Get this Claim as a Date.
     * If the value can't be converted to a Date, null will be returned.
     *
     * @return the value as a Date or null.
     */
    fun asDate(): Date?

    /**
     * Get this Claim as an Array of type T.
     * If the value isn't an Array, an empty Array will be returned.
     *
     * @return the value as an Array or an empty Array.
     * @throws DecodeException if the values inside the Array can't be converted to a class T.
     */
    @Throws(DecodeException::class)
    fun <T> asArray(tClazz: Class<T>?): Array<T>?

    /**
     * Get this Claim as a List of type T.
     * If the value isn't an Array, an empty List will be returned.
     *
     * @return the value as a List or an empty List.
     * @throws DecodeException if the values inside the List can't be converted to a class T.
     */
    @Throws(DecodeException::class)
    fun <T> asList(tClazz: Class<T>?): List<T>?

    /**
     * Get this Claim as a Object of type T.
     * If the value isn't of type Object, null will be returned.
     *
     * @return the value as a Object of type T or null.
     * @throws DecodeException if the value can't be converted to a class T.
     */
    @Throws(DecodeException::class)
    fun <T> asObject(tClazz: Class<T>?): T?
}