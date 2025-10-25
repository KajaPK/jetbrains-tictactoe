package org.jetbrains.kotlinx.tictactoe

class TicTacToeComputer : TicTacToe() {

    fun makeComputerMove() {
        if (isGameOver()) throw GameAlreadyOverException()
        if (getCurrentPlayer().symbol != 'O') return

        val (r, c) = computeComputerMove()
        makeMove(r, c)
    }

    private fun computeComputerMove(): Pair<Int, Int> {
        val emptyCells = getBoard().flatMapIndexed { r, row ->
            row.mapIndexedNotNull { c, cell -> if (cell == ' ') r to c else null }
        }
        return emptyCells.random()
    }
}
