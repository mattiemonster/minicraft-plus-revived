package minicraft.entity.particle;

import minicraft.gfx.Color;
import minicraft.gfx.Font;
import minicraft.gfx.Screen;

public class TextParticle extends Particle {
	private String msg; // Message of the text particle
	public double xa, ya, za; // x,y,z acceleration
	public double xx, yy, zz; // x,y,z coordinates
	
	private int color;
	
	public TextParticle(String msg, int x, int y, int col) {
		super(x, y, 60, null);
		this.xr = msg.length();
		
		this.color = col;
		this.msg = msg;
		xx = x; //assigns x pos
		yy = y; //assigns y pos
		zz = 2; //assigns z pos to be 2
		
		//assigns x,y,z acceleration:
		xa = random.nextGaussian() * 0.3;
		ya = random.nextGaussian() * 0.2;
		za = random.nextFloat() * 0.7 + 2;
	}

	public void tick() {
		super.tick();
		
		//move the particle according to the acceleration
		xx += xa;
		yy += ya;
		zz += za;
		if (zz < 0) {
			//if z pos if less than 0, alter accelerations...
			zz = 0;
			za *= -0.5;
			xa *= 0.6;
			ya *= 0.6;
		}
		za -= 0.15;  // za decreases by 0.15 every tick.
		//truncate x and y coordinates to integers:
		x = (int) xx;
		y = (int) yy;
	}
	
	public void render(Screen screen) {
		if(!msg.contains("Thanks")) {
			Font.draw(msg, screen, x - msg.length() * 4, y - (int)zz, color, Color.get(-1, 0)); //renders the shadow
		} else { // special, for "Thanks for Playing!" message?
			String msg1 = msg.substring(0, 19);
			String msg2 = msg.substring(19, msg.length());
			Font.draw(msg1, screen, x - msg.length() * 4, y - (int)zz - 8, color, Color.get(-1, 0));
			Font.draw(msg2, screen, x - msg.length() * 4, y - (int)zz, color, Color.get(-1, 0));
		}
	}
}
