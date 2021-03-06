/*
 * Copyright (C) 2018 Tang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.recurve.dagger2

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.recurve.adapter.creator.Creator
import com.recurve.apollo.dagger2.R
import com.recurve.core.databinding.ActivityRecurveBinding
import com.recurve.core.databinding.ContentListBinding
import com.recurve.core.ui.appbar
import com.recurve.core.ui.creator.*
import com.recurve.core.ui.creator.ext.AppbarExt
import com.recurve.core.ui.fragment.RecurveListFragment
import dagger.android.support.DaggerAppCompatActivity

open class RecurveDaggerListActivity :
        DaggerAppCompatActivity(), ContentCreate, RecyclerViewInit {


    private lateinit var activityRecurveBinding: ActivityRecurveBinding
    private lateinit var contentListBinding: ContentListBinding

    private lateinit var listFragment: RecurveListFragment

    private lateinit var appbarCreator: AppbarCreator
    private lateinit var contentCreate: ContentCreate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRecurveBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_recurve)
        contentCreate = RecurveContentCreate(activityRecurveBinding)
        appbarCreator = RecurveAppbarCreator(this, activityRecurveBinding)
        initView()
    }

    override fun <Binding : ViewDataBinding> initContentBinding(@LayoutRes layoutId: Int): Binding
            = contentCreate.initContentBinding(layoutId)

    override fun addItemCreator(creator: Creator<*>) {
        listFragment.addItemCreator(creator)
    }

    override fun addItemCreator(index: Int, creator: Creator<*>) {
        listFragment.addItemCreator(index, creator)
    }

    override fun setLayoutManager(lm: RecyclerView.LayoutManager){
        listFragment.setLayoutManager(lm)
    }

    private fun initView(){
        contentListBinding = contentCreate.initContentBinding(R.layout.content_list)
        listFragment = RecurveListFragment()
        supportFragmentManager.beginTransaction()
                .replace(R.id.content, listFragment).commit()
    }

    fun appbar(init: AppbarExt.() -> Unit) {
        appbar(appbarCreator, init)
    }

}