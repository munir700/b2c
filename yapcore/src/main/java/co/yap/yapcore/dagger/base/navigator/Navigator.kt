package co.yap.yapcore.dagger.base.navigator

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable

import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import com.github.florent37.inlineactivityresult.callbacks.ActivityResultListener


/* Copyright 2016 Patrick Löwenstein
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ------
 *
 * FILE CHANGED 2017 Tailored Media GmbH
 *
 * */
interface Navigator {

    fun finishActivity()
    fun startActivity(intent: Intent)
    fun startActivityForResult(intent: Intent, @Nullable listener: ActivityResultListener)
    fun startActivity(action: String)
    fun startActivityForResult(intent: Intent?, requestCode: Int)
    fun startActivity(action: String, uri: Uri)
    fun startActivity(activityClass: Class<out Activity>)
    fun startActivity(activityClass: Class<out Activity>, setArgsAction: PlainConsumer<Intent>)
    fun startActivity(
        activityClass: Class<out Activity>,
        setArgsAction: PlainConsumer<Intent>,
        clearAll: Boolean
    )
    fun startActivity(activityClass: Class<out Activity>, args: Bundle)
    fun startActivity(activityClass: Class<out Activity>, args: Parcelable)
    fun startActivity(activityClass: Class<out Activity>, arg: String)
    fun startActivity(activityClass: Class<out Activity>, arg: Int)
    fun startActivityWithTransition(
        activityClass: Class<out Activity>,
        setArgsAction: PlainConsumer<Intent>,
        includeNestedChildren: Boolean, includeStatusBar: Boolean, vararg views: View
    )

    fun startActivityForResult(activityClass: Class<out Activity>, requestCode: Int)
    fun startActivityForResult(
        activityClass: Class<out Activity>,
        setArgsAction: PlainConsumer<Intent>,
        requestCode: Int
    )

    fun startActivityForResult(
        activityClass: Class<out Activity>,
        setArgsAction: PlainConsumer<Intent>, @Nullable listener: ActivityResultListener
    )

    fun startActivityForResult(activityClass: Class<out Activity>, arg: Parcelable, requestCode: Int)
    fun startActivityForResult(activityClass: Class<out Activity>, arg: String, requestCode: Int)
    fun startActivityForResult(activityClass: Class<out Activity>, arg: Int, requestCode: Int)

    fun replaceFragment(@IdRes containerId: Int, fragment: Fragment, vararg transitionViews: View)
    fun replaceFragment(@IdRes containerId: Int, fragment: Fragment, args: Bundle, vararg transitionViews: View)
    fun replaceFragment(
        @IdRes containerId: Int, fragment: Fragment,
        fragmentTag: String,
        args: Bundle,
        vararg transitionViews: View
    )

    fun replaceFragmentAndAddToBackStack(
        @IdRes containerId: Int, fragment: Fragment,
        args: Bundle,
        backstackTag: String,
        vararg transitionViews: View
    )

    fun replaceFragmentAndAddToBackStack(
        @IdRes containerId: Int, fragment: Fragment,
        fragmentTag: String,
        args: Bundle,
        backstackTag: String,
        vararg transitionViews: View
    )

    fun <T : Fragment> findFragmentByTag(tag: String): T?

    fun <T : Fragment> findFragmentById(@IdRes containerId: Int): T?

    companion object {
        val EXTRA_ARG = "_args"
    }
}
