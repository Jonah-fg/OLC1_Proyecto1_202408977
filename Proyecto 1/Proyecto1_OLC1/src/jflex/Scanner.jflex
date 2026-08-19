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
"mage"          { return new Symbol(sym.MAGE, yytext()); }
"warrior"       { return symbol(sym.WARRIOR); }
"initial"       { return symbol(sym.INITIAL); }
"rules"         { return symbol(sym.RULES); }
"if"            { return symbol(sym.IF); }
"then"          {return symbol(sym.THEN); }
"else"          { return symbol(sym.ELSE); }
"true"          { return symbol(sym.TRUE);}
"false"         {return symbol(sym.FALSE); }

//Palabras reservadas para partidas y main
"match"         { return symbol(sym.MATCH); }
"players"       { return symbol(sym.PLAYERS); }
"rounds"        { return symbol(sym.ROUNDS); }
"scoring"       { return symbol(sym.SCORING); }
"bonuses"       { return symbol(sym.BONUSES); }
"main"          { return symbol(sym.MAIN); }
"run"           { return symbol(sym.RUN); }
"with"          { return symbol(sym.WITH); }
"seed"          { return symbol(sym.SEED); }

// Propiedades de puntuación
"damage_point"          {return symbol(sym.DAMAGE_POINT); }
"healing_point"         { return symbol(sym.HEALING_POINT); }
"successful_defense"    { return symbol(sym.SUCCESSFUL_DEFENSE); }
"victory_bonus"         { return symbol(sym.VICTORY_BONUS); }
"failed_action_penalty" {return symbol(sym.FAILED_ACTION_PENALTY);}

//Propiedades de bonificaciones
"mage_combo"            { return symbol(sym.MAGE_COMBO); }
"mage_combo_points"     { return symbol(sym.MAGE_COMBO_POINTS); }
"warrior_combo"         { return symbol(sym.WARRIOR_COMBO); }
"warrior_combo_points"  { return symbol(sym.WARRIOR_COMBO_POINTS); }
"low_health_victory"    { return symbol(sym.LOW_HEALTH_VICTORY); }

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

// Estados del sistema (Paso 1)
"self_health"       { return new Symbol(sym.SELF_HEALTH, yytext()); }
"opponent_health"   { return new Symbol(sym.OPPONENT_HEALTH, yytext()); }
"self_resource"     { return new Symbol(sym.SELF_RESOURCE, yytext()); }
"opponent_resource" { return new Symbol(sym.OPPONENT_RESOURCE, yytext()); }
"self_score"        { return new Symbol(sym.SELF_SCORE, yytext()); }
"opponent_score"    { return new Symbol(sym.OPPONENT_SCORE, yytext()); }
"round_number"      { return new Symbol(sym.ROUND_NUMBER, yytext()); }
"total_rounds"      { return new Symbol(sym.TOTAL_ROUNDS, yytext()); }
"random"            { return new Symbol(sym.RANDOM, yytext()); }
"self_history"      { return new Symbol(sym.SELF_HISTORY, yytext()); }
"opponent_history"  { return new Symbol(sym.OPPONENT_HISTORY, yytext()); }

// Funciones del sistema
"last_move"         { return new Symbol(sym.LAST_MOVE, yytext()); }
"get_move"          {return new Symbol(sym.GET_MOVE, yytext());}
"get_moves_count"   {return new Symbol(sym.GET_MOVES_COUNT, yytext()); }
"get_last_n_moves"  { return new Symbol(sym.GET_LAST_N_MOVES, yytext());}


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

// Números decimales (para random)
[0-9]+\.[0-9]+      { return new Symbol(sym.FLOAT, Double.parseDouble(yytext())); }

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