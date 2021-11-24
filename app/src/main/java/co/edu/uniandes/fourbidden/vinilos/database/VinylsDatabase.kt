package co.edu.uniandes.fourbidden.vinilos.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import co.edu.uniandes.fourbidden.vinilos.modelo.Album
import co.edu.uniandes.fourbidden.vinilos.modelo.Coleccionista
import co.edu.uniandes.fourbidden.vinilos.modelo.Musico
import java.util.stream.Collector


@Database(entities = [Album::class, Coleccionista::class, Musico::class], version = 1, exportSchema = false)
abstract class VinylRoomDatabase : RoomDatabase() {
    abstract fun albumsDao(): AlbumsDao
    abstract fun coleccionistasDao(): ColeccionistasDao
    abstract fun musicosDao(): MusicosDao


    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: VinylRoomDatabase? = null

        fun getDatabase(context: Context): VinylRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VinylRoomDatabase::class.java,
                    "vinyls_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}