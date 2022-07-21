package br.com.cardoso

import io.netty.handler.codec.http.HttpHeaders
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import org.asynchttpclient.*
import org.asynchttpclient.util.HttpConstants
import java.time.Instant
import kotlin.system.exitProcess
import kotlin.time.ExperimentalTime

class Benchmark

var count = 0
var countErrors = 0
var target = 10000

@ExperimentalTime
fun main() {
    val client = Dsl.asyncHttpClient()
    val resource = Benchmark::class.java.getResource("/artists.json")
    if (resource != null) {
//        val initialPostRequest: Request = RequestBuilder(HttpConstants.Methods.POST)
//                .setUrl("http://localhost:9999/add")
//                .addHeader("Accept", "application/json")
//                .addHeader("Content-Type", "application/json")
//                .setBody(resource.readText())
//                .build()
//        System.err.println("Response Initial Post: " + client.executeRequest(initialPostRequest).get().statusCode)

        val alphabet = "abcdefghijklmnopqrstuvxwyz"
        val after = Instant.now()
        CoroutineScope(Dispatchers.Default).async {
            while (count < ((target + 1) * alphabet.length)) {
                delay(300)
            }
            val before = Instant.now()
            val result = before.minusMillis(after.toEpochMilli())
            println("Tempo total de Execução: ${result.toEpochMilli()}ms")
            println("Transações: $count")
            println("Erros: $countErrors")
            exitProcess(0)
        }

        for (i in 0..target) {
            for (letter in alphabet.toCharArray()) {
                executeAsyncRequest(client, "http://localhost:9999/search/$letter")
            }
        }
    }
}

private fun executeAsyncRequest(client: AsyncHttpClient, url: String) {
    println("Executando a request '$url'")
    count++

    client.prepareGet(url)
            .execute(object : AsyncHandler<Int> {
                private var status: Int? = null

                override fun onStatusReceived(responseStatus: HttpResponseStatus): AsyncHandler.State {
                    status = responseStatus.statusCode
                    return AsyncHandler.State.ABORT
                }

                override fun onHeadersReceived(headers: HttpHeaders): AsyncHandler.State {
                    return AsyncHandler.State.ABORT
                }

                override fun onBodyPartReceived(bodyPart: HttpResponseBodyPart): AsyncHandler.State {
                    return AsyncHandler.State.ABORT
                }

                override fun onCompleted(): Int? {
                    println("Retorno para a request '$url': $status => $count")
                    return status
                }

                override fun onThrowable(t: Throwable) {
                    countErrors++
                    System.err.println("Erro ao obter o artista para a request '$url' => $count")
                }
            })
}