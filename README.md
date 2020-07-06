# Game-tracker v2.8.4b
This will be a modified version of the DnD initiative tracker that I put on Haxme. It will include tracking for additional RPGs and other games such as MTG

----------------
Changelog:

2.8.4b: Added a python program to fix loot tables which have newline characters between quotations. Tables formatted this way are improperly read by the Loot Table module. The program is in src >> lootTableDnD >> tableFix.py

2.8.4a: Added borders and insets to the Initiative Tracker to improve the GUI.

2.8.4: Made significant changes to the way that the ComboPopup.java GUI is programmed. It looks far more professional now. Everything is properly spaced with nice borders, and the create panel will be nicely centered and spaced when it pops up. Also added a "Pack" button to the menu bar so that if the window is resized, the user may then pack it back to the preferred size at will.

2.7.4: Removed the Dice.java class. Instead, ComboPopup.java will be the entry point for the Dice module. It was inconvenient to have to press the "My Combos" button in order to get to the combo list, since that's the main thing that people will be using the module for. I've added the 6 common dice to the JMenuBar, so they can still be easily used. Also implemented the "Cancel" button when people are trying to add a new combo.

2.6.4: Removed the CreatePopup.java class. Instead, when users press the "Add" or "Edit" buttons on the ComboPopup Frame it calls a method to add a new JPanel and 5 buttons below the main components that start in the frame. This new set of components allow users to create or edit their combo. Upon pressing the "Accept" or "Cancel" buttons amongst the new components, it will remove all of the new components and take the appropriate action for the combo database. This is not yet fully implemented. Currently only the "Add" button functions. None of the new components function, nor do the original "Edit", "Remove", or "Roll" buttons.

2.5.4: Fixed an error in the MetaCombo class with the getCombos() method. Also fixed a NullPointerException issue in the Initiative tracker that would trigger if someone pressed "cancel" on the name popup.

2.4.4: Fixed the NullPointerException in ComboPopup as well as many previously unknown errors. Added a JMenuBar and the ability to load a database *after* clicking "My Combos". There is currently no way to create a combo database. 

2.3.4: Mostly made changes to the Combo, MetaCombo, and ComboPopup classes. Removed the Chain subclass in Combo.java and just added a global Combo object that allows them to serve as linked lists. Having a subclass that did nothing other than hold such an object was pointless. Much of ComboPopup.java has been added, but there is an issue with the File Handling section that causes a NullPointerException.

2.2.4: Added in documentation for MTGLifePointTracker.java.

2.2.3: Fixed the Dice module front GUI to look nicer. Implemented all 6 common dice buttons using SecureRandom rather than Math.random so that DMs should be more comfortable using it. These random numbers are cryptographically secure and non-deterministic. Also added a JMenuItem to generate a .txt file with 1 million d20 rolls, and a Matlab program to graph their distribution. The Matlab program as well as a sample Dist.txt file and screenshot of the distribution have been included in the Dist directory. Also made changes to the Combo.java and MetaCombo.java classes. MetaCombos will hold an ArrayList of Combo Objects, and now Chain objects extend Combos so that these ArrayLists may also hold Linked Lists of Combos for ogranization/grouping.

2.1.3: Fixed issue where MTGLifeTracker mainFrame was crudely updated, causing the frame to move when a player has taken damage or healed. Before this patch, information in mainFrame was updated by disposing of the panel and regenerating it. Now, each Player object has a JLabel that will be updated. Also, MTGLifePointTracker's setDefaultCloseOperation changed to take dispose argument rather than exit argument.

2.1.2: Changed file extentions in initiativeDnD from having java files with ".Java" to ".java". Added package declaration to MTGLifeTracker. Moved position of setVisible in MTGLifeTracker. Removed setDefaultLookAndFeelDecorated in MTGLifeTracker, eliminating a glitch in the UI changing upon doing damage to player or healing player. Still more glitches to be fixed.

2.1.1: Added the MTG module with some minor glitches.

2.1: Started work on a Dice Manager module. *Nothing is implemented yet*. This will allow people to make databases with custom saved dice combinations (such as 2d6+2) that will automatically do the roll for you, as well as graph the probability distribution for that combo. Ideally, this module will also allow you to group combos together for complex actions, and allow naming each combo within the meta-combo (eg. "hit" = 1d20+4, "dmg" = 2d6+2)

2.0: Finished loot module. Fixed the split issue so that everything displays in the correct field. Changed the Popup.java class so that it only displays the fields that are necessary. Added the "about" JMenuItem description. Finished both myRolls and randomRolls for both special and generic rules. Changed default font in Popup class. Also fixed an issue in the initiative tracker where users could enter non-Integer initiative values when prompted to reroll due to a tie.

