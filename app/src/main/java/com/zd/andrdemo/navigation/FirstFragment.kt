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
import kotlinx.android.synthetic.main.fragment_first.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */


class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.i(TAG, "onCreateView: f ")
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var from = ""
        var number: Int = 0
        arguments?.let {
            if (it.containsKey(Arguments_From)) from = it.getString(
                Arguments_From, "")
            if (it.containsKey(Arguments_Number)) number = it.getInt(
                Arguments_Number, 0)
        }
        Log.i(TAG, "onViewCreated: f from :$from , number ：$number")
        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        textview_first.text= "${textview_first.text} \n from :$from , number ：$number"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.i(TAG, "onAttach: f")
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.i(TAG, "onHiddenChanged: f")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause: f")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume: f")
    }
}