package r.stookey.exoplanetexplorer

import org.junit.Test

import org.junit.Assert.*
import r.stookey.exoplanetexplorer.data.ExoplanetApiService

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


    @Test
    fun apiService_returnsNotNull(){
        val url = "https://exoplanetarchive.ipac.caltech.edu/TAP/sync?query=select+*+from+ps&format=json"
        val exoplanetApiService = ExoplanetApiService()
        val jsonObject = exoplanetApiService.makeJsonRequest(url)
    }

}