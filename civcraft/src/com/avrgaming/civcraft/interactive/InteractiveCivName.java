/*************************************************************************
 * 
 * AVRGAMING LLC
 * __________________
 * 
 *  [2013] AVRGAMING LLC
 *  All Rights Reserved.
 * 
 * NOTICE:  All information contained herein is, and remains
 * the property of AVRGAMING LLC and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to AVRGAMING LLC
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from AVRGAMING LLC.
 */
package com.avrgaming.civcraft.interactive;

import com.avrgaming.civcraft.config.CivSettings;
import com.avrgaming.civcraft.exception.CivException;
import com.avrgaming.civcraft.main.CivGlobal;
import com.avrgaming.civcraft.main.CivMessage;
import com.avrgaming.civcraft.object.Resident;
import com.avrgaming.civcraft.util.CivColor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class InteractiveCivName implements InteractiveResponse {

	@Override
	public void respond(String message, Resident resident) {
		Player player;
		try {
			player = CivGlobal.getPlayer(resident);
		} catch (CivException e) {
			return;
		}

		if (message.equalsIgnoreCase("cancel")) {
			CivMessage.send(player, CivSettings.localize.localizedString("interactive_civ_cancel"));
			resident.clearInteractiveMode();
			return;
		}

		if (!InteractiveCampName.valid(message)) {
			CivMessage.send(player, CivColor.Rose+ChatColor.BOLD+CivSettings.localize.localizedString("interactive_civ_invalid"));
			return;
		}
	
		message = message.replace(" ", "_");
		message = message.replace("\"", "");
		message = message.replace("\'", "");
		
		resident.desiredCivName = message;
		CivMessage.send(player, CivColor.LightGreen+CivSettings.localize.localizedString("var_interactive_civ_success1",CivColor.Yellow+message+CivColor.LightGreen));
		CivMessage.send(player, " ");
		CivMessage.send(player, CivColor.LightGreen+ChatColor.BOLD+CivSettings.localize.localizedString("interactive_civ_success3"));
		CivMessage.send(player, CivColor.LightGray+CivSettings.localize.localizedString("interactive_civ_tocancel"));
		resident.setInteractiveMode(new InteractiveCapitolName());

		return;
		
	}

}
