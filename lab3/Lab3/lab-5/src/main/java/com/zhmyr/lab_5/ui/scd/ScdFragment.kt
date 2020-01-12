package com.zhmyr.lab_5.ui.scd

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
import com.zhmyr.lab_5.databinding.FragmentScdBinding

class ScdFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bind : FragmentScdBinding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_scd, container, false)
        bind.butSecond1.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_nav_scd_to_fst)
        }
        bind.butSecond3.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_nav_scd_to_thd)
        }
        return bind.root
    }
}