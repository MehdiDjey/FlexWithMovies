package com.druide.flexwithmovies.movies

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.druide.flexwithmovies.model.Movies
import com.druide.flexwithmovies.network.MovieService
import com.druide.flexwithmovies.repository.MoviesRepository
import com.druide.flexwithmovies.ui.fragment.movies.MoviesViewModel
import com.druide.flexwithmovies.utils.*
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import io.mockk.MockKAnnotations
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory


@RunWith(JUnit4::class)
class MoviesViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    val context = Mockito.mock(Context::class.java)


    private lateinit var mockWebServer: MockWebServer

    private lateinit var service: MovieService


    var defaultApiRepository = mock<MoviesRepository>()
    private lateinit var viewModel: MoviesViewModel


    @Before
    fun setup() {
        //Used for initiation of Mockk
        MockKAnnotations.init(this)

        mockWebServer = MockWebServer()

        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())

            .build()
            .create(MovieService::class.java)

        viewModel = MoviesViewModel(defaultApiRepository)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun givenServerOk_whenFetch_shouldReturnSuccess() {
        enqueueResponse(
            "data_success.json"
        )
        runBlocking {
            val response: ApiResponse<Movies> = service.getMovies(1)
            Mockito.`when`(defaultApiRepository.getMovies(1))
                .thenReturn(response)

            viewModel.getMovieAtPage(1)
            val result = viewModel.movies.getOrAwaitValue()
            assert(result?.results?.isNotEmpty() == true)

        }
    }


    @Test
    fun givenServerOk_whenFetch_shouldReturnSuccessAndCanLoadMode() {
        enqueueResponse(
            "data_success.json"
        )
        runBlocking {
            val response: ApiResponse<Movies> = service.getMovies(1)
            Mockito.`when`(defaultApiRepository.getMovies(1))
                .thenReturn(response)

            viewModel.getMovieAtPage(1)
            val resultCanLoadMore = viewModel.canLoadMore.getOrAwaitValue()
            assert(resultCanLoadMore)

        }
    }


    @Test
    fun givenServerKO_whenFetch_shouldReturnInvalidApiKey() {
        enqueueResponse(
            "response_invalid_key.json"
        )

        runBlocking {
            val response: ApiResponse<Movies> = service.getMovies(1)

            val errorResponseBody: ResponseBody =
                response.toString().toResponseBody("application/json".toMediaTypeOrNull())

            val apiResponse: ApiResponse.Failure.Error<Movies> =
                ApiResponse.Failure.Error(Response.error(401, errorResponseBody))

            Mockito.`when`(defaultApiRepository.getMovies(1))
                .thenReturn(apiResponse)


            viewModel.getMovieAtPage(1)
            val result = viewModel.error.getOrAwaitValue()

            Assert.assertEquals(result, "$apiResponse [ Code : ${apiResponse.statusCode.code}], check your internet connection and retry" )
        }
    }


    @Test
    fun givenServerKO_whenFetch_shouldReturnUnauthorized() {

        runBlocking {
            val response: ApiResponse<Movies> = service.getMovies(1)

            val errorResponseBody: ResponseBody =
                response.toString().toResponseBody("application/json".toMediaTypeOrNull())

            val apiResponse: ApiResponse.Failure.Error<Movies> =
                ApiResponse.Failure.Error(Response.error(404, errorResponseBody))

            Mockito.`when`(defaultApiRepository.getMovies(1))
                .thenReturn(apiResponse)

            viewModel.getMovieAtPage(1)
            val result = viewModel.error.getOrAwaitValue()

            Assert.assertEquals(result, "$apiResponse [ Code : ${apiResponse.statusCode.code}], check your internet connection and retry" )
        }
    }

    @Test
    fun givenServerKO_whenFetch_shouldReturnExceptionError() {
        val exception = Exception("SomeException")
        val apiResponse: ApiResponse.Failure.Exception<Movies> = ApiResponse.error(exception)

        runBlocking {
            Mockito.`when`(defaultApiRepository.getMovies(1))
                .thenReturn(apiResponse)
        }
        viewModel.getMovieAtPage(1)
        val result = viewModel.error.getOrAwaitValue()

        Assert.assertEquals(result, ("Something wrong with : $apiResponse"))
    }


    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {

        val source = TestMockResponseFileReader(fileName)
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse
                .setBody(source.content)
        )
    }
}