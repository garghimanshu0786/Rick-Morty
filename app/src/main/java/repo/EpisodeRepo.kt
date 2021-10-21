package repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData


object EpisodeRepo {

    fun getSearchResults(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { EpisodePagingSource(query) }
        ).liveData
}
