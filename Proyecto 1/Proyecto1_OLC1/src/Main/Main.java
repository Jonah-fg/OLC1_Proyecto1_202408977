package Main;

import java.io.FileReader;
import analisis.Lexer;
import analisis.Parser;

public class Main {
    public static void main(String[] args) throws Exception {
        String filePath = (args.length > 0) ? args[0] : "ejemploo.btl";
        Lexer lexer = new Lexer(new FileReader(filePath));
        Parser parser = new Parser(lexer);
        parser.parse();
        System.out.println("¡Análisis exitoso!");
    }
}