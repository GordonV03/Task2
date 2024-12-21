package com.example.myapplication123

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication123.ui.theme.MyApplication123Theme

class SecondActivity : ComponentActivity() {
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)

        setContent {
            MyApplication123Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SecondScreen(sharedViewModel = sharedViewModel)
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondScreen(sharedViewModel: SharedViewModel) {
    val activity = LocalContext.current as? ComponentActivity
    val isRead by sharedViewModel.isSecondArticleRead // Используем состояние из ViewModel

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Second Activity") },
                navigationIcon = {
                    IconButton(onClick = {
                        sharedViewModel.markSecondArticleAsRead(isRead) // Сохраняем состояние
                        activity?.finish()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) {
        ArticleDetailScreen()
    }
}

@Composable
fun ArticleDetailScreen() {
    var isRead by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(52.dp))

        Text(
            text = "У Моуринью ни одной победы над командами из топ-6 лиги Турции. " +
                    "«Фенербахче» выиграл лишь 1 из 4 последних матчей",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "«Фенербахче» выиграл лишь один из четырех последних матчей.",
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Команда Жозе Моуринью сегодня разделила очки с «Еюпспором» (1:1) в 17-м туре чемпионата Турции.",
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Начиная с 7 декабря она также уступила «Бешикташу» (0:1) и «Атлетику» (0:2) и обыграла «Истанбул» (3:1).",
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.mourinio),
            contentDescription = "Моуриньо",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Прочитано")
            Spacer(modifier = Modifier.width(8.dp))
            Switch(
                checked = isRead,
                onCheckedChange = { isChecked -> isRead = isChecked }
            )

        }
    }
}
