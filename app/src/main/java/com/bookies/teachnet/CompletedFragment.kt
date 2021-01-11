package com.bookies.teachnet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
class CompletedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_completed, container, false)
        val togo: Button = view.findViewById(R.id.togo)
        togo.setOnClickListener {
            val intent: Intent = Intent(activity, CreatorAndInterpretorActivity::class.java)
            startActivity(intent)
        }
        return view
    }
}