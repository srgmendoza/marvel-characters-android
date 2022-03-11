package com.samr.marvelcharacterswiki

import android.graphics.Bitmap
import com.nhaarman.mockitokotlin2.*
import com.samr.data.utils.AspectRatio
import com.samr.data.utils.CustomError
import com.samr.data.utils.LayerResult
import com.samr.domain.models.Character
import com.samr.domain.models.Publishings
import com.samr.domain.models.Images
import com.samr.domain.usecases.CharacterDetailUseCase
import com.samr.domain.usecases.CharacterListUsecase
import com.samr.marvelcharacterswiki.ui.viewModels.CharacterPresenterImpl
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class CharacterPresenterTest {

    private lateinit var presenter: CharacterPresenter

    private val characterUseCase: CharacterListUsecase = mock(CharacterListUsecase::class.java)
    private val characterDetailUseCase: CharacterDetailUseCase = mock(CharacterDetailUseCase::class.java)
    private val imagesUseCase: ImagesUseCase = mock(ImagesUseCase::class.java)

    @Before
    fun setup() {

        presenter = CharacterPresenterImpl(
            characterUseCase,
            characterDetailUseCase,
            imagesUseCase
        )
    }

    @Test
    fun `should call for character list and get success response`() {

        whenever(

            runBlocking { characterUseCase.execute(any()) }

        ).thenAnswer {

            val callback = it.getArgument<((com.samr.data.utils.LayerResult<List<Character>>?) -> Unit)>(0)
            callback(com.samr.data.utils.LayerResult.Success(getCharacterEntityMockList()))
        }

        presenter.fetchCharacterList { result ->
            assert(result is com.samr.data.utils.LayerResult.Success)
        }
    }

    @Test
    fun `should call for character list and get error response with Presentation Layer origin`() {

        whenever(

            runBlocking { characterUseCase.execute(any()) }

        ).thenAnswer {

            val callback = it.getArgument<((com.samr.data.utils.LayerResult<List<Character>>?) -> Unit)>(0)
            callback(
                com.samr.data.utils.LayerResult.Error(
                    com.samr.data.utils.CustomError(
                        Throwable("TestException"),
                        com.samr.data.utils.CustomError.OriginLayer.PRESENTATION_LAYER
                    )
                )
            )
        }

        presenter.fetchCharacterList { result ->
            val error = result as com.samr.data.utils.LayerResult.Error
            val originLayer = (error.error as com.samr.data.utils.CustomError).getErrorOriginLayer()

            assert(originLayer == com.samr.data.utils.CustomError.OriginLayer.PRESENTATION_LAYER)
        }
    }

    @Test
    fun `should call for character detail and get success`() {

        whenever(

            runBlocking { characterDetailUseCase.execute(eq("someId"), any()) }

        ).thenAnswer {

            val callback = it.getArgument<((com.samr.data.utils.LayerResult<Character>?) -> Unit)>(1)
            callback(com.samr.data.utils.LayerResult.Success(mock()))
        }

        presenter.fetchCharacterDetail("someId") { result ->
            assert(result is com.samr.data.utils.LayerResult.Success)
        }
    }

    @Test
    fun `should call for character detail and get error response with Presentation Layer origin`() {

        whenever(

            runBlocking { characterDetailUseCase.execute(eq("someId"), any()) }

        ).thenAnswer {

            val callback = it.getArgument<((com.samr.data.utils.LayerResult<List<Character>>?) -> Unit)>(1)
            callback(
                com.samr.data.utils.LayerResult.Error(
                    com.samr.data.utils.CustomError(
                        Throwable("TestException"),
                        com.samr.data.utils.CustomError.OriginLayer.PRESENTATION_LAYER
                    )
                )
            )
        }

        presenter.fetchCharacterDetail("someId") { result ->
            val error = result as com.samr.data.utils.LayerResult.Error
            val originLayer = (error.error as com.samr.data.utils.CustomError).getErrorOriginLayer()

            assert(originLayer == com.samr.data.utils.CustomError.OriginLayer.PRESENTATION_LAYER)
        }
    }

    @Test
    fun `should call for image and get success`() {

        whenever(

            runBlocking { imagesUseCase.execute(eq(Images("", "")), eq(com.samr.data.utils.AspectRatio.Origin.LIST), any()) }

        ).thenAnswer {

            val callback = it.getArgument<((com.samr.data.utils.LayerResult<Bitmap>) -> Unit)>(2)
            callback(com.samr.data.utils.LayerResult.Success(mock()))
        }

        presenter.fetchImage(com.samr.marvelcharacterswiki.models.Thumbnail("", ""), com.samr.data.utils.AspectRatio.Origin.LIST) { result ->
            assert(result is com.samr.data.utils.LayerResult.Success)
        }
    }

    @Test
    fun `should call for image and get error response with Presentation Layer origin`() {

        whenever(

            runBlocking { imagesUseCase.execute(eq(Images("", "")), eq(com.samr.data.utils.AspectRatio.Origin.LIST), any()) }

        ).thenAnswer {

            val callback = it.getArgument<((com.samr.data.utils.LayerResult<Bitmap>) -> Unit)>(2)
            callback(
                com.samr.data.utils.LayerResult.Error(
                    com.samr.data.utils.CustomError(
                        Throwable("TestException"),
                        com.samr.data.utils.CustomError.OriginLayer.PRESENTATION_LAYER
                    )
                )
            )
        }

        presenter.fetchImage(com.samr.marvelcharacterswiki.models.Thumbnail("", ""), com.samr.data.utils.AspectRatio.Origin.LIST) { result ->

            val error = result as com.samr.data.utils.LayerResult.Error
            val originLayer = (error.error as com.samr.data.utils.CustomError).getErrorOriginLayer()

            assert(originLayer == com.samr.data.utils.CustomError.OriginLayer.PRESENTATION_LAYER)
        }
    }

    private fun getCharacterEntityMockList(): List<Character> {
        val list: MutableList<Character> = mutableListOf()

        for (i in 0..5) {

            list.add(
                Character(
                    id = i.toLong(),
                    name = "",
                    description = "",
                    modified = "",
                    thumbnail = Images(path = "", extension = ""),
                    resourceURI = "",
                    comics = Publishings(
                        available = 0,
                        collectionURI = "",
                        items = listOf(),
                        returned = 0
                    ),
                    series = Publishings(
                        available = 0,
                        collectionURI = "",
                        items = listOf(),
                        returned = 0
                    ),
                    stories = Publishings(
                        available = 0,
                        collectionURI = "",
                        items = listOf(),
                        returned = 0
                    ),
                    events = Publishings(
                        available = 0,
                        collectionURI = "",
                        items = listOf(),
                        returned = 0
                    ),
                    urls = listOf()
                )
            )
        }

        return list
    }
}
