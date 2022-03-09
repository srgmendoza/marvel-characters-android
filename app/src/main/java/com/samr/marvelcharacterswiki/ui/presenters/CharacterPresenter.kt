package com.samr.marvelcharacterswiki.ui.presenters

import android.graphics.Bitmap
import com.samr.data.utils.AspectRatio
import com.samr.data.utils.LayerResult
import com.samr.marvelcharacterswiki.models.CharacterDetailModel
import com.samr.marvelcharacterswiki.models.CharacterModel
import com.samr.marvelcharacterswiki.models.Thumbnail

interface CharacterPresenter {

    fun fetchCharacterList(callback: (com.samr.data.utils.LayerResult<List<CharacterModel>>) -> Unit)
    fun fetchCharacterDetail(characterId: String, callback: (com.samr.data.utils.LayerResult<CharacterDetailModel>) -> Unit)
    fun fetchImage(imageInfo: Thumbnail, origin: com.samr.data.utils.AspectRatio.Origin, callback: (com.samr.data.utils.LayerResult<Bitmap>) -> Unit)
}
