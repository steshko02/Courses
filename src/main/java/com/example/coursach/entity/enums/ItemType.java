package com.example.coursach.entity.enums;

import eu.senla.git.coowning.exception.shareditem.InvalidItemTypeException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum ItemType {

    AIR(0), LAND(1), WATER(2), REALTY(3);

    private static final Map<Integer, ItemType> LOOKUP;

    private final int val;

    static {
        LOOKUP = Collections.unmodifiableMap(Arrays.stream(ItemType.values())
                .collect(Collectors.toMap(ItemType::getVal, Function.identity())));
    }

    public static ItemType lookup(int val) {
        return Optional.ofNullable(LOOKUP.get(val))
                .orElseThrow(InvalidItemTypeException::new);
    }

    public static ItemType getItemTypeByName(String name) {
        try {
            return ItemType.valueOf(name);
        } catch (IllegalArgumentException e) {
            throw new InvalidItemTypeException();
        }
    }
}