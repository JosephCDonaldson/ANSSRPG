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
package disconsented.anssrpg.gui;

import disconsented.anssrpg.Main;
import disconsented.anssrpg.client.Data;
import disconsented.anssrpg.gui.components.ExpBox;
import disconsented.anssrpg.network.Request;
import disconsented.anssrpg.network.Request.REQUEST;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.util.ArrayList;

/**
 * @author Disconsented
 */
public class GUIExperience extends GuiScreen {
    private ArrayList<ExpBox> boxes = new ArrayList<ExpBox>();
    private int page;
    private int boxCount = 0;

    @Override
    public void initGui() {
        int x = width / 2;
        Data.skillInfo.clear();
        Data.skillInfoList.clear();
        page = 1;
        boxes.clear();
        boxCount = 0;
        Main.snw.sendToServer(new Request(REQUEST.SKILLS));
        this.buttonList.add(new GuiButton(0, x, 76 * 3, 40, 20, "Next"));
        this.buttonList.add(new GuiButton(1, x - 40, 76 * 3, 40, 20, "Prev"));
        for (int i = 0; i < 3; i++) {
            int y = i * 76;
            ExpBox box1 = new ExpBox(x - 176, y);
            ExpBox box2 = new ExpBox(x, y);
            boxes.add(box1);
            boxes.add(box2);
            boxCount += 2;
        }
    }

    @Override
    public void drawScreen(int x, int y, float renderPartialTick) {
        drawDefaultBackground();
        this.buttonList.clear();

        super.drawScreen(x, y, renderPartialTick);

        for (int i = 0; i < boxes.size(); i++) {
            int num = (page * i);
            ExpBox box = boxes.get(i);
            if (num < Data.skillInfoList.size()) {
                box.name = Data.skillInfoList.get(num).name;
                box.expCurrent = Data.skillInfoList.get(num).expCurrent;
                box.expRequired = Data.skillInfoList.get(num).expRequired;
                box.level = Data.skillInfoList.get(num).levelCurrent;
                box.expOld = Data.skillInfoList.get(num).expOld;
            } else {
                box.name = "";
                box.expCurrent = 0;
                box.expRequired = 1;
                box.level = 0;
            }
            boxes.set(i, box);
        }

        for (ExpBox box : boxes) {
            box.draw();
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0: {
                if (page < Math.round(Data.skillInfoList.size() / 6)) {
                    page++;
                }
                break;
            }
            case 1: {
                if (page > 1) {
                    page--;
                }
                break;
            }
        }
    }
}
