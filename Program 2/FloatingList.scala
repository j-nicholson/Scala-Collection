
import scala.collection.mutable.ListBuffer

/** Program to alter a list of floating point numbers **/
object FloatingList {

  def main(args: Array[String]): Unit = {

    var end = false
    val floatingArray: ListBuffer[Double] = ListBuffer()

    var command: String = ""
    var item: String = ""

    println("* * * Floating-point program started * * *")

    while (!end) {
      print("\nEnter a command: ")
      val input = scala.io.StdIn.readLine()

      if (input.contains(" ")) {
        val tokens = input.split(" ")
        command = tokens(0)
        item = tokens(1)
      }
      else {
        command = input
      }

      command match {
          // Insert command option
        case "Insert" =>
          if (item.matches(".*[0-9]+")) {
            floatingArray.+=(item.toDouble)
            print("\nThe array currently contains: \n")
            for (i <- 0 until floatingArray.length) {
              printf("Values[%d] = %.5f\n", i, floatingArray(i))
            }
          }
          else {
            print("\nInvalid option: please choose [Insert, Delete, Sum, End]")
          }
          // Delete command option
        case "Delete" =>
          if (item.matches(".*[0-9]+")) {
            if(floatingArray.contains(item.toDouble)) {
              floatingArray.-=(item.toDouble)
              print("\nThe array currently contains: \n")
              for (i <- 0 until floatingArray.length) {
                printf("Values[%d] = %.5f\n", i, floatingArray(i))
              }
            }
            else {
              print("\nThat number was not in the list\n")
            }
          }
          else {
            print("\nInvalid option: please choose [Insert, Delete, Sum, End]")
          }
          // Sum command option
        case "Sum" =>
          var total: Double = 0
          for (i <- 0 until floatingArray.length) {
            total += floatingArray(i)
          }
          printf("\nThe total is %.1f\n", total)
          // End command option
        case "End" =>
          print("\n* * * Floating-point program ended * * *")
          end = true
          // Default option
        case default =>
          print("\nInvalid option: please choose [Insert, Delete, Sum, End]")
      }
    }
  }
}
