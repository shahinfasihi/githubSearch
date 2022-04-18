package com.shahinfasihi.githubsearch

import com.shahinfasihi.githubsearch.data.remote.GithubApi
import com.shahinfasihi.githubsearch.data.repository.GithubRepositoryImpl
import com.shahinfasihi.githubsearch.domain.model.User
import com.shahinfasihi.githubsearch.domain.model.UserList
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.HttpURLConnection


class MainTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var baseUrl: HttpUrl

    private lateinit var service: GithubApi
    private lateinit var githubRepository: GithubRepositoryImpl

    @BeforeEach
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        baseUrl = mockWebServer.url("/search/")
        service = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(GithubApi::class.java)

        // instantiate the system in test
        githubRepository = GithubRepositoryImpl(
            api = service,
        )
    }

    @Test
    fun getUserList() = runBlocking {
        // condition the response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody("{}")
        )

        val emissions = githubRepository.getUserList("payconiq", 1).toList()

        assert(emissions[0].data == null) // loading phase
        assert(emissions[1].data is UserList) // get Data
    }

    @Test
    fun getUser() = runBlocking {
        // condition the response
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_OK)
                .setBody("{}")
        )

        val emissions = githubRepository.getUser("shahinfasihi").toList()

        assert(emissions[0].data == null) // loading phase
        assert(emissions[1].data is User) // get Data
    }


}