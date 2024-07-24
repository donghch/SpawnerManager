package me.henrydhc.spawnermanager.confighandler;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class MobConfig {

    private final EntityType type;
    private double cost;
    private final List<ItemStack> itemCost = new LinkedList<>();

    public MobConfig(EntityType type, double cost) {
        this.type = type;
        this.cost = cost;
    }

    /**
     * Get entity type
     * @return Entity type
     */
    public EntityType getType() {
        return type;
    }

    /**
     * Get entity econ cost
     * @return Entity cost
     */
    public double getEconCost() {
        return cost;
    }

    /**
     * Set entity econ cost
     * @param cost Entity's econ cost
     */
    public void setEconCost(double cost) {
        this.cost = cost;
    }

    /**
     * Get item cost of replacing a spawner's mob type.
     * @return List of items as cost
     */
    public ItemStack[] getItemCost() {
        return itemCost.toArray(new ItemStack[0]);
    }
    
}
