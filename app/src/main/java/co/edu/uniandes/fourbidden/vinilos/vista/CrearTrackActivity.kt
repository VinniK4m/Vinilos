package co.edu.uniandes.fourbidden.vinilos.vista

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import co.edu.uniandes.fourbidden.vinilos.R

class CrearTrackActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_track)
        val albumId = intent.extras?.get("albumId")
        intent.extras?.putString("albumId", albumId?.toString())

        val btColeccionista =findViewById<Button>(R.id.btColeccionistas)
        btColeccionista.setOnClickListener {
            val coleccionistaActivity = Intent(this, ColeccionistaActivity::class.java)
            startActivity(coleccionistaActivity)
        }

        val btAlbumes =findViewById<Button>(R.id.btAlbumes)
        btAlbumes.setOnClickListener {
            val activityAlbumes = Intent(this, AlbumActivity::class.java)
            startActivity(activityAlbumes)
        }

        val btMusico=findViewById<Button>(R.id.btMusico)
        btMusico.setOnClickListener {
            val activityMusicos = Intent(this, MusicoActivity::class.java)
            startActivity(activityMusicos)
        }

    }
}