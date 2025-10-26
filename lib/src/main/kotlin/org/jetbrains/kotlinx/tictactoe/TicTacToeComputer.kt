package org.jetbrains.kotlinx.tictactoe

class TicTacToeComputer : TicTacToe() {

    fun makeComputerMove(compChar: Char = 'O', humanChar: Char = 'X') {
        if (isGameOver()) throw GameAlreadyOverException(
            if (getWinner() != null) "${getWinner()!!.name} has already won!" else "It's a draw! The board is already full."
        )
        if (getCurrentPlayer().symbol == humanChar) return

        val (row, col) = findBestMove(compChar, humanChar)
        makeMove(row, col)
    }

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

    private fun evaluateBoard(board: List<List<Char>>, compChar: Char, humanChar: Char): Int? {
        if (checkWinner(compChar, board)) return +10
        if (checkWinner(humanChar, board)) return -10

        return null
    }
}
