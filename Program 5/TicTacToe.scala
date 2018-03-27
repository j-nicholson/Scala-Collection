import scala.collection.mutable.ListBuffer
import scala.util.Random

/**
  * Class that contains methods needed to set up a Tic Tac Toe match.
  */
class TicTacToe() {

  // List of winning player boards
  var winning_player_board: ListBuffer[ListBuffer[String]] = ListBuffer(ListBuffer())
  // Computer win status
  var computer_win_status: Boolean = true

  /**
    * Draws tic tac toe board
    * @param board board to be drawn
    */
  def drawBoard(board: ListBuffer[String]): Unit = {
    println("\n   |   |")
    println(" " + board(1) + " | " + board(2) + " | " + board(3))
    println("   |   |")
    println("-----------")
    println("   |   |")
    println(" " + board(4) + " | " + board(5) + " | " + board(6))
    println("   |   |")
    println("-----------")
    println("   |   |")
    println(" " + board(7) + " | " + board(8) + " | " + board(9))
    println("   |   |\n")
  }

  /**
    * Chooses first player
    * @return first player as a string
    */
  def chooseFirstPlayer(): String = "computer"

  /**
    * Asks for a rematch upon end of game
    * @return rematch string
    */
  def rematch(): String = "Would you like to play again? (y/n): "

  /**
    * Places player move at specified board location
    * @param board board to move on
    * @param letter letter to move with
    * @param move move location
    */
  def move(board: ListBuffer[String], letter: String, move: Int): Unit = {
    board(move) = letter
  }

  /**
    * Checks winning combinations to see if player has won the game
    * @param board board to check
    * @param letter letter to check
    * @return winning status as boolean
    */
  def isWinner(board: ListBuffer[String], letter: String): Boolean = {
    board(7) == letter && board(8) == letter && board(9) == letter ||
      board(4) == letter && board(5) == letter && board(6) == letter ||
      board(1) == letter && board(2) == letter && board(3) == letter ||
      board(7) == letter && board(4) == letter && board(1) == letter ||
      board(8) == letter && board(5) == letter && board(2) == letter ||
      board(9) == letter && board(6) == letter && board(3) == letter ||
      board(7) == letter && board(5) == letter && board(3) == letter ||
      board(9) == letter && board(5) == letter && board(1) == letter
  }

  /**
    * Returns a copy of the current tic tac toe board
    * @param board board to copy
    * @return copied board as a ListBuffer
    */
  def copyBoard(board: ListBuffer[String]): ListBuffer[String] = {
    val board_copy: ListBuffer[String] = ListBuffer()
    for (i <- board.indices) {
      board_copy.+=(board(i))
    }
    board_copy
  }

  /**
    * Checks to see if a specified space is free to make a legal move
    * @param board board to check
    * @param move move to check
    * @return space free status as boolean
    */
  def isSpaceFree(board: ListBuffer[String], move: Int): Boolean = board(move) == " "

  /**
    * Returns legal player move
    * @param board board to move on
    * @return player move as integer
    */
  def getPlayerMove(board: ListBuffer[String]): Int = {
    var move = " "
    while((!move.equals("1") && !move.equals("2") && !move.equals("3") && !move.equals("4") && !move.equals("5") && !move.equals("6") && !move.equals("7") && !move.equals("8") && !move.equals("9") && !move.equals("10"))
      || !this.isSpaceFree(board, move.toInt)) {
      print("Enter a move (1-9): ")
      move = scala.io.StdIn.readLine()
    }
    move.toInt
  }

  /**
    * AI to make the computer choose a move if a space is free based on the provided move set
    * @param board board to move on
    * @param move_list possible moves
    * @return computer move as integer
    */
  def computerMoveAI(board: ListBuffer[String], move_list: ListBuffer[Int]): Int = {
    val possible_moves: ListBuffer[Int] = ListBuffer()
    for(i <- move_list.indices) {
      if(this.isSpaceFree(board, i)) {
        possible_moves.+=(i)
      }
    }

    if(possible_moves.nonEmpty) {
      val start = 1
      val end = possible_moves.length
      val random_index = start + Random.nextInt(end - start)
      possible_moves(random_index)
    }
    else {
      0
    }
  }

  /**
    * AI to make the computer choose a move based on lists of previous losses
    * @param board board to move on
    * @param computer_letter letter of computer
    * @return computer move as integer
    */
  def getComputerMoveBasedOnLosingBoard(board: ListBuffer[String], computer_letter: String): Int = {
    var player_letter = ""
    if(computer_letter.equals("X")) {
      player_letter = "O"
    }
    else {
      player_letter = "X"
    }

    for(i <- this.winning_player_board.indices) {
      for(j <- 1 until this.winning_player_board(i).length) {
        if(!this.winning_player_board(i)(j).equals(computer_letter)) {
          val copy = this.copyBoard(board)
          if(this.isSpaceFree(copy, j)) {
            this.move(copy, player_letter, j)
            if(this.isWinner(copy, player_letter)) {
              j
            }
          }
        }
      }
    }
    this.computerMoveAI(board, ListBuffer(1, 2, 3, 4, 5, 6, 7, 8, 9))
  }

  /**
    * AI to make the computer choose a random move if available
    * @param board board to test possible moves
    * @return computer move as integer
    */
  def getComputerMove(board: ListBuffer[String]): Int = this.computerMoveAI(board, ListBuffer(1, 2, 3, 4, 5, 6, 7, 8, 9))

  /**
    * Checks to see if the game board's spaces are full
    * @param board board to check
    * @return full status as boolean
    */
  def isBoardFull(board: ListBuffer[String]): Boolean = {
    var is_full = true
    for(i <- 1 until 10) {
      if(this.isSpaceFree(board, i)) {
        is_full = false
      }
    }
    is_full
  }

  /**
    * Captures a list containing the losing moves of the computer AI
    * @param winning_player_board board computer lost on
    */
  def setCapturedComputerLoss(winning_player_board: ListBuffer[String]): Unit = this.winning_player_board.+=(winning_player_board)

  /**
    * Captures a list containing the losing moves of the computer AI as a Char ListBuffer
    * @param winning_player_board board computer lost on as ListBuffer[Char]
    */
  def setCapturedComputerLossChar(winning_player_board: ListBuffer[Char]): Unit  = {
    val converted_winning_player_board: ListBuffer[String] = ListBuffer()
    for(i <- winning_player_board.indices) {
      converted_winning_player_board.append(winning_player_board(i).toString)
    }
    this.winning_player_board.+=(converted_winning_player_board)
  }

  /**
    * Returns ListBuffer containing the losing moves of the computer AI
    * @return list of losing moves as ListBuffer
    */
  def getCapturedComputerLoss(): ListBuffer[ListBuffer[String]] = this.winning_player_board

  /**
    * Sets the specified win status of the computer
    * @param status win status as boolean
    */
  def setComputerWinStatus(status: Boolean): Unit = this.computer_win_status = status

  /**
    * Returns the current win status of the computer
    * @return win status as boolean
    */
  def getComputerWinStatus(): Boolean = this.computer_win_status

}
