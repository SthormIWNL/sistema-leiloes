import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet result;
    ArrayList<ProdutosDTO> listatela = new ArrayList<>();
    
    // Método de cadastrar produto (geralmente já tinhas este)
    public void cadastrarProduto (ProdutosDTO produto){
        conn = new conectaDAO().connectDB();
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?,?,?)";
        try {
            prep = conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setDouble(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            prep.executeUpdate();
            prep.close();
            JOptionPane.showMessageDialog(null, "Cadastro efetuado com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar: " + e.getMessage());
        }
    }
    
    // Método para listar todos os produtos (na listagem principal)
    public ArrayList<ProdutosDTO> listarProdutos(){
        conn = new conectaDAO().connectDB();
        String sql = "SELECT * FROM produtos";
        try {
            prep = conn.prepareStatement(sql);
            result = prep.executeQuery();
            listatela.clear();
            while (result.next()){
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(result.getInt("id"));
                produto.setNome(result.getString("nome"));
                produto.setValor(result.getInt("valor"));
                produto.setStatus(result.getString("status"));
                listatela.add(produto);
            }
            return listatela;
        } catch (Exception e) {
            return null;
        }
    }
    
    // NOVO: Método para alterar o status para "Vendido"
    public void venderProduto(int id) {
        conn = new conectaDAO().connectDB();
        String sql = "UPDATE produtos SET status = ? WHERE id = ?";
        try {
            prep = conn.prepareStatement(sql);
            prep.setString(1, "Vendido");
            prep.setInt(2, id);
            prep.executeUpdate();
            prep.close();
        } catch (Exception e) {
            System.out.println("Erro ao vender produto: " + e.getMessage());
        }
    }

    // NOVO: Método para buscar apenas os produtos vendidos
    public ArrayList<ProdutosDTO> listarProdutosVendidos() {
        conn = new conectaDAO().connectDB();
        String sql = "SELECT * FROM produtos WHERE status = ?";
        try {
            prep = conn.prepareStatement(sql);
            prep.setString(1, "Vendido");
            result = prep.executeQuery();
            
            ArrayList<ProdutosDTO> listaVendidos = new ArrayList<>();
            while (result.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(result.getInt("id"));
                produto.setNome(result.getString("nome"));
                produto.setValor(result.getInt("valor"));
                produto.setStatus(result.getString("status"));
                listaVendidos.add(produto);
            }
            return listaVendidos;
        } catch (Exception e) {
            System.out.println("Erro ao listar vendidos: " + e.getMessage());
            return null;
        }
    }
}