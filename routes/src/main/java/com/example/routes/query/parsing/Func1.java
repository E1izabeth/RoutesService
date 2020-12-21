package com.example.routes.query.parsing;

@FunctionalInterface
public interface Func1<T, TRet> {
    TRet execute(T arg);
}
