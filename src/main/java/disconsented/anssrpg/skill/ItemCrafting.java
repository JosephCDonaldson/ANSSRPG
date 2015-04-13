/*The MIT License (MIT)

Copyright (c) 2015 Disconsented, James Kerr

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 */
package disconsented.anssrpg.skill;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerOpenContainerEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import disconsented.anssrpg.data.PerkStore;
import disconsented.anssrpg.data.PlayerStore;
import disconsented.anssrpg.data.SkillStore;
import disconsented.anssrpg.handler.PlayerHandler;
import disconsented.anssrpg.perk.Slug;
import disconsented.anssrpg.player.PlayerData;
import disconsented.anssrpg.skill.objects.ItemSkill;
import disconsented.anssrpg.common.Quad;
import disconsented.anssrpg.common.Utils;
/**
 * @author James
 *         Handles crafting, both gaining XP and restricting use
 *         PlayerOpenContainer needs to handle blocking, checking for the output and what is in the crafting matrix (Maybe to expensive to compute/?) - For now just checking output, remember to ask about matrix gating in feedback
 *         I need to add a onCraftingMatrix changed event
 *         ItemCrafted handles just giving XP
 */
public class ItemCrafting {
  @SubscribeEvent
  public void onPlayerOpenCrafting(PlayerOpenContainerEvent event) {
      if (event.entityPlayer instanceof EntityPlayerMP){
          Container container = event.entityPlayer.openContainer;
          if ((container instanceof net.minecraft.inventory.ContainerWorkbench || container instanceof net.minecraft.inventory.ContainerPlayer) &&
                event.entityPlayer.openContainer.inventoryItemStacks.get(0) != null) {              
              EntityPlayerMP player = (EntityPlayerMP) event.entityPlayer;
              ItemStack stack = (ItemStack)player.openContainer.inventoryItemStacks.get(0);
              Item item = stack.getItem();             
              PlayerData playerData = PlayerStore.getPlayer(player);
              ArrayList<Slug> slugList = PerkStore.getSlugs(item);
              ArrayList<ItemSkill> skillStore = SkillStore.getInstance().getItemSkill();
              Boolean requiresPerk = false;
              
              if (slugList != null){
                  requiresPerk = true;
              }
              
              for (ItemSkill skill : skillStore){
                  for (Quad entry : skill.exp){
                      if(Utils.MatchObject(entry.object, entry.metadata, item, stack.getItemDamage())){
                          if (PlayerHandler.hasPerk(playerData, slugList)){
                              player.closeScreen();
                              PlayerHandler.taskFail(player);
                          }
                      }
                  }
              }
          }
      }
      
  }
  
    @SubscribeEvent //Assumed that crafting wasn't blocked
    public void onItemCraftedEvent(ItemCraftedEvent event) {
        if (event.player instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) event.player;
            ItemStack stack = event.crafting;
            Item item = stack.getItem();             
            PlayerData playerData = PlayerStore.getPlayer(player);
            ArrayList<ItemSkill> skillStore = SkillStore.getInstance().getItemSkill();
            
            for (ItemSkill skill : skillStore){
                for (Quad entry : skill.exp){
                    if(Utils.MatchObject(entry.object, entry.metadata, item, stack.getItemDamage())){
                        PlayerHandler.awardXP(player, skill, entry.experience);
                    }
                }
            }
        }        
    }

}