1.5: Added Popup class that serves as a custom display popup for your loot. Currently, it does not properly display things in the correct fields because of a minor issue with the split done on the input string. Also uploaded a sample loot table (note: some lines in the file are defective for some reason, so results are not 100% accurate).

1.4: The GUI class now uses your rolls by default when you press the enter key. It also checks to make sure that all 6 fields are not only integers, but that they are in the proper range. The Loot class now properly displays the correct kind and number of coins in addition to loot.

1.3b: To the initiative tracker: Added the Window JMenu, and the Text Size and Text Style options within it. Changed the default text area size, and changed the window size to pack. This allows automatic window scaling when changing text size.

1.3a: Added save & load options to the initiative tracker. DMs may now save in the middle of an encounter (at the top of initiative) and load it back up later. DMs may also add all mobs to a planned future encounter ahead of time to be loaded when the players get to it. Added scrollbar to the initiative text field for large encounters. Minor optimizations and changes were also made in the loot table package.

1.3: The special rules portion of the loot table package is nearly complete and is useable in its current state. Random loot has not been implemented. The GUI is nearly finished for both rulesets. A new class file may be added in the future for a custom pop-up with loot descriptions because JOptionPane message dialogs do not text-wrap and the current result is hideous. 

1.2: Added most of the GUI for the loot module

1.1: Added the loot table package and set up the skeleton for that module

1.0: Moved the initiative tracker to its own package and made the main class into a menu to select different modules

0.9: Original version by Freak posted on Haxme that only included the initiative tracker

# Wiki Grabber v1.0
This will pull the stats for every single 5e monster on dandwiki (https://www.dandwiki.com/wiki/5e_Monsters) and puts the information into individual .csv files for a comprehensive database.

Model .csv file is uploaded as 0000.csv The links in the table are just notes for certain entries that have those characteristics.

We do not own anything on dandwiki. dandwiki should not contain any copyrighted material, and therefore no database items should be property of Wizards of the Coast LLC or any other entity.
This program is compliant with dandwiki's terms of service for non-human visitors https://www.dandwiki.com/clypeate.php. No part of this program stores or collects email addresses from the site in any way.

Any resulting database created by this program will be licensed under the GNU Free Documentation License v1.3 as is dandwiki itself. https://www.gnu.org/licenses/fdl-1.3.en.html

----------------
Changelog:

1.0: All issues should be resolved. All relevant entries for all monsters that have been checked show up correctly within database items. List abilities are still not included, but that is more of a quality of life issue than a functionality issue; it will likely be solved in a future update.

0.7: Flavor text should no longer be added to database entries. Non-italicized entries on pages formatted like "Adrammelech, the Wroth" will now be properly counted. Commas removed from all entries to make integration with the Game Tracker easier. Added a 2 second sleep between loops so that I hopefully don't annoy dandwiki too much. Non-italicized entries on pages formatted like "AI Housing" do not work, but should soon. Abilities that contain lists such as with "Abominable Yeti Mage" and its spellcasting are not properly grabbed.

0.6: Rows 1-11 should work for all entries. Rows 12-15 should work for most entries, but inconsistent formatting on dandwiki makes some entries erroniously ignored, and some erroniously included. Minor changes should fix this (e.g. adding another while loop after line 77 without italics, AND adding another elsif in the body of the abilities foreach loop to check if entries are preceded by a picture indicating that they're flavor text)

0.5: Rows 1-8 should now all work, although lines 5 and 6 *might* not work for some unknown entries in cases where they are irregularly formatted (e.g. "vulnerabilities" shortened to "vulns"). This is complicated due to the fact that entries typically link to "/wiki/5e_SRD:Damage_Resistance_and_Vulnerability" prior to these items, therefore if you merely check for "vuln" or "resist" in the regex, then the program may put the same damage type as both a resistance *and* vulnerability. As stated, this is currently not an issue, but issues may arise if some entries are irregularly formatted (which is unconfirmed).

0.4: Issues with row 3 and 1 should be solved. Edited some of the other rows so they should be more robust. Rows 1-4 should work.

0.3: Row 4 should now work for all entries.  
For entries such as Adam, row 3 is known NOT to work.  
For entries such as Acro-Bandit, row 1 is known NOT to work.  
For entries such as Sea Serpent, all rows are working.  

0.2: Row 3 should now work for all entries. *(WRONG)

0.1: Currently the program can properly fill out the first 2 rows of the database design. Working on row 3.
