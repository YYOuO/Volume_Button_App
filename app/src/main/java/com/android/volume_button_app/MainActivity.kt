package com.android.volume_button_app

import android.app.StatusBarManager
import android.content.ComponentName
import android.content.Context
import android.graphics.drawable.Icon
import android.media.AudioManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.android.volume_button_app.ui.theme.Volume_button_appTheme
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState : Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			Volume_button_appTheme {
				val audioManager : AudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
				fun addToQuickSettings() {
					val statusBarManager : StatusBarManager = getSystemService(Context.STATUS_BAR_SERVICE) as StatusBarManager
					val componentName = ComponentName(this , VolumeTileService::class.java)
					val icon = Icon.createWithResource(this , R.drawable.ic_launcher_background)
					statusBarManager.requestAddTileService(componentName , "Volume_Btn" , icon , {} , {})
				}
			}
		}
	}
}



