import visitor.ParserVisitor
import support.Tokenizer
import visitor.CalcVisitor
import visitor.PrintVisitor

fun main() {
    val tokens = Tokenizer(System.`in`).tokenize()
    val parser = ParserVisitor(tokens)
    val parsedTokens = parser.parse()
    val printer = PrintVisitor(parsedTokens)
    val calc = CalcVisitor(parsedTokens)
    printer.printExpression()
    println("Evaluated expression: ${calc.evaluate()}")
}