package com.example.routes.query.parsing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Tokenizer
{
    private final static HashMap<TokenKind, OpTokenInfo> _builtinPatterns = new HashMap<TokenKind, OpTokenInfo>();

    private static void registerUtility(TokenKind kind, String pattern)
    {
        _builtinPatterns.put(kind, new OpTokenInfo(kind.toString(), null, Pattern.compile(pattern)));
    }

    private static void registerUnary(TokenKind kind, String pattern, int precedence)
    {
        OpTokenInfo info = new OpTokenInfo(kind.toString(), null, Pattern.compile(pattern));
        info.unaryBindingPower = precedence;
        _builtinPatterns.put(kind, info);
    }

    private static void registerBinary(TokenKind kind, String pattern, int precedence, boolean inverse)
    {
        OpTokenInfo info = new OpTokenInfo(kind.toString(), null, Pattern.compile(pattern));
        info.binaryBindingPower = precedence;
        info.inverseBinaryAssociativity = inverse;
        _builtinPatterns.put(kind, info);
    }

    static {
        registerUnary(TokenKind.Number, "^[0-9]+(\\.[0-9]+)?", Integer.MIN_VALUE);
        registerUnary(TokenKind.Symbol, "^[\\w\\.]+", Integer.MIN_VALUE);
        registerUnary(TokenKind.String, "^\"([^\"]|(\\.))*\"", Integer.MIN_VALUE);

        registerUtility(TokenKind.Whitespace, "^((\\s+)|(//[^\\n\\r]+[\r\n]))");
        registerUtility(TokenKind.Comma, "^\\,");

        OpTokenInfo info = new OpTokenInfo(TokenKind.OpenGroup.toString(), null, Pattern.compile("^\\("));
        info.unaryBindingPower = Integer.MAX_VALUE;
        info.binaryBindingPower = 100000;
        info.inverseBinaryAssociativity = false;
        _builtinPatterns.put(TokenKind.OpenGroup, info);

        registerUnary(TokenKind.CloseGroup, "^\\)", Integer.MIN_VALUE);
    }

    private final HashMap<String, OpTokenInfo> _opTokenInfoByCharacter = new HashMap<String, OpTokenInfo>();

    public Tokenizer()
    {
    }

    private OpTokenInfo registerOpSymbol(String name, String character)
    {
        if (_builtinPatterns.values().stream().filter(p -> p.name != "Symbol").anyMatch(p -> p.pattern.matcher(character).matches())) // s|| Enum.TryParse<TokenKind>(name, out var kind)))
            throw new IllegalArgumentException();

        OpTokenInfo info = _opTokenInfoByCharacter.get(character);
        if (info == null) {
            info = new OpTokenInfo(name, character, Pattern.compile("^" + Pattern.quote(character)));
            _opTokenInfoByCharacter.put(character, info);
        }

        return info;
    }

    public void registerBinaryOp(String name, String character, int precedence, Func2<Expr, Expr, Expr> handler) {
        this.registerBinaryOp(name, character, precedence, handler, false);
    }

    public void registerBinaryOp(String name, String character, int precedence, Func2<Expr, Expr, Expr> handler, boolean inverseAssociativity)
    {
        OpTokenInfo info = this.registerOpSymbol(name, character);
        info.binaryBindingPower = precedence;
        info.binaryHandler = handler;
        info.inverseBinaryAssociativity = inverseAssociativity;
    }

    public void registerUnaryOp(String name, String character, int precedence, Func1<Expr, Expr> unaryHandler)
    {
        OpTokenInfo info = this.registerOpSymbol(name, character);
        info.unaryBindingPower = precedence;
        info.unaryHandler = unaryHandler;
    }

    public List<Token> tokenize(String source)
    {
        ArrayList<Token> result = new ArrayList<Token>();

        String text = source;
        List<Map.Entry<TokenKind, OpTokenInfo>> knownTokens = Stream.concat(
            _builtinPatterns.entrySet().stream().filter(p -> !p.getValue().name.equals("Symbol")),
            _opTokenInfoByCharacter.values().stream()
                    .sorted((a, b) -> Integer.compare(b.symbol.length(), a.symbol.length()))
                    .map(v -> new Map.Entry<TokenKind, OpTokenInfo>() {
                        public TokenKind getKey() { return TokenKind.OpChar; }
                        public OpTokenInfo getValue() { return v; }
                        public OpTokenInfo setValue(OpTokenInfo value) { return v; }
                    })
        ).collect(Collectors.toList());
        _builtinPatterns.entrySet().stream().filter(p -> p.getValue().name.equals("Symbol")).forEach(p -> knownTokens.add(p));

        int pos = 0;
        while (text.length() > 0)
        {
            boolean ok = false;

            for (Map.Entry<TokenKind, OpTokenInfo> entry : knownTokens)
            {
                Matcher m = entry.getValue().pattern.matcher(text);

                if (m.find())
                {
                    result.add(new Token(entry.getKey(), m.group(), entry.getValue(), pos));
                    pos += m.end();
                    text = text.substring(m.end());
                    ok = true;
                    break;
                }
            }

            if (!ok)
                throw new QueryParsingException("error at " + pos + " near the text: " + source.substring(pos, Math.min(pos + 10, source.length())));
        }

        return result;
    }
}

