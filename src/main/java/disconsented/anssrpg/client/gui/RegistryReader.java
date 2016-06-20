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
package disconsented.anssrpg.client.gui;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityList;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by j on 3/09/2015.
 */
public class RegistryReader {
    private JPanel panelRegistryReader;
    private JTabbedPane tabbedPane1;
    private JList listItem;
    private JList listEntity;
    private JList listBlock;
    private JTextField textFieldSearch;
    private JSplitPane JSplit;
    private JList listPotion;
    private final JFrame frame;
    private final ArrayList<String> rawItems = new ArrayList<>();
    private final ArrayList<String> rawBlocks = new ArrayList<>();
    private final ArrayList<String> rawEntities = new ArrayList<>();
    private final ArrayList<String> rawPotions = new ArrayList<>();
    private static RegistryReader instance;

    private RegistryReader() {
        this.frame = new JFrame("Minecraft Registry Reader");
        this.frame.setContentPane(this.panelRegistryReader);
        this.frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.frame.pack();
        this.frame.setVisible(true);

        this.textFieldSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                RegistryReader.this.updateDisplayedLists();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                RegistryReader.this.updateDisplayedLists();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        Item.REGISTRY.getKeys().forEach(resourceLocation -> rawItems.add(resourceLocation.toString()));
        this.listItem.setListData(this.rawItems.toArray());

        EntityList.NAME_TO_CLASS.keySet().forEach(s -> { rawEntities.add(s); });
        this.listEntity.setListData(this.rawEntities.toArray());

        Block.REGISTRY.getKeys().forEach(resourceLocation -> rawBlocks.add(resourceLocation.toString()));
        this.listBlock.setListData(this.rawBlocks.toArray());

        Potion.REGISTRY.getKeys().forEach(resourceLocation -> rawPotions.add(resourceLocation.toString()));
        this.listPotion.setListData(this.rawPotions.toArray());


    }

    public static RegistryReader getInstance() {
        if (RegistryReader.instance == null) {
            RegistryReader.instance = new RegistryReader();
        }
        return RegistryReader.instance;
    }

    public void toggleShow() {
        this.frame.setVisible(!this.frame.isShowing());
    }

    public void show() {
        this.frame.setVisible(true);
    }

    private void updateDisplayedLists() {
        String query = this.textFieldSearch.getText();
        Pattern p = Pattern.compile(query);
        if (p == null)
            return;

        ArrayList<String> filteredItems = new ArrayList<>();
        this.filter(this.rawItems, p, filteredItems);
        this.listItem.setListData(filteredItems.toArray());

        ArrayList<String> filteredEntities = new ArrayList<>();
        this.filter(this.rawEntities, p, filteredEntities);
        this.listEntity.setListData(filteredEntities.toArray());

        ArrayList<String> filteredBlocks = new ArrayList<>();
        this.filter(this.rawBlocks, p, filteredBlocks);
        this.listBlock.setListData(filteredBlocks.toArray());

        ArrayList<String> filteredPotions = new ArrayList<>();
        this.filter(this.rawPotions, p, filteredBlocks);
        this.listPotion.setListData(filteredPotions.toArray());
    }

    /**
     * Generic method for filtering items from one list into another
     *
     * @param source      The source list to sort through.
     * @param pattern     The pattern to match against.
     * @param destination The destination list for those matched.
     */
    private void filter(ArrayList<String> source, Pattern pattern, ArrayList<String> destination) {
        for (String item : source) {
            if (pattern.matcher(item).find()) {
                destination.add(item);
            }
        }
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panelRegistryReader = new JPanel();
        panelRegistryReader.setLayout(new BorderLayout(0, 0));
        panelRegistryReader.setMinimumSize(new Dimension(600, 800));
        panelRegistryReader.setPreferredSize(new Dimension(600, 800));
        JSplit = new JSplitPane();
        JSplit.setContinuousLayout(false);
        JSplit.setDividerLocation(700);
        JSplit.setDividerSize(0);
        JSplit.setOrientation(0);
        panelRegistryReader.add(JSplit, BorderLayout.CENTER);
        tabbedPane1 = new JTabbedPane();
        JSplit.setLeftComponent(tabbedPane1);
        final JScrollPane scrollPane1 = new JScrollPane();
        tabbedPane1.addTab("Block", scrollPane1);
        listBlock = new JList();
        scrollPane1.setViewportView(listBlock);
        final JScrollPane scrollPane2 = new JScrollPane();
        tabbedPane1.addTab("Entity", scrollPane2);
        listEntity = new JList();
        scrollPane2.setViewportView(listEntity);
        final JScrollPane scrollPane3 = new JScrollPane();
        tabbedPane1.addTab("Item", scrollPane3);
        listItem = new JList();
        scrollPane3.setViewportView(listItem);
        textFieldSearch = new JTextField();
        JSplit.setRightComponent(textFieldSearch);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelRegistryReader;
    }
}
