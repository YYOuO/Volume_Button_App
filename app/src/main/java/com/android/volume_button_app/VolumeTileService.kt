package com.android.volume_button_app

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.media.AudioManager
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.util.Log

class VolumeTileService : TileService() {
	override fun onStartCommand(intent : Intent? , flags : Int , startId : Int) : Int {
		Log.i("test" , "receive command")
		updateQuickSettingsTile()
		return super.onStartCommand(intent , flags , startId)
	}

	// Replace as Volume Button
	override fun onClick() {
		super.onClick()
		val audioManager : AudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
		audioManager.adjustVolume(AudioManager.ADJUST_SAME , AudioManager.FLAG_SHOW_UI)
	}

	private val muteIcon : Icon = Icon.createWithResource("com.example.myapplication" , R.drawable.mute)
	private val unMuteIcon : Icon = Icon.createWithResource("com.example.myapplication" , R.drawable.unmute)
	private fun updateQuickSettingsTile() {
		val audioManager : AudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
		val muteState : Boolean = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) == 0
		if (muteState) {
			qsTile.icon = muteIcon
			qsTile.state = Tile.STATE_INACTIVE
		}
		else {
			qsTile.icon = unMuteIcon
			qsTile.state = Tile.STATE_ACTIVE
		}
		val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
		val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
		val volumePercentage = ((currentVolume.toFloat() / maxVolume.toFloat()) * 100).toInt()
		qsTile.subtitle = "$volumePercentage%"
		qsTile.updateTile()
	}
}