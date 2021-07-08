/*
 * Copyright 2019, The Android Open Source Project
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

package com.zd.andrdemo.major

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.zd.andrdemo.R
import com.zd.andrdemo.view.SearchHistoryView


class SearchFragment : Fragment() {
    private var historyView: SearchHistoryView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editText = view.findViewById<EditText>(R.id.search_et)
        editText
            .setOnEditorActionListener { v, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!v.text.toString().isBlank()) {
                        val text = v.text.toString()
                        search(text)
                        v.text = ""
                    }
                    return@setOnEditorActionListener true
                }
                false
            }
        view.findViewById<View>(R.id.search_bt).setOnClickListener {
            if (!editText.text.toString().isBlank()) {
                val text = editText.text.toString()
                search(text)
                editText.setText("")
            }
        }
        historyView = view.findViewById(R.id.historyView)
        historyView?.setOnHistoryItemClicker(object : SearchHistoryView.OnHistoryItemClick {
            override fun onClick(item: String, isOverflow: Boolean) {
                if (isOverflow) {
                    val isLimit = historyView!!.isLimit()
                    historyView!!.setLimit(!isLimit)
                } else editText.setText(item)
            }
        })
        val histories = arrayListOf("衣服", "夏季女上衣", "夏季连衣裙长裙", "洗发水", "玩具男宝宝两岁")
        historyView?.setHistorys(histories)
    }

    private fun search(text: String) {
        historyView?.addHistory(text)
    }
}
