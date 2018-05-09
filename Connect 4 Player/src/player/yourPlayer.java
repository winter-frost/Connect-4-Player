package player;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Create your connect 4 AI player here!
 * 
 * @author...
 */
public class yourPlayer implements Player
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see player.Player#requestMove(java.lang.Byte)
	 */
	@Override
	public Byte requestMove(Byte lastMove)
	{
		// Insert your code here.
		return this.selectMoveRandomly();
	}

	/**
	 * Here's a start, this method randomly selects a move between column 0 and
	 * 6... I wouldn't recommend this as a winning strategy though!
	 */
	private Byte selectMoveRandomly()
	{
		int randomNum = ThreadLocalRandom.current().nextInt(0, 7);
		return (byte) randomNum;
	}
}
