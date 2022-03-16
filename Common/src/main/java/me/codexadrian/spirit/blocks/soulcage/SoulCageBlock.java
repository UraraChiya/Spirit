package me.codexadrian.spirit.blocks.soulcage;

import me.codexadrian.spirit.Spirit;
import me.codexadrian.spirit.platform.Services;
import me.codexadrian.spirit.utils.SoulUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SoulCageBlock extends BaseEntityBlock {
    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 16, 16);

    public SoulCageBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return Services.REGISTRY.getSoulCageBlockEntity().create(blockPos, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, Services.REGISTRY.getSoulCageBlockEntity(), SoulCageBlockEntity::tick);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }

    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (interactionHand != InteractionHand.OFF_HAND) {
            ItemStack itemStack = player.getMainHandItem();
            final SoulCageBlockEntity soulSpawner = Services.REGISTRY.getSoulCageBlockEntity().getBlockEntity(level, blockPos);
            if (soulSpawner != null) {
                if (soulSpawner.isEmpty()) {
                    if (itemStack.getItem() == Services.REGISTRY.getSoulCrystal() && itemStack.hasTag()) {
                        if(SoulUtils.getTier(itemStack) != null) {
                            soulSpawner.setItem(0, itemStack.copy());
                            soulSpawner.setType();
                            if (level.isClientSide) soulSpawner.entity = null;
                            if (!player.getAbilities().instabuild) {
                                itemStack.shrink(1);
                            }
                            return InteractionResult.SUCCESS;
                        }
                    }
                } else if (player.isShiftKeyDown()) {
                    final ItemStack DivineCrystal = soulSpawner.removeItemNoUpdate(0);
                    soulSpawner.type = null;
                    if (level.isClientSide) soulSpawner.entity = null;
                    if (itemStack.isEmpty()) {
                        player.setItemInHand(interactionHand, DivineCrystal);
                    } else if (!player.addItem(DivineCrystal)) {
                        player.drop(DivineCrystal, false);
                    }
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Override
    public @NotNull List<ItemStack> getDrops(BlockState blockState, LootContext.Builder builder) {
        List<ItemStack> drops = super.getDrops(blockState, builder);
        BlockEntity blockE = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if(blockE instanceof SoulCageBlockEntity) {
            drops.add(((SoulCageBlockEntity) blockE).getItem(0));
        }
        return drops;
    }
}