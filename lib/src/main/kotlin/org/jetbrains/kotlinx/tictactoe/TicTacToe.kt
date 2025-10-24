package org.jetbrains.kotlinx.tictactoe

data class Player(val name: String, val symbol: Char)
class GameAlreadyOverException : Throwable()

class TicTacToe {
    private val board = MutableList(3) { MutableList(3) { ' ' } }
    private lateinit var playerX: Player
    private lateinit var playerO: Player
    private lateinit var currentPlayer: Player
    private var winner: Player? = null
    private var moves = 0

    fun startGame(namePlayerX: String, namePlayerO: String) {
        this.playerX = Player(namePlayerX, 'X')
        this.playerO = Player(namePlayerO, 'O')
        this.currentPlayer = playerX
    }

    fun makeMove(row: Int, col: Int) {
        if (row !in 0..2 || col !in 0..2) throw IllegalArgumentException("Invalid coordinates! Try again.")
        if (board[row][col] != ' ') throw IllegalArgumentException("Invalid move! Try again.")
        if (winner != null) throw GameAlreadyOverException()

        board[row][col] = currentPlayer.symbol
        moves++
        checkWinner(currentPlayer)

        currentPlayer = if (currentPlayer.symbol == 'X') playerO else playerX
    }

    private fun checkWinner(player: Player) {
        val s = player.symbol
        val win = listOf(
            listOf(board[0][0], board[0][1], board[0][2]),
            listOf(board[1][0], board[1][1], board[1][2]),
            listOf(board[2][0], board[2][1], board[2][2]),
            listOf(board[0][0], board[1][0], board[2][0]),
            listOf(board[0][1], board[1][1], board[2][1]),
            listOf(board[0][2], board[1][2], board[2][2]),
            listOf(board[0][0], board[1][1], board[2][2]),
            listOf(board[0][2], board[1][1], board[2][0])
        )
        if (win.any { line -> line.all { it == s } }) {
            winner = player
        }
    }

    fun isGameOver(): Boolean = winner != null || moves == 9

    fun getWinner(): Player? = winner

    fun getBoard(): List<List<Char>> = board

    fun getCurrentPlayer(): Player = currentPlayer
}
