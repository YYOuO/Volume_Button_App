package com.android.volume_button_app

import android.app.StatusBarManager
import android.content.ComponentName
import android.content.Context
import android.graphics.drawable.Icon
import android.media.AudioManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.android.volume_button_app.ui.theme.Volume_button_appTheme


// At the top level of your kotlin file:
val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "Volume_Values")

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState : Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			Volume_button_appTheme {
				val displayInfo = this.resources.displayMetrics.heightPixels / 16.0
				AppCompose(displayInfo , this)
			}
		}
	}
}

@Composable
fun AppCompose(deviceHeight : Double , context : Context) {
	val padding = 4.dp
	val modifier = Modifier
		.padding(padding)
		.height(deviceHeight.dp)
		.clickable(onClick = {})
		.fillMaxWidth()
	val textModifier = Modifier
		.padding(8.dp)
		.fillMaxWidth()
	val audioManager : AudioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
	val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
	Column(modifier = Modifier.padding(vertical = 4.dp)) {
		var sliderPosition by remember { mutableStateOf(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC).toFloat()) }
		Card(modifier) {
			Text(text = "Volume Percentage" , modifier = textModifier , textAlign = TextAlign.Center)
			Slider(value = sliderPosition , enabled = true , valueRange = 0f .. 100f , onValueChange = { sliderPosition = it } , onValueChangeFinished = {
				val set = maxVolume * sliderPosition / 100
				audioManager.setStreamVolume(AudioManager.STREAM_MUSIC , set.toInt() , 0)
			})
			Text(text = "${sliderPosition.toInt()}%" , modifier = textModifier , textAlign = TextAlign.Center)
		}
		Card(modifier) {
			Text(text = "Turn off all sounds" , modifier = textModifier , textAlign = TextAlign.Center)
//			Switch(checked = false , onCheckedChange = { modifyAllSounds(context , it) })
		}
		Card(modifier) {
			Text(text = "Add to Quick settings" , modifier = textModifier , textAlign = TextAlign.Center)
			Box(contentAlignment = Alignment.Center) {
				FloatingActionButton(onClick = {
					val statusBarManager : StatusBarManager = context.getSystemService(Context.STATUS_BAR_SERVICE) as StatusBarManager
					val componentName = ComponentName(context , VolumeTileService::class.java)
					val icon = Icon.createWithResource(context , R.drawable.unmute)
					statusBarManager.requestAddTileService(componentName , "Volume_Btn" , icon , {} , {})
				}) {
					Icon(
						Icons.Rounded.Add , contentDescription = null
					)
				}
			}
		}

	}


}



