package co.edu.uniandes.fourbidden.vinilos.vista

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import co.edu.uniandes.fourbidden.vinilos.R

class MusicoActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_musico)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)

        val btAlbumes =findViewById<Button>(R.id.btAlbumes)
        btAlbumes.setOnClickListener {
            val activityAlbumes = Intent(this, AlbumActivity::class.java)
            startActivity(activityAlbumes)
        }
        val btColeccionista =findViewById<Button>(R.id.btColeccionistas)
        btColeccionista.setOnClickListener {
            val coleccionistaActivity = Intent(this, ColeccionistaActivity::class.java)
            startActivity(coleccionistaActivity)
        }

    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}