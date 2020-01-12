package com.zhmyr.lab_5.ui.fst

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.zhmyr.lab_5.R
import com.zhmyr.lab_5.databinding.FragmentFstBinding

class FstFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bind : FragmentFstBinding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_fst, container, false)

        bind.butFirst.setOnClickListener{view ->
            view.findNavController().navigate(R.id.action_nav_fst_to_scd)
        }

        return bind.root
    }
}