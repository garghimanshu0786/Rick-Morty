package view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.rickmorty.R
import com.example.rickmorty.databinding.FragmentCharactersBinding

class CharacterDetailsFragment : Fragment(R.layout.fragment_characters) {

    private val viewModel by lazy {
        ViewModelProvider(this).get(CharacterViewModel::class.java)
    }
    private var _binding: FragmentCharactersBinding? = null
    private val binding
        get() = requireNotNull(_binding)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val episodeId = requireArguments().getInt("episodeId")
        val categories = listOf("All", "Male", "Female", "Genderless", "unknown")
        val spinner = view.findViewById<Spinner>(R.id.gender_spinner)
        spinner.adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1, categories)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position >= 0 && position < categories.count()) {
                    viewModel.filterGender(categories[position])
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do Nothing
            }
        }
        setHasOptionsMenu(true)
        _binding = FragmentCharactersBinding.bind(view)
        val adapter = CharacterAdapter(this)
        binding.recyclerview.apply {
            this.adapter = adapter
            this.setHasFixedSize(true)
        }

        viewModel.character.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
        viewModel.getCharacters(episodeId)
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
                viewModel.searchCharacters(newText ?: "")
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}