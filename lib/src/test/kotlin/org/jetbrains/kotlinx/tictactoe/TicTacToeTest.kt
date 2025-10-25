package org.jetbrains.kotlinx.tictactoe

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

class TicTacToeTest {

    private val game = TicTacToe()

    private val playerX = Player("Player 1", 'X')
    private val playerO = Player("Player 2", 'O')

    @BeforeEach
    fun setup() {
        game.startGame(playerX.name, playerO.name)
    }

    @Test
    fun startGameTest() {
        assertEquals(playerX, game.getPlayerX())
        assertEquals(playerO, game.getPlayerO())
        assertEquals(playerX, game.getCurrentPlayer())
        assertNull(game.getWinner())
    }

    @Test
    fun makeMoveTest_Valid() {
        game.makeMove(1 ,1)
        assertEquals('X', game.getBoard()[1][1])
        assertNull(game.getWinner())
        assertEquals(playerO, game.getCurrentPlayer())
    }

    @Test
    fun makeMoveTest_ValidMultiple() {
        game.makeMove(1 ,1)
        game.makeMove(0, 0)
        game.makeMove(2, 1)

        assertEquals('X', game.getBoard()[1][1])
        assertEquals('O', game.getBoard()[0][0])
        assertEquals('X', game.getBoard()[2][1])
        assertNull(game.getWinner())
        assertEquals(playerO, game.getCurrentPlayer())
    }

    @Test
    fun makeMoveTest_InvalidCoordinates() {
        val oldBoard = game.getBoard().map { it.toList() }
        val e = assertThrows(IllegalArgumentException::class.java) {
            game.makeMove(22, 0)
        }

        assertEquals("Invalid coordinates! Try again.", e.message)
        assertEquals(oldBoard, game.getBoard())

    }

    @Test
    fun makeMoveTest_InvalidMove() {
        game.makeMove(2, 0)
        val oldBoard = game.getBoard().map { it.toList() }
        val e = assertThrows(IllegalArgumentException::class.java) {
            game.makeMove(2, 0)
        }

        assertEquals("Invalid move! Try again.", e.message)
        assertEquals(oldBoard, game.getBoard())
    }

    @Test
    fun makeMoveTest_GameAlreadyOver() {
        game.makeMove(1 ,1)
        game.makeMove(0, 0)
        game.makeMove(2, 1)
        game.makeMove(2 ,2)
        game.makeMove(0, 1)

        assertEquals(playerX, game.getWinner())

        val oldBoard = game.getBoard().map { it.toList() }
        assertThrows(GameAlreadyOverException::class.java) {
            game.makeMove(1, 0)
        }
        assertEquals(oldBoard, game.getBoard())
    }

    @Test
    fun isGameOverTest_Winner() {
        game.makeMove(1 ,1)
        game.makeMove(0, 0)
        game.makeMove(2, 1)
        game.makeMove(2 ,2)
        game.makeMove(0, 1)

        assertEquals(playerX, game.getWinner())
        assertTrue(game.isGameOver())
    }

    @Test
    fun isGameOverTest_Draw() {
        game.makeMove(1 ,1)
        game.makeMove(0, 0)
        game.makeMove(2, 1)
        game.makeMove(0 ,1)
        game.makeMove(2, 2)
        game.makeMove(2, 0)
        game.makeMove(1, 0)
        game.makeMove(1, 2)
        game.makeMove(0, 2)

        assertNull(game.getWinner())
        assertTrue(game.isGameOver())
    }
}