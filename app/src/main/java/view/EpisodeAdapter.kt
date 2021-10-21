package view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickmorty.databinding.CardViewDesignBinding
import model.Episode

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.rickmorty.R


class EpisodeAdapter(private val fragment: Fragment) :
    PagingDataAdapter<Episode, EpisodeAdapter.ViewHolder>(EPISODE_DIFFUTIL_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CardViewDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
            holder.itemView.setOnClickListener {
                val fragment = CharacterDetailsFragment()
                val args = Bundle()
                args.putInt("episodeId", item.id)
                fragment.arguments = args
                val transaction = this.fragment.requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, fragment)
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
    }

    class ViewHolder(private val binding: CardViewDesignBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(episode: Episode) {
            binding.textView.text = episode.name
        }
    }

    companion object {
        private val EPISODE_DIFFUTIL_CALLBACK = object : DiffUtil.ItemCallback<Episode>() {
            override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }
}


