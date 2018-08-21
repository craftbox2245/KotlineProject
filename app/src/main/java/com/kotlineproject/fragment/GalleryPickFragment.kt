package com.kotlineproject.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView

import com.kotlineproject.R
import com.kotlineproject.custome.PathUtil
import java.io.File
import com.kotlineproject.custome.ImageInputHelper
import android.provider.MediaStore
import android.graphics.Bitmap
import java.io.IOException


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [firstFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [firstFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class GalleryPickFragment : Fragment(), ImageInputHelper.ImageActionListener {

    override fun onImageTakenFromCamera(uri: Uri?, imageFile: File?) {
        if (uri != null) {
            imageInputHelper.requestCropImage(uri, 800, 450, 16, 9)
        };
    }

    override fun onImageCropped(uri: Uri?, imageFile: File?) {
        try {
            // getting bitmap from uri
            val bitmap = MediaStore.Images.Media.getBitmap(activity!!.applicationContext.getContentResolver(), uri)
            // showing bitmap in image view
            pickImg.setImageBitmap(bitmap)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onImageSelectedFromGallery(uri: Uri?, imageFile: File?) {
        if (uri != null) {
            imageInputHelper.requestCropImage(uri, 800, 450, 16, 9)
        };
    }


    lateinit var pickImg: ImageView
    lateinit var galleryBtn: Button
    lateinit var file: File
    lateinit var imageInputHelper: ImageInputHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView: View = inflater.inflate(R.layout.fragment_gallery, container, false)
        imageInputHelper = ImageInputHelper(this)
        imageInputHelper.setImageActionListener(this)

        pickImg = rootView.findViewById(R.id.pick_img) as ImageView
        galleryBtn = rootView.findViewById(R.id.gallery_btn) as Button

        galleryBtn.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                intent.type = "*/*"
                startActivityForResult(intent, 7)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        pickImg.setOnClickListener {
            imageInputHelper.selectImageFromGallery();
        }

        return rootView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            if (requestCode == 23) {
                imageInputHelper.onActivityResult(requestCode, resultCode, data!!);
            } else {
                if (resultCode === Activity.RESULT_OK) {
                    val ur = data!!.getData()
                    try {
                        val PathHolder = PathUtil.getPath(activity, ur)
                        file = File(PathHolder)
                        if (file != null) {
                        }

                        pickImg.setImageURI(ur)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
