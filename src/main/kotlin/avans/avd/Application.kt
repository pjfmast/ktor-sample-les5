package avans.avd

import avans.avd.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(
        Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::rootModule)
        .start(wait = true)
}

fun Application.rootModule() {
    configureSerialization()
    configureHTTP()
    configureRouting()
}
