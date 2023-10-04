package com.android.volume_button_app

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.drawable.Icon
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService

class VolumeTileService : TileService() {
	private val receiver : BroadcastReceiver = object : BroadcastReceiver() {
		override fun onReceive(context : Context? , intent : Intent) {
			if (intent.action.equals("android.media.VOLUME_CHANGED_ACTION")) {
				updateQuickSettingsTile()
			}
		}
	}

	// Original plan was to use communicationDeviceChangeListener but failed
	override fun onStartListening() {
		super.onStartListening()
		updateQuickSettingsTile()
		val filter = IntentFilter()
		filter.addAction("android.media.VOLUME_CHANGED_ACTION")
		registerReceiver(receiver , filter)
	}

	override fun onStopListening() {
		super.onStopListening()
		unregisterReceiver(receiver)
	}

	override fun onClick() {
		super.onClick()
		val audioManager : AudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
		audioManager.adjustVolume(AudioManager.ADJUST_SAME , AudioManager.FLAG_SHOW_UI)
		updateQuickSettingsTile()
	}

	private fun updateQuickSettingsTile() {
		val audioManager : AudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
		if (audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) == 0) {
			qsTile.icon = Icon.createWithResource("com.android.volume_button_app" , R.drawable.mute)
			qsTile.state = Tile.STATE_INACTIVE
		}
		else {
			qsTile.icon = Icon.createWithResource("com.android.volume_button_app" , R.drawable.unmute)
			qsTile.state = Tile.STATE_ACTIVE
		}
		if (audioManager.communicationDevice?.type != AudioDeviceInfo.TYPE_BUILTIN_EARPIECE) {
			qsTile.icon = Icon.createWithResource("com.android.volume_button_app" , R.drawable.ic_launcher_foreground)
		}
		val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
		val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
		val volumePercentage = ((currentVolume.toFloat() / maxVolume.toFloat()) * 100).toInt()
		qsTile.subtitle = "$volumePercentage%"
		qsTile.updateTile()
	}
}