package graphics.GL.GUI;

import components.math.Matrix4f;
import graphics.GL.shaders.ShaderProgram;

public class GUIShader extends ShaderProgram{
	
	private static final String VERTEX_FILE 	= "/graphics/GL/GUI/guiVertexShader.txt";
	private static final String FRAGMENT_FILE 	= "/graphics/GL/GUI/guiFragmentShader.txt";
	
	private int location_transformationMatrix;

	public GUIShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	public void loadTransformation(Matrix4f matrix){
		super.loadMatrix(location_transformationMatrix, matrix);
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttributes(0, "position");
	}
	
	
	

}
