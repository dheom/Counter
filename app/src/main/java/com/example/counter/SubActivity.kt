package com.example.counter

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import com.example.counter.ui.theme.CounterTheme
import java.text.SimpleDateFormat
import java.util.Locale

class SubActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CounterTheme {
                MainScreen()
            }
        }
    }

    @Composable
    fun MainScreen() {
        val activity = LocalContext.current as? Activity
        val sharedPref = remember { //설정값을 가져오는것
            activity?.getPreferences(Context.MODE_PRIVATE) //매소드가 {}안에들어있음
        }

        @Composable
        fun rememberSharedPrefState(sharedPref: SharedPreferences?, key: String, defaultValue: Int): MutableState<Int> {
            return remember {
                val value = sharedPref?.getInt(key, defaultValue) ?: defaultValue
                mutableStateOf(value)
            }
        }

        var count1 by rememberSharedPrefState(sharedPref, "lastCount", 0)
        var count2 by rememberSharedPrefState(sharedPref, "Double", 0)
        var count3 by rememberSharedPrefState(sharedPref, "Reset", 0)
        var count4 by rememberSharedPrefState(sharedPref, "Date", )

//        var count1 by remember {//count에는 savecount변수를 저장할꺼야
//            val savecount = sharedPref?.getInt("lastCount", 0)// lastCount의 값을 가져와 saveCount에 넣고 그 값이 count에 저장될 값
//            mutableStateOf(savecount ?:0)//얘도 그냥 메소드가 {}안에 들어있는것
//        }
//        var count2 by remember {
//            val savecount = sharedPref?.getInt("DoubleCount",1)
//            mutableStateOf(savecount ?:1)
//        }
        Column {

            Button(modifier = Modifier
                .width(200.dp)
                .height(50.dp)
                .padding(5.dp),
                onClick = {
                    count1++
                    sharedPref?.edit { putInt("lastCount", count1) }//lastCount에 count를 넣고 수정하라
                }) {
                Text(text = "Plus 1 Count: ${count1}")
            }
            Button(modifier = Modifier
                .width(200.dp)
                .height(50.dp)
                .padding(5.dp),
                onClick = {
                    count2 = count2 * 2
                    sharedPref?.edit { putInt("DoubleCount", count2) }
                }) {
                Text(text = "Double Count : ${count2}")
            }
            Button(modifier = Modifier
                .width(200.dp)
                .height(50.dp)
                .padding(5.dp),
                onClick = {
                    count3 = 0
                    sharedPref?.edit { putInt("Reset", count3) }
                }) {
                Text(text = "Reset : ${count3}")
            }

        }
    }


    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        CounterTheme {
            MainScreen()
        }
    }
}
