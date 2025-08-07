
// Example of counter with mutable state
package mutableCounter {
    // Singleton object that maintains shared mutable state
    object Counter {
        // Private variable that can be modified (mutable state)
        private var counter = 0

        // Method that modifies internal state (side effect)
        def increment: Unit = {
            counter += 1  // Direct mutation of the variable
        }

        // Method to access the current counter value
        def getValue: Int = counter
    }

    // Main program to test the mutable counter
    @main def testCounter(): Unit = {
        println(Counter.getValue)  // Prints 0 (initial value)
        Counter.increment          // Increments to 1
        println(Counter.getValue)  // Prints 1 
        Counter.increment          // Increments to 2
        println(Counter.getValue)  // Prints 2
        Counter.increment          // Increments to 3
        println(Counter.getValue)  // Prints 3
    }
}

// Example of counter with immutable state (functional)
package immutableCounter {
    // Immutable case class - values cannot be modified after creation
    case class Counter private(value: Int) {
        // Method that returns a NEW counter with incremented value
        // Does not modify the current counter (immutability)
        def increment = Counter(value + 1)
    }

    // Companion object to create counter instances
    object Counter {
        // Factory method that creates a counter with initial value 0
        def apply(): Counter = new Counter(0)
    }

    // Main program to test the immutable counter
    @main def testCounter() = {
        // Each operation creates a NEW counter (immutability)
        val counter1 = Counter()               // Creates initial counter (0)
        val counter2 = counter1.increment      // Creates new counter (1)
        val counter3 = counter2.increment      // Creates new counter (2)
        val counter4 = counter3.increment      // Creates new counter (3)

        // All previous counters remain unchanged
        println(counter1.value)  // Prints 0 (original value preserved)
        println(counter2.value)  // Prints 1 (original value preserved)
        println(counter3.value)  // Prints 2 (original value preserved)
        println(counter4.value)  // Prints 3 (last value)
    }
}



