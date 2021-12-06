package co.edu.uniandes.fourbidden.vinilos.vista

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import co.edu.uniandes.fourbidden.vinilos.R

class AlbumActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)

        val btMusico=findViewById<Button>(R.id.btMusico)
        btMusico.setOnClickListener {
            val activityMusicos = Intent(this, MusicoActivity::class.java)
            startActivity(activityMusicos)
        }

        val btColeccionista =findViewById<Button>(R.id.btColeccionistas)
        btColeccionista.setOnClickListener {
            val coleccionistaActivity = Intent(this, ColeccionistaActivity::class.java)
            startActivity(coleccionistaActivity)
        }

        val btCrearAlbum =findViewById<Button>(R.id.btCrearAlbum)
        btCrearAlbum.setOnClickListener {
            val crearAlbumActivity = Intent(this, CrearAlbumActivity::class.java)
            startActivity(crearAlbumActivity)
        }

    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


}