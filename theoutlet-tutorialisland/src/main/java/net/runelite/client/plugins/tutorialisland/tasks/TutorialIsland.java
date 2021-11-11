package net.runelite.client.plugins.tutorialisland.tasks;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ItemID;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.plugins.iutils.api.Spells;
import net.runelite.client.plugins.iutils.game.iNPC;
import net.runelite.client.plugins.iutils.scene.RectangularArea;
import net.runelite.client.plugins.tutorialisland.Task;
import net.runelite.client.plugins.tutorialisland.TutorialIslandPlugin;

import java.awt.*;
import java.util.Random;

@Slf4j
public class TutorialIsland extends Task {

    private boolean bankPinSet = false;

    @Override
    public boolean validate() {
        return questProgress() < 671;
    }

    @Override
    public void run() {

        Random random = new Random();
        while (questProgress() < 1000) {
            log.info("Doing quest step: " + questProgress());
            game.tick();
            switch (questProgress()) {

                /*
                    GIELINOR GUIDE
                */
                case 1: // enter username
                    TutorialIslandPlugin.status = "Creating username";

                    // first time entering a name
                    if (game.widget(558, 2) != null) {
                        if (game.widget(558, 12).text().equals("*")) {
                            log.info("NO NAME, LETS PUT ONE");

                            // make a random name
                            if (TutorialIslandPlugin.taskConfig.userName().equals("")) {
                                game.chooseString(randomUsername());


                            } else {
                                // user selected name
                                game.chooseString(TutorialIslandPlugin.taskConfig.userName());
                            }
                        } else if (game.widget(558, 13).text().toLowerCase().contains("try clicking one of our suggestions")) {
                            int selectName = random.nextInt((17 - 15) + 1) + 15;
                            log.info("Name not valid, selecting " + selectName);
                            game.widget(558, selectName).interact("Set name");
                        } else if (game.widget(558, 13).text().toLowerCase().contains("you may set this name now")) {
                            log.info("Name is valid, selecting it!");
                            game.widget(558, 19).interact("Set name");
                        }

                    }

                    // setting looks of character
                    if (game.widget(679, 2) != null) {
                        if (TutorialIslandPlugin.taskConfig.setFemale()) {
                            log.info("Setting as female");
                            game.widget(679, 66).interact("Female");
                        }
                        if (TutorialIslandPlugin.taskConfig.randomAppearance()) {
                            log.info("Customizing a random look");
                            // head
                            int randomHead = random.nextInt((23 - 1) + 1) + 1;
                            for (int i = 0; i < randomHead; ++i) {
                                game.widget(679, 13).interact("Select");
                            }
                            // jaw
                            if (!TutorialIslandPlugin.taskConfig.setFemale()) {
                                int randomJaw = random.nextInt((14 - 1) + 1) + 1;
                                for (int i = 0; i < randomJaw; ++i) {
                                    game.widget(679, 17).interact("Select");
                                }
                            }
                            // torso
                            int randomTorso = random.nextInt((13 - 1) + 1) + 1;
                            for (int i = 0; i < randomTorso; ++i) {
                                game.widget(679, 21).interact("Select");
                            }
                            // arms
                            int randomArms = random.nextInt((11 - 1) + 1) + 1;
                            for (int i = 0; i < randomArms; ++i) {
                                game.widget(679, 25).interact("Select");
                            }
                            // hands
                            int randomHands = random.nextInt((2 - 1) + 1) + 1;
                            for (int i = 0; i < randomHands; ++i) {
                                game.widget(679, 29).interact("Select");
                            }
                            // legs
                            int randomLegs = random.nextInt((10 - 1) + 1) + 1;
                            for (int i = 0; i < randomLegs; ++i) {
                                game.widget(679, 33).interact("Select");
                            }
                            // feet
                            int randomFeet = random.nextInt((5 - 1) + 1) + 1;
                            for (int i = 0; i < randomFeet; ++i) {
                                game.widget(679, 37).interact("Select");
                            }
                            // hair color
                            int randomHairColor = random.nextInt((23 - 1) + 1) + 1;
                            for (int i = 0; i < randomHairColor; ++i) {
                                game.widget(679, 44).interact("Select");
                            }
                            // torso color
                            int randomTorsoColor = random.nextInt((26 - 1) + 1) + 1;
                            for (int i = 0; i < randomTorsoColor; ++i) {
                                game.widget(679, 48).interact("Select");
                            }
                            // leg color
                            int randomLegColor = random.nextInt((26 - 1) + 1) + 1;
                            for (int i = 0; i < randomLegColor; ++i) {
                                game.widget(679, 52).interact("Select");
                            }
                            // feet color
                            int randomFeetColor = random.nextInt((3 - 1) + 1) + 1;
                            for (int i = 0; i < randomFeetColor; ++i) {
                                game.widget(679, 56).interact("Select");
                            }
                            // skin color
                            int randomSkinColor = random.nextInt((7 - 1) + 1) + 1;
                            for (int i = 0; i < randomSkinColor; ++i) {
                                game.widget(679, 60).interact("Select");
                            }
                        }
                        game.widget(679, 68).interact("Confirm");
                    }
                    break;
                case 2: // talk to gielinor guide
                    iNPC guide = game.npcs().withName("Gielinor Guide").nearest();
                    if (guide == null) {
                        return;
                    }

                    guide.interact("Talk-to");

                    if (chat)

                    chatOptionalNpc(GIELINOR_GUIDE, "Gielinor Guide", "I am an experienced player.");
                    break;
                case 3: // open settings tab
                    game.widget(164, 46).interact("Settings");
                    break;
                case 7: // talk to gielinor guide
                    chatNpc(GIELINOR_GUIDE, "Gielinor Guide");
                    break;
                case 10: // leave house
                    walking.walkTo(SURVIVAL_EXPERT);
                    break;

                /*
                    SURVIVAL GUIDE
                */
                case 20: // talk to survival guide
                    TutorialIslandPlugin.status = "Learning to survive";
                    chatNpc(SURVIVAL_EXPERT, "Survival Expert");
                    break;
                case 30: // continue dialog and open inventory
                    game.widget(164, 61).interact("Inventory");
                    break;
                case 40: // click fishing spot
                    interactNpc(SURVIVAL_EXPERT, "Fishing spot", "Net");
                    waitItem("Raw shrimps");
                    break;
                case 50: // open stats
                    game.widget(164, 59).interact("Skills");
                    break;
                case 60: // talk to guide
                    chatNpc(SURVIVAL_EXPERT, "Survival Expert");
                    break;
                case 70: // continue dialog, chop tree
                    interactObject(SURVIVAL_EXPERT, "Tree", "Chop down");
                    waitItem("Logs");
                    break;
                case 80: // light logs
                    useItemItem("Logs", "Tinderbox");
                    waitAnimationEnd(733);
                    break;
                case 90: // cook shrimp
                    useItemObject(SURVIVAL_EXPERT, "Raw shrimps", "Fire");
                    waitAnimationEnd(897);
                    break;
                case 120: // open gate
                    walking.walkTo(MASTER_CHEF);
                    break;

                /*
                    CHEF GUIDE
                */
                case 130: // enter chefs door
                    TutorialIslandPlugin.status = "Learning to bake";
                    break;
                case 140: // talk to chef
                    chatNpc(MASTER_CHEF, "Master Chef");
                    break;
                case 150: // use flour on water
                    useItemItem("Pot of flour", "Bucket of water");
                    break;
                case 160: // cook dough
                    useItemObject(MASTER_CHEF, "Bread Dough", "Range");
                    waitItem("Bread");
                    break;
                case 170: // exit chefs
                case 200: // enter quest house
                    walking.walkTo(QUEST_GUIDE);
                    break;

                /*
                    QUEST GUIDE
                */
                case 220: // talk to quest guide
                    TutorialIslandPlugin.status = "Learning about quests";
                    chatNpc(QUEST_GUIDE, "Quest Guide");
                    break;
                case 230: // continue dialog, open quest menu
                    game.widget(164,60).interact("Quest List");
                    break;
                case 240: // talk to quest guide
                    chatNpc(QUEST_GUIDE, "Quest Guide");
                    break;
                case 250: // go downstairs
                    walking.walkTo(MINING_INSTRUCTOR);
                    break;

                case 260: // talk to mining guide
                    TutorialIslandPlugin.status = "Learning how to mine";
                    chatNpc(MINING_INSTRUCTOR, "Mining Instructor");
                    break;

                /*
                    MINING GUIDE
                */
                case 300: // mine tin
                    interactObject(MINE_TIN, "Rocks", "Mine");
                    waitItem("Tin ore");
                    break;
                case 310: // mine copper
                    interactObject(MINE_COPPER, "Rocks", "Mine");
                    waitItem("Copper ore");
                    break;
                case 320: // use furnace
                    interactObject(MINING_INSTRUCTOR,"Furnace", "Use");
                    waitItem("Bronze bar");
                    break;
                case 330: // talk to mining guide
                    TutorialIslandPlugin.status = "Learning how to smith";
                    chatNpc(MINING_INSTRUCTOR, "Mining Instructor");
                    break;
                case 340: // use anvil
                    if (!game.localPlayer().isMoving()) {
                        interactObject(MINING_INSTRUCTOR, "Anvil", "Smith");
                    }
                    break;
                case 350: // make dagger
                    if (game.widget(WidgetInfo.SMITHING_ANVIL_DAGGER) != null) {
                        game.widget(WidgetInfo.SMITHING_ANVIL_DAGGER).interact("Smith");
                        waitItem("Bronze dagger");
                    } else {
                        if (!game.localPlayer().isMoving())
                        interactObject(MINING_INSTRUCTOR, "Anvil", "Smith");
                    }
                    break;
                case 360: // leave mining gate
                    walking.walkTo(COMBAT_INSTRUCTOR);
                    break;

                /*
                    COMBAT GUIDE
                */
                case 370: // talk to combat guide
                    TutorialIslandPlugin.status = "Learning how to fight for your life";
                    chatNpc(COMBAT_INSTRUCTOR, "Combat Instructor");
                    break;
                case 390: // open equipment tab
                    game.widget(164, 62).interact("Worn Equipment");
                    break;
                case 400: // view equipment stats
                    game.widget(387, 1).interact("View equipment stats");
                    break;
                case 405: // equip dagger
                    equip("Bronze dagger");
                    break;
                case 410: // talk to combat guide
                    chatNpc(COMBAT_INSTRUCTOR, "Combat Instructor");
                    break;
                case 420: // equip sword and shield
                    // equip sword
                    if (!equipment.isEquipped(ItemID.BRONZE_SWORD)) {
                        equip(ItemID.BRONZE_SWORD);
                    }

                    // equip shield
                    if (!equipment.isEquipped(ItemID.WOODEN_SHIELD)) {
                        equip(ItemID.WOODEN_SHIELD);
                    }
                    break;
                case 430: // open combat options
                    game.widget(164, 58).interact("Combat Options");
                    break;
                case 440: // go into rat pit
                    walking.walkTo(RAT_PIT);
                    break;
                case 450: // attack rat
                case 460: // kill rat
                    if (!inCombat()){
                        interactNpc(RAT_PIT, "Giant rat", "Attack");
                        return;
                    }
                    break;
                case 470: // talk to combat guide
                    TutorialIslandPlugin.status = "Learning how to fight from a distance";
                    walking.walkTo(COMBAT_INSTRUCTOR);
                    chatNpc(COMBAT_INSTRUCTOR, "Combat Instructor");
                    break;
                case 480: // equip bow and arrows and attack rat
                case 490: // wait til rat is dead

                    // equip bow
                    if (!equipment.isEquipped(ItemID.SHORTBOW)) {
                        equip("Shortbow");
                    }

                    // equip arrows
                    if (!equipment.isEquipped(ItemID.BRONZE_ARROW)) {
                        equip("Bronze arrow");
                    }

                    // go to ranged spot and kill rat
                    if (!inCombat()) {
                        interactNpc(RANGED_RAT_SPOT, "Giant rat", "Attack");
                    }
                    return;
                case 500: // exit dungeon
                    walking.walkTo(BANK_AREA);
                    break;

                /*
                    BANKING
                */
                case 510: // open bank
                    TutorialIslandPlugin.status = "Checkin' out the banks";
                    interactObject(BANK_AREA, "Bank booth", "Use");
                    break;
                case 520: // open poll booth
                    TutorialIslandPlugin.status = "Checkin' out polls";
                    interactObject(BANK_AREA, "Poll booth", "Use");
                    chatbox.continueChats();
                    break;
                case 525: // exit banking area
                    walking.walkTo(ACCOUNT_GUIDE);
                    break;

                /*
                    ACCOUNT MANAGER
                */
                case 530: // talk to account guide
                    TutorialIslandPlugin.status = "Learning more about yourself";
                    chatNpc(ACCOUNT_GUIDE, "Account Guide");
                    break;
                case 531: // open account management tab
                    game.widget(164,44).interact("Account Management");
                    break;
                case 532: // talk to account guide
                    chatNpc(ACCOUNT_GUIDE, "Account Guide");
                    break;
                case 540: // leave account guide area
                    walking.walkTo(BROTHER_BRACE);
                    break;

                /*
                    PRAYER GUIDE
                */
                case 550: // talk to prayer guide
                    TutorialIslandPlugin.status = "Learning how to pray. Amen.";
                    chatNpc(BROTHER_BRACE, "Brother Brace");
                    break;
                case 560: // open prayer tab
                    game.widget(164,63).interact("Prayer");
                    break;
                case 570: // talk to prayer guide
                    TutorialIslandPlugin.status = "Learning how to make friends";
                    chatNpc(BROTHER_BRACE, "Brother Brace");
                    break;
                case 580: // open friends chat
                    game.widget(164,45).interact("Friends List");
                    break;
                case 600: // talk to prayer guide
                    chatNpc(BROTHER_BRACE, "Brother Brace");
                    break;
                case 610: // leave prayer area
                    if (TutorialIslandPlugin.taskConfig.gameMode() == TutorialIslandPlugin.GameMode.REGULAR) {
                        walking.walkTo(MAGIC_INSTRUCTOR);
                    } else {
                        walking.walkTo(IRONMAN_TUTOR);
                    }
                    break;

                /*
                    MAGIC GUIDE
                */
                case 620: // talk to magic guide

                    if (game.widget(WidgetInfo.BANK_PIN_CONTAINER) != null) {
                        log.info("bank pin time");
                        String pin = TutorialIslandPlugin.taskConfig.bankPin();

                        for (char c : pin.toCharArray()) {
                            game.chooseString(String.valueOf(c));
                            game.tick(1);
                        }
                        return;
                    }

                    // handle bankpin if we are setting up an ironman account
                    if (chatbox != null && chatbox.getOptions().contains("Okay, let me set a PIN.")) {
                        log.info("Selecting set pin option");
                        chatbox.chooseOption("Okay, let me set a PIN.");
                    } else if (game.widget(WidgetInfo.DIALOG_NOTIFICATION_CONTINUE) != null && game.widget(WidgetInfo.DIALOG_NOTIFICATION_CONTINUE).text().contains("mode has been updated")) {
                        bankPinSet = true;
                        log.info("Bankpin has been set");
                    }

                    if (TutorialIslandPlugin.taskConfig.gameMode() != TutorialIslandPlugin.GameMode.REGULAR && !bankPinSet) {

                        // chat bot says we are ironmen now
                        log.info("Ironman mode selected");

                        if (game.widget(215, 6) != null && !game.widget(215, 6).hidden()) {
                            log.info("Ironman options screen is open");
                            if (TutorialIslandPlugin.taskConfig.gameMode() == TutorialIslandPlugin.GameMode.IRONMAN) {
                                log.info("Selecting Ironman");
                                if (!game.localPlayer().female()) {
                                    game.widget(215, 11).interact("Standard Iron Man");
                                } else {
                                    game.widget(215, 11).interact("Standard Iron Woman");
                                }

                            } else if (TutorialIslandPlugin.taskConfig.gameMode() == TutorialIslandPlugin.GameMode.HARDCORE_IRONMAN) {
                                log.info("Selecting Hardcore");
                                if (!game.localPlayer().female()) {
                                    game.widget(215, 12).interact("Hardcore Iron Man");
                                } else {
                                    game.widget(215, 12).interact("Hardcore Iron Woman");
                                }

                            } else if (TutorialIslandPlugin.taskConfig.gameMode() == TutorialIslandPlugin.GameMode.ULTIMATE_IRONMAN) {
                                log.info("Selecting Ultimate");
                                if (!game.localPlayer().female()) {
                                    game.widget(215, 13).interact("Ultimate Iron Man");
                                } else {
                                    game.widget(215, 13).interact("Ultimate Iron Woman");
                                }
                            }
                            game.tick(1);
                            chatbox.chooseOption("Okay, let me set a PIN.");

                        } else if (!game.localPlayer().position().inside(IRONMAN_TUTOR)) {
                            log.info("Running to ironman");
                            walking.walkTo(IRONMAN_TUTOR);

                        } else {
                            log.info("Talking to ironman tutor");
                            log.info("We are female: " + game.localPlayer().female());
                            if (!game.localPlayer().female()) {
                               chatOptionalNpc(IRONMAN_TUTOR, "Iron Man tutor", "Tell me about Iron Men.", "I'd like to change my Iron Man mode.");
                            } else {
                                chatOptionalNpc(IRONMAN_TUTOR, "Iron Man tutor", "Tell me about Iron Women.", "I'd like to change my Iron Woman mode.");
                            }
                        }
                    } else {
                        TutorialIslandPlugin.status = "Learning about magic";
                        chatNpc(MAGIC_INSTRUCTOR, "Magic Instructor");
                    }

                    break;

                case 630: // open magic tab

                    TutorialIslandPlugin.status = "Opening Magic Tab";
                    game.widget(164,64).interact("Magic");
                    break;

                case 640: // talk to magic guide
                    TutorialIslandPlugin.status = "Learning more about magic";
                    chatNpc(MAGIC_INSTRUCTOR, "Magic Instructor");
                    break;

                case 650: // cast wind strike on chicken
                    TutorialIslandPlugin.status = "Killing Chicken";

                    if (game.localPlayer().position().inside(MAGIC_INSTRUCTOR)) {
                        log.info("Kill Chicken");
//                        game.widget(WidgetInfo.SPELL_WIND_STRIKE).useOn(game.npcs().withName("Chicken").nearest());
                        castSpellNpc("Chicken", Spells.WIND_STRIKE);
                    } else {
                        log.info("Walking to Magic Instructor Area");
                        walking.walkTo(MAGIC_INSTRUCTOR);
                    }
                    break;

                case 670: // talk to magic guide

                    var account_type = game.getFromClientThread(() -> game.client().getAccountType());
                    log.info("Account type: " + account_type);

                    // Regular account
                    if (!account_type.isIronman()) {
                        chatOptionalNpc(MAGIC_INSTRUCTOR, "Magic Instructor", "Yes.", "No, I'm not planning to do that.");
                    // Ironman account
                    } else {
                        chatOptionalNpc(MAGIC_INSTRUCTOR, "Magic Instructor", "Yes.");
                        }
                    }
                    break;
            }
        }




    private String randomUsername() {
        // TODO: Create a random name function
        return "Zezima";
    }

    private int questProgress() {
        return game.varp(281);
    }
}
