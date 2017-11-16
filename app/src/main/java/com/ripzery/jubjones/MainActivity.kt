package com.ripzery.jubjones

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ripzery.jubjones.models.User
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mSleep: Int = -1
    private var mCapture: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCapture.isEnabled = false
        btnSleep.isEnabled = false

        val mDatabase = FirebaseDatabase.getInstance().reference.child("users").child("e4fed1a9-cbaf-5d3f-a6ef-6a30bcefa64b")
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue(User::class.java)
                mSleep = value?.sleep ?: -1
                mCapture = value?.capture ?: -1
                btnCapture.isEnabled = true
                btnSleep.isEnabled = true
                Log.d("data", value.toString())
            }
        })

        btnSleep.setOnClickListener {
            mSleep++
            val user = User(mCapture, mSleep)
            mDatabase.setValue(user)
        }
        btnCapture.setOnClickListener {
            mCapture++
            val user = User(mCapture, mSleep)
            mDatabase.setValue(user)
        }
    }
}
