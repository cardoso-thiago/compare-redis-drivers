package br.com.cardoso.redisson.controller

import br.com.cardoso.redisson.model.Artist
import br.com.cardoso.redisson.service.ArtistService
import org.springframework.web.bind.annotation.*

@RestController
class ArtistController(private val artistService: ArtistService) {

    @PostMapping("/add")
    fun addArtist(@RequestBody listArtists: List<Artist>) = artistService.addArtists(listArtists)

    @GetMapping("/search/{letter}")
    fun searchArtist(@PathVariable("letter") letter:Char) = artistService.searchArtist(letter)

    @GetMapping("/all")
    fun getAllArtists() = artistService.getAllArtists()
}