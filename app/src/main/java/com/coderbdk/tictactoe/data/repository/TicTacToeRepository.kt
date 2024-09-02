package com.coderbdk.tictactoe.data.repository

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONObject

class TicTacToeRepository(private val url: String) {

    private lateinit var webSocket: WebSocket

    fun connect(
        onMessageReceived: (JSONObject) -> Unit,
        onConnectionOpened: () -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        val client = OkHttpClient()

        val request = Request.Builder().url(url).build()
        val listener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
                onConnectionOpened()
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                val json = JSONObject(text)
                onMessageReceived(json)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: okhttp3.Response?) {
                onFailure(t)
            }
        }

        webSocket = client.newWebSocket(request, listener)
        client.dispatcher.executorService.shutdown()
    }

    fun createRoom() {
        val json = JSONObject().apply {
            put("action", "createRoom")
        }
        webSocket.send(json.toString())
    }

    fun joinRoom(roomId: String) {
        val json = JSONObject().apply {
            put("action", "joinRoom")
            put("roomId", roomId)
        }
        webSocket.send(json.toString())
    }

    fun makeMove(move: String) {
        val json = JSONObject().apply {
            put("action", "move")
            put("move", move)
        }
        webSocket.send(json.toString())
    }

    fun close() {
        webSocket.close(1000, "Closing")
    }
}
