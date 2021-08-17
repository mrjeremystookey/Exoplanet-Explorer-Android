package r.stookey.exoplanetexplorer.model

interface DomainMapper<T, Planet> {

    fun mapToPlanetModel(entity: T): Planet
    fun mapFromPlanetModel(planet: Planet): T

}