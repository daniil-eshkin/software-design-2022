package sd.lab6;

import sd.lab6.token.Token;
import sd.lab6.tokenizer.Tokenizer;
import sd.lab6.visitor.CalcVisitor;
import sd.lab6.visitor.ParserVisitor;
import sd.lab6.visitor.PrintVisitor;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        try (Reader reader = new InputStreamReader(System.in)) {
            Tokenizer tokenizer = new Tokenizer(reader);
            List<Token> tokens = tokenizer.parse();
            PrintVisitor printVisitor = new PrintVisitor();
            List<Token> parsedTokens = new ParserVisitor().parse(tokens);
            System.out.println(new CalcVisitor().calc(parsedTokens));
            printVisitor.println(parsedTokens);
        }
    }
}
