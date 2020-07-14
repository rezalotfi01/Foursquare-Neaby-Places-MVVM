package com.reza.mymvvm.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.reza.mymvvm.BuildConfig
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class ApiServiceTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: ApiService

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
                .baseUrl(mockWebServer.url(""))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun requestPlaces() {
        runBlocking {
            enqueueFromApi("fake_resource.json")
            val resultResponse = service.getPlacesList("35.782361, 51.385202",15,30,BuildConfig.CLIENT_ID
                ,BuildConfig.CLIENT_SECRET,"20200627").body()

            assertNotNull(resultResponse)
        }
    }

    @Test
    fun getPlacesResponse() {
        runBlocking {
            enqueueFromApi("fake_resource.json")
            val resultResponse = service.getPlacesList("35.782361, 51.385202",15,30,BuildConfig.CLIENT_ID
                ,BuildConfig.CLIENT_SECRET,"20200627").body()
            val places = resultResponse!!.response

            assertThat(places.groups.size, `is`(1))
        }
    }


    @Test
    fun getPlaceItem() {
        runBlocking {
            enqueueFromApi("fake_resource.json")
            val resultResponse = service.getPlacesList("35.782361, 51.385202",15,30,BuildConfig.CLIENT_ID
                ,BuildConfig.CLIENT_SECRET,"20200627").body()
            val place = resultResponse!!.response.groups[0].items[0].venue


            assertThat(place.id, `is`("5ac78d1d4a1cc0044edc580d"))
            assertThat(place.name, `is`("Cedar Café Roastery"))
            assertThat(place.location.lat, `is`(35.784788))
            assertThat(place.location.lng, `is`(51.384723))
            assertThat(place.location.city, `is`("Tehran"))
            assertThat(place.location.country, `is`("Iran"))
        }
    }

    private fun enqueueFromApi(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream("api_fake_response/$fileName")
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(mockResponse.setBody(
                source.readString(Charsets.UTF_8))
        )
    }
}
