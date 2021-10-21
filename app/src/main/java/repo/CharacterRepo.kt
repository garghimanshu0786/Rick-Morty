package repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData

object CharacterRepo {

    fun getCharacters(query: String, gender: String, episodeId: Int) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CharacterPagingSource(query, gender, episodeId) }
        ).liveData
}
