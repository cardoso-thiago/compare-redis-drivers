package br.com.cardoso.redisson.service

import br.com.cardoso.redisson.model.Artist

interface ArtistService {

    fun addArtists(listArtists: List<Artist>)
    fun searchArtist(letter: Char): List<Artist>
    fun getAllArtists(): List<Artist>
}