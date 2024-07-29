package me.henrydhc.spawnermanager.confighandler;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class MobConfig {

    private final EntityType type;
    private double cost;
    private final List<ItemStack> itemCosts = new LinkedList<>();

    public MobConfig(EntityType type, double cost, List<String> itemCosts) {
        this.type = type;
        this.cost = cost;
        int amount;

        for (String itemString: itemCosts) {
            String itemData[] = itemString.split(" ");
            Material material = Material.getMaterial(itemData[0]);
            try {
                amount = Integer.parseInt(itemData[1]);
                if (material != null) {
                    this.itemCosts.add(new ItemStack(material, amount));
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
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
    public ItemStack[] getItemCosts() {
        return itemCosts.toArray(new ItemStack[0]);
    }
    
}
