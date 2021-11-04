package co.edu.uniandes.fourbidden.vinilos.vista

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import co.edu.uniandes.fourbidden.vinilos.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bingreso=findViewById<Button>(R.id.bingreso)
        bingreso.setOnClickListener {
            val activityAlbum = Intent(this, AlbumActivity::class.java)
            startActivity(activityAlbum)
        }
    }
}