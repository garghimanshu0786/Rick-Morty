package repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import model.Character
import repo.WebClient.client
import retrofit2.HttpException
import java.io.IOException

class CharacterPagingSource(
    private val query: String,
    private val gender: String,
    private val episodeId: Int
) : PagingSource<Int, Character>() {
    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return try {
            val response = client.getEpisodeById(episodeId)
            val charactersUrls = response.characters
            var characters = charactersUrls.map {
                client.getCharacter(it)
            }
            characters = characters.filter { it.name.contains(query) }
            if (gender.isNotEmpty() && gender != "All") {
                characters = characters.filter { it.gender == gender }
            }
            LoadResult.Page(
                data = characters,
                prevKey = null,
                nextKey = null
            )
        } catch (ex: IOException) {
            LoadResult.Error(ex)
        } catch (ex: HttpException) {
            LoadResult.Error(ex)
        }
    }
}