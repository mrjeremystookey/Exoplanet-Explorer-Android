package r.stookey.exoplanetexplorer.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import r.stookey.exoplanetexplorer.domain.Planet
import r.stookey.exoplanetexplorer.domain.PlanetFts
import timber.log.Timber

@Database(entities = [Planet::class, PlanetFts::class], version = 3, exportSchema = false)
abstract class PlanetDatabase: RoomDatabase() {

    abstract fun planetDao(): PlanetDao

    companion object{
        @Volatile private var instance: PlanetDatabase? = null

        fun getInstance(context: Context): PlanetDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

    private fun buildDatabase(context: Context): PlanetDatabase {
        return Room.databaseBuilder(context, PlanetDatabase::class.java, "planets-db")
            .addCallback(
                object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Timber.i("planets-db created")
                    }
                }
            ).fallbackToDestructiveMigration()
            .build()
    }
    }

}