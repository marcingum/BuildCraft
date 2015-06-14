package buildcraft.api.core;

import net.minecraft.util.EnumFacing;

import buildcraft.api.enums.EnumColor;
import buildcraft.api.enums.EnumEnergyStage;
import buildcraft.api.enums.EnumEngineType;
import buildcraft.api.enums.EnumFillerPattern;
import buildcraft.api.enums.EnumLaserTableType;
import buildcraft.api.enums.EnumMachineState;
import buildcraft.api.enums.EnumSpring;
import buildcraft.api.properties.BuildCraftProperty;

public final class BuildCraftProperties {
    public static final BuildCraftProperty<EnumFacing> BLOCK_FACING = BuildCraftProperty.create("facing", EnumFacing.Plane.HORIZONTAL.facings());
    public static final BuildCraftProperty<EnumFacing> BLOCK_FACING_6 = BuildCraftProperty.create("facing", EnumFacing.class);

    public static final BuildCraftProperty<EnumColor> BLOCK_COLOR = BuildCraftProperty.create("color", EnumColor.class);
    public static final BuildCraftProperty<EnumSpring> SPRING_TYPE = BuildCraftProperty.create("type", EnumSpring.class);
    public static final BuildCraftProperty<EnumEngineType> ENGINE_TYPE = BuildCraftProperty.create("type", EnumEngineType.class);
    public static final BuildCraftProperty<EnumLaserTableType> LASER_TABLE_TYPE = BuildCraftProperty.create("type", EnumLaserTableType.class);
    public static final BuildCraftProperty<EnumMachineState> MACHINE_STATE = BuildCraftProperty.create("state", EnumMachineState.class);
    public static final BuildCraftProperty<EnumEnergyStage> ENERGY_STAGE = BuildCraftProperty.create("stage", EnumEnergyStage.class);

    public static final BuildCraftProperty<Integer> PIPE_DATA = BuildCraftProperty.create("data", 0, 15);

    public static final BuildCraftProperty<Boolean> JOINED_BELOW = BuildCraftProperty.create("joined_below", false);
    public static final BuildCraftProperty<Boolean> MOVING = BuildCraftProperty.create("moving", false);
    public static final BuildCraftProperty<Boolean> LED_POWER = BuildCraftProperty.create("led_power", false);
    public static final BuildCraftProperty<Boolean> LED_ACTIVE = BuildCraftProperty.create("led_active",false);

    // Unlisted properties
    // public static final PropertyDouble FLUID_HEIGHT_NE = new PropertyDouble("height_ne", 0, 1);
    // public static final PropertyDouble FLUID_HEIGHT_NW = new PropertyDouble("height_nw", 0, 1);
    // public static final PropertyDouble FLUID_HEIGHT_SE = new PropertyDouble("height_se", 0, 1);
    // public static final PropertyDouble FLUID_HEIGHT_SW = new PropertyDouble("height_sw", 0, 1);
    // public static final PropertyDouble FLUID_FLOW_DIRECTION = new PropertyDouble("direction",
    // Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    public static final BuildCraftProperty<EnumFillerPattern> FILLER_PATTERN = BuildCraftProperty.create("pattern", EnumFillerPattern.class);

    // Block state setting flags -these are used by World.markAndNotifyBlock and World.setBlockState. These flags can be
    // added together to pass the additions
    public static final int UPDATE_NONE = 0;
    /** This updates the neighbouring blocks that the new block is set. It also updates the comparator output of this
     * block. */
    public static final int UPDATE_NEIGHBOURS = 1;
    /** This will mark the block for an update next tick, as well as send an update to the client (if this is a server
     * world). */
    public static final int MARK_BLOCK_FOR_UPDATE = 2;
    /** This will mark the block for an update, even if this is a client world. It is useless to use this if
     * world.isRemote returns false. */
    public static final int UPDATE_EVEN_CLIENT = 4 + MARK_BLOCK_FOR_UPDATE; // 6

    // Pre-added flags- pass these as-is to the World.markAndNotifyBlock and World.setBlockState methods.
    /** This will do what both {@link #UPDATE_NEIGHBOURS} and {@link #MARK_BLOCK_FOR_UPDATE} do. */
    public static final int MARK_THIS_AND_NEIGHBOURS = UPDATE_NEIGHBOURS + MARK_BLOCK_FOR_UPDATE;
    /** This will update everything about this block. */
    public static final int UPDATE_ALL = UPDATE_NEIGHBOURS + MARK_BLOCK_FOR_UPDATE + UPDATE_EVEN_CLIENT;

    /** Deactivate constructor */
    private BuildCraftProperties() {}
}