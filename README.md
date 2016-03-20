# RecursiveDescentParser
A simple recursive descent parser for a simple grammar

Grammar
STAT ::= var = EXPR;
EXPR ::= TERM | TERM + EXPR
TERM ::= FACTOR | FACTOR * TERM
FACTOR ::= (EXPR) | var | constant | call(fun, EXPR)
