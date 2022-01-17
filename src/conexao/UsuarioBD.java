package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import vo.UsuarioVO;

/**
 *
 * @author Guilherme Gomes
 */
public class UsuarioBD implements UsuarioDAO {

    private Connection connection;
    
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    public void openConnection() throws Exception {
        try {
            connection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/coursera", "postgres", "postgres");
        } catch (SQLException ex) {
            throw new Exception("Não foi possível abrir a conexão!", ex);
        }
    }
    
    @Override
    public void inserir(UsuarioVO u) {
        String sql = "INSERT INTO usuario(login, email, nome, senha, pontos) VALUES (?, ?, ?, ?, ?)";
                
        try(PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, u.getLogin());
            stm.setString(2, u.getEmail());
            stm.setString(3, u.getNome());
            stm.setString(4, u.getSenha());
            stm.setInt(5, u.getPontos());
            
            stm.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public UsuarioVO recuperar(String login) {
        UsuarioVO usuario = null;
        String sql = "SELECT * FROM usuario WHERE login = ?";
        
        try(PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, login);
            try(ResultSet rs = stm.executeQuery()) {
                if(rs.next()) {
                    usuario = new UsuarioVO(rs.getString("login"),
                                            rs.getString("email"),
                                            rs.getString("nome"),
                                            rs.getString("senha"),
                                            rs.getInt("pontos"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return usuario;
    }

    @Override
    public void adicionarPontos(String login, int pontos) {
        String sql = "UPDATE usuario SET pontos = pontos + ? WHERE login = ?";
                
        try(PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, pontos);
            stm.setString(2, login);
            
            stm.executeUpdate();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<UsuarioVO> ranking() {
        List<UsuarioVO> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario ORDER BY pontos DESC;";
        
        try(PreparedStatement stm = connection.prepareStatement(sql)) {
            try(ResultSet rs = stm.executeQuery()) {
                while(rs.next()) {
                    UsuarioVO usuario = new UsuarioVO(rs.getString("login"),
                                            rs.getString("email"),
                                            rs.getString("nome"),
                                            rs.getString("senha"),
                                            rs.getInt("pontos"));
                    usuarios.add(usuario);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return usuarios;
    }
}
