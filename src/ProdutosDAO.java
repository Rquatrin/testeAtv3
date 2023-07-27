import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ProdutosDAO {
    
    
        private conectaDAO conexao;
        private ArrayList<ProdutosDTO> listagem;

	public ProdutosDAO(conectaDAO conexao) {

		this.conexao = conexao;
		this.listagem = new ArrayList<>();
	}

        public ProdutosDAO() {

	}

    
    public boolean cadastrarProduto (ProdutosDTO produto){
            
        
        try {
            conexao.connectDB();
            Connection conn = conexao.getConexao();
            PreparedStatement st = conn.prepareStatement("INSERT INTO produtos" + " (nome, valor, status) VALUES (?,?,?)");
            
            st.setString(1, produto.getNome());
            st.setInt(2, produto.getValor());
            st.setString(3, produto.getStatus());
            
            int status = st.executeUpdate();
            st.close();
            JOptionPane.showMessageDialog(null, "cadastro com sucesso");
            return status > 0;
            
        } catch (Exception e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
            return false;
        }
            
    }
    

    public ArrayList<ProdutosDTO> listagemProdutos() {
        String sql = "SELECT * from produtos";
       
        
        try {
            conexao.connectDB();
            Connection conn = conexao.getConexao();
            
            PreparedStatement st = conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            listagem.clear();
            
            while (rs.next()) { // se encontrou o filme, vamos carregar os dados
                ProdutosDTO produtos = new ProdutosDTO();
                
                produtos.setId(rs.getInt("id"));
                produtos.setNome(rs.getString("nome"));
                produtos.setValor(rs.getInt("valor"));
                produtos.setStatus(rs.getString("status"));
                
                listagem.add(produtos);

            }
            st.close();
            conexao.desconectarDB();
            return listagem;
            
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return null;
        }
    }
    



   public List<ProdutosDTO> listaTabela(String statusVenda) {

		String sql = "SELECT * FROM produtos WHERE status = ? ";

		try {

			conexao.connectDB();
			Connection conn = conexao.getConexao();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setString(1, statusVenda);

			ResultSet rs = st.executeQuery();

			List<ProdutosDTO> lista = new ArrayList<>();

			while (rs.next()) {

				ProdutosDTO produto = new ProdutosDTO();

				produto.setId(rs.getInt("id"));
				produto.setNome(rs.getString("nome"));
				produto.setValor(rs.getInt("valor"));
				produto.setStatus(rs.getString("status"));

				lista.add(produto);

			}

			return lista;

		} catch (SQLException ex) {

			System.out.println("Erro ao pesquisar: " + ex.getMessage());
			return null;
		}

	}

    
}
    
    
    
    
    
        


