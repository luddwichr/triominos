package com.github.luddwichr.triominos.pile;

import com.github.luddwichr.triominos.tile.Tile;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PileTest {

	private static final Set<Tile> tileSet = Set.of(new Tile(1, 2, 3), new Tile(3, 2, 1));
	private final Pile pile = new Pile(tileSet);

	@Test
	void pileHasAsManyRemainingTilesAsClassicTileSetContains() {
		assertThat(pile.remainingTiles()).isEqualTo(tileSet.size());
	}

	@Test
	void isEmptyIsFalseForInitialPile() {
		assertThat(pile.isEmpty()).isFalse();
	}

	@Test
	void isEmptyAfterAllTilesHaveBeenDrawn() {
		while (pile.remainingTiles() > 0) {
			pile.drawRandomTile();
		}
		assertThat(pile.isEmpty()).isTrue();
	}

	@Test
	void drawRandomTileEventuallyReturnsAllTilesFromTileSet() {
		Set<Tile> tilesFromPile = Stream.generate(pile::drawRandomTile).limit(tileSet.size()).collect(toSet());
		assertThat(tilesFromPile).containsExactlyInAnyOrderElementsOf(tileSet);
	}

	@Test
	void drawRandomTileFromEmptyPileThrows() {
		tileSet.forEach(tile -> pile.drawRandomTile());
		assertThatThrownBy(pile::drawRandomTile).isInstanceOf(EmptyPileException.class);
	}
}
