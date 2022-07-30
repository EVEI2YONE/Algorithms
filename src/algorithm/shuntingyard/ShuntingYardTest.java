package algorithm.shuntingyard;

import static org.junit.jupiter.api.Assertions.*;

class ShuntingYardTest {
    ShuntingYard sy;


    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        sy = new ShuntingYard();
    }

    @org.junit.jupiter.api.Test
    void shunt() {
        String input = "A+B*C-D";
        String expected = "ABC*+D-";
        String  result = sy.Shunt(input).toString();
        assertEquals(expected, result);
    }

    @org.junit.jupiter.api.Test
    void shunt2() {
        String input = "3+4*(7-5)^2+3*3-1";
        String expected = "3475-2^*+33*+1-";
        String  result = sy.Shunt(input).toString();
        assertEquals(expected, result);
    }

    @org.junit.jupiter.api.Test
    void shunt3() {
        String input = "3-4*7+5";
        String expected = "347*-5+";
        String  result = sy.Shunt(input).toString();
        assertEquals(expected, result);
    }

    @org.junit.jupiter.api.Test
    void shunt4() {
        String input = "3-4*7+5^2";
        String expected = "347*-52^+";
        String  result = sy.Shunt(input).toString();
        assertEquals(expected, result);
    }
}