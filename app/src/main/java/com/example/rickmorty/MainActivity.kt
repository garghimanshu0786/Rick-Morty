package com.example.rickmorty

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import view.EpisodeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val info = (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo

        if (info == null) {
            Toast.makeText(this, "No Internet Connection", LENGTH_SHORT).show()
            setContentView(R.layout.activity_main)
        } else {
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            val fragment = EpisodeFragment()
            setContentView(R.layout.fragment_container_layout)
            transaction.replace(R.id.fragment_container, fragment, fragment.toString())
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}