package com.android.volume_button_app

import android.app.StatusBarManager
import android.content.ComponentName
import android.content.Context
import android.graphics.drawable.Icon
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
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.android.volume_button_app.ui.theme.Volume_button_appTheme

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

	fun addToQuickSettings(context : Context) {
		val statusBarManager : StatusBarManager = context.getSystemService(Context.STATUS_BAR_SERVICE) as StatusBarManager
		val componentName = ComponentName(context , VolumeTileService::class.java)
		val icon = Icon.createWithResource(context , R.drawable.unmute)
		statusBarManager.requestAddTileService(componentName , "Volume_Btn" , icon , {} , {})
	}
	Column(modifier = Modifier.padding(vertical = 4.dp)) {
		Card(modifier) {
			Text(text = "Volume Percentage" , modifier = textModifier , textAlign = TextAlign.Center)
			Slider(value = 0.5f , onValueChange = {})
		}
		Card(modifier) {
			Text(text = "Turn off all sounds" , modifier = textModifier , textAlign = TextAlign.Center)
			Switch(checked = true , onCheckedChange = {})
		}
		Card(modifier) {
			Text(text = "Est time" , modifier = textModifier , textAlign = TextAlign.Center)
		}
		Card(modifier) {
			Text(text = "Add to Quick settings" , modifier = textModifier , textAlign = TextAlign.Center)
			Box(contentAlignment = Alignment.Center) {
				FloatingActionButton(onClick = { addToQuickSettings(context) }) {
					Icon(
						Icons.Rounded.Add ,
						contentDescription = null
					)
				}
			}
		}

	}


}



