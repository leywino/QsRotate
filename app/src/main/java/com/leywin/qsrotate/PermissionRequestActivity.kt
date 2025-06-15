package com.leywin.qsrotate

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast

class PermissionRequestActivity : AppCompatActivity() {

    private lateinit var permissionContainer: View
    private lateinit var instructionContainer: View
    private lateinit var grantButton: Button
    private lateinit var websiteButton: Button
    private lateinit var statusText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)

        permissionContainer = findViewById(R.id.permissionContainer)
        instructionContainer = findViewById(R.id.instructionContainer)
        grantButton = findViewById(R.id.grantButton)
        websiteButton = findViewById(R.id.websiteButton)
        statusText = findViewById(R.id.statusText)

        checkPermissionAndUpdateUI()

        grantButton.setOnClickListener {
            val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS).apply {
                data = Uri.parse("package:$packageName")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(intent)
            Toast.makeText(this, "Please grant 'Modify system settings' permission", Toast.LENGTH_LONG).show()
        }

        websiteButton.setOnClickListener {
            val websiteIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/leywino/QsRotate"))
            startActivity(websiteIntent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Re-check permission in case user granted it while away
        checkPermissionAndUpdateUI()
    }

    private fun checkPermissionAndUpdateUI() {
        if (Settings.System.canWrite(this)) {
            // Permission granted, show instructions
            permissionContainer.visibility = View.GONE
            instructionContainer.visibility = View.VISIBLE
        } else {
            // Permission NOT granted, show permission request UI
            permissionContainer.visibility = View.VISIBLE
            instructionContainer.visibility = View.GONE
        }
    }
}
