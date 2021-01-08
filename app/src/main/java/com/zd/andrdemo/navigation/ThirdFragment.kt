package com.zd.andrdemo.navigation

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.zd.andrdemo.Arguments_From
import com.zd.andrdemo.Arguments_Number
import com.zd.andrdemo.R
import com.zd.andrdemo.TAG
import kotlinx.android.synthetic.main.fragment_second.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ThirdFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(TAG, "onCreateView: t")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated: t")
        view.findViewById<Button>(R.id.button_second).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
        var from = ""
        var number: Int = 0
        arguments?.let {
            if (it.containsKey(Arguments_From)) from = it.getString(
                Arguments_From, "")
            if (it.containsKey(Arguments_Number)) number = it.getInt(
                Arguments_Number, 0)
        }
        Log.i(TAG, "onViewCreated: t from :$from , number ：$number")
        textview_second.text = "${textview_second.text} \n from :$from , number ：$number"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.i(TAG, "onAttach: t")
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.i(TAG, "onHiddenChanged: t")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause: t")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume: t")
    }
}