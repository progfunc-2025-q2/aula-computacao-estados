package counter

import cats.data.State

// Represents a simple counter with a step value
case class Counter(count: Int, step: Int)

object CounterOps {
    // Type alias for stateful computations on Counter
    type CounterState[A] = State[Counter, A]

    // Increments the counter by its step value
    def increment: CounterState[Int] = State { counter =>
        val newCounter = counter.copy(count = counter.count + counter.step)
        (newCounter, newCounter.count)
    }

    // Decrements the counter by its step value
    def decrement: CounterState[Int] = State { counter =>
        val newCounter = counter.copy(count = counter.count - counter.step)
        (newCounter, newCounter.count)
    }

    // Gets the current value of the counter
    def getValue: CounterState[Int] = State.inspect(counter => counter.count)
}

// Main function: demonstrates a sequence of counter operations
@main def testCounter = {
    import CounterOps._

    // Program: increment twice, then decrement
    val program = for {
        _ <- increment
        _ <- increment
        lastCount <- decrement
    } yield lastCount

    val (finalState, result) = program.run(Counter(0, 2)).value

    println(s"Final State: $finalState")
    println(s"Result: $result")
}

// Main function: demonstrates sequencing a list of counter operations
@main def testSequence = {
    import CounterOps._
    import cats.syntax.traverse._

    val operations: List[CounterState[Int]] = List(
        increment,
        increment,
        decrement,
        increment,
        getValue
    )

    // Sequence combines all operations into one stateful computation
    val program: CounterState[List[Int]] = operations.sequence
    val (finalState, results) = program.run(Counter(0, 3)).value

    println(s"Initial State: Counter(0, 3)")
    println(s"Final State: $finalState")
    println(s"Results: $results")
}