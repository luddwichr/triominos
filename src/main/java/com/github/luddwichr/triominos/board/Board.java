package com.github.luddwichr.triominos.board;

import com.github.luddwichr.triominos.tile.Location;
import com.github.luddwichr.triominos.tile.Placement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {

	private static final Location UP_CENTER_TILE_LOCATION = new Location(0, 0);
	private static final Location DOWN_CENTER_TILE_LOCATION = new Location(0, 1);
	private final List<Placement> placements = new ArrayList<>();

	public void placeTile(Placement placement) {
		if (placements.isEmpty()) {
			verifyFirstTileLocationIsCentered(placement);
			placements.add(placement);
		} else {
			verifyAdjacentPlacementExists(placement);
			placements.add(placement);
		}
	}

	private void verifyFirstTileLocationIsCentered(Placement placement) {
		if (placement.getOrientation().isFacingUp()) {
			if (!placement.getLocation().equals(UP_CENTER_TILE_LOCATION)) {
				throw new IllegalPlacementException("First placement must be located at 0/0 when facing up!");
			}
		} else {
			if (!placement.getLocation().equals(DOWN_CENTER_TILE_LOCATION)) {
				throw new IllegalPlacementException("First placement must be located at 0/1 when facing down!");
			}
		}
	}

	private void verifyAdjacentPlacementExists(Placement placement) {
		if (placements.stream().noneMatch(existingPlacement -> existingPlacement.getLocation().isSharingEdgeWith(placement.getLocation()))) {
			throw new IllegalPlacementException("Placement is not adjacent to any existing placement!");
		}
	}

	public List<Placement> getTilePlacements() {
		return Collections.unmodifiableList(placements);
	}
}