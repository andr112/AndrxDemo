package com.zd.andrdemo

import android.view.View
import androidx.navigation.findNavController

const val TAG = "zdTest"
const val Arguments_From = "from"
const val Arguments_Number = "number"

val homeListItems = listOf(
    ZdListItem("navigation",
        View.OnClickListener { v -> v.findNavController().navigate(R.id.action_navigation) })
)
val majorListItems = listOf(
    ZdListItem("intent",
        View.OnClickListener { v -> v.findNavController().navigate(R.id.action_intent) },"消息传递对象,用来请求操作（启动Activity、启动服务、传递广播）")
)

val questionListItems = listOf(
    ZdListItem("intent",
        View.OnClickListener { v -> v.findNavController().navigate(R.id.action_intent) })
)