package com.zd.andrdemo

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    private var actionSettingsClickNum: Int = 0
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_switch -> {
                actionSettingsClickNum++
                val args = Bundle().apply {
                    putInt(Arguments_Number, actionSettingsClickNum)
                    putString(Arguments_From, "MainActivity")
                }
                val navController = nav_host_fragment.findNavController()
                if (navController.currentDestination?.id == R.id.FirstFragment) {
                    val navOptions = NavOptions.Builder()
                        .setEnterAnim(R.anim.slide_in_right) //进入动画
                        .setExitAnim(R.anim.slide_out_left) //退出动画
                        //.setPopEnterAnim(R.anim.slide_out_right) //弹出进入动画
                        // .setPopExitAnim(R.anim.slide_in_left) //弹出退出动画
                        .build()
                    navController.navigate(R.id.SecondFragment, args, navOptions)
                } else {
                    val navOptions = NavOptions.Builder()
                        .setEnterAnim(R.anim.slide_in_left)
                        .setExitAnim(R.anim.slide_out_right).build()
                    navController.navigate(R.id.FirstFragment, args, navOptions)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}