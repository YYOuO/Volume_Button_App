package com.android.volume_button_app

import android.app.StatusBarManager
import android.content.ComponentName
import android.content.Context
import android.content.IntentFilter
import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.android.volume_button_app.ui.theme.Volume_button_appTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState : Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			Volume_button_appTheme {
				fun addToQuickSettings() {
					val statusBarManager : StatusBarManager = getSystemService(Context.STATUS_BAR_SERVICE) as StatusBarManager
					val componentName = ComponentName(this , VolumeTileService::class.java)
					val icon = Icon.createWithResource(this , R.drawable.ic_launcher_background)
					statusBarManager.requestAddTileService(componentName , "Volume_Btn" , icon , {} , {})
				}
				// Register BroadCastReceiver
				val receiver = VolumeMonitor()
				val filter = IntentFilter()
				filter.addAction("android.media.VOLUME_CHANGED_ACTION")
				registerReceiver(receiver , filter)
			}
		}
	}
}

@Preview(showBackground = true , wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE)
@Composable
fun MyApp() {
	Volume_button_appTheme {

		Card() {
			Column {
				Row(verticalAlignment = Alignment.CenterVertically) {
					Text(text = "This is a text  ")
					Switch(checked = true , onCheckedChange = {})
				}
				Divider()
				Row(verticalAlignment = Alignment.CenterVertically) {
					Text(text = "This is a text")
					Slider(value = 0.5f , onValueChange = {})
				}
				FloatingActionButton(
					onClick = { /*TODO*/ } ,
					modifier = Modifier
						.padding(16.dp)
				) {
					Icon(Icons.Filled.Add , "Add qs")
				}
			}
		}
	}


}
