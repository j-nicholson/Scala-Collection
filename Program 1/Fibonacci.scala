/** Program to find the nth Fibonacci number f[n - 1] + f[n - 2] **/
object Fibonacci {

  /**
    * Finds the nth fibonacci number
    * @param n: the number to be calculated
    */
  def fib(n: Int): Int = {
    if (n <= 2) {
      return 1
    }

    fib(n - 1) + fib(n - 2)
  }

  def main(args: Array[String]): Unit = {
    print("* * * Fibonacci Printer * * *\n")
    print("\nWhich Fibonacci number would you like to see?: ")
    val input = scala.io.StdIn.readInt()

    if(input > 0 && input < 46) {

      val start = System.nanoTime()
      val result = fib(input)
      val end = System.nanoTime()

      val totalTime: Double = (end - start) * 0.000000001

      printf("\nFibonacci number %d is: %d\n", input, result)
      printf("\nThis calculation required %.3f seconds", totalTime)
    }
    else {
      print("\nError: number to calculate must be between 1 and 45 inclusive.")
    }
  }
}
