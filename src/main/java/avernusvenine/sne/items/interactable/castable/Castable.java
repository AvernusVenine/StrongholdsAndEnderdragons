package avernusvenine.sne.items.interactable.castable;

import avernusvenine.sne.*;
import avernusvenine.sne.Globals.ActionType;
import avernusvenine.sne.gui.DefaultGUI;
import avernusvenine.sne.gui.utility.SpellSelectGUI;
import avernusvenine.sne.items.SneItem;
import avernusvenine.sne.items.interactable.Interactable;
import avernusvenine.sne.spells.Spell;
import avernusvenine.sne.spells.targeted.TargetedSpell;
import avernusvenine.sne.status.Casting;
import de.tr7zw.changeme.nbtapi.NBTItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class Castable extends Interactable {

    private final static TextComponent INVALID_MANA_RESPONSE = Component.text("You do not have enough mana!");
    private final static TextComponent INVALID_SPELL_LEVEL_RESPONSE = Component.text("You are not high enough level to cast this!");
    private final static TextComponent INVALID_SPELL_CLASS_RESPONSE = Component.text("Your class cannot cast this!");

    protected boolean swappable = true;
    protected List<String> validSpells = new ArrayList<>();


    public static ItemStack generateCastableItem(Material material, int amount, TextComponent displayName, List<TextComponent> lore,
                                            List<ItemFlag> itemFlags, boolean unbreakable, String id){

        ItemStack item = generateItem(material, amount, displayName, lore, itemFlags, unbreakable, id);

        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setString(NBTFlags.spellID, "fireball");

        return nbtItem.getItem();
    }

    public void startCast(Player player, Spell spell){
        player.setCooldown(spell.getMaterial(), 20);
        PlayerDictionary.get(player).addStatusEffect(new Casting(player, spell));
    }

    public void openSpellSelectGUI(Player player, ItemStack item){
        Globals.openGUI(player, new SpellSelectGUI(player, item));
    }

    @Override
    public void onItemUse(Player player, Entity entity, ActionType type, Event event){

        if((type == ActionType.PLAYER_SHIFT_RIGHT_CLICK_ENTITY || type == ActionType.PLAYER_SHIFT_RIGHT_CLICK_AIR ||
                type == ActionType.PLAYER_SHIFT_LEFT_CLICK_BLOCK) && swappable){
            openSpellSelectGUI(player, item);
            return;
        }

        if(!(type == ActionType.PLAYER_RIGHT_CLICK_ENTITY || type == ActionType.PLAYER_RIGHT_CLICK_AIR ||
                type == ActionType.PLAYER_RIGHT_CLICK_BLOCK))
            return;

        if(!denyIfInvalid(player))
            return;

        ItemStack item = player.getInventory().getItemInMainHand();
        NBTItem nbtItem = new NBTItem(item);

        Spell spell = SpellDictionary.get(nbtItem.getString(NBTFlags.spellID));

        if(spell == null)
            return;

        // Check to see if the player can even use the spell right now
        if(PlayerDictionary.get(player).checkCooldown(nbtItem.getString(NBTFlags.spellID)))
            return;
        if(!spell.validClass(PlayerDictionary.get(player).getPlayerCharacter().getClassType())){
            player.sendMessage(INVALID_SPELL_CLASS_RESPONSE);
            return;
        }
        if(PlayerDictionary.get(player).getPlayerCharacter().getLevel() < spell.getLevelRequired()){
            player.sendMessage(INVALID_SPELL_LEVEL_RESPONSE);
            return;
        }
        if(!PlayerDictionary.get(player).hasEnoughResource(spell.getResourceCost())){
            player.sendMessage(INVALID_MANA_RESPONSE);
            return;
        }
        if(player.getCooldown(spell.getMaterial()) != 0){
            return;
        }

        // Checks if the spell is targeted and if it has a valid target
        if(spell instanceof TargetedSpell targeted) {
            if (!(targeted.getEntityTarget(player) instanceof LivingEntity) && targeted.getBlockTarget(player) == null)
                return;
        }

        // Cancels casting from the player if they are already casting
        if(PlayerDictionary.get(player).hasStatusEffect(Casting.class))
            PlayerDictionary.get(player).removeStatusEffect(Casting.class);

        PlayerDictionary.get(player).addCooldown(spell.getID(), spell.getCooldown());
        startCast(player, spell);
    }

    public static void setSpell(String id, ItemStack item){
        List<TextComponent> lore = new ArrayList<>();

        NBTItem nbtItem = new NBTItem(item);
        nbtItem.setString(NBTFlags.spellID, id);

        lore.add(Component.text(id));
        Interactable interactable = (Interactable) ItemDictionary.get(nbtItem.getString(NBTFlags.itemID));

        Spell spell = SpellDictionary.get(id);

        if(interactable.overrideItemModel()){
            nbtItem.setInteger(NBTFlags.customModelID, spell.getCustomModel());
            nbtItem.setInteger(NBTFlags.cooldownModelID, spell.getCooldownModel());
        }

        item.setItemMeta(nbtItem.getItem().getItemMeta());
        item.lore(lore);

        if(interactable.overrideItemModel()) {
            item.setType(SpellDictionary.get(id).getMaterial());
            SneItem.setCustomModel(item, spell.getCustomModel());
        }
    }

    public List<String> getValidSpells(){
        return validSpells;
    }

}
