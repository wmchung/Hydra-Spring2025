public class FishingRod extends Item {
    public FishingRod(String itemID, String itemName, String itemDescription, String tag, String location) {
        super(itemID, itemName, itemDescription, tag,location);
    }
    //fish method, as in using the fishingRod
    public void fish (Player player){
        //check if the player is in the Island room
        if(!player.getCurrentRoom().getRoomId().equalsIgnoreCase("IR")){ //Island ID
            System.out.println("You can only use the Fishing rod on the Island.");
            return;
        }
        double chance = Math.random();
        if(chance < 0.5){
            System.out.println("You caught a fish!");
            Consumable fish = new Consumable("AR04.2","fish", "Glistening in the sunlight, the fish serve as another health source for the player, however, they will spoil if taken away from the island.", "healing","island",1,5);
            player.getInventory().add(fish); //inventory is a List <Item> type
        }else{
            System.out.println("Unsuccessful catch! Try again!");
        }

    }

}
