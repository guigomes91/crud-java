package principal;

import conexao.UsuarioBD;
import java.util.List;
import vo.UsuarioVO;

/**
 *
 * @author Guilherme Gomes
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        UsuarioVO user1 = new UsuarioVO("teste", "teste@email.com", "Teste CRUD", "123", 0);
        
        UsuarioBD bd = new UsuarioBD();
        
        bd.openConnection();
        bd.inserir(user1);
        bd.adicionarPontos("teste", 100);
        
        List<UsuarioVO> usuarios = bd.ranking();
        
        usuarios.forEach(System.out::println);
        System.out.println(bd.recuperar("teste"));
    }
    
}
