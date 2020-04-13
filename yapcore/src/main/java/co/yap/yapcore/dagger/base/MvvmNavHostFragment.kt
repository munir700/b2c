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

package co.yap.yapcore.dagger.base

import android.content.Context
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import co.yap.yapcore.dagger.base.interfaces.CanFetchExtras
import co.yap.yapcore.helpers.extentions.handleExtras
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * [NavHostFragment]-based fragment which supports the handling and further propagation of the common [BaseViewModelActivity] events.
 * <br>
 * To be used as a Host Fragment for when you rely on the [BaseViewModelFragment].
 * <br>
 * When you include this fragment in your layout file you should give it the appropriate id ([R.id.nav_host_fragment])
 */
open class MvvmNavHostFragment : NavHostFragment(), CanFetchExtras {

//    @Inject
//    protected lateinit var daggerFragmentInjectionFactory: InjectingFragmentFactory

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun fetchExtras(extras: Bundle?) {
        childFragmentManager.fragments.handleExtras(extras)
    }
}