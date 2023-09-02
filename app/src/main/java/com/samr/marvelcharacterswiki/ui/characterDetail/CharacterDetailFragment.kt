package com.samr.marvelcharacterswiki.ui.characterDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.samr.domain.models.Character
import com.samr.marvelcharacterswiki.databinding.FragmentCharacterDetailBinding
import com.samr.marvelcharacterswiki.ui.utils.ViewUtils
import org.koin.java.KoinJavaComponent.inject

class CharacterDetailFragment : Fragment() {

    private val viewModel: CharacterDetailViewModel by inject(CharacterDetailViewModel::class.java)

    private lateinit var binding: FragmentCharacterDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val characterId = arguments?.let { CharacterDetailFragmentArgs.fromBundle(it).characterId }

        if(characterId != null)
            setupObservers(characterId)
        else
            renderError()

    }

    private fun setupObservers(id: String) {
/*        viewModel.getCharacterById(id).observe(viewLifecycleOwner) {
            renderView(it)
        }*/
    }

    private fun renderView(character: Character) {

        binding.characterName.text = character.name
        binding.characterDescription.text = character.description.ifEmpty { "Description not available" }

        binding.comicsCount.text = "${character.comics.items.size} Comics"
        binding.seriesCount.text = "${character.series.items.size} Series"
        binding.storiesCount.text = "${character.stories.items.size} Stories"

        binding.moreInfoBtn.setOnClickListener {
            navigateToWebView(character.detailUrl)
        }

        ViewUtils.paintImage(character.thumbnail.poster, binding.characterImage)
    }

    private fun renderError() {

        activity?.let {
            ViewUtils.onDialog(
                "Error getting Marvel Character",
                it
            ) {}
        }
    }

    private fun navigateToWebView(url: String) {
        val action = CharacterDetailFragmentDirections.actionSecondFragmentToWebview(url)
        findNavController().navigate(action)
    }
}
