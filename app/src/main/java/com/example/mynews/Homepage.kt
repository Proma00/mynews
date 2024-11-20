package com.example.mynews

import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.kwabenaberko.newsapilib.models.Article

@Composable
fun Homepage(newsViewModel: NewsViewModel,navController: NavController) {
    val articles = newsViewModel.articles.observeAsState(emptyList()).value

    Column(
        modifier = Modifier.fillMaxSize()

    ) {
        CategoriesBar(newsViewModel)
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(articles) { article ->
                ArticleItem(article,navController)
            }
        }
    }
}

@Composable
fun CategoriesBar(newsViewModel: NewsViewModel) {
    var searchQuery by remember {
        mutableStateOf("")
    }
    var isSearchExpanded by remember {
        mutableStateOf(false)
    }
    val categoriesList = listOf(
        "GENERAL",
        "BUSINESS",
        "ENTERTAINMENT",
        "HEALTH",
        "SCIENCE",
        "SPORTS",
        "TECHNOLOGY"
    )
    Row(
        Modifier
            .fillMaxWidth() // Occupy only horizontal space
            .horizontalScroll(rememberScrollState())
            .padding(vertical = 8.dp), // Add some padding to separate it from LazyColumn

    ) {
        if(isSearchExpanded){
            OutlinedTextField(
                modifier = Modifier.padding(8.dp)
                    .height(48.dp)
                    .border(1.dp,Color.Gray, CircleShape)
                    .clip(CircleShape),
                value = searchQuery,
                onValueChange = {
                    searchQuery= it
                },
                trailingIcon = {IconButton(onClick = {
                    isSearchExpanded = false
                    if(searchQuery.isNotEmpty()){
                        newsViewModel.fetchEverythingwithQuery(searchQuery)
                    }
                }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "search icon" )
                }
                })}


         else {
            IconButton(onClick =  {
                isSearchExpanded=true

            }){
                Icon(imageVector = Icons.Default.Search, contentDescription = "search icon" )
            }


        }
        categoriesList.forEach { category ->
            Button(
                onClick = { newsViewModel.fetchNewsTopheadLines(category)/* Add category-specific logic here */ },
                modifier = Modifier.padding(4.dp)
            ) {
                Text(text = category)
            }
        }
    }
}

@Composable
fun ArticleItem(article: Article,navController: NavController) {
    // Card UI to display the image and article text
    Card(modifier = Modifier.padding(8.dp), elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick ={
            navController.navigate(NewsArticleScreen(article.url))
        } ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            // Correct usage of AsyncImage
            AsyncImage(
                model = article.urlToImage,
                "Article Image",
                modifier = Modifier
                    .size(80.dp)
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = article.title ?: "No Title", // Handle null titles gracefully
                    fontWeight = FontWeight.Bold,
                    maxLines = 3
                )

                Text(
                    text = article.source?.name ?: "Unknown Source", // Handle null sources gracefully
                    maxLines = 1,
                    fontSize = 14.sp
                )
            }
        }
    }

}




