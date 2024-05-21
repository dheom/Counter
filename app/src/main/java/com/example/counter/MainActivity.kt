package com.example.counter

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

class MainActivity : ComponentActivity() {
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
        val activity = LocalContext.current as? Activity //액티비티가 null일수있다
        val sharedPref = remember{ // remember를 활용할수있도록 정리
            activity?.getPreferences(Context.MODE_PRIVATE)
        }//소문자로 시작은 변수,, 대문자는 함수
        //하나의 환경설정만 저장하기 위함

        // mutableSateOf의 default값을 saveCount 로 변경, remember{}안으로 saveCount 옮김, 액티비티 불러올때 불러오지않기위해

        var count by remember {
            val saveCount = sharedPref?.getInt("lastCount",0)//접근을해서 값이 없으면 0
            mutableStateOf(saveCount ?:0) } //mutableStateOf가 nullable로 변경되어서 하단 오류남
        //?:0는 띄어쓰기 없이
        Button(modifier = Modifier
            .width(200.dp)
            .height(60.dp), onClick = { count++
            sharedPref?.edit {
                putInt("lastCount",count)
            } } //safe가 null이 가능하다는 건가
        ) {
            Text(text = "클릭 횟수 : $count")
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun MainScreenPreview() {
        CounterTheme {
            MainScreen()
        }
    }

}

