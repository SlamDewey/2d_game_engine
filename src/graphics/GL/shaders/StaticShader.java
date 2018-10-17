package graphics.GL.shaders;

import components.math.Matrix4f;

public class StaticShader extends ShaderProgram {

	private static final String VERTEX_FILE 	= "/graphics/GL/shaders/vertexShader.txt";
	private static final String FRAGMENT_FILE 	= "/graphics/GL/shaders/fragmentShader.txt";
	
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	
	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttributes(0, "position");
		super.bindAttributes(1, "textureCoords");
	}
	
	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix 	= super.getUniformLocation("transformationMatrix");
		location_projectionMatrix		= super.getUniformLocation("projectionMatrix");
	}
	
	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	
	public void loadProjectionMatrix(Matrix4f matrix) {
		super.loadMatrix(location_projectionMatrix, matrix);
	}
	
}
