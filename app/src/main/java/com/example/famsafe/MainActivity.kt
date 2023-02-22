package com.example.famsafe

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.famsafe.GuardFragment.Companion.newInstance
import com.example.famsafe.HomeFragment.Companion.newInstance
import com.example.famsafe.ProfileFragment.Companion.newInstance
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    val permissions= arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.READ_CONTACTS
    )
    val permissionCode=78


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (!allPermissionsGranted()) {
            askForPermission()
        }

        val bottomNavBar=findViewById<BottomNavigationView>(R.id.bottom_nav_bar)
        bottomNavBar.setOnNavigationItemSelectedListener { menuItem->
            if(menuItem.itemId==R.id.guard_icon){
                inflateGuardFragment()
            }
            else if(menuItem.itemId==R.id.home_icon)
            {
                inflateHomeFragment()
            }
            else if(menuItem.itemId==R.id.profile_icon){
                inflateProfile()
            }
            else{
                inflateMaps()
            }
            true
        }
    }

    private fun askForPermission() {
        ActivityCompat.requestPermissions(this, permissions, permissionCode)

    }

    private fun inflateMaps() {
        val transaction=supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container_frame, MapsFragment())
        transaction.commit()
    }


    private fun inflateProfile() {
        val transaction=supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container_frame, ProfileFragment.newInstance())
        transaction.commit()
    }

    private fun inflateHomeFragment() {
        val transaction=supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container_frame, HomeFragment.newInstance())
        transaction.commit()
    }

    private fun inflateGuardFragment() {
        val transaction=supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container_frame, GuardFragment.newInstance())
        transaction.commit()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode==permissionCode)
        {
            if(allPermissionsGranted()){}
            else{}
        }
    }

    private fun allPermissionsGranted(): Boolean {
        for(i in permissions){
            if(ContextCompat.checkSelfPermission(this,i)!=PackageManager.PERMISSION_GRANTED)
            {
                return false
            }
        }
        return true

    }


}