package co.edu.uniandes.fourbidden.vinilos

import android.app.Application
import co.edu.uniandes.fourbidden.vinilos.database.VinylRoomDatabase

class VinylsApplication: Application()  {
    val database by lazy { VinylRoomDatabase.getDatabase(this) }
}