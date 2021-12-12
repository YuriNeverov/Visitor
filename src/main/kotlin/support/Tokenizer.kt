package support

import token.*
import java.io.InputStream
import java.lang.StringBuilder

class Tokenizer(private val inputStream: InputStream) {
    enum class ReaderState {
        READ,
        READ_NUMBER
    }

    fun tokenize() : List<Token> {
        val tokens = mutableListOf<Token>()
        val intBuilder = StringBuilder()
        var state = ReaderState.READ

        inputStream.bufferedReader().use {
            var charCode = it.read()
            while (charCode > 0) {
                val ch = charCode.toChar()
                when (state) {
                    ReaderState.READ -> {
                        when (ch) {
                            '(' -> tokens.add(LeftBraceToken())
                            ')' -> tokens.add(RightBraceToken())
                            '+' -> tokens.add(AddOperation())
                            '-' -> tokens.add(SubOperation())
                            '*' -> tokens.add(MulOperation())
                            '/' -> tokens.add(DivOperation())
                            else -> {
                                if (ch.isDigit()) {
                                    state = ReaderState.READ_NUMBER
                                    continue
                                }
                                if (!ch.isWhitespace()) {
                                    error("Unexpected symbol: $ch")
                                }
                            }
                        }
                    }
                    ReaderState.READ_NUMBER -> {
                        if (ch.isDigit()) {
                            intBuilder.append(ch)
                        } else {
                            tokens.add(NumberToken(intBuilder.toString().toInt()))
                            intBuilder.clear()
                            state = ReaderState.READ
                            continue
                        }
                    }
                }
                charCode = it.read()
            }
        }
        return tokens
    }
}