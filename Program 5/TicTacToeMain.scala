import scala.collection.mutable.ListBuffer
import java.io._

import scala.io.Source

/**
  * Main Tic Tac Toe file that runs game
  */
object TicTacToeMain {

  def main(args: Array[String]): Unit = {
    val game = new TicTacToe()
    print("* * * Welcome to Tic Tac Toe * * *\n")
    var running = true

    while(running) {
      val game_board = ListBuffer(" ", " ", " ", " ", " ", " ", " ", " ", " ", " ")
      val player_letter = "O"
      val computer_letter = "X"

      var turn = game.chooseFirstPlayer()
      print("The " + turn + " will go first.")

      var game_playing = true

      // Reads file for computer losses if file exists
      if(new File("computer_losses.txt").exists()) {
        val file_losses = Source.fromFile("computer_losses.txt").getLines().toList
        val file_losses_conversion = file_losses.to[ListBuffer]
        val file_losses_conversion_buffer_char: ListBuffer[ListBuffer[Char]] = ListBuffer()


        for(i <- file_losses_conversion.indices) {
          file_losses_conversion_buffer_char.append(file_losses_conversion(i).to[ListBuffer])
        }
        for(i <- file_losses_conversion_buffer_char.indices) {
          if(!game.getCapturedComputerLoss().contains(file_losses_conversion_buffer_char(i))) {
            game.setCapturedComputerLossChar(file_losses_conversion_buffer_char(i))
          }
        }
        game.setComputerWinStatus(false)
      }

      while(game_playing) {
        if(turn.equals("player")) {
          game.drawBoard(game_board)
          val player_move = game.getPlayerMove(game_board)
          game.move(game_board, player_letter, player_move)
          if(game.isWinner(game_board, player_letter)) {
            game.drawBoard(game_board)
            game.setCapturedComputerLoss(game.copyBoard(game_board))

            val losses = game.getCapturedComputerLoss()

            // Append computer losses to text file.
            val file = new File("computer_losses.txt")
            val bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))
            for(i <- losses.indices) {
              for(j <- 1 until losses(i).length) {
                bw.write(losses(i)(j))
              }
              bw.newLine()
            }
            bw.close()

            game.setComputerWinStatus(false)
            print("You have won!\n")
            game_playing = false
          }
          else {
            if(game.isBoardFull(game_board)) {
              game.drawBoard(game_board)
              print("This game ends in a tie!\n")
              game_playing = false
            }
            else {
              turn = "computer"
            }
          }
        }

        else {
          var computer_move = 0
          game.drawBoard(game_board)
          if(!game.getComputerWinStatus()) {
            computer_move = game.getComputerMoveBasedOnLosingBoard(game_board, computer_letter)
            game.move(game_board, computer_letter, computer_move)
          }
          else {
            computer_move = game.getComputerMove(game_board)
            game.move(game_board, computer_letter, computer_move)
          }

          if(game.isWinner(game_board, computer_letter)) {
            game.drawBoard(game_board)
            print("The computer has won. You lose.\n")
            game_playing = false
          }
          else {
            if(game.isBoardFull(game_board)) {
              game.drawBoard(game_board)
              print("This game ends in a tie!\n")
              game_playing = false
            }
            else {
              turn = "player"
            }
          }
        }
      }

      print(game.rematch())
      val rematch = scala.io.StdIn.readLine()
      if(rematch.contains("n")) {
        running = false
      }
    }
  }
}
