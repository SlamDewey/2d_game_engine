package graphics.GL.GUI;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import components.math.Matrix4f;
import graphics.GL.Loader;
import graphics.GL.models.RawModel;
import graphics.GL.toolbox.Maths;

public class GUIRenderer {
	
	private final RawModel quad;
	private GUIShader shader;
	
	public GUIRenderer(Loader loader) {
		float[] quad_positions = {-1, 1, -1, -1, 1, 1, 1, -1};
		quad = loader.loadToVAO(quad_positions);
		shader = new GUIShader();
	}
	
	public void render(List<GUIElement> guis) {
		shader.start();
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		for (GUIElement gui : guis) {
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, gui.getFillTexture());
			Matrix4f transform = Maths.createTransformationMatrix(gui.getPosition(), gui.getScale());
			shader.loadTransformation(transform);
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
		}
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_BLEND);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.stop();
	}
	
	public void cleanUp() {
		shader.cleanUp();
	}
}
