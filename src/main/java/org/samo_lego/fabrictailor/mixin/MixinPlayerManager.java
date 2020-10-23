package org.samo_lego.fabrictailor.mixin;

import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.samo_lego.fabrictailor.TailoredPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.samo_lego.fabrictailor.command.SkinCommand.fetchSkinByName;

@Mixin(PlayerManager.class)
public abstract class MixinPlayerManager {
    @Inject(method = "onPlayerConnect(Lnet/minecraft/network/ClientConnection;Lnet/minecraft/server/network/ServerPlayerEntity;)V", at = @At("RETURN"))
    private void onPlayerConnect(ClientConnection clientConnection, ServerPlayerEntity player, CallbackInfo ci) {
        String value = ((TailoredPlayer) player).getSkinValue();
        String signature = ((TailoredPlayer) player).getSkinSignature();
        if(value != null && signature != null)
            ((TailoredPlayer) player).setSkin(value, signature);
        else
            // Trying to fetch skin by playername
            fetchSkinByName(player, player.getName().toString(), false);
    }
}