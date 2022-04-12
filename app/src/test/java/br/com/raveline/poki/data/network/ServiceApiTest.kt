package br.com.raveline.poki.data.network


import br.com.raveline.poki.data.request.PokeRequest
import br.com.raveline.poki.utils.FAILURE_CODE_RESPONSE
import br.com.raveline.poki.utils.SUCCESS_CODE_RESPONSE
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceApiTest {
    private lateinit var service: PokiApiServices
    private lateinit var postServices: PokiApiPostServices
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokiApiServices::class.java)

        postServices = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokiApiPostServices::class.java)
    }

    private fun enqueueMockResponse(fileName: String) {
        val inputStream = javaClass.classLoader?.getResourceAsStream(fileName)
        val source = inputStream?.source()?.buffer()
        val mockResponse = MockResponse()

        mockResponse.setBody(source!!.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }

    @Test
    fun getPokemons_sentRequest_receivedExpected() {
        runBlocking {
            enqueueMockResponse("pokemons.json")
            val responseBody = service.getPokemon(null).body()
            val request = server.takeRequest()

            print(responseBody)

            assertThat(responseBody).isNotNull()

            assertThat(responseBody?.results?.size).isGreaterThan(0)

            assertThat(request.path).isEqualTo("/pokemon?limit=20")
        }
    }

    @Test
    fun postPokemons_sentRequest_receivedExpected() {
        runBlocking {
            enqueueMockResponse("response.json")
            val responseBody = postServices.postPokemon(PokeRequest())
            val request = server.takeRequest()

            print(responseBody)

            assertThat(responseBody).isNotNull()

            assertThat(responseBody.isSuccessful).isTrue()

            assertThat(responseBody.body()?.status).isEqualTo(SUCCESS_CODE_RESPONSE)
            assertThat(responseBody.body()?.status).isNotEqualTo(FAILURE_CODE_RESPONSE)

            assertThat(request.path).isEqualTo("/pokemon")
        }
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}