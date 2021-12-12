package visitor

import token.BraceToken
import token.NumberToken
import token.Operation

interface TokenVisitor {
    fun visit(token: NumberToken)
    fun visit(token: BraceToken)
    fun visit(token: Operation)
}
