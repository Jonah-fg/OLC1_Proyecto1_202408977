package Main;

import analisis.Lexer;
import analisis.Parser;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) throws Exception {
        String filePath = (args.length > 0) ? args[0] : "ejemploo.btl";
        Lexer lexer = new Lexer(new FileReader(filePath));
        Parser parser = new Parser(lexer);
        parser.parse();
        System.out.println("¡Análisis exitoso!");
    }
}