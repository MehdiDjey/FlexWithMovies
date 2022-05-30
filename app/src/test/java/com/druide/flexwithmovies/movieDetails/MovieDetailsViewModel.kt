package com.druide.flexwithmovies.movieDetails

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.druide.flexwithmovies.model.Movie
import com.druide.flexwithmovies.model.Movies
import com.druide.flexwithmovies.movies.MoviesViewModel
import com.druide.flexwithmovies.network.MovieService
import com.druide.flexwithmovies.repository.MovieRepository
import com.druide.flexwithmovies.repository.MoviesRepository
import com.druide.flexwithmovies.ui.fragment.movie.MovieDetailsViewModel
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
class MovieDetailsViewModelTest {


    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()



    private lateinit var mockWebServer: MockWebServer

    private lateinit var service: MovieService


    var defaultApiRepository = mock<MovieRepository>()
    private lateinit var viewModel: MovieDetailsViewModel


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

        viewModel = MovieDetailsViewModel(defaultApiRepository)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun givenServerOk_whenFetch_shouldReturnSuccess() {
        enqueueResponse(
            "movie_success.json"
        )
        runBlocking {
            val response: ApiResponse<Movie> = service.getMovieById(675353)
            Mockito.`when`(defaultApiRepository.getMovie(675353))
                .thenReturn(response)

            viewModel.getMovieDetailWithId(675353)
            val result = viewModel.movieDetails.getOrAwaitValue()
            assert(result?.id == 675353)
            assert(result?.releaseDate == "2022-03-30")
            assert(result?.title == "Sonic the Hedgehog 2")

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

            val apiResponse: ApiResponse.Failure.Error<Movie> =
                ApiResponse.Failure.Error(Response.error(401, errorResponseBody))

            Mockito.`when`(defaultApiRepository.getMovie(675353))
                .thenReturn(apiResponse)


            viewModel.getMovieDetailWithId(675353)
            val result = viewModel.error.getOrAwaitValue()

            Assert.assertEquals(result, "$apiResponse [ Code : ${apiResponse.statusCode.code}], check your internet connection and retry" )
        }
    }


    @Test
    fun givenServerKO_whenFetch_shouldReturnExceptionError() {
        val exception = Exception("SomeException")
        val apiResponse: ApiResponse.Failure.Exception<Movie> = ApiResponse.error(exception)

        runBlocking {
            Mockito.`when`(defaultApiRepository.getMovie(675353))
                .thenReturn(apiResponse)
        }
        viewModel.getMovieDetailWithId(675353)
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