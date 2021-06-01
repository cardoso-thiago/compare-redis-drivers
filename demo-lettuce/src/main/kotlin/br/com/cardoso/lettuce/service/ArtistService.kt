package br.com.cardoso.lettuce.service

import br.com.cardoso.lettuce.model.Artist

interface ArtistService {

    fun addArtists(listArtists: List<Artist>)
    fun searchArtist(letter: Char): List<Artist>
    fun getAllArtists(): List<Artist>
}