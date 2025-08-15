package optionMonad

/*
* Demonstrates that Option satisfies the pure property
*/
@main def optionHasPure = {
    val pureValue: Option[Int] = Some(42)   // Option can wrap a value
    val noneValue: Option[Int] = None       // Option can represent absence
}

/*
* Demonstrates flatMap for Option
*/
@main def optionHasFlatmap = {
    val flatMapValue: Option[Int] = Some(42).flatMap(x => Some(x * 2)) // Applies function if value exists
    val flatMapNone: Option[Int] = None.flatMap(x => None)             // Remains None if absent

    println(s"FlatMap value: $flatMapValue")
    println(s"FlatMap none: $flatMapNone")
}

// Function that adds two positive integers, returns None if any is not positive
def addPositive(x: Int, y: Int): Option[Int] = {
    if (x > 0 && y > 0) Some(x + y)
    else None
}

// Main function: demonstrates composition of Option-returning functions (success case)
@main def testCompositionSuccess = {
    val result: Option[Int] = addPositive(1, 2)
        .flatMap(sum1 => addPositive(sum1, 3))
        .flatMap(sum2 => addPositive(sum2, 4))
        .flatMap(sum3 => addPositive(sum3, 5))

    result match {
        case Some(value) => println(s"Final composed value: $value")
        case None => println("Composed value is not positive")
    }
}

// Main function: demonstrates composition of Option-returning functions (failure case)
@main def testCompositionFailure = {
    val result: Option[Int] = addPositive(1, 2)
        .flatMap(sum1 => addPositive(sum1, -3))
        .flatMap(sum2 => addPositive(sum2, 4))
        .flatMap(sum3 => addPositive(sum3, 5))

    result match {
        case Some(value) => println(s"Final composed value: $value")
        case None => println("Composed value is not positive")
    }
}

// Error types for addPositive2 function
sealed trait AddError
case class LeftOperandNegative(x: Int) extends AddError
case class RightOperandNegative(y: Int) extends AddError

// Function that adds two positive integers and returns an Either type to signal success or failure
def addPositive2(x: Int, y: Int): Either[AddError, Int] = {
    println(s"Adding $x and $y")
    if (x > 0 && y > 0) Right(x + y)
    else if (x <= 0) Left(LeftOperandNegative(x))
    else Left(RightOperandNegative(y))
}

// Main function: test Either with error handling
@main def testEitherWithError = {
    val result: Either[AddError, Int] = addPositive2(1, 2)
        .flatMap(addPositive2(_, -3))
        .flatMap(addPositive2(_, 4)) // This will cause an error
        .flatMap(addPositive2(_, 5))

    result match {
        case Right(value) => println(s"Final composed value: $value")
        case Left(error) => println(s"Composed value is not positive: $error")
    }
}

// test monad composition using fold function
@main def eitherWithFold = {
    def addPositives(start: Int, values: Int*): Either[AddError, Int] = {
        values.foldLeft(Right(start): Either[AddError, Int]) { (acc, v) =>
            acc.flatMap(addPositive2(_, v))
        }
    }

    addPositives(1, 2, -3, 4, 5) match {
        case Right(value) => println(s"Final composed value: $value")
        case Left(error) => println(s"Composed value is not positive: $error")
    }
}
