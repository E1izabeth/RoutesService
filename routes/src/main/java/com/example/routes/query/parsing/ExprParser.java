package com.example.routes.query.parsing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ExprParser {
    class ParsingContext {
        private final List<Token> _tokens;
        private int _index;

        private Token _currentToken;
        private boolean _hasNextToken;

        public Token getCurrentToken() { return  _currentToken; }
        public boolean hasNextToken() { return  _hasNextToken; }

        public ParsingContext(List<Token> tokens) {
            _tokens = StreamSupport.stream(tokens.spliterator(), false).filter(t -> t.kind != TokenKind.Whitespace).collect(Collectors.toList());
            _index = 0;

            _currentToken = null;
            _hasNextToken = true;
            // this.advance();
        }

        public Token expectToken(TokenKind kind) {
            Token t = this.advance();
            if (t.kind != kind)
                throw new QueryParsingException("Unexpected " + t.kind + " while expecting " + kind + " at " + t.pos);

            return t;
        }

        public Token advance() {
            if (!this._hasNextToken)
                throw new QueryParsingException("Unexpected end of expression");

            _currentToken = _tokens.get(_index);
            _index++;
            _hasNextToken = _index < _tokens.size();
            return this.getCurrentToken();
        }

        public Token peek() {
            return this._hasNextToken ? _tokens.get(_index) : null;
        }
    }

    private final Tokenizer _tokenizer;

    public Tokenizer getTokenizer() { return _tokenizer; }

    private final HashMap<TokenKind, Func2<ParsingContext,Integer,Expr>> _prefixHandlers;
    private final HashMap<TokenKind, Func3<ParsingContext,Integer,Expr,Expr>> _infixHandlers;

    public ExprParser() {
        _tokenizer = new Tokenizer();

        _prefixHandlers = new HashMap<TokenKind, Func2<ParsingContext, Integer, Expr>>() {{
            put(TokenKind.Number, (ctx, bp) -> new Expr.Number(Double.parseDouble(ctx.getCurrentToken().content)));
            put(TokenKind.Symbol, (ctx, bp) -> new Expr.Symbol(ctx.getCurrentToken().content));
            put(TokenKind.String, (ctx, bp) -> new Expr.Characters(ctx.getCurrentToken().content.substring(1, ctx.getCurrentToken().content.length() - 1).replaceAll("\\\\(.)", "$1")));
            put(TokenKind.OpenGroup, (ctx, bp) -> {
                Expr inner = ExprParser.this.parseInternal(ctx);
                ctx.expectToken(TokenKind.CloseGroup);
                return inner;
            });
            put(TokenKind.OpChar, (ctx, bp) -> {
                Token opToken = ctx.getCurrentToken();
                Expr arg = ExprParser.this.parseInternal(ctx, bp);
                return opToken.tokenInfo.unaryHandler.execute(arg);
            });
        }};

        _infixHandlers = new HashMap<TokenKind, Func3<ParsingContext, Integer, Expr, Expr>>() {{
            put(TokenKind.OpenGroup, (ctx, bp, left) -> {
                ArrayList<Expr> args = new ArrayList<Expr>();
                if (ctx.peek().kind != TokenKind.CloseGroup) {
                    args.add(ExprParser.this.parseInternal(ctx));
                    while (ctx.peek().kind == TokenKind.Comma) {
                        ctx.advance();
                        args.add(ExprParser.this.parseInternal(ctx));
                    }
                }
                ctx.expectToken(TokenKind.CloseGroup);
                return new Expr.Apply(left, args);
            });
            put(TokenKind.OpChar, (ctx, bp, left) -> {
                Token opToken = ctx.getCurrentToken();
                Expr right = ExprParser.this.parseInternal(ctx, bp);
                return opToken.tokenInfo.binaryHandler.execute(left, right);
            });
        }};
    }

    public Expr parse(String text) {
        ParsingContext ctx = new ParsingContext(_tokenizer.tokenize(text));
        return this.parseInternal(ctx);
    }

    private Expr parseInternal(ParsingContext ctx) {
        return this.parseInternal(ctx, Integer.MIN_VALUE);
    }

    private Expr parseInternal(ParsingContext ctx, int rbp) {
        Token prevToken = ctx.getCurrentToken();
        Expr left = this.parsePrefix(ctx);

        while (this.getBindingPower(false, ctx.peek()) > rbp ||
                (prevToken != null && ctx.peek() != null && ctx.peek().tokenInfo == prevToken.tokenInfo && prevToken.tokenInfo.inverseBinaryAssociativity)) {
            left = this.parseInfix(ctx, left);
        }

        return left;
    }

    private Expr parsePrefix(ParsingContext ctx) {
        final Token token = ctx.advance();

        return  _prefixHandlers.getOrDefault(token.kind, (ctx2, bp) -> {
            throw new QueryParsingException("Unexpected leading expression syntax " + token.tokenInfo + " at " + token.pos);
        }).execute(ctx, this.getBindingPower(true, token));
    }

    private Expr parseInfix(ParsingContext ctx, Expr left) {
        final Token token = ctx.advance();

        return _infixHandlers.getOrDefault(token.kind, (ctx2, bp, leftBranch) -> {
            throw new QueryParsingException("Unexpected infix expression syntax " + token.tokenInfo + " at " + token.pos);
        }).execute(ctx, this.getBindingPower(false, token), left);
    }

    private int getBindingPower(boolean prefix, Token token) {
        if (token == null)
            return Integer.MIN_VALUE;

        Integer precedence = prefix ? token.tokenInfo.unaryBindingPower
                : token.tokenInfo.binaryBindingPower;

        if (precedence == null) {
            if (token.kind != TokenKind.OpChar)
                precedence = Integer.MIN_VALUE;
            else
                throw new QueryParsingException("Operator " + token.tokenInfo + " cannot be applied in " + (prefix ? "prefix" : "infix") + " form at " + token.pos);
        }

        return precedence;
    }
}

