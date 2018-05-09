package player;

public interface Player
{
	/**
	 * Receives a request to make a move.
	 * 
	 * @param lastMove
	 *            - Move made by the other player, will be null if the other
	 *            player hasn't played yet.
	 * 
	 * @return - The move this player makes.
	 */
	public Byte requestMove(Byte lastMove);
}
