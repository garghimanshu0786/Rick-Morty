package view

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import repo.CharacterRepo

class CharacterViewModel : ViewModel() {

    private val episode = MutableLiveData(0)
    private val query = MutableLiveData("")
    private val gender = MutableLiveData("")

    data class EpisodeAndQuery(val id: Int = 0, val query: String = "", val gender: String)

    private val characters = MediatorLiveData<EpisodeAndQuery>().apply { value = EpisodeAndQuery(1,"","") }//.apply { CharacterRepo.getCharacters("", gender, it).cachedIn(viewModelScope) }
    val character = characters.switchMap {
        CharacterRepo.getCharacters(it.query, it.gender, it.id).cachedIn(viewModelScope)
    }
    init {
        characters.addSource(episode) { value ->
            val previous = characters.value
            characters.value = previous?.copy(id = value)
        }
        characters.addSource(query) { value ->
            val previous = characters.value
            characters.value = previous?.copy(query = value)
        }
        characters.addSource(gender) { value ->
            val previous = characters.value
            characters.value = previous?.copy(gender = value)
        }
    }

    fun getCharacters(id: Int) {
        episode.value = id
    }

    fun searchCharacters(query: String) {
        this.query.value = query
    }

    fun filterGender(gender: String) {
        this.gender.value = gender
    }
}