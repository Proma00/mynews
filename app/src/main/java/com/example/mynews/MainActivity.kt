package com.example.mynews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.mynews.ui.theme.MynewsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val newsViewModel=ViewModelProvider(owner = this)[NewsViewModel::class.java]
        setContent {
            val navController= rememberNavController()
            MynewsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding)
                            .fillMaxSize()
                    ){
                        Text(text = "NEWS NOW",
                            modifier =Modifier.align(Alignment.CenterHorizontally),

                            color= Color.Red,
                            fontSize = 25.sp,
                            fontFamily = FontFamily.Serif)
                        val navController = rememberNavController()
                        NavHost(navController = navController, startDestination = HomePageScreen) {
                            composable<HomePageScreen> {
                                Homepage(newsViewModel,navController)
                            }
                            composable<NewsArticleScreen> {
                                val args=it.toRoute<NewsArticleScreen>()
                                NewsArticlePage(args.url)
                            }
                        }



                    }

                    }
                }
            }
        }
    }

