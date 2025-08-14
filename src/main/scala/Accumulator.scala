import immutableAccount.Account

// Accumulator for collecting balances and account state through a sequence of operations
case class AccountAccumulator(balances: List[Double], account: Account) {
  // Applies an operation to the account, updating balances and account state
  def apply(operation: Account => (Double, Account)): AccountAccumulator = {
    val (newBalance, newAccount) = operation(account)
    AccountAccumulator(balances :+ newBalance, newAccount)
  }
}

// Main function to demonstrate usage of AccountAccumulator
@main def testAccountAccumulator = {
  // Start with an empty balance history and initial account
  val result = AccountAccumulator(List.empty[Double], Account())
    (_.deposit(100.0))   // Deposit 100
    (_.deposit(50.0))    // Deposit 50
    (_.withdraw(30.0))   // Withdraw 30
    (_.deposit(40.0))    // Deposit 40
    (_.withdraw(90.0))   // Withdraw 90

  // Pattern match to extract balances and final account
  result match {
    case AccountAccumulator(balances, account) =>
      println(s"Balance history: $balances")      // Print all balances
      println(s"Final balance: ${account.balance}") // Print final balance
  }
}
