package buildcraft.lib.cache;

import java.lang.ref.WeakReference;
import java.util.EnumMap;
import java.util.Map;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.Chunk.EnumCreateEntityType;

import buildcraft.lib.misc.ChunkUtil;
import buildcraft.lib.misc.PositionUtil;
import buildcraft.lib.misc.data.FaceDistance;
import buildcraft.lib.tile.TileBC_Neptune;

/** An {@link ITileCache} that only caches the immediate neighbours of a {@link TileEntity}. (Essentially caches
 * everything that {@link TileBC_Neptune#getNeighbourTile(EnumFacing)} can return). */
public class NeighbourTileCache implements ITileCache {

    // TODO: Test the performance!

    private final TileEntity tile;
    private BlockPos lastSeenTilePos;
    private final Map<EnumFacing, WeakReference<TileEntity>> cachedTiles = new EnumMap<>(EnumFacing.class);

    public NeighbourTileCache(TileEntity tile) {
        this.tile = tile;
    }

    @Override
    public void invalidate() {
        cachedTiles.clear();
    }

    @Override
    public TileCacheRet getTile(BlockPos pos) {
        if (!canUseCache()) {
            return null;
        }
        FaceDistance offset = PositionUtil.getDirectOffset(lastSeenTilePos, pos);
        if (offset == null || offset.distance != 1) {
            return null;
        }
        return getTile0(offset.direction);
    }

    private boolean canUseCache() {
        if (tile.isInvalid()) {
            return false;
        }
        BlockPos tPos = tile.getPos();
        if (!tPos.equals(lastSeenTilePos)) {
            lastSeenTilePos = tPos.toImmutable();
            cachedTiles.clear();
        }
        return true;
    }

    @Override
    public TileCacheRet getTile(EnumFacing offset) {
        if (!canUseCache()) {
            return null;
        }
        return getTile0(offset);
    }

    private TileCacheRet getTile0(EnumFacing offset) {
        WeakReference<TileEntity> ref = cachedTiles.get(offset);
        if (ref != null) {
            TileEntity oTile = ref.get();
            if (oTile == null || oTile.isInvalid()) {
                cachedTiles.remove(offset);
            } else {
                return new TileCacheRet(oTile);
            }
        }
        Chunk chunk;
        BlockPos offsetPos = lastSeenTilePos.offset(offset);
        if (tile instanceof TileBC_Neptune) {
            chunk = ((TileBC_Neptune) tile).getChunk(offsetPos);
        } else {
            chunk = ChunkUtil.getChunk(tile.getWorld(), offsetPos, true);
        }
        TileEntity offsetTile = chunk.getTileEntity(offsetPos, EnumCreateEntityType.IMMEDIATE);
        if (offsetTile != null) {
            cachedTiles.put(offset, new WeakReference<>(offsetTile));
        }
        return new TileCacheRet(offsetTile);
    }
}
