package br.com.cardoso.redisson.repository

import br.com.cardoso.redisson.model.Artist
import org.springframework.data.repository.CrudRepository

interface ArtistRepository : CrudRepository<Artist, Long> {

    fun findByNameStartsWithIgnoreCase(letter: Char): List<Artist>
}