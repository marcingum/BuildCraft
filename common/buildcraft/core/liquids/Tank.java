/*
 * Copyright (c) SpaceToad, 2011-2012
 * http://www.mod-buildcraft.com
 * 
 * BuildCraft is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package buildcraft.core.liquids;

import buildcraft.core.gui.tooltips.ToolTip;
import buildcraft.core.gui.tooltips.ToolTipLine;
import java.util.Locale;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;

/**
 *
 * @author CovertJaguar <http://www.railcraft.info/>
 */
public class Tank extends FluidTank {

	private final String name;

	public Tank(String name, int capacity) {
		super(capacity);
		this.name = name;
	}

	public boolean isEmpty() {
		return getFluid() == null || getFluid().amount <= 0;
	}

	public boolean isFull() {
		return getFluid() != null && getFluid().amount >= getCapacity();
	}

	public Fluid getFluidType() {
		return getFluid() != null ? getFluid().getFluid() : null;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		NBTTagCompound tankData = new NBTTagCompound();
		super.writeToNBT(tankData);
		nbt.setCompoundTag(name, tankData);
		return nbt;
	}

	@Override
	public FluidTank readFromNBT(NBTTagCompound nbt) {
		if (nbt.hasKey(name)) {
			NBTTagCompound tankData = nbt.getCompoundTag(name);
			super.readFromNBT(tankData);
		}
		return this;
	}

	public ToolTip getToolTip() {
		return toolTip;
	}

	protected void refreshTooltip() {
		toolTip.clear();
		int amount = 0;
		if (getFluid() != null && getFluid().amount > 0) {
			ToolTipLine fluidName = new ToolTipLine(getFluid().getFluid().getLocalizedName());
			fluidName.setSpacing(2);
			toolTip.add(fluidName);
			amount = getFluid().amount;
		}
		toolTip.add(new ToolTipLine(String.format(Locale.ENGLISH, "%,d / %,d", amount, getCapacity())));
	}
	protected final ToolTip toolTip = new ToolTip() {
		@Override
		public void refresh() {
			refreshTooltip();
		}
	};
}
