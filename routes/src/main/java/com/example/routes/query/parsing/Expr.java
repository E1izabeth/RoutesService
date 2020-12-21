package com.example.routes.query.parsing;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public abstract class Expr // : IComparable<Expr>, IEquatable<Expr>
{
// protected Expr() { }

    public <T> T apply(IExprVisitor<T> visitor) {
        return this.applyImpl(visitor);
    }

    protected abstract <T> T applyImpl(IExprVisitor<T> visitor);

    public Expr apply(Expr ... args) { return new Apply(this, args); }

// public Expr this[params Expr[] args] { get { return new Apply(this, args); } }

    public String toString() {
        return this.apply(ExprCollector.instance);
    }
//
//public int CompareTo(Expr other)
//        {
//        return other == null ? -1 : this.ToString().CompareTo(other.ToString());
//        }
//
//public bool Equals(Expr other)
//        {
//        return other == null ? false : this.CompareTo(other) == 0;
//        }
//
//public override bool Equals(object obj)
//        {
//        return obj is Expr other && this.Equals(other);
//        }
//
//public override int GetHashCode()
//        {
//        return this.ToString().GetHashCode();
//        }


    public static class Number extends Expr {
        public final double value;

        public Number(double value) {
            this.value = value;
        }

        protected <T> T applyImpl(IExprVisitor<T> visitor) {
            return visitor.visitNumber(this);
        }
    }

    public static class Characters extends Expr {
        public final String text;

        public Characters(String text) {
            this.text = text;
        }

        protected <T> T applyImpl(IExprVisitor<T> visitor) {
            return visitor.visitCharacters(this);
        }
    }

    public static class Symbol extends Expr {
        public final String name;

        public Symbol(String name) {
            if (name == null)
                throw new IllegalArgumentException();

            this.name = name;
        }

        protected <T> T applyImpl(IExprVisitor<T> visitor) {
            return visitor.visitSymbol(this);
        }
    }

    public static class Apply extends Expr {
        public final Expr head;
        public final List<Expr> args;

        public Apply(Expr head, Expr... args) {
            this(head, Arrays.asList(args.clone()));
        }

        public Apply(Expr head, Iterable<Expr> args) {
            if (head == null)
                throw new IllegalArgumentException();
            if (args == null)
                throw new IllegalArgumentException();

            this.head = head;
            this.args = Collections.unmodifiableList(StreamSupport.stream(args.spliterator(), false).collect(Collectors.toList()));
        }

        protected <T> T applyImpl(IExprVisitor<T> visitor) {
            return visitor.visitApply(this);
        }
    }
}