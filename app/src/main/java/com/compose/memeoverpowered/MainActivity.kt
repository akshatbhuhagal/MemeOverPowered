package com.compose.memeoverpowered

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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


                        AsyncImage(
                            model = memeUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxHeight(0.5f)
                                .fillMaxWidth(1f)
                        )

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
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

                            Box(Modifier.width(12.dp)) {

                            }

                            Button(onClick = {

                                val sendIntent: Intent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(Intent.EXTRA_TEXT, memeUrl)
                                    type = "text/plain"
                                }
                                val shareIntent = Intent.createChooser(sendIntent, null)

                                context.startActivity(shareIntent)

                            }) {
                                Icon(
                                    Icons.Filled.Share, "Share Meme",
                                    Modifier.size(14.dp)
                                )
                            }
                        }



                    }
                }
            }
        }
    }
}
