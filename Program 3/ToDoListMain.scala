import scala.collection.mutable.ListBuffer

/**
  * Main routine to run to-do list editor.
  */
object ToDoListMain {

  def main(args: Array[String]): Unit = {

    val list: ListBuffer[String] = ListBuffer()
    val toDoList = new ToDoList(list)
    var end = false

    println("* * * To Do List * * *")

    while(!end) {

      println("\nEnter a command (Show, Add, Move, Complete) or End")
      val input = scala.io.StdIn.readLine()

      // Show command
      if(input == "Show") {
        var index = 1
        for(item <- 0 until toDoList.toDoList.length) {
          println(index + ". " + toDoList.toDoList(item))
          index += 1
        }
      }
      // Add command
      else if(input.contains("Add")) {
        val inputArgs = input.split(" ", 2)
        val item = inputArgs(1)
        toDoList.addListItem(item)
      }
      // Complete command
      else if(input.contains("Complete")) {
        val inputArgs = input.split(" ")
        val item = inputArgs(1)
        if(toDoList.isAllDigits(item)) {
          if(item.toInt > 0 && item.toInt <= toDoList.toDoList.length) {
            toDoList.completeListItem(item.toInt - 1)
          }
          else {
            println("This item is not in the list.")
          }
        }
        else {
          println("Error: Command " + item + " must be a number.")
        }
      }
      // Move command
      else if(input.contains("Move")) {
        val inputArgs = input.split(" ")
        val location = inputArgs(1)
        val destination = inputArgs(2)
        if(toDoList.isAllDigits(location) && toDoList.isAllDigits(destination)) {
          if((location.toInt > 0 && location.toInt <= toDoList.toDoList.length) && (destination.toInt > 0 && destination.toInt <= toDoList.toDoList.length)) {
            toDoList.moveListItem(location.toInt - 1, destination.toInt - 1)
          }
          else {
            println("This item is not in the list.")
          }
        }
        else {
          println("Error: Move location and destination options must be numbers.")
        }
      }
      // End command
      else if(input == "End") {
        println(toDoList.endList())
        end = true
      }
      else {
        println(input + " is an unrecognized command.")
      }
    }
  }
}

