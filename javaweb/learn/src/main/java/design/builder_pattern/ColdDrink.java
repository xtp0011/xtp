package com.xtp.design.builder_pattern;

public abstract class ColdDrink  implements Item{
    @Override
    public Packing packing() {
        return new Bottle();
    }
}
