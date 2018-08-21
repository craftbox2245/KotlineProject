package com.kotlineproject.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.kotlineproject.R


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [firstFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [firstFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class SecondFragment : Fragment() {

    lateinit var simpleTxt: TextView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView: View = inflater.inflate(R.layout.fragment_first, container, false)

        simpleTxt = rootView.findViewById(R.id.simple_txt) as TextView

        try {
            simpleTxt.text = "Second Fragment"
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return rootView
    }

}
