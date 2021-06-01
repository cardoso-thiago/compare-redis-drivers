package br.com.cardoso.lettuce.repository

import br.com.cardoso.lettuce.model.Artist
import org.springframework.data.repository.CrudRepository

interface ArtistRepository : CrudRepository<Artist, Long> {

    fun findByNameStartsWithIgnoreCase(letter: Char): List<Artist>
}