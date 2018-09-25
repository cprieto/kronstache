package com.cprieto.kronstache

import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream

class KronstacheEvaluator(private val constants: Map<String, Any>): KronstacheBaseVisitor<Any?>() {
    fun evaluate(input: String): Any? {
        val stream = CharStreams.fromString(input)
        val lexer = KronstacheLexer(stream)
        val tokens = CommonTokenStream(lexer)
        val parser = KronstacheParser(tokens)

        return this.visit(parser.stat())
    }

    override fun visitInt(ctx: KronstacheParser.IntContext?): Any? {
        return ctx!!.INT().text.toInt()
    }

    override fun visitString(ctx: KronstacheParser.StringContext?): Any? {
        return ctx!!.STRING().text.trim('\'', '"')
    }

    override fun visitVariable(ctx: KronstacheParser.VariableContext?): Any? {
        val key = ctx!!.ID().text
        return if (constants.containsKey(key)) constants[key] else null
    }
}