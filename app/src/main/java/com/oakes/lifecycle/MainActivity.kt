package com.oakes.lifecycle

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore
import android.text.Editable
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
    private var mLoggedInIntent: Intent? = null
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
        mLoggedInIntent = Intent(this, LoggedInActivity::class.java)
        Log.i("savedInstanceState", "$savedInstanceState")
        if (savedInstanceState != null) {
            mFirstEdit!!.setText(savedInstanceState.getString("savedFirstName"))
            mFirstEdit!!.setText(savedInstanceState.getString("savedMiddleName"))
            mLastEdit!!.setText(savedInstanceState.getString("savedLastName"))
            Log.i("onRestore", "onRestoreInstanceState done")
        }
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
                    mLoggedInIntent!!.putExtra("FIRST_NAME_STRING", mFirstName)
                    mLoggedInIntent!!.putExtra("MIDDLE_NAME_STRING", mMiddleName)
                    mLoggedInIntent!!.putExtra("LAST_NAME_STRING", mLastName)
                    startActivity(mLoggedInIntent)
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

        outState.putString("savedFirstName", mFirstEdit!!.text.toString())
        outState.putString("savedMiddleName", mMiddleEdit!!.text.toString())
        outState.putString("savedLastName", mLastEdit!!.text.toString())
        Log.i("onSave", "onSaveInstanceState done")
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