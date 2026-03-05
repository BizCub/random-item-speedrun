package com.bizcub.randomItemSpeedrun.util;

import java.util.List;

public record RemovableItems(
        List<String> equalItems,
        List<String> containItems
) {
}
