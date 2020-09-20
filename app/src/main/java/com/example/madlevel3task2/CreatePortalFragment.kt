package com.example.madlevel3task2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_create_portal.*

const val PORTAL_REQUEST_KEY = "req_portal"
const val BUNDLE_PORTAL_TITLE_KEY = "bundle_portal_title"
const val BUNDLE_PORTAL_URL_KEY = "bundle_portal_url"

class CreatePortalFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_portal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnAddPortal.setOnClickListener{
            onAddPortal()
        }
    }

    private fun onAddPortal() {
        val args = Bundle()
        val portalTitle = etPortalTitle.text.toString()
        val portalUrl = etPortalUrl.text.toString()

        if (portalTitle.isNotBlank() && portalUrl.isNotBlank()) {
            setFragmentResult(PORTAL_REQUEST_KEY, bundleOf(Pair(BUNDLE_PORTAL_TITLE_KEY, portalTitle), Pair(
                BUNDLE_PORTAL_URL_KEY, portalUrl)))
            findNavController().popBackStack()
        }  else {
            Toast.makeText(
                activity,
                "Invalid portal", Toast.LENGTH_SHORT
            ).show()
        }
    }
}