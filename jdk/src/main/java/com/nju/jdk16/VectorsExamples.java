//package com.nju.jdk16;
//
//import jdk.incubator.vector.FloatVector;
//import jdk.incubator.vector.VectorSpecies;
//
///**
// * @description
// * @date 2024/6/12 23:46
// * @author: qyl
// */
//public class VectorsExamples {
//    public static void main(String[] args) {
//        new VectorAddition ().calculate ();
//        new VectorMultiplication().calculate ();
//    }
//
//}
//
//class VectorAddition {
//    // 使用 Float 类型的 256 位向量
//    private final VectorSpecies<Float> SPECIES = FloatVector.SPECIES_256;
//
//    public void calculate() {
//        float[] a = {1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f};
//        float[] b = {1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f};
//        float[] c = new float[a.length];
//
//        // 使用向量化进行加法操作
//        int i = 0;
//        for (; i < SPECIES.loopBound(a.length); i += SPECIES.length()) {
//            var va = FloatVector.fromArray(SPECIES, a, i);
//            var vb = FloatVector.fromArray(SPECIES, b, i);
//            var vc = va.add(vb);
//            vc.intoArray(c, i);
//        }
//        for (float v : c) {
//            System.out.print(v + " ");
//        }
//        // 处理剩余的元素
//        for (; i < a.length; i++) {
//            c[i] = a[i] + b[i];
//        }
//
//        // 打印结果
//        for (float v : c) {
//            System.out.print(v + " ");
//        }
//    }
//}
//
//class VectorMultiplication {
//    // 使用 Float 类型的 256 位向量
//    private static final VectorSpecies<Float> SPECIES = FloatVector.SPECIES_256;
//
//    public void calculate() {
//        float[] a = {1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f};
//        float[] b = {2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f};
//        float[] c = new float[a.length];
//
//        // 使用向量化进行乘法操作
//        int i = 0;
//        for (; i < SPECIES.loopBound(a.length); i += SPECIES.length()) {
//            var va = FloatVector.fromArray(SPECIES, a, i);
//            var vb = FloatVector.fromArray(SPECIES, b, i);
//            var vc = va.mul(vb);
//            vc.intoArray(c, i);
//        }
//
//        // 处理剩余的元素
//        for (; i < a.length; i++) {
//            c[i] = a[i] * b[i];
//        }
//
//        // 打印结果
//        for (float v : c) {
//            System.out.print(v + " ");
//        }
//    }
//}
