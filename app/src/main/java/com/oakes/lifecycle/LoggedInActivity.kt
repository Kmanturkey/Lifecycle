package com.oakes.lifecycle

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

class LoggedInActivity : AppCompatActivity() {
    private var mFirstNameReceived: String? = null
    private var mMiddleNameReceived: String? = null
    private var mLastNameReceived: String? = null

    private var mLoggedinMessage: TextView? = null
    private var mFirstText: TextView? = null
    private var mMiddleText: TextView? = null
    private var mLastText: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logged_in)

        mLoggedinMessage = findViewById(R.id.logged_in_message)
        mFirstText = findViewById(R.id.first_name_logged)
        mMiddleText = findViewById(R.id.middle_name_logged)
        mLastText = findViewById(R.id.last_name_logged)

        val receivedIntent = intent

        mFirstNameReceived = receivedIntent.getStringExtra("FIRST_NAME_STRING")
        mMiddleNameReceived = receivedIntent.getStringExtra("MIDDLE_NAME_STRING")
        mLastNameReceived = receivedIntent.getStringExtra("LAST_NAME_STRING")

        val loggedInMessage: String? = "$mFirstNameReceived $mLastNameReceived is logged in!"
        mLoggedinMessage!!.text = loggedInMessage
        mFirstText!!.text = mFirstNameReceived
        mMiddleText!!.text = mMiddleNameReceived
        mLastText!!.text = mLastNameReceived
    }
}