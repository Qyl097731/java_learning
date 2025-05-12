package com.nju.jdk16;

/**
 * @description
 * @date 2024/6/26 0:53
 * @author: qyl
 */
public class SealClassExamples {

    public sealed interface Expr
            permits ConstantExpr, PlusExpr, TimesExpr, NegExpr {  }

//    public final class ConstantExpr implements Expr { ... }
//    public final class PlusExpr     implements Expr { ... }
//    public final class TimesExpr    implements Expr { ... }
//    public final class NegExpr      implements Expr { ... }

    public record ConstantExpr(int i)       implements Expr {  }
    public record PlusExpr(Expr a, Expr b)  implements Expr {  }
    public record TimesExpr(Expr a, Expr b) implements Expr {  }
    public record NegExpr(Expr e)           implements Expr {  }
}
