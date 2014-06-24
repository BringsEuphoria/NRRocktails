import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import org.parabot.environment.api.interfaces.Paintable;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.api.utils.Timer;
import org.parabot.environment.scripts.Category;
import org.parabot.environment.scripts.Script;
import org.parabot.environment.scripts.ScriptManifest;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.Bank;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.methods.Menu;
import org.rev317.min.api.methods.Npcs;
import org.rev317.min.api.methods.Players;
import org.rev317.min.api.methods.SceneObjects;
import org.rev317.min.api.wrappers.SceneObject;
import org.rev317.min.api.wrappers.Tile;
import org.rev317.min.api.wrappers.TilePath;
;
@ScriptManifest(author = "BusinessEitqette", category = Category.FISHING, description = "Fishes Rocktails at fishing guild", name = "NRRocktails", servers = { "Near-Reality" }, version = 1.0)

public class NRRocktails extends Script implements Paintable  {
	
	public int c;

	 private final ArrayList<Strategy> strategies = new ArrayList<Strategy>();
		
		public boolean onExecute() {
			strategies.add(new Fish());
	        strategies.add(new Banking());
	        provide(strategies);
	        return true;
			
			}
		public void onFinish() {
			
		}
		
		//END MAIN
		
	
		
		//START CUSTOM BANK DEPOSITE (MODIFIED depositeAllExecpt FOR NEAR-REALITY)
		
		
		//START FISH
		public class Fish implements Strategy {

			
			Tile[] FISH_WALK = {new Tile (2586, 3418, 0), new Tile(2593, 3415 ,0), new Tile (2604, 3424, 0)};
			
			TilePath path = new TilePath(FISH_WALK);
			
			@Override
			public boolean activate() {
				
				   for (SceneObject i : SceneObjects.getNearest(1102)) {
			            if (i !=null 
			            		&& !Players.getMyPlayer().isInCombat()
			            		&& !(Inventory.getCount() == 28)
			            		&& Players.getMyPlayer().getAnimation() == -1) {
			            	return true;
			            }
				   }
				return false;
			}

			@Override
			public void execute() {
				if (path !=null) {
					path.traverse();
					Time.sleep(2000);
			}
		      Npcs.getNearest(233)[0].interact(0);
		       	Time.sleep(4000);
		       
		      
		        
		    }
			
				
				
			}
			
		
		//END FISHING
		
		//START BANKING
		public class Banking implements Strategy {

			Tile[] BANK_WALK = {new Tile (2604, 3424, 0), new Tile(2596, 3420, 0), new Tile (2586, 3418 ,0)};
			
			TilePath path = new TilePath(BANK_WALK);
			
			
			@Override
			public boolean activate() {
				if(Inventory.getCount() == 28 && Players.getMyPlayer().getAnimation() == -1) {
					return true;	
				}
				
				return false;
			}

			@Override
			public void execute() {		
				if (path !=null) {
					path.traverse();
					Time.sleep(2000);
			}
				Bank.open();	
				Time.sleep(2000);
	            
				//THIS CODE IS EXTREMLY POORLY DESIGNED, PLEASE IGNORE IT
				if (Bank.isOpen()) {
					c = c + Inventory.getCount(true, 15271);
					System.out.println(c);
					Time.sleep(500);		
					Menu.sendAction(431, 15270, 2, 5064);
					Time.sleep(1000);
			            Bank.close();
			            Time.sleep(1000);
			        }
			}
			
		}
		
		
	  
		//START: Code generated using Enfilade's Easel
	    private Image getImage(String url) {
	        try {
	            return ImageIO.read(new URL(url));
	        } catch(IOException e) {
	            return null;
	        }
	    }

	    private final Color color1 = new Color(0, 0, 255);
	    private final Color color2 = new Color(51, 51, 0);

	    private final BasicStroke stroke1 = new BasicStroke(1);

	    private final Font font1 = new Font("Arial", 1, 20);
	    private final Font font2 = new Font("Arial", 1, 9);

	    private final Image img1 = getImage("http://geupdater.com/images/items/full/15270-raw-rocktail.png");
	    private final Image img2 = getImage("http://i155.photobucket.com/albums/s300/btbbbjl/Nightgunner5/LlamaSlayers/Fishingcapet.png");

	    
	    Timer runTime = new Timer(0);
	    
	    

	    public void paint(Graphics g1) {
	        Graphics2D g = (Graphics2D)g1;
	        g.setColor(color1);
	        g.fillRoundRect(10, 347, 483, 120, 16, 16);
	        g.setStroke(stroke1);
	        g.drawRoundRect(10, 347, 483, 120, 16, 16);
	        g.setFont(font1);
	        g.setColor(color2);
	        g.drawString("Time Running: ", 119, 378);
	        g.drawString("Rocktails Caught: ", 123, 414);
	        g.drawString("Profit made: ", 118, 449);
	        g.setFont(font2);
	        g.drawString("NRRocktails by BusinessEtiquette", 356, 17);
	        g.drawImage(img1, 19, 355, null);
	        g.setFont(font1);
	        g.drawString(runTime.toString(), 279, 378);
	        g.drawString("" + c, 315, 414);
	        g.drawString("" + c*.1 + "M", 315, 449);
	        g.drawImage(img2, 372, 358, null);

	    }
	    //END: Code generated using Enfilade's Easel
}
