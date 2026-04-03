package net.larson.larsonsmod.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.List;

public class GemPolishingRecipe implements Recipe<SingleStackRecipeInput> {
    private final ItemStack output;
    private final List<Ingredient> recipeItems;

    public GemPolishingRecipe(List<Ingredient> ingredients, ItemStack itemStack) {
        this.output = itemStack;
        this.recipeItems = ingredients;
    }

    @Override
    public boolean matches(SingleStackRecipeInput input, World world) {
        if(world.isClient()) {
            return false;
        }

        return recipeItems.get(0).test(input.getStackInSlot(0));
    }

    @Override
    public ItemStack craft(SingleStackRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return output.copy();
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return output;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.ofSize(this.recipeItems.size());
        list.addAll(recipeItems);
        return list;
    }

    @Override
    public RecipeSerializer<GemPolishingRecipe> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<GemPolishingRecipe> getType() {
        return Type.INSTANCE;
    }

    @Override
    public RecipeBookCategory getRecipeBookCategory() {
        return RecipeBookCategories.MISC;
    }

    public static class Type implements RecipeType<GemPolishingRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "gem_polishing";
    }

    public static class Serializer implements RecipeSerializer<GemPolishingRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "gem_polishing";

        public static final MapCodec<GemPolishingRecipe> CODEC = RecordCodecBuilder.mapCodec(in -> in.group(
                validateAmount(Ingredient.CODEC, 9).fieldOf("ingredients").forGetter(GemPolishingRecipe::getIngredients),
                ItemStack.VALIDATED_CODEC.fieldOf("output").forGetter(r -> r.output)
        ).apply(in, GemPolishingRecipe::new));

        public static final PacketCodec<RegistryByteBuf, GemPolishingRecipe> PACKET_CODEC =
                PacketCodec.ofStatic(Serializer::write, Serializer::read);

        private static Codec<List<Ingredient>> validateAmount(Codec<Ingredient> delegate, int max) {
            return delegate.listOf().flatXmap(list -> {
                if (list.isEmpty()) return DataResult.error(() -> "Recipe has no ingredients!");
                if (list.size() > max) return DataResult.error(() -> "Recipe has too many ingredients!");
                return DataResult.success(list);
            }, DataResult::success);
        }

        @Override
        public MapCodec<GemPolishingRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, GemPolishingRecipe> packetCodec() {
            return PACKET_CODEC;
        }

        private static GemPolishingRecipe read(RegistryByteBuf buf) {
            int size = buf.readVarInt();
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(size);

            for(int i = 0; i < size; i++) {
                inputs.add(Ingredient.PACKET_CODEC.decode(buf));
            }

            ItemStack output = ItemStack.PACKET_CODEC.decode(buf);
            return new GemPolishingRecipe(inputs, output);
        }

        private static void write(RegistryByteBuf buf, GemPolishingRecipe recipe) {
            buf.writeVarInt(recipe.getIngredients().size());

            for (Ingredient ingredient : recipe.getIngredients()) {
                Ingredient.PACKET_CODEC.encode(buf, ingredient);
            }

            ItemStack.PACKET_CODEC.encode(buf, recipe.getResult(null));
        }
    }
}
