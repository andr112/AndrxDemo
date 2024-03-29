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
    ZdListItem(
        "intent",
        View.OnClickListener { v -> v.findNavController().navigate(R.id.action_intent) },
        "消息传递对象,用来请求操作（启动Activity、启动服务、传递广播）"
    ),
    ZdListItem(
        "search",
        View.OnClickListener { v -> v.findNavController().navigate(R.id.action_search) },
        "自定义搜索历史记录view ，默认展示两行，支持收起和展开"
    ),
    ZdListItem(
        "animate",
        View.OnClickListener { v -> v.findNavController().navigate(R.id.action_animate) },
        "动画相关,自绘animated-vector三角形选中一圈动画"
    )
)

val questionListItems = listOf(
    ZdListItem("intent",
        View.OnClickListener { v -> v.findNavController().navigate(R.id.action_intent) }),
    ZdListItem("intent",
        View.OnClickListener { v -> v.findNavController().navigate(R.id.action_intent) })
)