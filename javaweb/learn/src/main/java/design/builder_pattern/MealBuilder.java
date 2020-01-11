package com.xtp.design.builder_pattern;

/**
 * 建造者模式（Builder Pattern）使用多个简单的对象一步一步构建成一个复杂的对象。
 * 这种类型的设计模式属于创建型模式，它提供了一种创建对象的最佳方式。
 * 一个 Builder 类会一步一步构造最终的对象。该 Builder 类是独立于其他对象的。
 *
 * 主要解决：主要解决在软件系统中，有时候面临着"一个复杂对象"的创建工作，
 * 其通常由各个部分的子对象用一定的算法构成；由于需求的变化，
 * 这个复杂对象的各个部分经常面临着剧烈的变化，但是将它们组合在一起的算法却相对稳定。
 *
 * 何时使用：一些基本部件不会变，而其组合经常变化的时候。
 *
 *
 * 优点： 1、建造者独立，易扩展。 2、便于控制细节风险。
 *
 * 缺点： 1、产品必须有共同点，范围有限制。 2、如内部变化复杂，会有很多的建造类。
 *
 * 使用场景： 1、需要生成的对象具有复杂的内部结构。 2、需要生成的对象内部属性本身相互依赖。
 *
 */

public class MealBuilder {
    public Meal prepareVegMeal (){
        Meal meal = new Meal();
        meal.addItem(new VegBurger());
        meal.addItem(new Coke());
        return meal;
    }

    public Meal prepareNonVegMeal (){
        Meal meal = new Meal();
        meal.addItem(new ChickenBurger());
        meal.addItem(new Pepsi());
        return meal;
    }
}
