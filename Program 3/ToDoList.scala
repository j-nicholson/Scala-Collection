import scala.collection.mutable.ListBuffer

/**
  * Class that creates a to-do list with methods to alter it
  * @param toDoList list containing to-do items
  */
class ToDoList (val toDoList: ListBuffer[String]) {

  /**
    * Adds a list item to the to-do list
    * @param item list item to be added
    */
  def addListItem(item: String): Unit = {
    this.toDoList.+=(item)
  }

  /**
    * Moves an item form one location to another in the to-do list
    * @param location item to be moved
    * @param destination location item will be moved to
    */
  def moveListItem(location: Int, destination: Int): Unit = {
    val to_move = this.toDoList.remove(location)
    this.toDoList.insert(destination, to_move)
  }

  /**
    * Completes an item in the to-do list by removing it
    * @param item item to be completed
    */
  def completeListItem(item: Int): Unit = {
    this.toDoList.remove(item)
  }

  /**
    * Ends to-do list editing
    * @return the end notification message
    */
  def endList(): String = {
    "\n* * * To Do List Ended * * *"
  }

  /**
    * Checks to see if a string of characters contains digits
    * @param item string to be checked for digits
    * @return boolean value depending on digit status
    */
  def isAllDigits(item: String) = item forall Character.isDigit
}
