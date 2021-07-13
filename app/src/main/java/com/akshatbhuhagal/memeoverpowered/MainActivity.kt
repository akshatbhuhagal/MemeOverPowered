package com.akshatbhuhagal.memeoverpowered

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.akshatbhuhagal.memeoverpowered.databinding.ActivityMainBinding
import com.android.volley.Request
import com.android.volley.toolbox.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var currentImageUrl: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadMemes()


    }

    private fun loadMemes() {

        binding.progressBar.visibility = View.VISIBLE


        val url = "https://meme-api.herokuapp.com/gimme/dankmemes"

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                currentImageUrl = response.getString("url")
                Glide.with(this).load(currentImageUrl).listener(object: RequestListener<Drawable>{

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.progressBar.visibility = View.GONE
                        return false
                    }

                }).into(binding.ivMemes)
            },
            {
                Toast.makeText(this, "Error! Please Restart Your App", Toast.LENGTH_LONG).show()
            })

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)

    }

    fun nextMeme(view: View) {
        loadMemes()
    }

    fun shareMeme(view: View) {

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, currentImageUrl)
        val chooser = Intent.createChooser(intent, "Share this Meme using...")
        startActivity(chooser)

    }




}