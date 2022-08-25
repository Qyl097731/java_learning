package util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author qyl
 * @program ArithsUtil.java
 * @Description 计算工具列
 * @createTime 2022-08-25 17:24
 */

public class ArithUtil {
    private static final int DEF_DIV_SCALE = 10;
    private static final float ILL_VALE = 999999.0F;

    private ArithUtil() {
    }

    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    public static float add(float v1, float v2) {
        BigDecimal b1 = new BigDecimal(Float.toString(v1));
        BigDecimal b2 = new BigDecimal(Float.toString(v2));
        return b1.add(b2).floatValue();
    }

    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    public static float sub(float v1, float v2) {
        BigDecimal b1 = new BigDecimal(Float.toString(v1));
        BigDecimal b2 = new BigDecimal(Float.toString(v2));
        return b1.subtract(b2).floatValue();
    }

    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    public static float mul(float v1, float v2) {
        BigDecimal b1 = new BigDecimal(Float.toString(v1));
        BigDecimal b2 = new BigDecimal(Float.toString(v2));
        return b1.multiply(b2).floatValue();
    }

    public static float mul(float v1, float v2, int scale) {
        BigDecimal b1 = new BigDecimal(Float.toString(v1));
        BigDecimal b2 = new BigDecimal(Float.toString(v2));
        return b1.multiply(b2).setScale(scale, 4).floatValue();
    }

    public static double div(double v1, double v2) {
        return div(v1, v2, 10);
    }

    public static float div(float v1, float v2) {
        return div(v1, v2, 10);
    }

    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        } else {
            BigDecimal b1 = new BigDecimal(Double.toString(v1));
            BigDecimal b2 = new BigDecimal(Double.toString(v2));
            return b1.divide(b2, scale, 4).doubleValue();
        }
    }

    public static float div(float v1, float v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        } else {
            BigDecimal b1 = new BigDecimal(Float.toString(v1));
            BigDecimal b2 = new BigDecimal(Float.toString(v2));
            return b1.divide(b2, scale, 4).floatValue();
        }
    }

    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        } else {
            BigDecimal b = new BigDecimal(Double.toString(v));
            BigDecimal one = new BigDecimal("1");
            return b.divide(one, scale, 4).doubleValue();
        }
    }

    public static float round(float v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        } else {
            BigDecimal b = new BigDecimal(Float.toString(v));
            BigDecimal one = new BigDecimal("1f");
            return b.divide(one, scale, 4).floatValue();
        }
    }

    public static double roundDown(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        } else {
            BigDecimal b = new BigDecimal(Double.toString(v));
            BigDecimal one = new BigDecimal("1");
            return b.divide(one, scale, 1).doubleValue();
        }
    }

    public static float roundDown(float v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        } else {
            BigDecimal b = new BigDecimal(Float.toString(v));
            BigDecimal one = new BigDecimal("1f");
            return b.divide(one, scale, 1).floatValue();
        }
    }

    public static String getCrc(byte[] data) {
        int wcrc = 65535;

        for(int i = 0; i < data.length; ++i) {
            int high = wcrc >> 8;
            wcrc = high ^ data[i];

            for(int j = 0; j < 8; ++j) {
                int flag = wcrc & 1;
                wcrc >>= 1;
                if (flag == 1) {
                    wcrc ^= 40961;
                }
            }
        }

        return Integer.toHexString(wcrc);
    }

    public static String format(String vale, int scale) {
        if (null == vale) {
            return null;
        } else {
            BigDecimal bigDecimal = (new BigDecimal(String.valueOf(vale))).setScale(scale, RoundingMode.HALF_UP);
            return bigDecimal.compareTo(new BigDecimal(999999.0D)) == -1 && bigDecimal.compareTo(new BigDecimal(-999999.0D)) == 1 ? bigDecimal.stripTrailingZeros().toPlainString() : null;
        }
    }

    public static String formatDouble(Double vale, int scale) {
        if (null == vale) {
            return null;
        } else {
            BigDecimal bigDecimal = (new BigDecimal(vale)).setScale(scale, RoundingMode.HALF_UP);
            return bigDecimal.compareTo(new BigDecimal(999999.0D)) == -1 && bigDecimal.compareTo(new BigDecimal(-999999.0D)) == 1 ? bigDecimal.stripTrailingZeros().toPlainString() : null;
        }
    }

    public static String formatFloat(float vale, int scale) {
        BigDecimal bigDecimal = (new BigDecimal((double)vale)).setScale(scale, RoundingMode.HALF_UP);
        return bigDecimal.compareTo(new BigDecimal(999999.0D)) == -1 && bigDecimal.compareTo(new BigDecimal(-999999.0D)) == 1 ? bigDecimal.stripTrailingZeros().toPlainString() : null;
    }

    public static float[] formatFloatArray(float[] floats, int scale) {
        if (null == floats) {
            return null;
        } else {
            float[] results = new float[floats.length];

            for(int i = 0; i < floats.length; ++i) {
                BigDecimal bigDecimal = (BigDecimal.valueOf((double) floats[i])).setScale(scale, RoundingMode.HALF_UP);
                if (bigDecimal.compareTo(new BigDecimal("999999.0")) == -1 && bigDecimal.compareTo(BigDecimal.valueOf(-999999.0D)) == 1) {
                    results[i] = bigDecimal.stripTrailingZeros().floatValue();
                } else {
                    results[i] = -999999.0F;
                }
            }

            return results;
        }
    }

    public static void main(String[] args) {
        String a = "CP=&&DataTime=20170815180800;a34004-Min=48.2,a34004-Avg=49.5,a34004-Max=49.7,a34004-Flag=N;a34002-Min=109.2,a34002-Avg=110.4,a34002-Max=117.5,a34002-Flag=N;a01001-Min=27.2,a01001-Avg=27.2,a01001-Max=27.2,a01001-Flag=N;a01002-Min=42.9,a01002-Avg=43.0,a01002-Max=43.0,a01002-Flag=N;a01007-Min=0.0,a01007-Avg=0.0,a01007-Max=0.0,a01007-Flag=N;a01008-Min=0,a01008-Avg=0,a01008-Max=0,a01008-Flag=N;LA-Min=42.5,LA-Avg=46.3,LA-Max=51.6,LA-Flag=N;a01006-Min=1004.0,a01006-Avg=1004.0,a01006-Max=1004.1,a01006-Flag=N&&";
        System.out.println(getCrc(a.getBytes()));
    }
}
