package sequencing

// Computes the factorial of a number, returns None for negative input
// Demonstrates Option as a container for possible failure
def factorial(v: Int): Option[Int] =
  if (v < 0) None else Some((1 to v).product)

// Computes the reciprocal of an integer, returns None if input is zero
// Shows how Option can represent division by zero
def reciprocal(x: Int): Option[Double] =
  if (x != 0) Some(1.0 / x) else None

// Chaining computations using pattern matching on Option
// Returns Some(result) if all steps succeed, None otherwise
def calculateMatch(n: Int): Option[Double] =
  factorial(n) match {
    case Some(f) => reciprocal(f) match {
      case Some(r) => Some(r + 10)
      case None    => None
    }
    case None    => None
  }

// Chaining computations using flatMap and map
// More idiomatic and concise functional style
def calculateFlatmap(n: Int): Option[Double] =
  factorial(n).flatMap(f => reciprocal(f).map(_ + 10))

// Chaining computations using for-comprehension
// Most readable for multiple Option steps
def calculateForComprehension(n: Int): Option[Double] =
  for {
    f <- factorial(n)
    r <- reciprocal(f)
  } yield r + 10

// Main function: demonstrates all three approaches and edge cases
@main def run = {
    println(calculateMatch(4))
    println(calculateFlatmap(4))
    println(calculateForComprehension(4))

    println()

    println(calculateMatch(-1))
    println(calculateFlatmap(-1))
    println(calculateForComprehension(-1))
}
