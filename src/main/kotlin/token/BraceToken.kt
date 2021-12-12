package token

import visitor.TokenVisitor

open class BraceToken : Token {
    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }
}

class LeftBraceToken : BraceToken()

class RightBraceToken : BraceToken()