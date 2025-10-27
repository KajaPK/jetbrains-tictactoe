package org.jetbrains.kotlinx.tictactoe

/**
 * Adds logic for AI moves
 */
class TicTacToeComputer : TicTacToe() {
    /**
     * Finds the best possible move using the minimax algorithm and sets that spot to the AI's symbol
     *
     * @param compChar the symbol used by the AI, O by default
     * @param humanChar the symbol used by the human player, X by default
     * @throws GameAlreadyOverException if the game has already ended
     */
    fun makeComputerMove(compChar: Char = 'O', humanChar: Char = 'X') {
        if (isGameOver()) throw GameAlreadyOverException(
            if (getWinner() != null) "${getWinner()!!.name} has already won!" else "It's a draw! The board is already full."
        )
        if (getCurrentPlayer().symbol == humanChar) return

        val (row, col) = findBestMove(compChar, humanChar)
        makeMove(row, col)
    }

    /**
     * Computes the best possible move using the minimax algorithm
     *
     * @param compChar the symbol used by the AI
     * @param humanChar the symbol used by the human player
     * @return the coordinates of the best move
     */
    private fun findBestMove(compChar: Char, humanChar: Char): Pair<Int, Int> {
        var bestScore = Int.MIN_VALUE
        var bestMove: Pair<Int, Int>? = null

        val board = getBoard().map { it.toMutableList() }

        for (r in 0..2) {
            for (c in 0..2) {
                if (board[r][c] == ' ') {
                    board[r][c] = compChar
                    val score = minimax(board, 0, false, compChar, humanChar)
                    board[r][c] = ' '

                    if (score > bestScore) {
                        bestScore = score
                        bestMove = r to c
                    }
                }
            }
        }

        return bestMove ?: (1 to 1)
    }

    /**
     * Evaluates the move using the minimax algorithm.
     * Maximizes the score for the AI and minimizes for the human.
     *
     * @param board the current state of the board
     * @param depth the current depth in the game tree
     * @param isMaximizing true if evaluating the AI's move, false if evaluating human's move
     * @param compChar the symbol used by the AI
     * @param humanChar the symbol used by the human player
     * @return an integer score representing the evaluated value of the board from the AI's perspective:
     *         - Positive: favorable for AI
     *         - Negative: favorable for human
     *         - Zero: draw or neutral position
     */
    private fun minimax(
        board: List<MutableList<Char>>,
        depth: Int,
        isMaximizing: Boolean,
        compChar: Char,
        humanChar: Char
    ): Int {
        val result = evaluateBoard(board, compChar, humanChar)
        if (result != null) return (result - depth)

        if (board.flatten().none { it == ' ' }) return 0
        if (isMaximizing) {
            var bestScore = Int.MIN_VALUE
            for (r in 0..2) {
                for (c in 0..2) {
                    if (board[r][c] == ' ') {
                        board[r][c] = compChar
                        val score = minimax(board, depth + 1, false, compChar, humanChar)
                        board[r][c] = ' '
                        bestScore = maxOf(score, bestScore)
                    }
                }
            }
            return bestScore
        } else {
            var bestScore = Int.MAX_VALUE
            for (r in 0..2) {
                for (c in 0..2) {
                    if (board[r][c] == ' ') {
                        board[r][c] = humanChar
                        val score = minimax(board, depth + 1, true, compChar, humanChar)
                        board[r][c] = ' '
                        bestScore = minOf(score, bestScore)
                    }
                }
            }
            return bestScore
        }
    }

    /**
     * Checks the state of the given board and returns the respective values needed for the minimax algorithm.
     *
     * @param board the board to be evaluated
     * @param compChar the symbol used by the AI
     * @param humanChar the symbol used by the human player
     * @return 10 if the computer has won, -10 if the human has won and null otherwise
     */
    private fun evaluateBoard(board: List<List<Char>>, compChar: Char, humanChar: Char): Int? {
        if (checkWinner(compChar, board)) return +10
        if (checkWinner(humanChar, board)) return -10

        return null
    }
}
