package view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.rickmorty.R
import com.example.rickmorty.databinding.FragmentEpisodeBinding
import viewmodel.EpisodeViewModel

class EpisodeFragment : Fragment(R.layout.fragment_episode) {
    private val viewModel by lazy {
        ViewModelProvider(this)[EpisodeViewModel::class.java]
    }
    private var _binding: FragmentEpisodeBinding? = null
    private val binding
        get() = requireNotNull(_binding)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        _binding = FragmentEpisodeBinding.bind(view)
        val adapter = EpisodeAdapter(this)
        binding.recyclerview.apply {
            this.adapter = adapter
            this.setHasFixedSize(true)
        }
        viewModel.episodes.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater)
        menuInflater.inflate(R.menu.search_menu, menu)
        val myActionMenuItem: MenuItem = menu.findItem(R.id.action_search)
        val searchView: SearchView = myActionMenuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                binding.recyclerview.scrollToPosition(0)
                viewModel.searchEpisodes(newText ?: "")
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}