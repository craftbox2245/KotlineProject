package com.kotlineproject.custome

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.util.Log

import java.io.File
import java.io.IOException

/**
 * Created by CRAFT BOX on 12/19/2016.
 */

class ImageInputHelper {

    private var tempFileFromSource: File? = null
    private var tempUriFromSource: Uri? = null

    private var tempFileFromCrop: File? = null
    private var tempUriFromCrop: Uri? = null

    /**
     * Activity object that will be used while calling startActivityForResult(). Activity then will
     * receive the callbacks to its own onActivityResult() and is responsible of calling the
     * onActivityResult() of the ImageInputHelper for handling result and being notified.
     */
    private var mContext: Activity? = null

    /**
     * Fragment object that will be used while calling startActivityForResult(). Fragment then will
     * receive the callbacks to its own onActivityResult() and is responsible of calling the
     * onActivityResult() of the ImageInputHelper for handling result and being notified.
     */
    private var fragment: Fragment? = null

    /**
     * Listener instance for callbacks on user events. It must be set to be able to use
     * the ImageInputHelper object.
     */
    private var imageActionListener: ImageActionListener? = null

    constructor(mContext: Activity) {
        this.mContext = mContext
    }

    constructor(fragment: Fragment) {
        this.fragment = fragment
        this.mContext = fragment.activity
    }

    fun setImageActionListener(imageActionListener: ImageActionListener) {
        this.imageActionListener = imageActionListener
    }

    /**
     * Handles the result of events that the Activity or Fragment receives on its own
     * onActivityResult(). This method must be called inside the onActivityResult()
     * of the container Activity or Fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {

        if (requestCode == REQUEST_PICTURE_FROM_GALLERY && resultCode == Activity.RESULT_OK) {

            Log.d(TAG, "Image selected from gallery")
            imageActionListener!!.onImageSelectedFromGallery(data.data, tempFileFromSource)

        } else if (requestCode == REQUEST_PICTURE_FROM_CAMERA && resultCode == Activity.RESULT_OK) {

            Log.d(TAG, "Image selected from camera")
            imageActionListener!!.onImageTakenFromCamera(tempUriFromSource, tempFileFromSource)

        } else if (requestCode == REQUEST_CROP_PICTURE && resultCode == Activity.RESULT_OK) {

            Log.d(TAG, "Image returned from crop")
            imageActionListener!!.onImageCropped(tempUriFromCrop, tempFileFromCrop)
        }
    }

    /**
     * Starts an intent for selecting image from gallery. The result is returned to the
     * onImageSelectedFromGallery() method of the ImageSelectionListener interface.
     */
    fun selectImageFromGallery() {
        checkListener()

        if (tempFileFromSource == null) {
            try {
                tempFileFromSource = File.createTempFile("choose", ".png", mContext!!.externalCacheDir)
                tempUriFromSource = Uri.fromFile(tempFileFromSource)
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, tempUriFromSource)
        if (fragment == null) {
            mContext!!.startActivityForResult(intent, REQUEST_PICTURE_FROM_GALLERY)
        } else {
            fragment!!.activity!!.startActivityForResult(intent, REQUEST_PICTURE_FROM_GALLERY)
        }
    }

    /**
     * Starts an intent for taking photo with camera. The result is returned to the
     * onImageTakenFromCamera() method of the ImageSelectionListener interface.
     */
    fun takePhotoWithCamera() {
        checkListener()

        if (tempFileFromSource == null) {
            try {
                tempFileFromSource = File.createTempFile("choose", ".png", mContext!!.externalCacheDir)
                tempUriFromSource = Uri.fromFile(tempFileFromSource)
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, tempUriFromSource)
        if (fragment == null) {
            mContext!!.startActivityForResult(intent, REQUEST_PICTURE_FROM_CAMERA)
        } else {
            fragment!!.startActivityForResult(intent, REQUEST_PICTURE_FROM_CAMERA)
        }
    }

    /**
     * Starts an intent for cropping an image that is saved in the uri. The result is
     * returned to the onImageCropped() method of the ImageSelectionListener interface.
     *
     * @param uri     uri that contains the data of the image to crop
     * @param outputX width of the result image
     * @param outputY height of the result image
     * @param aspectX horizontal ratio value while cutting the image
     * @param aspectY vertical ratio value of while cutting the image
     */
    fun requestCropImage(uri: Uri, outputX: Int, outputY: Int, aspectX: Int, aspectY: Int) {
        checkListener()

        if (tempFileFromCrop == null) {
            try {
                tempFileFromCrop = File.createTempFile("crop", ".png", mContext!!.externalCacheDir)
                tempUriFromCrop = Uri.fromFile(tempFileFromCrop)
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        // open crop intent when user selects image
        val intent = Intent("com.android.camera.action.CROP")
        intent.setDataAndType(uri, "image/*")
        intent.putExtra("output", tempUriFromCrop)
        intent.putExtra("outputX", outputX)
        intent.putExtra("outputY", outputY)
        intent.putExtra("aspectX", aspectX)
        intent.putExtra("aspectY", aspectY)
        intent.putExtra("scale", true)
        intent.putExtra("noFaceDetection", true)
        if (fragment == null) {
            mContext!!.startActivityForResult(intent, REQUEST_CROP_PICTURE)
        } else {
            fragment!!.startActivityForResult(intent, REQUEST_CROP_PICTURE)
        }
    }

    private fun checkListener() {
        if (imageActionListener == null) {
            throw RuntimeException("ImageSelectionListener must be set before calling openGalleryIntent(), openCameraIntent() or requestCropImage().")
        }
    }

    /**
     * Listener interface for receiving callbacks from the ImageInputHelper.
     */
    interface ImageActionListener {
        fun onImageSelectedFromGallery(uri: Uri?, imageFile: File?)

        fun onImageTakenFromCamera(uri: Uri?, imageFile: File?)

        fun onImageCropped(uri: Uri?, imageFile: File?)
    }

    companion object {

        val REQUEST_PICTURE_FROM_GALLERY = 23
        val REQUEST_PICTURE_FROM_CAMERA = 24
        val REQUEST_CROP_PICTURE = 25
        private val TAG = "ImageInputHelper"
    }
}
