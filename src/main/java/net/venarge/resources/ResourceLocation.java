package net.venarge.resources;

import net.venarge.brigadier.exeptions.SimpleCommandExceptionType;
import net.venarge.network.chat.TranslatableComponent;
import net.venarge.serialization.Codec;

public class ResourceLocation implements Comparable<ResourceLocation>{
    public static final Codec<ResourceLocation> CODEC = Codec.STRING.comapFlatMap (ResourceLocation::read, ResourceLocation::toString).stable ();

    private static final net.venarge.brigadier.exeptions.SimpleCommandExceptionType ERROR_INVALID = new SimpleCommandExceptionType (new TranslatableComponent ("argument.id.invalid"))
}
