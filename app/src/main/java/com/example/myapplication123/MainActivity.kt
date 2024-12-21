package com.example.myapplication123

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.myapplication123.ui.theme.MyApplication123Theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        setContent {
            MyApplication123Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ArticleScreen(sharedViewModel)
                }
            }
        }
    }
}

// Shared ViewModel to manage state across activities
class SharedViewModel : ViewModel() {
    private val _isSecondArticleRead = mutableStateOf(false)
    val isSecondArticleRead: State<Boolean> get() = _isSecondArticleRead

    // Update the state for the second article
    fun markSecondArticleAsRead(isRead: Boolean) {
        _isSecondArticleRead.value = isRead
    }
}

@Composable
fun ArticleScreen(sharedViewModel: SharedViewModel) {
    val context = LocalContext.current
    val isSecondArticleRead by sharedViewModel.isSecondArticleRead

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Винисиус – лучший игрок мира на данный момент по версии GiveMeSport. " +
                    "Салах – 2-й, Родри – 3-й, Мбаппе выше Беллингема, Месси – 10-й",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Лучшим игроком мира на данный момент в GiveMeSport считают Винисиуса Жуниора.",
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Вингер «Реала» возглавил список, включающий 25 футболистов. " +
                    "В критериях отмечается, что игрок должен быть признанным мастером мирового класса, " +
                    "быть уважаемым и стабильным.",
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.vinicius),
            contentDescription = "Винисиус",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Винисиус – лучший игрок мира на данный момент по версии GiveMeSport.")
                type = "text/plain"
            }
            context.startActivity(Intent.createChooser(shareIntent, "Поделиться через"))
        }) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.share),
                    contentDescription = "Иконка поделиться",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Поделиться")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Button to go to the second article screen
        Button(
            onClick = {
                val intent = Intent(context, SecondActivity::class.java).apply {
                    putExtra("isSecondArticleRead", isSecondArticleRead)
                }
                context.startActivity(intent)
            },
            enabled = !isSecondArticleRead,
            modifier = Modifier.alpha(if (isSecondArticleRead) 0.5f else 1f)
        ) {
            Text("У Моуринью ни одной победы...")
        }
    }
}
