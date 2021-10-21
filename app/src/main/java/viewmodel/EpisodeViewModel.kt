package viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import repo.EpisodeRepo


class EpisodeViewModel : ViewModel() {

    private val currentQuery = MutableLiveData("")

    val episodes = currentQuery.switchMap {
        EpisodeRepo.getSearchResults(it).cachedIn(viewModelScope)
    }

    fun searchEpisodes(query: String) {
        currentQuery.value = query
    }
}