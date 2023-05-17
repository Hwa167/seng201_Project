package com.dd.playgame.test;

import org.junit.jupiter.api.Test;

import com.dd.playgame.bean.Consumable;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConsumableTest {

    @Test
    void testGetEffectStr() {
        Consumable consumable = new Consumable();
        consumable.effect = 3.14159;

        String expected = BigDecimal.valueOf(3.14159)
                .setScale(2, RoundingMode.FLOOR)
                .toPlainString();
        String actual = consumable.getEffectStr();

        assertEquals(expected, actual);
    }

    @Test
    void testFormatBasic() {
        Consumable consumable = new Consumable();
        consumable.name = "Health Potion";
        consumable.effect = 50;
        consumable.description = "Restores health points";

        String expected = "Health Potion                  effect:50.00       Restores health points";
        String actual = consumable.formatBasic();

        assertEquals(expected, actual);
    }

    @Test
    void testToString() {
        Consumable consumable = new Consumable();
        consumable.num = 5;
        consumable.name = "Mana Potion";
        consumable.effect = 100;

        String expected = " num=5, name='Mana Potion', effect=100.0";
        String actual = consumable.toString();

        assertEquals(expected, actual);
    }
}

