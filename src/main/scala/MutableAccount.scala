// Example of bank account with mutable state
package mutableAccount

// Class that maintains mutable state (balance can be modified)
class Account(private var balance: Double = 0.0) {

  // Method to query the current balance
  def getBalance: Double = balance

  // Method that modifies internal state (deposit)
  // Returns the new balance after the operation
  def deposit(amount: Double): Double = {
    balance += amount  // Direct mutation of the balance field
    balance           // Returns the new balance
  }

  // Method that modifies internal state (withdrawal)
  // Returns the new balance after the operation
  def withdraw(amount: Double): Double = {
    balance -= amount  // Direct mutation of the balance field
    balance           // Returns the new balance
  }
}

// Main program to test the mutable account
@main def testAccount = {
  val account = new Account()  // Creates a new account with balance 0
  
  // List of operations - each call modifies the account's state
  val balanceReport = List(
    account.deposit(100.0),   // Deposits 100 → balance: 100
    account.deposit(50.0),    // Deposits 50  → balance: 150
    account.withdraw(30.0),   // Withdraws 30 → balance: 120
    account.deposit(40.0),    // Deposits 40  → balance: 160
    account.withdraw(90.0)    // Withdraws 90 → balance: 70
  )
  
  println(balanceReport)      // History of balances after each operation
  println(account.getBalance) // Final account balance (70.0)
}
