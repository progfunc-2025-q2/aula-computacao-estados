// Demonstrates stateful computations using foldLeft in functional programming
import immutableAccount.Account
import immutableAccount.AccountError

// Example 1: Chaining operations and printing intermediate balances
@main def statefulComputationWithFoldLeft1 = {
    // List of operations to apply to the account
    val operations = List(
      (account: Account) => account.deposit(100.0),
      (account: Account) => account.deposit(50.0),
      (account: Account) => account.withdraw(30.0),
      (account: Account) => account.withdraw2(300.0), // May trigger error
      (account: Account) => account.deposit(40.0),
      (account: Account) => account.withdraw(90.0),
    //   (account: Account) => account.applyInterest(0.05, 30),
    )

    // foldLeft applies each operation in sequence, passing the result to the next
    val finalAccount = operations.foldLeft(Account()) {
      (currentAccount, operation) =>
        val (balance, nextAccount) = operation(currentAccount)
        println(balance) // Print each intermediate balance
        nextAccount     // Pass updated account to next operation
    }

    println(s"Final balance: ${finalAccount.balance}")
}

// Example 2: Concise chaining of operations using foldLeft
@main def statefulComputationWithFoldLeftConcise = {
    val initialAccount = Account()

    // List of operations using concise syntax
    val operations = List[Account => (Double, Account)](
      _.deposit(100.0),
      _.deposit(50.0),
      _.withdraw(30.0),
      _.deposit(40.0),
      _.withdraw(90.0)
    )

    // Only the final account is kept
    val finalAccount = operations.foldLeft(initialAccount) { (acc, operation) =>
      val (_, nextAcc) = operation(acc)
      nextAcc
    }

    println(s"Final balance: ${finalAccount.balance}")
}

// Example 3: Collecting all intermediate balances using foldLeft
@main def statefulComputationWithFoldLeftAndHistory = {
    val initialAccount = Account()

    val operations = List(
      (account: Account) => account.deposit(100.0),
      (account: Account) => account.deposit(50.0),
      (account: Account) => account.withdraw(30.0),
      (account: Account) => account.deposit(40.0),
      (account: Account) => account.withdraw(90.0),
    )

    // Accumulator is a tuple: (currentAccount, balanceHistory)
    val (finalAccount, balanceHistory) =
      operations.foldLeft((initialAccount, List(initialAccount.balance))) {
        (acc, operation) =>
            val (currentAccount, report) = acc
            val (newBalance, nextAccount) = operation(currentAccount)
            (nextAccount, report :+ newBalance) // Add new balance to history
      }

    println(s"Balance history: $balanceHistory")
    println(s"Final balance: ${finalAccount.balance}")
}

// Example 4: Handling errors in operations and collecting results
@main def statefulComputationWithFoldLeftError = {
    val initialAccount = Account()

    val operations = List(
      (account: Account) => account.deposit(100.0),
      (account: Account) => account.deposit(50.0),
      (account: Account) => account.withdraw(30.0),
      (account: Account) => account.withdraw2(300.0), // May return error
      (account: Account) => account.deposit(40.0),
      (account: Account) => account.withdraw(90.0),
    )

    // Accumulator collects balances and possible errors
    val (finalAccount, balanceHistory) =
      operations.foldLeft((initialAccount, List[Double | Either[AccountError, Double]](initialAccount.balance))) {
        (acc, operation) =>
            val (currentAccount, report) = acc
            val (newBalance, nextAccount) = operation(currentAccount)
            (nextAccount, report :+ newBalance)
      }

    println(s"Balance history: $balanceHistory")
    println(s"Final balance: ${finalAccount.balance}")
}