package me.henrydhc.spawnermanager.confighandler;

import org.bukkit.entity.EntityType;

public class MobConfig {

    private final EntityType type;
    private final double cost;

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
     * Get entity cost
     * @return Entity cost
     */
    public double getCost() {
        return cost;
    }
    
}
