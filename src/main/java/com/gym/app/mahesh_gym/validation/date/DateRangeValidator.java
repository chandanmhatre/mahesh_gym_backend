package com.gym.app.mahesh_gym.validation.date;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateRangeValidator implements ConstraintValidator<DateRange, LocalDate> {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    private LocalDate minDate;
    private LocalDate maxDate;

    @Override
    public void initialize(DateRange constraintAnnotation) {
        try {
            if (!constraintAnnotation.min().isEmpty()) {
                this.minDate = LocalDate.parse(constraintAnnotation.min(), DATE_FORMATTER);
            }
            if (!constraintAnnotation.max().isEmpty()) {
                this.maxDate = LocalDate.parse(constraintAnnotation.max(), DATE_FORMATTER);
            }
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format in @DateRange annotation (must be YYYY-MM-DD): " + e.getMessage());
        }
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // @NotNull should handle nulls if required
        }
        boolean isValid = true;
        if (minDate != null) {
            isValid = isValid && !value.isBefore(minDate);
        }
        if (maxDate != null) {
            isValid = isValid && !value.isAfter(maxDate);
        }
        return isValid;
    }
}
