package com.samr.domain

class CharactersDetailUseCaseTest {

//    private lateinit var useCase: CharacterDetailUseCase
//    private lateinit var repo: CharacterDetailRepo
//
//    @ExperimentalCoroutinesApi
//    @get:Rule
//    var mainCoroutineRule = MainCoroutineRule()
//
//    @Before
//    fun setup() {
//
//        repo = mock()
//        useCase = CharacterDetailUseCase(repo)
//    }
//
//    @ExperimentalCoroutinesApi
//    @Test
//    fun `should succeed calling Usecase and get LayerResult-Success`() {
//
//        whenever(
//
//            runBlocking { repo.fetchCharacterDetail(eq("someId"), any()) }
//
//        ).thenAnswer {
//
//            val callback = it.getArgument<((com.samr.data.utils.LayerResult<List<Character>>) -> Unit)>(1)
//            callback(com.samr.data.utils.LayerResult.Success(mock()))
//        }
//
//        useCase.execute("someId") { result ->
//            assert(result is com.samr.data.utils.LayerResult.Success)
//        }
//    }
//
//    @Test
//    fun `should fail calling usecase and get LayerResult-Error`() {
//
//        whenever(
//
//            runBlocking { repo.fetchCharacterDetail(eq("someId"), any()) }
//
//        ).thenAnswer {
//            val callback = it.getArgument<((com.samr.data.utils.LayerResult<List<Character>>) -> Unit)>(1)
//            callback(
//                com.samr.data.utils.LayerResult.Error(
//                    com.samr.data.utils.CustomError(
//                        Throwable("TestException"),
//                        com.samr.data.utils.CustomError.OriginLayer.DATA_LAYER
//                    )
//                )
//            )
//        }
//
//        useCase.execute("someId") { result ->
//            assert(result is com.samr.data.utils.LayerResult.Error)
//        }
//    }
}
