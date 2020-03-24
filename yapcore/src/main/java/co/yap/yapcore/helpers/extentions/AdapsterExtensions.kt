/*
 * Copyright 2018 Arthur Ivanets, arthur.ivanets.l@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:JvmName("AdapsterUtils")

package co.yap.yapcore.helpers.extentions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter


inline fun FragmentPagerAdapter.forEachFragment(consumer : (Fragment) -> Unit) {
    for(i in 0 until this.count) {
        consumer(this.getItem(i))
    }
}

val PagerAdapter.isEmpty : Boolean
    get() = (this.count == 0)

val RecyclerView.Adapter<*>.isEmpty : Boolean
    get() = (this.itemCount == 0)