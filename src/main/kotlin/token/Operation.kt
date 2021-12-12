package token

import visitor.TokenVisitor

enum class Associativity {
    LEFT,
    RIGHT
}

abstract class Operation : Token {
    open val priority = 0
    open val associativity = Associativity.LEFT

    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }

    abstract fun eval(left: Int, right: Int) : Int
}

class AddOperation : Operation() {
    override val priority = 1

    override fun eval(left: Int, right: Int): Int {
        return left + right
    }
}

class SubOperation : Operation() {
    override val priority = 1

    override fun eval(left: Int, right: Int): Int {
        return left - right
    }
}

class MulOperation : Operation() {
    override val priority = 2

    override fun eval(left: Int, right: Int): Int {
        return left * right
    }
}

class DivOperation : Operation() {
    override val priority = 2

    override fun eval(left: Int, right: Int): Int {
        return left / right
    }
}
