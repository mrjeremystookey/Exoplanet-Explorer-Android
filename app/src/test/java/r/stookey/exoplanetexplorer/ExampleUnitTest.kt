package r.stookey.exoplanetexplorer

import org.json.JSONObject
import org.junit.Test

import org.junit.Assert.*
import r.stookey.exoplanetexplorer.data.ExoplanetApiService
import r.stookey.exoplanetexplorer.repository.RepositoryImpl

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    /*@Test
    suspend fun apiService_returnsNotNull(){
        var exoplanetApiService = ExoplanetApiService()
        var repo = RepositoryImpl(exoplanetApiService)
        assertEquals(repo.getAllPlanets(), JSONObject())
    }*/

}