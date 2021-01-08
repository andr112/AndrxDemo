package com.zd.andrdemo.navigation

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.zd.andrdemo.Arguments_From
import com.zd.andrdemo.Arguments_Number
import com.zd.andrdemo.R
import kotlinx.android.synthetic.main.activity_navigation.*

class NavigationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_test, menu)
        return true
    }

    private var actionSettingsClickNum = 0
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        actionSettingsClickNum++
        val args = Bundle().apply {
            putInt(Arguments_Number, actionSettingsClickNum)
            putString(Arguments_From, "TestActivity")
        }
        return when (item.itemId) {
            R.id.action_next -> {
                val navController = nav_host_fragment.findNavController()
                val isFirst = navController.currentDestination?.id == R.id.FirstFragmentTest
                navController.navigate(
                    if (isFirst) R.id.SecondFragmentTest else R.id.FirstFragmentTest,
                    args, getNavOptions(isFirst)
                )
                true
            }
            R.id.action_up -> {
                // nav_host_fragment.findNavController().navigateUp()
                nav_host_fragment.findNavController().popBackStack()
                true
            }
            R.id.action_to_b -> {
                nav_host_fragment.findNavController().navigate(R.id.activityTestB)
                true
            }
            R.id.action_deepLink -> {
                val uri = Uri.parse("http://www.xixi.com/test")
                if (nav_host_fragment.findNavController().graph.hasDeepLink(uri))
                    nav_host_fragment.findNavController().navigate(uri)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (nav_host_fragment.findNavController().popBackStack()) else super.onBackPressed()
    }

    private fun getNavOptions(isLeft: Boolean): NavOptions {
        return NavOptions.Builder().apply {
            if (isLeft) {
                setEnterAnim(R.anim.slide_in_right) //进入动画
                setExitAnim(R.anim.slide_out_left) //退出动画
                //setPopEnterAnim(R.anim.slide_out_right) //弹出进入动画
                // setPopExitAnim(R.anim.slide_in_left) //弹出退出动画
            } else {
                setEnterAnim(R.anim.slide_in_left)
                setExitAnim(R.anim.slide_out_right)
            }
        }.build()
    }
}