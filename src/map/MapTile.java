package map;

/**
 * @author Rui Z.
 * @about the basic class for a tile on the map
 */
public class MapTile {
	private int[] img;
	private boolean solid;

	public MapTile(int[] img, boolean solid) {
		this.img = img;
		this.solid = solid;
	}

	public boolean isSolid() {
		return solid;
	}

	public int[] getSprite() {
		return img;
	}
}
