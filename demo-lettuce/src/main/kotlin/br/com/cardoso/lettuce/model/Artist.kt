package br.com.cardoso.lettuce.model

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "artists")
class Artist(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,
        var name: String
) : Serializable