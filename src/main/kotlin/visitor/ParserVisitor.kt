package visitor

import token.*
import java.lang.Exception
import java.util.*

class ParseError(override val message: String) : Exception()

class ParserVisitor(private val tokens: List<Token>) : TokenVisitor {
    private val stack = Stack<Token>()
    private val output = mutableListOf<Token>()

    fun parse() : List<Token> {
        if (output.isNotEmpty()) {
            return output
        }
        for (token in tokens) {
            visit(token)
        }
        while (!stack.empty()) {
            val popped = stack.pop()
            if (popped !is Operation) {
                throw ParseError("No right brace to match left brace")
            }
            output.add(popped)
        }
        return output
    }

    private fun visit(token: Token) {
        when (token) {
            is BraceToken -> visit(token)
            is NumberToken -> visit(token)
            is Operation -> visit(token)
            else -> throw ParseError("Cannot parse token $token")
        }
    }

    override fun visit(token: BraceToken) {
        when (token) {
            is LeftBraceToken -> stack.push(token)
            is RightBraceToken -> {
                var popped = stack.pop()
                while (popped !is LeftBraceToken) {
                    output.add(popped)
                    if (stack.empty()) {
                        error("No left brace to match right brace")
                    } else {
                        popped = stack.pop()
                    }
                }
            }
        }
    }

    override fun visit(token: NumberToken) {
        output.add(token)
    }

    override fun visit(token: Operation) {
        while (!stack.empty()) {
            val peeked = stack.peek()
            if (peeked !is Operation) {
                break
            }
            if (peeked.priority > token.priority ||
                peeked.associativity == Associativity.LEFT && peeked.priority == token.priority) {
                output.add(stack.pop())
            } else {
                break
            }
        }
        stack.push(token)
    }
}