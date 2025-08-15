package comprehensions

// Error types for addPositive2 function
sealed trait AddError
case class LeftOperandNegative(x: Int) extends AddError
case class RightOperandNegative(y: Int) extends AddError

// Adds two positive integers, returns Either with error if any is not positive
def addPositive2(x: Int, y: Int): Either[AddError, Int] = {
    println(s"Adding $x and $y")
    if (x > 0 && y > 0) Right(x + y)
    else if (x <= 0) Left(LeftOperandNegative(x))
    else Left(RightOperandNegative(y))
}

// Main function: demonstrates for-comprehension with Either (success case)
@main def testEitherComprehensionSuccess = {
    val result: Either[AddError, Int] = for {
        sum1 <- addPositive2(1, 2)
        sum2 <- addPositive2(sum1, 3)
        sum3 <- addPositive2(sum2, 4)
    } yield sum3

    result match {
        case Right(value) => println(s"Final composed value: $value")
        case Left(error) => println(s"Composed value is not positive: $error")
    }
}

// Main function: demonstrates for-comprehension with Either (failure case)
@main def testEitherComprehensionFailure = {
    val result: Either[AddError, Int] = for {
        sum1 <- addPositive2(1, 2)
        sum2 <- addPositive2(sum1, -3) // This will cause an error
        sum3 <- addPositive2(sum2, 4)
    } yield sum3

    result match {
        case Right(value) => println(s"Final composed value: $value")
        case Left(error) => println(s"Composed value is not positive: $error")
    }
}

// Validates if a number is in the range [1,100], returns Either with error message
def validateRange(x: Int): Either[String, Int] = {
    if (x >= 1 && x <= 100) Right(x)
    else Left(s"Number $x out of range [1,100]")
}

// Main function: demonstrates for-comprehension with heterogeneous error types
@main def testComprehensionHeterogeneous = {
    val result: Either[AddError | String, Int] = for {
        sum1 <- addPositive2(1, 2)
        sum2 <- addPositive2(sum1, -3) // This will cause an error
        sum3 <- addPositive2(sum2, 4)
        result <- validateRange(sum3) // Different type of computation
    } yield result

    result match {
        case Right(value) => println(s"Final composed value: $value")
        case Left(error) => println(s"Composed value is not positive: $error")
    }
}

// Main function: demonstrates for-comprehension with lists and filtering
@main def listComprehension = {
    val nums1 = List(1, 2)
    val nums2 = List(10, 20)
    
    // For-comprehension with filter
    val forResult = for {
        x <- nums1
        y <- nums2
        if x + y > 15
    } yield x * y
    
    // Equivalent with flatMap/map/filter
    val explicitResult = nums1.flatMap(x =>
        nums2.filter(y => x + y > 15).map(y => x * y)
    )
    
    println(s"For-comprehension: $forResult")  // List(20, 40)
    println(s"Explicit:          $explicitResult")
    println(s"Equal: ${forResult == explicitResult}")
}

// Main function: demonstrates for-comprehension with lists (cartesian product)
@main def listComprehension2 = {
    val nums1 = List(1, 2)
    val nums2 = List(10, 20)
    
    // For-comprehension for cartesian product
    val forResult = for {
        x <- nums1
        y <- nums2
    } yield (x, y)

    // Equivalent with flatMap/map
    val explicitResult = nums1.flatMap(x =>
        nums2.map(y => (x, y))
    )
    
    println(s"For-comprehension: $forResult")
    println(s"Explicit:          $explicitResult")
    println(s"Equal: ${forResult == explicitResult}")
}