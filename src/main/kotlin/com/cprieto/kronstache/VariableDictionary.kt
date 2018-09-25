package com.cprieto.kronstache

class VariableDictionary(private val current: Map<String, Any> = mapOf()) {
    private val variableRegex = "#\\{(.*?)}".toRegex(RegexOption.IGNORE_CASE)

    fun evaluate(variables: Map<String, Any>, constants: Map<String, Any> = this.current, guard: Boolean = false): Map<String, Any> {
        if (variables.isEmpty()) return constants

        if (variables.all { variableRegex.matches(it.value.toString()) } && guard) return constants

        val head = variables.entries.first()
        val tail = variables.filter { it.key != head.key }

        if (head.value !is String || !variableRegex.matches(head.value.toString()))
            return evaluate(tail, constants.plus(head.toPair()))

        val evaluator = KronstacheEvaluator(constants)
        val value = variableRegex.replace(head.value.toString()) {
            val expr = it.groups[1]!!.value

            evaluator.evaluate(expr)?.toString() ?: it.value
        }

        return if (variableRegex.matches(value)) constants else constants.plus(Pair(head.key, value))
    }
}

