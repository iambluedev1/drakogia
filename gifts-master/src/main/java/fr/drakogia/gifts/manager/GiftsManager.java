package fr.drakogia.gifts.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import fr.drakogia.core.player.DrakogiaPlayer;
import fr.drakogia.core.player.PlayerManager;
import fr.drakogia.gifts.DrakogiaGifts;
import fr.drakogia.gifts.object.Gift;
import fr.drakogia.gifts.table.IgnoreModel;
import fr.drakogia.gifts.table.InboxModel;
import lombok.Getter;

@Getter
public class GiftsManager {

	private static GiftsManager instance;
	private Map<Integer, List<Integer>> ignores;
	private Map<Integer, List<Gift>> gifts;
	private Map<String, Gift> sendingGifts;
	private HashMap<String, Integer> cooldownMap;
	private Map<String, Gift> viewingGifts;
	
	private IgnoreModel ignoreModel;
	private InboxModel inboxModel;
	
	private Integer cooldown;
	
	private GiftsManager(){
		this.ignores = new HashMap<Integer, List<Integer>>();
		this.gifts = new HashMap<Integer, List<Gift>>();
		this.sendingGifts = new HashMap<String, Gift>();
		this.cooldownMap = new HashMap<String, Integer>();
		this.viewingGifts = new HashMap<String, Gift>(); 
		
		this.ignoreModel = new IgnoreModel();
		this.inboxModel = new InboxModel();
		
		this.cooldown = DrakogiaGifts.getInstance().getDrakogiaConfig().getFileConfig().getInt("don.cooldown");
	}
	
	public void load(){
		System.out.println("Load Ignores");
		
		int amountIgnore = 0;
		
		for(Entry<Integer, Integer> ignore : this.ignoreModel.getIgnores().entrySet()){
			this.addIgnore(ignore.getKey(), ignore.getValue(), false);
			amountIgnore++;
		}
		
		System.out.println("Loaded " + amountIgnore + " Ignore !");
		
		System.out.println("Load Gifts");
		
		int amountGift = 0;
		for(Gift gift : this.inboxModel.getGifts()){
			System.out.println("FROM " + gift.getSender() + " TO " + gift.getRecipient());
			this.addGift(gift.getRecipient(), gift, false);
			amountGift++;
		}
		
		System.out.println("Loaded " + amountGift + " Gifts !");
	}
	
	public static GiftsManager getInstance(){
		if(instance == null){
			instance = new GiftsManager();
		}
		
		return instance;
	}
	
	public boolean isIgnored(Player ignorer, Player ignored){
		DrakogiaPlayer player = PlayerManager.getInstance().get(ignorer);
		return this.isIgnored(player.getId(), PlayerManager.getInstance().getModel().getId(ignored.getName()));
	}
	
	public void addIgnore(Player ignorer, Player ignored){
		DrakogiaPlayer player = PlayerManager.getInstance().get(ignorer);
		this.addIgnore(player.getId(), PlayerManager.getInstance().getModel().getId(ignored.getName()), true);
	}
	
	public void removeIgnore(Player ignorer, Player ignored){
		DrakogiaPlayer player = PlayerManager.getInstance().get(ignorer);
		this.removeIgnore(player.getId(), PlayerManager.getInstance().getModel().getId(ignored.getName()), true);
	}
	
	public List<String> getIgnored(Player ignorer){
		List<String> ignored = new ArrayList<String>();
		DrakogiaPlayer player = PlayerManager.getInstance().get(ignorer);
		
		for(Integer id : this.getIgnored(player.getId())){
			ignored.add(PlayerManager.getInstance().getModel().getName(id));
		}
		
		return ignored;
	}
	
	public void addGift(Player recipient, Gift gift){
		DrakogiaPlayer player = PlayerManager.getInstance().get(recipient);
		this.addGift(player.getId(), gift, true);
	}
	
	public void removeGift(Player recipient, Inventory inventory){
		DrakogiaPlayer player = PlayerManager.getInstance().get(recipient);
		this.removeGift(player.getId(), inventory, true);
	}
	
	public List<Gift> getGifts(Player recipient){
		DrakogiaPlayer player = PlayerManager.getInstance().get(recipient);
		return this.getGifts(player.getId());
	}
	
	public List<Gift> getPage(Player recipient, Integer page){
		DrakogiaPlayer player = PlayerManager.getInstance().get(recipient);
		return this.getPage(player.getId(), page);
	}
	
	public Integer countPages(Player sender){
		DrakogiaPlayer player = PlayerManager.getInstance().get(sender);
		Integer count = this.countPages(player.getId());
		return (count == 0) ? 1 : count;
	}
	
	public Gift getSendingGift(Player player){
		return this.getSendingGift(player.getName());
	}
	
	public void setSendingGift(Player player, Gift gift){
		this.setSendingGift(player.getName(), gift);
	}
	
	public boolean isSendingGift(Player player){
		return this.isSendingGift(player.getName());
	}
	
	public boolean isViewingGift(Player player){
		return this.isViewingGift(player.getName());
	}
	
