package com.compose.memeoverpowered

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.compose.memeoverpowered.ui.theme.MemeOverPoweredTheme
import org.json.JSONObject

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val scaffoldState = rememberScaffoldState()
            val context = LocalContext.current
            var memeUrl by remember { mutableStateOf("") }

            MemeOverPoweredTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {

                        Button(onClick = {

                            val url = "https://meme-api.com/gimme"

                            val queue = Volley.newRequestQueue(context)

                            val stringRequest = StringRequest(Request.Method.GET, url, { response ->

                                val person = JSONObject(response)
                                memeUrl = person.getString("url")

                            }, {
                                println("That didn't work!")
                            })

                            queue.add(stringRequest)

                        }) {
                            Text(text = "Get Meme")
                        }

                        AsyncImage(
                            model = memeUrl,
                            contentDescription = null
                        )


                    }
                }
            }
        }
    }
}
