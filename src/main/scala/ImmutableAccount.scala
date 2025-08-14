// Example of bank account with immutable state (functional)
package immutableAccount

sealed trait AccountError
case class InsufficientFunds(balance: Double, amount: Double) extends AccountError

// Immutable case class - balance cannot be modified after creation
case class Account(balance: Double = 0.0) {

  // Method that returns a tuple: (new_balance, new_account)
  // Does not modify the current account, creates a new instance
  def deposit(amount: Double): (Double, Account) = {
    val newBalance = balance + amount           // Calculates new balance
    (newBalance, Account(newBalance))          // Returns tuple with balance and new account
  }

  // Method that returns a tuple: (new_balance, new_account)
  // Does not modify the current account, creates a new instance
  def withdraw(amount: Double): (Double, Account) = {
    val newBalance = balance - amount          // Calculates new balance
    (newBalance, Account(newBalance))         // Returns tuple with balance and new account
  }

  def withdraw2(amount: Double): (Either[AccountError, Double], Account) = {
    if (amount > balance) {
      (Left(InsufficientFunds(balance, amount)), this)
    } else {
      val newBalance = balance - amount
      (Right(newBalance), Account(newBalance))
    }
  }
}

// Approach 1: State computation using val bindings
// Each operation creates a new account, preserving previous ones
@main def statefulComputationWithValBindings = {
  val account = Account()  // Initial account with balance 0

  // Each operation returns the current balance and a new account
  val (balance1, acc1) = account.deposit(100.0)  // Deposits 100 → balance: 100
  val (balance2, acc2) = acc1.deposit(50.0)      // Deposits 50  → balance: 150
  val (balance3, acc3) = acc2.withdraw(30.0)     // Withdraws 30 → balance: 120
  val (balance4, acc4) = acc3.deposit(40.0)      // Deposits 40  → balance: 160
  val (balance5, acc5) = acc4.withdraw(90.0)     // Withdraws 90 → balance: 70

  val balanceReport = List(balance1, balance2, balance3, balance4, balance5)
  println(balanceReport)     // Balance history
  println(acc5.balance)      // Final balance (70.0)
}