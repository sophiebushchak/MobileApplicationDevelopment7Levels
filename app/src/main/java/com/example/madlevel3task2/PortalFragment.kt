package com.example.madlevel3task2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel3task2.helper.PortalAdapter
import com.example.madlevel3task2.model.Portal
import kotlinx.android.synthetic.main.fragment_portal.*
import androidx.fragment.app.setFragmentResultListener
import kotlinx.android.synthetic.main.fragment_create_portal.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PortalFragment : Fragment() {
    private val portals = arrayListOf<Portal>()
    private val portalAdapter = PortalAdapter(portals)

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_portal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeAddPortalResult()
    }

    private fun observeAddPortalResult() {
        var portalTitle : String? = null;
        setFragmentResultListener(PORTAL_REQUEST_KEY) { key, bundle ->
            bundle.getString(BUNDLE_PORTAL_TITLE_KEY
            )?.let {
                portalTitle = it
                bundle.getString(BUNDLE_PORTAL_URL_KEY
                )?.let {
                    val newPortal = Portal(portalTitle!!, it)
                    portals.add(newPortal)
                    portalAdapter.notifyDataSetChanged()
                } ?: Log.e("PortalFragment", "Request triggered but empty portal url!")
            } ?: Log.e("PortalFragment", "Request triggered, but empty portal title!")
        }
    }

    private fun initViews() {
                rvPortals.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL,false)
                rvPortals.adapter = portalAdapter
    }


}