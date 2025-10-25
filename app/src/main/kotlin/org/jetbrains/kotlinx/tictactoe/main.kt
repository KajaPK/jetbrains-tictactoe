package org.jetbrains.kotlinx.tictactoe

fun main() {
    println("Welcome to Tic-Tac-Toe!")
    print("Enter name for Player X: ")
    val playerX : String = readln()

    print("Enter name for Player O: ")
    val playerO : String = readln()

    val game = TicTacToe()
    game.startGame(playerX, playerO)
    println(printBoard(game))

    playGame(game)

    println(evaluateGame(game))
}

fun evaluateGame(game : TicTacToe) : String {
    val winner = game.getWinner()
    return if (winner != null) {
        "${winner.name} wins!"
    } else {
        "It's a draw!"
    }
}

fun playGame(game : TicTacToe) {
    var current = game.getCurrentPlayer()

    while (!game.isGameOver()) {

        println("${current.name}'s turn (${current.symbol})")
        print("Enter row and column (0, 1, 2): ")

        val input = readln().trim().split(" ")
        if (input.size != 2) {
            println("Invalid input. Try again.")
            continue
        }

        val (row, col) = input.map { it.toIntOrNull() ?: -1 }

        try {
            game.makeMove(row, col)
        }
        catch (e : GameAlreadyOverException){
            break
        }
        catch (e : IllegalArgumentException){
            println(e.message)
            continue
        }

        println(printBoard(game))
        current = game.getCurrentPlayer()
    }
}

fun printBoard(game: TicTacToe) : String {
    var result = "\n"
    for (row in game.getBoard()) {
        result += row.joinToString(" | ")
        result += "\n--+---+--\n"
    }
    result += "\n"

    return result
}
