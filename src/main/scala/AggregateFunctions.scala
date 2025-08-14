// Demonstrates aggregate functions using foldLeft and foldRight
val numbers = List(1, 2, 3, 4, 5)

// Sum using foldLeft: accumulates from left to right
val sumFoldLeft = numbers.foldLeft(0)((acc, n) => acc + n)
// Sum using foldRight: accumulates from right to left
val sumFoldRight = numbers.foldRight(0)((n, acc) => n + acc)
// Sum using foldLeft with concise syntax
val sumFoldLeftMask = numbers.foldLeft(0)(_ + _)
// Sum using foldRight with concise syntax
val sumFoldRightMask = numbers.foldRight(0)(_ + _)
// Subtraction using foldLeft (order matters)
val subFoldLeftMask = numbers.foldLeft(0)(_ - _)
// Subtraction using foldRight (order matters)
val subFoldRightMask = numbers.foldRight(0)(_ - _)
// Subtraction using foldLeft with explicit function
val subFoldLeft = numbers.foldLeft(0)((acc, n) => acc - n)
// Subtraction using foldRight with explicit function
val subFoldRight = numbers.foldRight(0)((n, acc) => n - acc)

// Main function to test and print results of aggregate functions
@main def testAggregateFunctions(): Unit = {
  println(s"Sum (fold left): $sumFoldLeft")           // 15
  println(s"Sum (fold right): $sumFoldRight")         // 15
  println(s"Sum (fold left, mask): $sumFoldLeftMask") // 15
  println(s"Sum (fold right, mask): $sumFoldRightMask") // 15
  println(s"Subtraction (fold left, mask): $subFoldLeftMask") // -15
  println(s"Subtraction (fold right, mask): $subFoldRightMask") // 5
  println(s"Subtraction (fold left): $subFoldLeft")   // -15
  println(s"Subtraction (fold right): $subFoldRight") // 5
}