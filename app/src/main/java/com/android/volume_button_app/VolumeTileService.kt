package com.android.volume_button_app

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.service.quicksettings.TileService
import android.util.Log

class VolumeTileService : TileService() {
	override fun onStartCommand(intent : Intent? , flags : Int , startId : Int) : Int {
		Log.i("test" , "receive command")
		volumePercentage()
		checkQSActiveAndIcon()
		return super.onStartCommand(intent , flags , startId)
	}

	// Replace as Volume Button
	override fun onClick() {
		super.onClick()
		val audioManager : AudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
		audioManager.adjustVolume(AudioManager.ADJUST_SAME , AudioManager.FLAG_SHOW_UI)
	}

	private fun checkQSActiveAndIcon() {
	}

	private fun volumePercentage() {
		val audioManager : AudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
		val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
		val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
		val volumePercentage = ((currentVolume.toFloat() / maxVolume.toFloat()) * 100).toInt()
		qsTile.subtitle = "$volumePercentage%"
		qsTile.updateTile()
		Log.i("test" , "percentagechanged")
	}
}