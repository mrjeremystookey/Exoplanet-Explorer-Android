package r.stookey.exoplanetexplorer.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import r.stookey.exoplanetexplorer.domain.Planet
import r.stookey.exoplanetexplorer.domain.PlanetDtoImpl
import timber.log.Timber
import java.io.IOException
import java.net.HttpRetryException
import javax.inject.Inject

private const val PLANET_STARTING_PAGE_INDEX = 1

class PlanetPagingSource(private val exoplanetApiService: ExoplanetApiService, private val query: String): PagingSource<Int, Planet>() {

    @Inject
    lateinit var planetMapper: PlanetDtoImpl

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Planet> {
        val position = params.key ?: PLANET_STARTING_PAGE_INDEX
        return try {
            val response = exoplanetApiService.getPlanets(query)
            var listOfPlanets = planetMapper.convertJsonToPlanets(response)
            Timber.d("Result ready")
            LoadResult.Page(
                data = listOfPlanets,
                prevKey = if (position == PLANET_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (listOfPlanets.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            Timber.d("paging data lookup failed")
            LoadResult.Error(exception)
        } catch (exception: HttpRetryException) {
            Timber.d("paging data lookup failed")
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Planet>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}