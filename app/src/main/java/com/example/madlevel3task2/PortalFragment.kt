package com.example.madlevel3task2

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel3task2.helper.PortalAdapter
import com.example.madlevel3task2.model.Portal
import kotlinx.android.synthetic.main.fragment_portal.*
import androidx.fragment.app.setFragmentResultListener


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PortalFragment : Fragment() {
    private val portals = arrayListOf<Portal>()
    private val portalAdapter = PortalAdapter(portals) { portal : Portal -> portalClicked(portal) }

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

    private fun initViews() {
        rvPortals.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL,false)
        rvPortals.adapter = portalAdapter
    }

    /*
    * Observes the Portal request from CreatePortalFragment and adds the portal if the bundle has
    * the portal title and url.*/
    private fun observeAddPortalResult() {
        var portalTitle : String? = null
        setFragmentResultListener(PORTAL_REQUEST_KEY) { _, bundle ->
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

    /*
    * Clicker handler for Portal, opens url in the portal as a Chrome Custom Tab
    * Credit/inspiration: https://github.com/anandwana001/mindorks-cct/blob/master/app/src/main/java/com/akshay/mindorks_cct/MainActivity.kt*/
    private fun portalClicked(portal: Portal) {
        val uri = portal.url
        val builder = CustomTabsIntent.Builder()
        builder.setToolbarColor(ContextCompat.getColor(this.requireContext(), R.color.colorPrimary))
        builder.addDefaultShareMenuItem()
        builder.setShowTitle(true)
        builder.setStartAnimations(this.requireContext(), android.R.anim.fade_in, android.R.anim.fade_out)
        builder.setExitAnimations(this.requireContext(), android.R.anim.fade_in, android.R.anim.fade_out)
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this.requireContext(), Uri.parse(uri))
        }
    }
