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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class VolumeTileService : TileService() {
	private val receiver : BroadcastReceiver = object : BroadcastReceiver() {
		override fun onReceive(context : Context? , intent : Intent) {
			if (intent.action.equals("android.media.VOLUME_CHANGED_ACTION")) {
				updateQuickSettingsTile()
			}
		}
	}
	private val muteIcon = Icon.createWithResource("com.android.volume_button_app" , R.drawable.mute)
	private val bluetoothIcon = Icon.createWithResource("com.android.volume_button_app" , R.drawable.bluetooth)
	private val oneThirdIcon = Icon.createWithResource("com.android.volume_button_app" , R.drawable.thirty_third)
	private val twoThirdsIcon = Icon.createWithResource("com.android.volume_button_app" , R.drawable.sixty_six)
	private val hundredIcon = Icon.createWithResource("com.android.volume_button_app" , R.drawable.hundred)
	private val isFixedIcon = Icon.createWithResource("com.android.volume_button_app" , R.drawable.fixed)

	// Original plan was to use communicationDeviceChangeListener but failed
	override fun onStartListening() {
		super.onStartListening()
		val coroutineScope = CoroutineScope(Dispatchers.Main)
		coroutineScope.launch {
			val result = getValue()
			if (result == true) {
				qsTile.state = Tile.STATE_UNAVAILABLE
				qsTile.subtitle = "is Fixed"
				qsTile.icon = isFixedIcon
				qsTile.updateTile()
			}
			else {
				updateQuickSettingsTile()
			}
		}
	}

	suspend fun getValue() : Boolean? {
		return this.dataStore.data.map { preferences -> preferences[isFixed] }.first()
	}

	override fun onCreate() {
		super.onCreate()
		val filter = IntentFilter()
		filter.addAction("android.media.VOLUME_CHANGED_ACTION")
		registerReceiver(receiver , filter)
	}

	override fun onDestroy() {
		super.onDestroy()
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
		val volumePercentage = ((audioManager.getStreamVolume(AudioManager.STREAM_MUSIC).toFloat() / audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC).toFloat()) * 100).toInt()
		if (audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) == 0) {
			qsTile.icon = muteIcon
			qsTile.state = Tile.STATE_INACTIVE
		}
		else {
			qsTile.state = Tile.STATE_ACTIVE
			if (volumePercentage > 66) {
				qsTile.icon = hundredIcon
			}
			else if (volumePercentage > 33) {
				qsTile.icon = twoThirdsIcon
			}
			else {
				qsTile.icon = oneThirdIcon
			}
		}
		if (audioManager.communicationDevice?.type != AudioDeviceInfo.TYPE_BUILTIN_EARPIECE) {
			qsTile.icon = bluetoothIcon
		}
		qsTile.subtitle = "$volumePercentage%"
		qsTile.updateTile()
	}
}