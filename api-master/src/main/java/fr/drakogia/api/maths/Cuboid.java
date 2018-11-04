package fr.drakogia.api.maths;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cuboid {
	
	private Location loc1;
    private Location loc2;
    private int maxX;
    private int minX;
    private int maxY;
    private int minY;
    private int maxZ;
    private int minZ;

    public Cuboid(Location l, Location l2) {
        this.loc1 = l;
        this.loc2 = l2;
        if (l.getBlockX() > l2.getBlockX()) {
            this.maxX = l.getBlockX();
            this.minX = l2.getBlockX();
        } else {
            this.maxX = l2.getBlockX();
            this.minX = l.getBlockX();
        }
        if (l.getBlockY() > l2.getBlockY()) {
            this.maxY = l.getBlockY();
            this.minY = l2.getBlockY();
        } else {
            this.maxY = l2.getBlockY();
            this.minY = l.getBlockY();
        }
        if (l.getBlockZ() > l2.getBlockZ()) {
            this.maxZ = l.getBlockZ();
            this.minZ = l2.getBlockZ();
        } else {
            this.maxZ = l2.getBlockZ();
            this.minZ = l.getBlockZ();
        }
    }

    public boolean isInCuboid(Player p) {
        return this.isInCuboid(p.getLocation().getBlock().getLocation());
    }

    public boolean isInCuboid(Location l) {
        if ((l.getX() <= this.maxX) && (l.getX() >= this.minX)
                && (l.getY() <= this.maxY) && (l.getY() >= this.minY)
                && (l.getZ() <= this.maxZ) && (l.getZ() >= this.minZ)) {
            return true;
        }
        return false;
    }

    public static Cuboid createWithRadius(Location location, double x, double y, double z) {
        return new Cuboid(location.clone().add(x, y, z), location.clone().subtract(x, y, z));
	}

}
