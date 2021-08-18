package r.stookey.exoplanetexplorer.cache

import androidx.room.Database
import r.stookey.exoplanetexplorer.domain.Planet

@Database(entities = [Planet::class], version = 1)
abstract class PlanetDatabase {
}