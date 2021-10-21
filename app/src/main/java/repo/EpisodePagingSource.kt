package repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import model.Episode
import repo.WebClient.client
import retrofit2.HttpException
import java.io.IOException

private const val FIRST_PAGE_INDEX = 1

class EpisodePagingSource(
    private val query: String
) : PagingSource<Int, Episode>() {
    override fun getRefreshKey(state: PagingState<Int, Episode>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Episode> {
        val position = params.key ?: FIRST_PAGE_INDEX
        return try {
            val response = client.getEpisodesByName(position, query)
            val episodes = response.episodes
            LoadResult.Page(
                data = episodes,
                prevKey = pageIndex(response.info.prev),
                nextKey = pageIndex(response.info.next)
            )
        } catch (ex: IOException) {
            LoadResult.Error(ex)
        } catch (ex: HttpException) {
            LoadResult.Error(ex)
        }
    }

    private fun pageIndex(url: String?): Int? {
        return url?.split("?page=")?.get(1)?.firstOrNull()?.code
    }

}