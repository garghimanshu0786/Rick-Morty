package repo

import model.Episode
import model.Character
import model.Episodes
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface RickAndMortyApi {

    @GET("episode")
    suspend fun getEpisodesByName(@Query("page") query: Int, @Query("name") name: String): Episodes

    @GET("episode/{id}")
    suspend fun getEpisodeById(@Path("id") id: Int): Episode

    @GET
    suspend fun getCharacter(@Url url: String?): Character

}