package engine.graph;

import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram {
    private final int programID;

    private int vertexShaderId;

    private int fragmentShaderId;

    public ShaderProgram() throws Exception {
        programID = glCreateProgram();
        if (programID == 0)
            throw new Exception("Could not create Shader");

    }

    public void createVertexShader(String shaderCode) throws Exception{
        vertexShaderId = createShader(shaderCode, GL_VERTEX_SHADER);
    }

    public void createFragmentShader(String shaderCode) throws Exception{
        fragmentShaderId = createShader(shaderCode, GL_FRAGMENT_SHADER);
    }

    public int createShader(String shaderCode, int shaderType) throws Exception {
        int shaderId = glCreateShader(shaderType);
        if (shaderId == 0)
            throw new Exception("Error  creating shader. Type: " + shaderType);

        glShaderSource(shaderId, shaderCode);
        glCompileShader(shaderId);
        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0){
            throw new Exception("Error compiling Shader code: " + glGetShaderInfoLog(shaderId, 1024));
        }

        glAttachShader(programID, shaderId);

        return shaderId;
    }

    public void link() throws Exception {
        glLinkProgram(programID);
        if (glGetProgrami(programID, GL_LINK_STATUS) == 0)
            throw new Exception("Error linking Shader code: " + glGetProgramInfoLog(programID, 1024));
        if (vertexShaderId != 0){
            glDetachShader(programID, vertexShaderId);
            glDeleteShader(vertexShaderId);
        }
        if (fragmentShaderId !=0){
            glDetachShader(programID, fragmentShaderId);
            glDeleteShader(fragmentShaderId);
        }

        glValidateProgram(programID);
        if (glGetProgrami(programID, GL_VALIDATE_STATUS) == 0)
            System.err.println("Warning validating Shader code: " + glGetProgramInfoLog(programID, 1024));


    }

    public void bind(){
        glUseProgram(programID);
    }

    public void unbind(){
        glUseProgram(0);
    }

    public void cleanup(){
        unbind();
        if (programID != 0)
            glDeleteProgram(programID);
    }
}
