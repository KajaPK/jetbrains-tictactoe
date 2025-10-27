package org.jetbrains.kotlinx.tictactoe

data class Player(val name: String, val symbol: Char)
class GameAlreadyOverException(message: String? = null) : Exception(message)

/**
 * Manages the game logic of TicTacToe
 */
open class TicTacToe {
    private val board = MutableList(3) { MutableList(3) { ' ' } }
    private lateinit var playerX: Player
    private lateinit var playerO: Player
    private lateinit var currentPlayer: Player
    private var winner: Player? = null
    private var moves = 0

    companion object Checker {
        /**
         * Checks whether a player with the given symbol has won a game of TicTacToe.
         *
         * @param symbol the symbol that you want to check
         * @param board the board that will be checked
         * @return true if the player has won, false otherwise
         */
        fun checkWinner(symbol: Char, board: List<List<Char>>): Boolean {
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
            return win.any { line -> line.all { it == symbol } }
        }
    }

    /**
     * Starts a game of TicTacToe by initializing players and setting the current player.
     * Player with symbol X starts.
     *
     * @param namePlayerX the name of the player with symbol X
     * @param namePlayerO the name of the player with symbol O
     */
    fun startGame(namePlayerX: String, namePlayerO: String = "Computer") {
        this.playerX = Player(namePlayerX, 'X')
        this.playerO = Player(namePlayerO, 'O')
        this.currentPlayer = playerX
    }

    /**
     * Sets the spot indicated with the passed parameters to the currentPlayer's symbol.
     * Checks whether the input is valid.
     *
     * @param row the row where the symbol will be placed, must be between 0 and 2
     * @param col the column where the symbol will be placed, must be between 0 and 2
     * @throws IllegalArgumentException if the row and/or column is not 0, 1 or 2 or if the spot is already taken
     * @throws GameAlreadyOverException if the game has already ended
     */
    fun makeMove(row: Int, col: Int) {
        if (row !in 0..2 || col !in 0..2) throw IllegalArgumentException("Invalid coordinates! Try again.")
        if (board[row][col] != ' ') throw IllegalArgumentException("Invalid move! Try again.")
        if (isGameOver()) throw GameAlreadyOverException(
            if (winner != null) "${winner!!.name} has already won!" else "It's a draw! The board is already full."
        )

        board[row][col] = currentPlayer.symbol
        moves++
        if(checkWinner(currentPlayer.symbol, board)) winner = currentPlayer

        currentPlayer = if (currentPlayer == playerX) playerO else playerX
    }

    /**
     * Checks if the game is over already, either because a player has already won
     * or because the board is already full.
     *
     * @return true if the game is over, false otherwise
     */
    fun isGameOver(): Boolean = winner != null || moves == 9

    fun getWinner(): Player? = winner
    fun getBoard(): List<List<Char>> = board
    fun getCurrentPlayer(): Player = currentPlayer
    fun getPlayerX(): Player = playerX
    fun getPlayerO(): Player = playerO

}
