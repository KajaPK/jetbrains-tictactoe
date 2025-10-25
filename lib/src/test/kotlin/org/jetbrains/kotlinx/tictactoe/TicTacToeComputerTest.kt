package org.jetbrains.kotlinx.tictactoe

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

class TicTacToeComputerTest {

    private val game = TicTacToeComputer()

    private val playerX = Player("Player 1", 'X')
    private val playerO = Player("Computer", 'O')

    @BeforeEach
    fun setup() {
        game.startGame(playerX.name)
    }

    @Test
    fun startGameTest() {
        assertEquals(playerX, game.getPlayerX())
        assertEquals(playerO, game.getPlayerO())
        assertEquals(playerX, game.getCurrentPlayer())
        assertNull(game.getWinner())
    }

    @Test
    fun makeComputerMoveTest_Valid(){
        game.makeMove(1 ,1)
        game.makeComputerMove()

        assertEquals('X', game.getBoard()[1][1])
        assertEquals('O', game.getBoard()[0][0])
        assertNull(game.getWinner())
        assertEquals(playerX, game.getCurrentPlayer())
    }

    @Test
    fun makeComputerMoveTest_BlockHumanFromWinning(){
        game.makeMove(1 ,1)
        game.makeComputerMove()
        game.makeMove(0 ,1)
        game.makeComputerMove()

        assertEquals('O', game.getBoard()[2][1])
        assertNull(game.getWinner())
        assertEquals(playerX, game.getCurrentPlayer())
    }

    @Test
    fun makeComputerMoveTest_ComputerWin(){
        game.makeMove(1 ,1)
        game.makeComputerMove()
        game.makeMove(2 ,2)
        game.makeComputerMove()

        assertEquals('O', game.getBoard()[0][2])
        assertNull(game.getWinner())

        game.makeMove(2 ,0)
        game.makeComputerMove()
        assertEquals('O', game.getBoard()[0][1])
        assertEquals(playerO, game.getWinner())
    }

    @Test
    fun makeComputerMoveTest_HumansTurn(){
        game.makeMove(1 ,1)
        game.makeComputerMove()

        val oldBoard = game.getBoard().map { it.toList() }
        game.makeComputerMove()
        assertEquals(oldBoard, game.getBoard())
        assertEquals(playerX, game.getCurrentPlayer())
    }

    @Test
    fun makeComputerMoveTest_GameAlreadyOver() {
        game.makeMove(1 ,1)
        game.makeComputerMove()
        game.makeMove(2 ,2)
        game.makeComputerMove()
        game.makeMove(2 ,0)
        game.makeComputerMove()

        assertEquals(playerO, game.getWinner())
        val oldBoard = game.getBoard().map { it.toList() }
        assertThrows(GameAlreadyOverException::class.java) {
            game.makeComputerMove()
        }
        assertEquals(oldBoard, game.getBoard())
    }
}