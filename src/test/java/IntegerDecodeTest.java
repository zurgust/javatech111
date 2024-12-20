import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegerDecodeTest {

    @Test
    void testDecodeNull() {
        assertThrows(NullPointerException.class, () -> Integer.decode(null));
    }

    @Test
    void testDecodeZero() {
        assertEquals(0, Integer.decode("0"));
        assertEquals(0, Integer.decode("-0"));
        assertEquals(0, Integer.decode("+0"));
        assertEquals(0, Integer.decode("0x0"));
        assertThrows(NumberFormatException.class, () -> Integer.decode("-0-"));
        assertThrows(NumberFormatException.class, () -> Integer.decode("0-"));
        assertThrows(NumberFormatException.class, () -> Integer.decode("0x0-"));
    }

    @Test
    void testDecodeNegativeNumber() {
        assertEquals(-123, Integer.decode("-123"));
        assertEquals(-1, Integer.decode("-1"));
        assertEquals(-100, Integer.decode("-100"));
        assertThrows(NumberFormatException.class, () -> Integer.decode("-123-"));
        assertThrows(NumberFormatException.class, () -> Integer.decode("--123"));
    }

    @Test
    void testDecodePositiveNumberWithPlus() {
        assertEquals(123, Integer.decode("+123"));
        assertEquals(5, Integer.decode("+5"));
        assertEquals(14645, Integer.decode("+14645"));
        assertEquals(0, Integer.decode("+0"));
        assertThrows(NumberFormatException.class, () -> Integer.decode("++123"));
    }

    @Test
    void testDecodeMaxValue() {
        assertEquals(Integer.MAX_VALUE, Integer.decode(String.valueOf(Integer.MAX_VALUE)));
        assertThrows(NumberFormatException.class, () -> Integer.decode(String.valueOf(Integer.MAX_VALUE) + "1"));
        assertThrows(NumberFormatException.class, () -> Integer.decode("0x" + Integer.toHexString(Integer.MAX_VALUE) + "G"));
        assertThrows(NumberFormatException.class, () -> Integer.decode(String.valueOf(Long.MAX_VALUE)));
        assertThrows(NumberFormatException.class, () -> Integer.decode("2147483648")); // больше максимального
    }

    @Test
    void testDecodeMinValue() {
        assertEquals(Integer.MIN_VALUE, Integer.decode(String.valueOf(Integer.MIN_VALUE)));
        assertThrows(NumberFormatException.class, () -> Integer.decode(String.valueOf(Integer.MIN_VALUE) + "1"));
        assertThrows(NumberFormatException.class, () -> Integer.decode("-2147483649")); // меньше минимального
        assertThrows(NumberFormatException.class, () -> Integer.decode("-0x"));
        assertThrows(NumberFormatException.class, () -> Integer.decode("-2147483649"));
    }

    @Test
    void testDecodeHexadecimalWith0x() {
        assertEquals(26, Integer.decode("0x1A"));
        assertEquals(10, Integer.decode("0xA"));
        assertEquals(255, Integer.decode("0xFF"));
        assertEquals(0, Integer.decode("0x0"));
        assertThrows(NumberFormatException.class, () -> Integer.decode("0x1G")); // некорректный символ
    }

    @Test
    void testDecodeHexadecimalWithHash() {
        assertEquals(26, Integer.decode("#1A"));
        assertEquals(255, Integer.decode("#FF"));
        assertEquals(0, Integer.decode("#0"));
        assertEquals(4095, Integer.decode("#FFF"));
        assertThrows(NumberFormatException.class, () -> Integer.decode("#G")); // некорректный символ
    }

    @Test
    void testDecodeHexadecimalUppercase() {
        assertEquals(26, Integer.decode("0X1A"));
        assertEquals(255, Integer.decode("0XFF"));
        assertEquals(0, Integer.decode("0X0"));
        assertEquals(4095, Integer.decode("0XFFF"));
        assertThrows(NumberFormatException.class, () -> Integer.decode("0X1I")); // некорректный символ
    }

    @Test
    void testDecodeNegativeHexadecimal() {
        assertEquals(-26, Integer.decode("-0x1A"));
        assertEquals(-255, Integer.decode("-0xFF"));
        assertEquals(0, Integer.decode("-0x0"));
        assertEquals(-4095, Integer.decode("-0xFFF"));
        assertThrows(NumberFormatException.class, () -> Integer.decode("-0x1G")); // некорректный символ
    }

    @Test
    void testDecodePositiveHexadecimal() {
        assertEquals(255, Integer.decode("0xFF"));
        assertEquals(4095, Integer.decode("0xFFF"));
        assertEquals(16, Integer.decode("0x10"));
        assertThrows(NumberFormatException.class, () -> Integer.decode("0x")); // неполный формат
        assertThrows(NumberFormatException.class, () -> Integer.decode("0xG")); // некорректный символ
    }

    @Test
    void testDecodeOctal() {
        assertEquals(83, Integer.decode("0123"));
        assertEquals(8, Integer.decode("010"));
        assertEquals(0, Integer.decode("00"));
        assertThrows(NumberFormatException.class, () -> Integer.decode("08"));
        assertThrows(NumberFormatException.class, () -> Integer.decode("0129"));
    }

    @Test
    void testDecodeNegativeOctal() {
        assertEquals(-83, Integer.decode("-0123"));
        assertEquals(-8, Integer.decode("-010"));
        assertEquals(0, Integer.decode("-00"));
        assertThrows(NumberFormatException.class, () -> Integer.decode("-08"));
        assertThrows(NumberFormatException.class, () -> Integer.decode("-0129"));
    }

    @Test
    void testDecodeDecimalWithSpaces() {
        assertThrows(NumberFormatException.class, () -> Integer.decode("   123"));
        assertThrows(NumberFormatException.class, () -> Integer.decode("123   "));
        assertThrows(NumberFormatException.class, () -> Integer.decode("  0x10"));
        assertThrows(NumberFormatException.class, () -> Integer.decode("   -123"));
        assertThrows(NumberFormatException.class, () -> Integer.decode("0 123"));
    }

    @Test
    void testDecodeInvalidString() {
        assertThrows(NumberFormatException.class, () -> Integer.decode("invalid"));
        assertThrows(NumberFormatException.class, () -> Integer.decode("123abc"));
        assertThrows(NumberFormatException.class, () -> Integer.decode("0x123agc"));
        assertThrows(NumberFormatException.class, () -> Integer.decode("-0x123abs"));
        assertThrows(NumberFormatException.class, () -> Integer.decode("abc123"));
    }
}
