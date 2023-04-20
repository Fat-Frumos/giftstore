package com.epam.esm;

import com.epam.esm.config.EnumConverterFactory;
import com.epam.esm.criteria.SortField;
import com.epam.esm.criteria.SortOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.core.convert.converter.Converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConverterFactoryTest {

    private EnumConverterFactory converterFactory;

    @BeforeEach
    void setUp() {
        converterFactory = new EnumConverterFactory();
    }

    @Test
    @DisplayName("Should throw RuntimeException if enum constant not found")
    void shouldThrowRuntimeExceptionIfSortOrderConstantNotFound() {
        Converter<String, Enum> converter = converterFactory.getConverter(Enum.class);
        assertThrows(RuntimeException.class, () -> converter.convert("NonExistingValue"));
    }


    @DisplayName("Should convert string to enum ignoring case sensitivity")
    @ParameterizedTest(name = "Test converting {0} to {1}")
    @CsvSource({
            "DESC, Desc",
            "ASC, Asc",
            "UNSORTED, "
    })
    void testShouldConvertStringToEnumIgnoringCaseSensitivity(SortOrder expected, String input) {
        SortOrder actual = converterFactory.getConverter(SortOrder.class).convert(input);
        assertEquals(expected, actual);
    }

    @DisplayName("Should convert string to enum ignoring case sensitivity")
    @ParameterizedTest(name = "Test converting {0} to {1}")
    @CsvSource({
            "DATE, date",
            "NAME, name",
            "ID, id"
    })
    void testShouldConvertStringToEnumFiledIgnoringCaseSensitivity(SortField expected, String input) {
        SortField actual = converterFactory.getConverter(SortField.class).convert(input);
        assertEquals(expected, actual);
    }
}