	public void setViewingGift(Player player, Gift gift){
		this.setViewingGift(player.getName(), gift);
	}
	
	public Gift getViewingGift(Player player){
		return this.getViewingGift(player.getName());
	}
	
	public boolean canSendGift(Player player) {
        return this.canSendGift(player.getName());
    }

    public void hadSendGift(Player player) {
       this.hadSendGift(player.getName());
    }

    public long getHowManyTime(Player player) {
        return this.getHowManyTime(player.getName());
    }
	
	public void addIgnore(Integer ignorer, Integer ignored, boolean persistant){
		if(!this.ignores.containsKey(ignorer)){
			List<Integer> tmp2 = (this.ignores.containsKey(ignorer)) ? this.ignores.get(ignorer) : new ArrayList<Integer>();
			tmp2.add(ignored);
			this.ignores.put(ignorer, tmp2);
			
			if(persistant)
				this.ignoreModel.add(ignorer, ignored);
		}
	}
	
	public void removeIgnore(Integer ignorer, Integer ignored, boolean persistant){
		if(this.ignores.containsKey(ignorer)){
			List<Integer> tmp = this.ignores.get(ignorer);
			tmp.remove(ignored);
            this.ignores.put(ignorer, tmp);
           
            if(persistant)
            	this.ignoreModel.remove(ignorer, ignored);
		}
	}
	
	public boolean isIgnored(Integer ignorer, Integer ignored){
		List<Integer> tmp = this.ignores.get(ignorer);
        return tmp != null && tmp.contains(ignored);

	}
	
	public List<Integer> getIgnored(Integer ignorer){
		return (this.ignores.containsKey(ignorer) ? this.ignores.get(ignorer) : Arrays.asList());
	}

	
	public void addGift(Integer recipient, Gift gift, boolean persistant){
		List<Gift> tmp2 = (this.gifts.containsKey(recipient)) ? this.gifts.get(recipient) : new ArrayList<Gift>();
		tmp2.add(gift);
		this.gifts.put(recipient, tmp2);
		
		System.out.println("2 FROM " + gift.getSender() + " TO " + recipient);
		
		if(persistant)
			this.inboxModel.add(recipient, gift);
	}
	
	public void removeGift(Integer player, Inventory inventory, boolean persistant) {
		if (this.getGifts(player).size() == 0) return;
		
		for (Gift gifts : this.getGifts(player)) {
            if (gifts.getInventory().equals(inventory)) {
                List<Gift> tmp = this.getGifts(player);
                tmp.remove(gifts);
                this.gifts.put(player, tmp);
                
                if(persistant)
                	this.inboxModel.remove(player, gifts);
                
                break;
            }
        }
	}
	
	public List<Gift> getGifts(Integer player){
		if(!this.gifts.containsKey(player)){
			this.gifts.put(player, new ArrayList<Gift>());
		}
		
        return this.gifts.get(player);
	}
	
	public Gift getSendingGift(String player){
		return this.sendingGifts.get(player);
	}
	
	public void setSendingGift(String player, Gift gift){
		this.sendingGifts.remove(player);
		this.sendingGifts.put(player, gift);
	}
	
	public boolean isSendingGift(String player){
		return this.sendingGifts.get(player) != null;
	}
	
	public boolean isViewingGift(String player){
		return this.viewingGifts.get(player) != null;
	}
	
	public void setViewingGift(String player, Gift gift){
		this.viewingGifts.remove(player);
		this.viewingGifts.put(player, gift);
	}
	
	public Gift getViewingGift(String player){
		return this.viewingGifts.get(player);
	}
	
	public boolean canSendGift(String player) {
        Integer lastTime = cooldownMap.getOrDefault(player, 0);
        return lastTime == 0 || lastTime + (this.cooldown * 60) <= (System.currentTimeMillis() / 1000);
    }

    public void hadSendGift(String player) {
        this.cooldownMap.put(player, (int) System.currentTimeMillis() / 1000);
    }

    public long getHowManyTime(String player) {
        return (cooldownMap.get(player) + (this.cooldown * 60)) - (System.currentTimeMillis() / 1000);
    }
    
	public List<Gift> getPage(Integer player, Integer page){
		List<List<Gift>> pages = this.formatGifts(player);
		if(pages.size() == 0){
			return Arrays.asList();
		}
		
		if(pages.size() >= page){
			return pages.get(page);
		}
		
		return Arrays.asList();
	}
	
	public Integer countPages(Integer player){
		return this.formatGifts(player).size();
	}
	
	private List<List<Gift>> formatGifts(Integer player){
		return this.chopped(this.getGifts(player), 9 * 5 + 1);
	}
	
	private <T> List<List<T>> chopped(List<T> list, final int L) {
	    List<List<T>> parts = new ArrayList<List<T>>();
	    final int N = list.size();
	    for (int i = 0; i < N; i += L) {
	        parts.add(new ArrayList<T>(
	            list.subList(i, Math.min(N, i + L)))
	        );
	    }
	    return parts;
	}
}
