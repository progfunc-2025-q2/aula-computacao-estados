package counterState

import cats.data.{State, EitherT}

// Represents a counter with minimum, maximum, and step constraints
case class BoundedCounter(count: Int, min: Int, max: Int, step: Int)

// Type alias for stateful computations on BoundedCounter
// CounterState[A] represents a computation that transforms the counter and returns a value of type A
type CounterState[A] = State[BoundedCounter, A]

// Error types for bounded counter operations
sealed trait CounterError
case object MaxBoundReached extends CounterError // Error when exceeding max bound
case object MinBoundReached extends CounterError // Error when going below min bound

// Companion object for safe construction of BoundedCounter
object BoundedCounter {
  // Creates a BoundedCounter only if initial value is within bounds
  def apply(
      count: Int,
      min: Int,
      max: Int,
      step: Int = 1
  ): Either[CounterError, BoundedCounter] = {
    if (count < min) Left(MinBoundReached)
    else if (count > max) Left(MaxBoundReached)
    else Right(new BoundedCounter(count, min, max, step))
  }
}

// Operations on BoundedCounter using EitherT for error handling
object BoundedCounterOps {
  // Increments the counter, returns error if max bound is exceeded
  def increment: EitherT[CounterState, CounterError, Int] = EitherT {
    State { counter =>
      val newCount = counter.count + counter.step
      if (newCount > counter.max) {
        (counter, Left(MaxBoundReached))
      } else {
        val newCounter = counter.copy(count = newCount)
        (newCounter, Right(newCount))
      }
    }
  }

  // Decrements the counter, returns error if min bound is exceeded
  def decrement: EitherT[CounterState, CounterError, Int] = EitherT {
    State { counter =>
      val newCount = counter.count - counter.step
      if (newCount < counter.min) {
        (counter, Left(MinBoundReached))
      } else {
        val newCounter = counter.copy(count = newCount)
        (newCounter, Right(newCount))
      }
    }
  }

  // Gets the current value of the counter
  def getValue: EitherT[CounterState, CounterError, Int] =
    EitherT.liftF(State.inspect(_.count))
}

// Unsafe operations: returns Either in state, but does not short-circuit on error
object BoundedCounterOpsUnsafe {
  // Increments the counter, returns error if max bound is exceeded
  def increment: State[BoundedCounter, Either[CounterError, Int]] =
    State { counter =>
      val newCount = counter.count + counter.step
      if (newCount > counter.max) {
        (counter, Left(MaxBoundReached))
      } else {
        val newCounter = counter.copy(count = newCount)
        (newCounter, Right(newCount))
      }
    }

  // Decrements the counter, returns error if min bound is exceeded
  def decrement: State[BoundedCounter, Either[CounterError, Int]] =
    State { counter =>
      val newCount = counter.count - counter.step
      if (newCount < counter.min) {
        (counter, Left(MinBoundReached))
      } else {
        val newCounter = counter.copy(count = newCount)
        (newCounter, Right(newCount))
      }
    }

  // Gets the current value of the counter
  def getValue: State[BoundedCounter, Either[CounterError, Int]] =
    State.inspect(counter => Right(counter.count))
}

// Main function: demonstrates safe bounded counter operations with error handling
@main def testBoundedCounter = {
  BoundedCounter(8, 0, 10, 2) match {
    case Left(error) => println(s"Failed to create counter: $error")
    case Right(initialCounter) =>
      import BoundedCounterOps._

      // Program: increment counter multiple times, short-circuits on error
      val program = for {
        _ <- increment
        _ <- increment
        _ <- increment // This should fail (10 -> 12)
        currentCount <- increment // This will short circuit
      } yield currentCount

      val (finalCounter, result) = program.value.run(initialCounter).value

      println(s"Initial counter: $initialCounter")
      result match {
        case Left(error)  => println(s"Program failed with error: $error")
        case Right(count) => println(s"Program succeeded with count: $count")
      }
      println(s"Final counter: $finalCounter")
  }
}

// Main function: demonstrates unsafe bounded counter operations (no short-circuit)
@main def testBoundedCounterUnsafe = {
  BoundedCounter(8, 0, 10, 2) match {
    case Left(error) => println(s"Failed to create counter: $error")
    case Right(initialCounter) =>
      import BoundedCounterOpsUnsafe._

      // Program: increment counter multiple times, manually checks for errors
      val program = for {
        result1 <- increment
        result2 <- if (result1.isRight) increment else State.pure(result1)
        result3 <- if (result2.isRight) increment else State.pure(result2) // This should fail (10 -> 12)
        result4 <- if (result3.isRight) increment else State.pure(result3) // This should also fail
        currentCount <- if (result4.isRight) getValue else State.pure(Left(result4.left.getOrElse(MaxBoundReached)))
      } yield currentCount

      val (finalCounter, result) = program.run(initialCounter).value

      println(s"Initial counter: $initialCounter")
      result match {
        case Left(error)  => println(s"Program failed with error: $error")
        case Right(count) => println(s"Program succeeded with count: $count")
      }
      println(s"Final counter: $finalCounter")
  }
}
