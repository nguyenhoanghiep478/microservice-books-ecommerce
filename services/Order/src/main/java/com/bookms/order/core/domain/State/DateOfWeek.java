package com.bookms.order.core.domain.State;

public enum DateOfWeek {
    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5),
    SATURDAY(6),
    SUNDAY(7);


    final int value;
    DateOfWeek(int value) {
        this.value = value;
    }

}
