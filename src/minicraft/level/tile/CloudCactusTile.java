package minicraft.level.tile;

import minicraft.entity.AirWizard;
import minicraft.entity.Entity;
import minicraft.entity.Mob;
import minicraft.entity.Player;
import minicraft.entity.particle.SmashParticle;
import minicraft.entity.particle.TextParticle;
import minicraft.gfx.Color;
import minicraft.gfx.Sprite;
import minicraft.item.Item;
import minicraft.item.ToolItem;
import minicraft.item.ToolType;
import minicraft.level.Level;
import minicraft.screen.OptionsMenu;
import minicraft.screen.ModeMenu;

public class CloudCactusTile extends Tile {
	private static Sprite sprite = new Sprite(17, 1, 2, 2, Color.get(444, 111, 333, 555));
	
	protected CloudCactusTile(String name) {
		super(name, sprite);
	}
	
	public boolean mayPass(Level level, int x, int y, Entity e) {
		if (e instanceof AirWizard) return true;
		return false;
	}

	public void hurt(Level level, int x, int y, Mob source, int dmg, int attackDir) {
		hurt(level, x, y, 0);
	}

	public boolean interact(Level level, int xt, int yt, Player player, Item item, int attackDir) {
		if (item instanceof ToolItem) {
			ToolItem tool = (ToolItem) item;
			if (tool.type == ToolType.Pickaxe) {
				if (player.payStamina(6 - tool.level)) {
					hurt(level, xt, yt, 1);
					return true;
				}
			}
		}
		return false;
	}
	
	public void hurt(Level level, int x, int y, int dmg) {
		int damage = level.getData(x, y) + dmg;
		int health = 10;
		if(ModeMenu.creative) dmg = damage = health;
		level.add(new SmashParticle(x * 16, y * 16));
		level.add(new TextParticle("" + dmg, x * 16 + 8, y * 16 + 8, Color.get(-1, 500)));
		if (damage >= health)
			level.setTile(x, y, Tiles.get("cloud"));
		else
			level.setData(x, y, damage);
	}

	public void bumpedInto(Level level, int x, int y, Entity entity) {
		if (entity instanceof AirWizard) return;
		entity.hurt(this, x, y, 1+OptionsMenu.diff);
	}
}
