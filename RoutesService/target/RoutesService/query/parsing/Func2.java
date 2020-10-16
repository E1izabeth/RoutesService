package main.webapp.query.parsing;

@FunctionalInterface
public interface Func2<T1, T2, TRet> {
    TRet execute(T1 arg1, T2 arg2);
}
