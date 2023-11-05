package org.carrental;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UnitTestExampleTest {

    private UnitTestExample unitTestExample = new UnitTestExample();

    @Test
    void shouldCorrectlyAddTwoIntegers(){
        int result = unitTestExample.add(1, 1);

        assertEquals(2, result);
    }

    @ParameterizedTest
    @ValueSource(ints = {1,5,8,10,50})
    void shouldCorrectlyAddTwoIntegers(int a){
        int result = unitTestExample.add(a, 1);

        assertEquals(a+1, result);
    }

    @ParameterizedTest
    @MethodSource(value = "provideArguments")
    void shouldCorrectlyAddTwoIntegers(int a, int b){
        int result = unitTestExample.add(a, b);

        assertEquals(a+b, result);
    }

    @ParameterizedTest
    @MethodSource(value = "provideArgumentsAndResult")
    void shouldCorrectlyAddTwoIntegers(int a, int b, int expectedResult){
        int result = unitTestExample.add(a, b);

        assertEquals(expectedResult, result);
    }

    public static Stream<Arguments> provideArgumentsAndResult() {
        return Stream.of(
                Arguments.of(0, 1, 1)
        );
    }

    public static Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of(1,1),
                Arguments.of(5,10),
                Arguments.of(0,0)
        );
    }

}