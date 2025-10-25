package org.jetbrains.kotlinx.tictactoe

class TicTacToeComputer : TicTacToe() {

    fun makeComputerMove() {
        if (isGameOver()) throw GameAlreadyOverException()
        if (getCurrentPlayer().symbol != 'O') return

        val (row, col) = findBestMove()
        makeMove(row, col)
    }

    private fun findBestMove(): Pair<Int, Int> {
        var bestScore = Int.MIN_VALUE
        var bestMove: Pair<Int, Int>? = null

        val board = getBoard().map { it.toMutableList() }

        for (r in 0..2) {
            for (c in 0..2) {
                if (board[r][c] == ' ') {
                    board[r][c] = 'O'
                    val score = minimax(board, 0, false)
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

    private fun minimax(board: List<MutableList<Char>>, depth: Int, isMaximizing: Boolean): Int {
        val result = evaluateBoard(board)
        if (result != null) return result - depth

        if (board.flatten().none { it == ' ' }) return 0
        if (isMaximizing) {
            var bestScore = Int.MIN_VALUE
            for (r in 0..2) {
                for (c in 0..2) {
                    if (board[r][c] == ' ') {
                        board[r][c] = 'O'
                        val score = minimax(board, depth + 1, false)
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
                        board[r][c] = 'X'
                        val score = minimax(board, depth + 1, true)
                        board[r][c] = ' '
                        bestScore = minOf(score, bestScore)
                    }
                }
            }
            return bestScore
        }
    }

    private fun evaluateBoard(board: List<List<Char>>): Int? {
        if (checkWinner('O', board)) return +10
        if (checkWinner('X', board)) return -10

        return null
    }
}
