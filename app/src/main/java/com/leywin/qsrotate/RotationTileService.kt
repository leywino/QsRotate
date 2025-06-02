package com.leywin.qsrotate

import android.content.Intent
import android.graphics.drawable.Icon
import android.provider.Settings
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.util.Log
import android.widget.Toast

class RotationTileService : TileService() {

    override fun onClick() {
        super.onClick()

        if (!Settings.System.canWrite(this)) {
            val intent = Intent(this, PermissionRequestActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(intent)
            Toast.makeText(this, "Please grant 'Modify system settings' permission", Toast.LENGTH_LONG).show()
            return
        }

        // Permission granted, proceed to toggle rotation...
        toggleRotation()
    }

    private fun toggleRotation() {
        val currentRotation = Settings.System.getInt(contentResolver, Settings.System.USER_ROTATION, 0)
        val newRotation = if (currentRotation == 0) 1 else 0

        Settings.System.putInt(contentResolver, Settings.System.ACCELEROMETER_ROTATION, 0)
        Settings.System.putInt(contentResolver, Settings.System.USER_ROTATION, newRotation)

        qsTile?.apply {
            icon = Icon.createWithResource(
                this@RotationTileService,
                if (newRotation == 0)
                    R.drawable.ic_rotation_portrait
                else
                    R.drawable.ic_rotation_landscape
            )
            state = if (newRotation == 0) Tile.STATE_INACTIVE else Tile.STATE_ACTIVE
            updateTile()
        }
    }



    override fun onStartListening() {
        super.onStartListening()

        val currentRotation = Settings.System.getInt(contentResolver, Settings.System.USER_ROTATION, 0)
        qsTile?.apply {
            icon = Icon.createWithResource(
                this@RotationTileService,
                if (currentRotation == 0)
                    R.drawable.ic_rotation_portrait
                else
                    R.drawable.ic_rotation_landscape
            )
            state = if (currentRotation == 0) Tile.STATE_INACTIVE else Tile.STATE_ACTIVE
            updateTile()
        }
    }

}
