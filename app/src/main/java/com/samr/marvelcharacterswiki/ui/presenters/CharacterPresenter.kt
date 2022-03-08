package com.samr.marvelcharacterswiki.ui.presenters

import android.graphics.Bitmap
import com.samr.core.utils.AspectRatio
import com.samr.core.utils.LayerResult
import com.samr.marvelcharacterswiki.models.CharacterDetailModel
import com.samr.marvelcharacterswiki.models.CharacterModel
import com.samr.marvelcharacterswiki.models.Thumbnail

interface CharacterPresenter {

    fun fetchCharacterList(callback: (LayerResult<List<CharacterModel>>) -> Unit)
    fun fetchCharacterDetail(characterId: String, callback: (LayerResult<CharacterDetailModel>) -> Unit)
    fun fetchImage(imageInfo: Thumbnail, origin: AspectRatio.Origin, callback: (LayerResult<Bitmap>) -> Unit)
}
