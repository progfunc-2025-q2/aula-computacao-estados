package monadLawsOption

// Demonstrates the three monad laws for Option: left identity, right identity, and associativity
@main def LawsForSome = {    
  def a = 42
  def m = Some(a)
  def f(a: Int) = Some(a * 2)
  def g(a: Int) = Some(a + 3)

  // Left identity: pure(a).flatMap(f) == f(a)
  def leftIdentity = Some(a).flatMap(f) == f(a)

  // Right identity: m.flatMap(pure) == m
  def rightIdentity = m.flatMap(Some(_)) == m

  // Associativity: m.flatMap(f).flatMap(g) == m.flatMap(a => f(a).flatMap(g))
  def associativity = m.flatMap(f).flatMap(g) == m.flatMap(a => f(a).flatMap(g))

  println(s"Left Identity: $leftIdentity")
  println(s"Right Identity: $rightIdentity")
  println(s"Associativity: $associativity")
}

// Demonstrates the three monad laws for Option when the value is None
@main def LawsForNone = {    
  def a = 42
  def m = None: Option[Int]
  def f(a: Int) = None
  def g(a: Int) = None

  // Left identity: pure(a).flatMap(f) == f(a)
  def leftIdentity = None.flatMap(f) == f(a)

  // Right identity: m.flatMap(pure) == m
  def rightIdentity = m.flatMap(x => None) == m

  // Associativity: m.flatMap(f).flatMap(g) == m.flatMap(a => f(a).flatMap(g))
  def associativity = m.flatMap(f).flatMap(g) == m.flatMap(a => f(a).flatMap(g))

  println(leftIdentity)
  println(rightIdentity)
  println(associativity)
}
