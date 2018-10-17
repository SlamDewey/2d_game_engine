package graphics.GL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import graphics.GL.entities.GraphicalEntity2D;
import graphics.GL.models.TexturedModel;
import graphics.GL.shaders.StaticShader;

public class MasterRenderer {
	
	private StaticShader shader;
	private Renderer renderer;
	
	private Map<TexturedModel, List<GraphicalEntity2D>> entities = new HashMap<TexturedModel, List<GraphicalEntity2D>>();
	
	public MasterRenderer() {
		shader = new StaticShader();
		renderer = new Renderer(shader);
	}
	
	public void render() {
		renderer.prepare();
		shader.start();
		
		renderer.render(entities);
		
		shader.stop();
		entities.clear();
	}
	
	public void processEntity(GraphicalEntity2D entity) {
		TexturedModel texMod = entity.getTexturedModel();
		List<GraphicalEntity2D> batch = entities.get(texMod);
		if (batch != null)
			batch.add(entity);
		else {
			List<GraphicalEntity2D> newBatch = new ArrayList<GraphicalEntity2D>();
			newBatch.add(entity);
			entities.put(texMod, newBatch);
		}
	}
	
	public void cleanUp() {
		shader.cleanUp();
	}
}
