package com.android.volume_button_app

import android.app.StatusBarManager
import android.content.ComponentName
import android.content.Context
import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
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

			}
		}
	}
}

//@Composable
//fun Greeting(name : String , modifier : Modifier = Modifier , func : Unit) {
//	Column {
//		Text(
//			text = "Hello $name!" , modifier = modifier
//		)
//		Button(modifier = Modifier.padding(15.dp) , onClick = { func })
//	}
//
//}

//@Preview(showBackground = true , wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE)
//@Composable
//fun GreetingPreview() {
//	Volume_button_appTheme {
//		Greeting("Android")
//	}
//}

@Preview(showBackground = true , wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE)
@Composable
fun MyApp() {
	Volume_button_appTheme {
		Card() {
			Column {
				Row(verticalAlignment = Alignment.CenterVertically) {
					Text(text = "This is a text")
					Switch(checked = true , onCheckedChange = {})
				}
				Divider()
				Row(verticalAlignment = Alignment.CenterVertically) {
					Text(text = "This is a text")
					Slider(value = 0.5f , onValueChange = {})
				}
			}
		}
	}


}
