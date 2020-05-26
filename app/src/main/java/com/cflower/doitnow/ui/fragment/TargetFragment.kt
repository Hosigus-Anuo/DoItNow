package com.cflower.doitnow.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cflower.doitnow.R
import kotlinx.android.synthetic.main.activity_time.*

class TargetFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_time, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        start.setOnClickListener {
            wbv_time_clock.start(10000L)
        }
        pause.setOnClickListener {
            wbv_time_clock.pause()
        }
        stop.setOnClickListener {
            wbv_time_clock.stop()
        }

    }


}