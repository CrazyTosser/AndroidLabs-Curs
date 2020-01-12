package com.zhmyr.lab_5.ui.thd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.zhmyr.lab_5.R
import com.zhmyr.lab_5.databinding.FragmentThdBinding

class ThdFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bind : FragmentThdBinding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_thd, container, false)
        bind.butThird1.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_nav_thd_to_fst)
        }
        bind.butThird2.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_nav_thd_to_scd)
        }
        return bind.root
    }
}