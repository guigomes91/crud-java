package conexao;

import java.util.List;
import vo.UsuarioVO;

/**
 *
 * @author Guilherme Gomes
 */
public interface UsuarioDAO {

    //insere um novo usuário no banco de dados
    public void inserir(UsuarioVO u);

    //recupera o usuário pelo seu login
    public UsuarioVO recuperar(String login);

    //adiciona os pontos para o usuário no banco
    public void adicionarPontos(String login, int pontos);

    //retorna a lista de usuários ordenada por pontos (maior primeiro)
    public List<UsuarioVO> ranking();
}
