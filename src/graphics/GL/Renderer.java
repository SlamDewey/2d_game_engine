package graphics.GL;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import components.math.Matrix4f;
import components.math.Vector3f;
import graphics.GL.entities.GraphicalEntity2D;
import graphics.GL.models.RawModel;
import graphics.GL.models.TexturedModel;
import graphics.GL.shaders.StaticShader;
import graphics.GL.toolbox.Maths;

public class Renderer {

	public static final float FOV = 70;
	private static final float NEAR_PLANE = 0.01f;
	private static final float FAR_PLANE = 1000f;

	private Matrix4f projectionMatrix;
	private StaticShader shader;

	public Renderer(StaticShader shader) {
		createProjectionMatrix();
		this.shader = shader;
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}

	public void prepare() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(0.5f, 0.5f, 0f, 1);
	}

	public void render(Map<TexturedModel, List<GraphicalEntity2D>> entities) {
		for (TexturedModel model : entities.keySet()) {
			prepareTexturedModel(model);
			List<GraphicalEntity2D> batch = entities.get(model);
			for (GraphicalEntity2D entity : batch) {
				Vector3f pos = entity.getPosition();
				pos.z = 0 + DisplayManager.VIRTUAL_ZOOM;
				prepareInstance(entity, pos);
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			unbindTexturedModel();
		}
	}

	private void prepareTexturedModel(TexturedModel texturedModel) {
		RawModel rawModel = texturedModel.getRawModel();
		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);

		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getTextureID());
	}

	private void unbindTexturedModel() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}

	private void prepareInstance(GraphicalEntity2D entity, Vector3f relativePosition) {
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(relativePosition, entity.getXRot(),
				entity.getYRot(), entity.getZRot(), entity.getXScale(), entity.getYScale());
		shader.loadTransformationMatrix(transformationMatrix);
	}

	private void createProjectionMatrix() {
		float aspectRatio = (float) DisplayManager.WIDTH / (float) DisplayManager.HEIGHT;
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;

		projectionMatrix = new Matrix4f();
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
		projectionMatrix.m33 = 0;
	}
}
