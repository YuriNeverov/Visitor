package visitor

import token.*
import java.lang.UnsupportedOperationException

class PrintVisitor(private val tokens: List<Token>) : TokenVisitor {
    fun printExpression() {
        for (token in tokens) {
            visit(token)
        }
        println()
    }

    private fun visit(token: Token) {
        when (token) {
            is BraceToken -> visit(token)
            is NumberToken -> visit(token)
            is Operation -> visit(token)
            else -> throw UnsupportedOperationException("Cannot print token $token")
        }
    }

    override fun visit(token: BraceToken) {
        when (token) {
            is LeftBraceToken -> print("( ")
            is RightBraceToken -> print(") ")
        }
    }

    override fun visit(token: NumberToken) {
        print("${token.value} ")
    }

    override fun visit(token: Operation) {
        when (token) {
            is AddOperation -> print("+ ")
            is SubOperation -> print("- ")
            is MulOperation -> print("* ")
            is DivOperation -> print("/ ")
        }
    }
}