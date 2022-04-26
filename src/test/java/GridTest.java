import org.game.Direction;
import org.game.GameGrid;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GridTest {

    GameGrid grid1 = new GameGrid(4);
    GameGrid grid2 = new GameGrid(4);

    @Test
    public void moveTest() {
        grid1.setTile(3, 3, 2);
        grid1.setTile(3, 2, 2);
        assertFalse(grid1.move(Direction.DOWN));
        assertTrue(grid1.move(Direction.RIGHT));
        grid2.setTile(3,3, 4);
        assertEquals(grid1, grid2);
    }

}
