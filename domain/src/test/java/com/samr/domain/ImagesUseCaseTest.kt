package com.samr.domain

class ImagesUseCaseTest {

//    private lateinit var useCase: ImagesUseCase
//    private lateinit var repo: ImageRepo
//
//    @ExperimentalCoroutinesApi
//    @get:Rule
//    var mainCoroutineRule = MainCoroutineRule()
//
//    @Before
//    fun setup() {
//
//        repo = mock()
//        useCase = ImagesUseCase(repo)
//    }
//
//    @ExperimentalCoroutinesApi
//    @Test
//    fun `should succeed calling Usecase and get LayerResult-Success`() {
//
//        whenever(
//
//            runBlocking { repo.fetchImage(eq("somePath.someExtension"), any()) }
//
//        ).thenAnswer {
//
//            val callback = it.getArgument<((com.samr.data.utils.LayerResult<Bitmap>) -> Unit)>(1)
//            callback(com.samr.data.utils.LayerResult.Success(mock()))
//        }
//
//        val thumbnail = Thumbnail(path = "somePath", extension = "someExtension")
//        useCase.execute(thumbnail, mock()) { result ->
//            assert(result is com.samr.data.utils.LayerResult.Success)
//        }
//    }
//
//    @Test
//    fun `should fail calling usecase and get LayerResult-Error`() {
//
//        whenever(
//
//            runBlocking { repo.fetchImage(eq("somePath.someExtension"), any()) }
//
//        ).thenAnswer {
//            val callback = it.getArgument<((com.samr.data.utils.LayerResult<Bitmap>) -> Unit)>(1)
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
//        val thumbnail = Thumbnail(path = "somePath", extension = "someExtension")
//        useCase.execute(thumbnail, mock()) { result ->
//            assert(result is com.samr.data.utils.LayerResult.Error)
//        }
//    }
}
