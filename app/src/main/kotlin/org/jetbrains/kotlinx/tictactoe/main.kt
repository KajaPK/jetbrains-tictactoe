package org.jetbrains.kotlinx.tictactoe

fun main() {
    println("Welcome to Tic-Tac-Toe!")
    print("Do you want to play against a computer? (yes/no) ")

    var choice : String = readln()
    while (choice.lowercase() !in listOf("yes", "no")) {
        print("Please enter yes or no: ")
        choice = readln().trim()
    }

    val game: TicTacToe
    if (choice == "yes") {
        game = TicTacToeComputer()
        playComputerMode(game)
    } else {
        game = TicTacToe()
        playHumanMode(game)
    }

    println(printResults(game))
}

/**
 * Sets up the game to play against an AI opponent.
 */
fun playComputerMode(game: TicTacToeComputer) {
    print("Enter name for Player X: ")
    val playerX = readln()
    game.startGame(playerX)

    playGame(game) { it.makeComputerMove() }
}

/**
 * Sets up the game to play against a human opponent.
 */
fun playHumanMode(game: TicTacToe) {
    print("Enter name for Player X: ")
    val playerX = readln()
    print("Enter name for Player O: ")
    val playerO = readln()
    game.startGame(playerX, playerO)

    playGame(game)
}

/**
 * The game loop, prints the state of the board after every move
 * and prompts the player(s) for their move.
 *
 * @param onComputerMove used to get the AI move, null if two humans are playing against each other
 */
fun <T: TicTacToe> playGame(game: T, onComputerMove: ((T) -> Unit)? = null) {
    println(printBoard(game))
    while (!game.isGameOver()) {
        val current = game.getCurrentPlayer()
        println("${current.name}'s turn (${current.symbol})")
        print("Enter row and column (0, 1, 2): ")

        val input = readln().trim().split(" ")
        if (input.size != 2) {
            println("Invalid input. Try again.")
            continue
        }
        val (row, col) = input.mapNotNull { it.toIntOrNull() }

        try {
            game.makeMove(row, col)
        } catch (e: IllegalArgumentException) {
            println(e.message)
            continue
        } catch (e: GameAlreadyOverException) {
            println(e.message)
            break
        }

        println(printBoard(game))

        if (game.isGameOver()) break

        onComputerMove?.let {
            it(game)
            println(printBoard(game))
        }
    }
}

/**
 * Prints the result of the given game.
 *
 * @param game the game which results will be printed
 */
fun printResults(game : TicTacToe) : String {
    val winner = game.getWinner()
    return if (winner != null) {
        "${winner.name} wins!"
    } else {
        "It's a draw!"
    }
}

/**
 * Prints the current state of the board.
 *
 * @param game the game which state will be printed
 */
fun printBoard(game: TicTacToe): String {
    val board = game.getBoard()
    val sb = StringBuilder("\n")

    for ((i, row) in board.withIndex()) {
        sb.append(row.joinToString(" | ")).append("\n")
        if (i < board.lastIndex) sb.append("--+---+--\n")
    }

    sb.append("\n")
    return sb.toString()
}
