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

import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.zd.andrdemo.R


class IntentFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_intent, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.startAppXsBt).setOnClickListener {
            onClickStartXs(it)
        }
        view.findViewById<View>(R.id.startAppYsBt).setOnClickListener {
            onClickStartYs(it)
        }
        view.findViewById<View>(R.id.startServiceBt).setOnClickListener {
            //public ComponentName startService(Intent service)
            //public boolean bindService(Intent service, ServiceConnection conn, int flags)
            startActivity( Intent(Intent.ACTION_VIEW).apply { data = Uri.parse("http://login") })
        }
        view.findViewById<View>(R.id.sendBroadcastBt).setOnClickListener {
            //public void sendBroadcast(Intent intent)
            //public void sendOrderedBroadcast(Intent intent, String receiverPermission)
            //sendStickyBroadcast(Intent intent)
            startActivity( Intent(Intent.ACTION_VIEW).apply { data = Uri.parse("http://login") })
        }
    }

   private fun onClickStartXs(view: View) {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        val cn =
            ComponentName("com.duomai.cpsapp", "com.duomai.cpsapp.page.splash.SplashActivity")
        intent.component = cn
        startActivity(intent)
    }

    private fun onClickStartYs(view: View) {
        val intent = Intent("test")
        //intent.data = Uri.parse("CPS://login")
        intent.data =Uri.parse("AXE://haha")
        startActivity(intent)
    }
}
