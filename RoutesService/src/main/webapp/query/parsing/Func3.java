package main.webapp.query.parsing;

public interface Func3<T1, T2, T3, TRet> {
    TRet execute(T1 arg1, T2 arg2, T3 arg3);
}
