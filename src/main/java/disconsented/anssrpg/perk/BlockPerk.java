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
package disconsented.anssrpg.perk;

import com.google.gson.annotations.Expose;
import disconsented.anssrpg.common.Logging;
import disconsented.anssrpg.common.ObjectPerkDefinition;
import lombok.NoArgsConstructor;
import net.minecraft.block.Block;

import java.util.ArrayList;

@NoArgsConstructor
public class BlockPerk extends Perk {

    @Expose
    public ArrayList<ObjectPerkDefinition<Block>> blocks = new ArrayList<ObjectPerkDefinition<Block>>();

    public BlockPerk(String name, ArrayList<Requirement> requirements, String description, int pointCost, ArrayList<ObjectPerkDefinition<Block>> blocks) {
        super(name, requirements, description, pointCost);
        this.blocks = blocks;
    }

    @Override
    public void searchObject() {
        ArrayList<ObjectPerkDefinition<Block>> initialised = new ArrayList<ObjectPerkDefinition<Block>>();
        for (ObjectPerkDefinition object : blocks) {
            object.object = Block.blockRegistry.getObject(object.name);
            if (object.object != null) {
                Logging.debug(object.name + " has been found. Passing on.");
                initialised.add(object);
            } else {
                Logging.error(object.name + " has not been found. Skipping");
            }
        }
        this.blocks = initialised;
    }

}
