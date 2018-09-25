grammar Kronstache;

@header{
package com.cprieto.kronstache;
}

stat: INT # int
    | STRING # string
    | ID # variable ;

// lexer rules
INT : [0-9]+ ;
STRING: '\'' .*? '\'';
ID: [a-zA-Z][a-zA-Z_]+;
WS: [ \t\n\r] -> skip;