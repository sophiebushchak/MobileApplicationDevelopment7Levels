package com.example.madlevel7task1

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_create_profile.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class CreateProfileFragment : Fragment() {
    private val viewModel: ProfileViewModel by activityViewModels()
    private var profileImageUri: Uri? = null
    companion object {
        const val GALLERY_REQUEST_CODE = 100
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnGallery.setOnClickListener {
            onGalleryClick()
        }

        btnConfirm.setOnClickListener {
            onConfirmClick()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                GALLERY_REQUEST_CODE -> {
                    profileImageUri = data?.data
                    ivProfileImage.setImageURI(profileImageUri)
                }
            }
        }
    }

    private fun CharSequence?.ifNullOrEmpty(default: String) =
        if (this.isNullOrEmpty()) default else this.toString()

    private fun Uri?.ifNullOrEmpty() =
        this?.toString() ?: ""

    private fun onGalleryClick() {
        // Create an Intent with action as ACTION_PICK
        val galleryIntent = Intent(Intent.ACTION_PICK)

        // Sets the type as image/*. This ensures only components of type image are selected
        galleryIntent.type = "image/*"

        // Start the activity using the gallery intent
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE)
    }

    private fun onConfirmClick() {
        viewModel.createProfile(
            etFirstName.text.ifNullOrEmpty(""),
            etLastName.text.ifNullOrEmpty(""),
            etProfileDescription.text.ifNullOrEmpty(""),
            profileImageUri.ifNullOrEmpty()
        )

        observeProfileCreation()

        findNavController().navigate(R.id.profileFragment)
    }

    private fun observeProfileCreation() {
        viewModel.createSuccess.observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity, R.string.successfully_created_profile, Toast.LENGTH_LONG)
                .show()
            findNavController().popBackStack()
        })

        viewModel.errorText.observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        })
    }

}