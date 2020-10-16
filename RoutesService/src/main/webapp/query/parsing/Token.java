package main.webapp.query.parsing;

public class Token
{
    public final TokenKind kind;
    public final String content;
    public final OpTokenInfo tokenInfo;
    public final int pos;

    public Token(TokenKind kind, String content, OpTokenInfo tokenInfo, int pos)
    {
        this.kind = kind;
        this.content = content;
        this.tokenInfo = tokenInfo;
        this.pos = pos;
    }

    public String toString()
    {
        return (this.tokenInfo == null ? this.kind.toString() : this.tokenInfo.name) + "@" + this.pos + ": " + this.content;
    }
}
