package com.samr.domain.mappers

import com.samr.data.entities.CharacterDomain

object CharacterMapper {

    fun mapCharacterResult(result: ByteArray): List<CharacterDomain>{

        var res = ""
        res = result.toString(Charsets.UTF_8)


        //TODO: Convert res to List<Characters>

//        domainInteractor.onCharactersDataFetched(listOf())

        return listOf()
    }
}