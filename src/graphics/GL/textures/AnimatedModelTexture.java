package graphics.GL.textures;

import game.MathThread;

public class AnimatedModelTexture extends ModelTexture {

	ModelTexture[] sequence;
	
	private int curTick = 0;
	private int waitTicks;
	private int curTexture = 0;
	
	public AnimatedModelTexture(ModelTexture[] sequence, double waitTime) {
		super(-1);
		this.sequence = sequence;
		waitTicks = (int) (waitTime * MathThread.UPS_CAP);
	}
	
	@Override
	public void tick() {
		curTick++;
		if (curTick > waitTicks) {
			curTick = 0;
			curTexture = (curTexture == sequence.length - 1) ? 0 : curTexture + 1;
		}
	}
	
	@Override
	public int getTextureID() {
		return sequence[curTexture].getTextureID();
	}

}
