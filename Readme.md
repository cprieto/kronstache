# Kronstache, a simple mini language for configuration for Java

The language is very simple, I am still working in the grammar but I am taking inspirations from [Octostache](https://github.com/OctopusDeploy/Octostache) which is written in .NET.


The project started when I needed a very simple configuration language in Java where I can basically pass a dictionary of _variables_ and it can return a dictionary with the full evaluation, something like the `VariableDictionary` in Octostache and I couldn't find something simple enough to use, yes, I could use something like any implemented template language in Java (there are many) or something like [Spring Expression Language](https://docs.spring.io/spring/docs/4.3.10.RELEASE/spring-framework-reference/html/expressions.html) but they were still too big and complex for what I wanted, I just wanted something to work in a line base (no multiline expressions) and be easy to embed everywhere.

The grammar is still in draft but so far I will expect something like this:

 * By default everything is a string literal, so `1` evaluates to `1` and `2-1` evaluates to `2-1` (not 3)
 * Expressions are denoted by `#{}`, so if we have a variable `NAME` with value _Cristian_ the expression `hello #{ NAME }` will evaluate to `hello Cristian`
 * If the variable _doesn't exist_, it will evaluate to nothing or empty string, so if `hello #{ NOT_FOUND }` and `NOT_FOUND` doesn't exist, it will evaluate to `hello `
 *  By default, every expression will be coerced into its string representation
 * You can use expressions and they will evaluate, for example `sum is #{ 1 + 2}` will evaluate to `sum is 3`
 * So far we will implement only the mathematical operators `+` and `-`, they will apply **only** to _numbers_
 * We can use string literals inside the expression, for example `hello #{ 'Cristian' }` is the equivalent to `hello Cristian`
 * Variable names **are not** case sensitive, so `HELLO` is the same as `hello` for a variable
 * Support for a very small set of functions, in this context named _filters_, for example, to turn a variable uppercase we can use `hello #{ NAME|upper }` and it will return `hello CRISTIAN`
 * All _variable values_ are string
 * _Right now_ a very small set of filters are supported: `lower`, `upper`, `int` (to convert a string to integer), `trim`, the first parameter is always the expression receiver, for example, given the variable `NAME` then the expression `#{NAME|lower}` will be the equivalent to call a function `lower(NAME)`
 * Functions can support additional parameters, in that case only pass them as additional parameters, for example, the function `concat` requires a parameter to concat the string to: `#{NAME|concat('-Prieto')}`
 * Some functions can be executed without receivers, this is the case for _date_ functions: `#{|today}` will return today's date.
 * Some functions can have optional parameters, for example, `#{|today('utc')}` returns today's date in an UTC calendar
 * We can _pipe_ functions, for example, `#{|today|format('%Y-%m-%d)}` will execute in _left-to-right_ order.

 
