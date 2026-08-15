package analisis;

import java_cup.runtime.Symbol;
import analisis.sym;

%%
%public
%class Lexer
%cup
%line
%column

%{
    //Para crear objetos Symol con línea y columna
    private Symbol symbol(int type) {
        return new Symbol(type, yyline+1, yycolumn+ 1);
    }
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline +1, yycolumn+1, value);
    }
%}

%%

// Palabras reservadas
"mage"          { return symbol(sym.MAGE);}
"warrior"       { return symbol(sym.WARRIOR); }
"initial"       { return symbol(sym.INITIAL); }
"rules"         { return symbol(sym.RULES); }
"if"            { return symbol(sym.IF); }
"then"          {return symbol(sym.THEN); }
"else"          { return symbol(sym.ELSE); }
"true"          { return symbol(sym.TRUE);}
"false"         {return symbol(sym.FALSE); }

// Acciones de mago
"ARCANE_BOLT"   { return symbol(sym.ARCANE_BOLT); }
"FIREBALL"      { return symbol(sym.FIREBALL); }
"MAGIC_BARRIER" { return symbol(sym.MAGIC_BARRIER);}
"HEALING_RUNE"  { return symbol(sym.HEALING_RUNE);}
"MEDITATE"      {return symbol(sym.MEDITATE);}

//Acciones de guerero
"SLASH"         { return symbol(sym.SLASH); }
"HEAVY_STRIKE"  { return symbol(sym.HEAVY_STRIKE); }
"SHIELD_BLOCK"  { return symbol(sym.SHIELD_BLOCK);}
"WAR_CRY"       { return symbol(sym.WAR_CRY); }
"REST"          { return symbol(sym.REST); }

//Operadores y símbolos
":"             { return symbol(sym.COLON); }
","             { return symbol(sym.COMMA); }
"{"             { return symbol(sym.LBRACE); }
"}"             { return symbol(sym.RBRACE); }
"["             { return symbol(sym.LBRACKET); }
"]"             { return symbol(sym.RBRACKET); }
"("             { return symbol(sym.LPAREN); }
")"             { return symbol(sym.RPAREN); }

// Comparadores
"=="            { return symbol(sym.EQ); }
"!="            { return symbol(sym.NE); }
">"             { return symbol(sym.GT); }
"<"             { return symbol(sym.LT); }
">="            { return symbol(sym.GE); }
"<="            { return symbol(sym.LE); }
"&&"            { return symbol(sym.AND); }
"||"            { return symbol(sym.OR); }
"!"             { return symbol(sym.NOT);}

// Números enteros
[0-9]+          { return symbol(sym.INTEGER, Integer.parseInt(yytext())); }

// Identificadores (nombres)
[a-zA-Z_][a-zA-Z0-9_]* { return symbol(sym.IDENTIFIER, yytext()); }

// Saltos de línea, espacios y tabs (ignorar)
[ \t\r\n\f]     { /* ignorar */ }

// Comentarios de una línea
"//".*          {/* ignorar */}

// Comentarios multilínea
"/*"([^*]|\*+[^*/])*(\*+"/")? {
}

// Cualquier otro carácter significa error
.               { return symbol(sym.error, yytext()); }