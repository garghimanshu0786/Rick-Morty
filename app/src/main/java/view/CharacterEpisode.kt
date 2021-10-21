package view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickmorty.databinding.CardViewDesignBinding
import model.Character


class CharacterAdapter(private val fragment: Fragment) :
    PagingDataAdapter<Character, CharacterAdapter.ViewHolder>(CHARACTER_DIFFUTIL_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardViewDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    class ViewHolder(private val binding: CardViewDesignBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character) {
            binding.textView.text = character.name
        }
    }

    companion object {
        private val CHARACTER_DIFFUTIL_CALLBACK = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }
}
