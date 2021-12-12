package visitor

import token.*
import java.lang.Exception
import java.lang.UnsupportedOperationException
import java.util.*

class CalculationError(override val message: String) : Exception()

class CalcVisitor(private val tokens: List<Token>) : TokenVisitor {
    private val stack = Stack<Int>()

    fun evaluate() : Int {
        if (!stack.empty()) {
            return stack.peek()
        }
        for (token in tokens) {
            visit(token)
        }
        if (stack.size > 1) {
            throw CalculationError("Too many arguments")
        }
        return stack.peek()
    }

    private fun visit(token: Token) {
        when (token) {
            is BraceToken -> visit(token)
            is NumberToken -> visit(token)
            is Operation -> visit(token)
            else -> throw UnsupportedOperationException("Cannot evaluate token $token")
        }
    }

    override fun visit(token: BraceToken) {
        throw UnsupportedOperationException("Cannot evaluate brackets")
    }

    override fun visit(token: NumberToken) {
        stack.push(token.value)
    }

    private fun tryPopNumber() : Int {
        if (stack.empty()) {
            throw CalculationError("Not enough arguments to evaluate an operation")
        }
        return stack.pop()
    }

    override fun visit(token: Operation) {
        val right = tryPopNumber()
        val left = tryPopNumber()
        stack.push(token.eval(left, right))
    }
}