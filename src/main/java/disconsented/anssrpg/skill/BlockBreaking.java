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
/**
 * @author James
 * Handles when to add experience and blocking of events
 */

import disconsented.anssrpg.common.*;
import disconsented.anssrpg.data.PerkStore;
import disconsented.anssrpg.data.PlayerStore;
import disconsented.anssrpg.data.SkillStore;
import disconsented.anssrpg.handler.PlayerHandler;
import disconsented.anssrpg.perk.BlockPerk;
import disconsented.anssrpg.player.PlayerData;
import disconsented.anssrpg.skill.objects.BlockSkill;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;

import java.util.ArrayList;

public class BlockBreaking {

    private static boolean requiresPerk(ArrayList<BlockPerk> perkList, Block block, int metadata) {
        if (perkList != null) {
            for (BlockPerk perk : perkList) {
                for (ObjectPerkDefinition definition : perk.blocks) {
                    if (Utils.MatchObject(definition.object, definition.metadata, block, metadata)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void onBreakEvent(BreakEvent event) {
        boolean isFakePlayer = event.getPlayer() instanceof FakePlayer;
        if (isFakePlayer && !Settings.isBlockFakePlayers()) {
            return;
        }
        if (event.getPlayer() instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) event.getPlayer();
            System.out.println(player.getEntityId());
            player.setEntityId(20);
            System.out.println(player.getEntityId() + "|");
            PlayerData playerData = PlayerStore.getPlayer(player);
            ArrayList<BlockPerk> perkList = PerkStore.getPerks(event.block);
            ArrayList<BlockSkill> skillStore = SkillStore.getBlockSkill();

            for (BlockSkill skill : skillStore) {
                for (Quad entry : skill.exp) {
                    if (Utils.MatchObject(entry.object, entry.metadata, event.block, event.blockMetadata)) {
                        if (requiresPerk(perkList, event.block, event.blockMetadata)) {
                            if (PlayerHandler.hasPerk(playerData, perkList)) {
                                PlayerHandler.awardToolXP(player, skill, entry.experience);
                            } else {
                                if (!isFakePlayer) {
                                    PlayerHandler.taskFail(player);
                                    event.setCanceled(true);
                                    return;
                                } else {
                                    if (Settings.isBlockFakePlayers()) {
                                        Logging.debug("Fake player blocked at " + player.chunkCoordX + "," + player.chunkCoordY + "," + player.chunkCoordZ);
                                        event.setCanceled(true);
                                        return;
                                    }
                                }
                                return;
                            }
                        } else {
                            PlayerHandler.awardToolXP(player, skill, entry.experience);
                            return;
                        }
                    }
                }
            }
        }
    }
}


