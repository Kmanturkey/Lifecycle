package com.oakes.lifecycle

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var mFirstName: String? = null
    private var mMiddleName: String? = null
    private var mLastName: String? = null

    private var mFirstEdit: EditText? = null
    private var mMiddleEdit: EditText? = null
    private var mLastEdit: EditText? = null
    private var mButtonSubmit: Button? = null
    private var mButtonPicture: Button? = null
    private var mPic: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mButtonSubmit = findViewById(R.id.submit_button)
        mButtonPicture = findViewById(R.id.picture_button)
        mFirstEdit = findViewById(R.id.first_name)
        mMiddleEdit = findViewById(R.id.middle_name)
        mLastEdit = findViewById(R.id.last_name)

        mButtonSubmit!!.setOnClickListener(this)
        mButtonPicture!!.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.submit_button -> {
                mFirstName = mFirstEdit!!.text.toString()
                mMiddleName = mMiddleEdit!!.text.toString()
                mLastName = mLastEdit!!.text.toString()
                if (mFirstName.isNullOrBlank() || mMiddleName.isNullOrBlank() || mLastName.isNullOrBlank()) {
                    Toast.makeText(this@MainActivity, "Must enter first, middle, or last name", Toast.LENGTH_SHORT).show()
                } else {
                    val messageIntent = Intent(this, LoggedInActivity::class.java)
                    messageIntent.putExtra("FIRST_NAME_STRING", mFirstName)
                    messageIntent.putExtra("MIDDLE_NAME_STRING", mMiddleName)
                    messageIntent.putExtra("LAST_NAME_STRING", mLastName)
                    this.startActivity(messageIntent)
                }
            }

            R.id.picture_button -> {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                try {
                    cameraActivity.launch(cameraIntent)
                } catch (ex:ActivityNotFoundException) {
                    Toast.makeText(this@MainActivity, "Camera isn't working", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString("", "")
    }

    private val cameraActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result ->
        if (result.resultCode == RESULT_OK) {
            mPic = findViewById<View>(R.id.iv_pic) as ImageView
            val thumbnailImage = result!!.data!!.getParcelableExtra("data", Bitmap::class.java)
            mPic!!.setImageBitmap(thumbnailImage)
        }
    }
}