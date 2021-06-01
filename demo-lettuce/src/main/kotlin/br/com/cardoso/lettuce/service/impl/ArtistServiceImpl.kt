package br.com.cardoso.lettuce.service.impl

import br.com.cardoso.lettuce.model.Artist
import br.com.cardoso.lettuce.repository.ArtistRepository
import br.com.cardoso.lettuce.service.ArtistService
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class ArtistServiceImpl(private val artistRepository: ArtistRepository) : ArtistService {

    @CacheEvict(cacheNames = ["Artist"], allEntries = true)
    override fun addArtists(listArtists: List<Artist>) {
        artistRepository.saveAll(listArtists)
    }

    @Cacheable(cacheNames = ["Artist"], key = "#letter")
    override fun searchArtist(letter: Char): List<Artist> {
        return artistRepository.findByNameStartsWithIgnoreCase(letter)
    }

    @Cacheable(cacheNames = ["Artist"], key = "#root.method.name")
    override fun getAllArtists() = artistRepository.findAll().toList()
}
