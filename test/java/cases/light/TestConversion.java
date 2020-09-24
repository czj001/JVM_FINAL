package cases.light;


import cases.TestUtil;

public class TestConversion {


    public static void main(String[] args) {
        double d1 = 5.1d;
        double d2 = 5.2d;
        double d3 = 5.3d;
        d3 = d1 + d2 + d2; // 15.5
        float f =  5.5f;
        d3 = d3 - (double) f; // 10.0
        TestUtil.reach((int)d3);

    }

}
