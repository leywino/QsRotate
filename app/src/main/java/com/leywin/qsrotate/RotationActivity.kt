package com.leywin.qsrotate

import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle

class RotationActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val orientation = intent.getIntExtra("orientation", 0)
        requestedOrientation = if (orientation == 0)
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        else
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        // Finish shortly after to let orientation apply
        window.decorView.postDelayed({
            finish()
        }, 300)
    }
}