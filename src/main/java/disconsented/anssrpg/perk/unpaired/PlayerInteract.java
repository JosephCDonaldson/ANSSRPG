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
package disconsented.anssrpg.perk.unpaired;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import disconsented.anssrpg.data.PerkStore;
import disconsented.anssrpg.data.PlayerStore;
import disconsented.anssrpg.perk.Slug;
import disconsented.anssrpg.player.PlayerData;
import disconsented.anssrpg.task.TaskInteraction;
import disconsented.anssrpg.task.TaskMaster;

/**
 * @author Disconsented
 * Makes use of the tasking system to check
 */
public class PlayerInteract {
    
    public void onPlayerInteract(PlayerInteractEvent event){
        if(event.action.equals(PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) && !(event.entityPlayer.openContainer instanceof net.minecraft.inventory.ContainerPlayer)){
            TaskMaster.getInstance().addTask(new TaskInteraction(event.entityPlayer));
        }
    }
    public void onIneractionTask(TaskInteraction task){
    	//Do the things
    }
    

}
