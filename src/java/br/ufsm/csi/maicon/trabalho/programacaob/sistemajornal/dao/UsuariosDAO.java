/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufsm.csi.maicon.trabalho.programacaob.sistemajornal.dao;

import br.ufsm.csi.maicon.trabalho.programacaob.sistemajornal.model.Usuarios;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author maicon
 */
public class UsuariosDAO {
     
    
    private UsuariosDAO(){
     }

 

    // para realizar a edicao de um novo usuario ao sistema 
    public boolean adicionar(Usuarios usuarios) throws SQLException{
        Connection conc = getConnection();
        PreparedStatement ps = null;
        try{
             String url = "jdbc:postgresql://localhost:5432/traballhoprogramacaob\"";  
            String usuario = "postgres";  
            String senha = "fr27sa03t5";  
  
            Class.forName("org.postgresql.Driver");  
    
  
            conc = DriverManager.getConnection(url, usuario, senha);  
  
            System.out.println("Conexão realizada com sucesso.");  
  
            Statement stm = conc.createStatement();  

            // as ? seriam mais um teste para dizer se o banco se encontra pronto para receber os dados ou nao
            ps = conc.prepareStatement("INSERT INTO USUARIO (NOME, LOGIN, SENHA, ativo) VALUES (?,?,?,?)"); 
            // aqui estarei setando os campos
            ps.setString(1, usuarios.getNome());
            ps.setString(2, usuarios.getLogin());
            ps.setString(3, usuarios.getSenha());
            ps.setInt(4, usuarios.getAtivo());
            // qualquer erro/problema  que vier a ocorrer dai vai executar o commit e fechar a conexao
            int inseriu = ps.executeUpdate();
            if(inseriu!=0)
                return true;
             // caso ocorra como o planejado vai executar senao vai para o rollback
            conc.commit();
        }catch(Exception e){
            e.printStackTrace();
            // algum erro aconteceu ai executa o rollback
            try{ conc.rollback(); 
            } catch(Exception ex) {
            }
        }
        finally{
            // Fechar da ordem inversa que abriu
            try{ ps.close(); } catch(Exception e) {}
            try{ conc.close(); } catch(Exception e) {}
        }
        return false;
    }

    // para realizar a esxlusao dos usuário
    public boolean remover(String id) throws SQLException {
        Connection conc = getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conc.prepareStatement("DELETE FROM USUARIO WHERE id=?");
            ps.setInt(1, Integer.parseInt(id));
            // teste se foi enserido corretamente
            int excluiu = ps.executeUpdate();
            if (excluiu!=0)
                return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            try { conc.rollback();
            } catch (Exception e) {
            }
        } finally {
            try { rs.close(); 
            } catch (Exception e) {
            }
            try { ps.close(); 
            } catch (Exception e) {
            }
            try { conc.close(); } 
            catch (Exception e) {
            }
        }
        return false;
    }

    // vou testar se o login esta de acordo ai retorno o usuario
    public Usuarios loginGeraUsuario(String login, String senha) throws SQLException {
        Connection conc = getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conc.prepareStatement("SELECT id, nome, login, senha, ativo FROM USUARIO WHERE login LIKE ? AND senha LIKE ?");
            ps.setString(1, login);
            ps.setString(2, senha);
            rs = ps.executeQuery();
            // testo se encontrei ao usuario 
            if (rs.next()) {
                Usuarios usuario = new Usuarios();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setLogin(rs.getString("login"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setAtivo(rs.getInt("ativo"));
               
                return usuario;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            try { conc.rollback();
            } catch (Exception e) { 
            }
        } finally {
            try { rs.close(); 
            } catch (Exception e) {
            }
            try { ps.close();
            } catch (Exception e) { 
            }
            try { conc.close(); } catch (Exception e) {
            }
        }
        return null;
    }

    public Usuarios buscaUsuarioPeloId (int id) throws SQLException{
        Connection conc = getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Usuarios u = null;
        try{
            //? seria como um teste para preparar ao banco e ver se esta tudo de acordo
            ps = conc.prepareStatement("SELECT NOME, LOGIN, SENHA, ativo FROM USUARIO WHERE ID=?"); 
            // inicio da parte para realiar a setar os campos e tem 1 porque e para ser o primeiro e para o ususario nao tentar colocar valor negativo
            ps.setInt(1, id); 
            rs = ps.executeQuery();
            if (rs.next()){
                u = new Usuarios();
                u.setId(id);
                u.setNome(rs.getString("nome"));
                u.setLogin(rs.getString("login"));
                u.setSenha(rs.getString("senha"));
                u.setAtivo(rs.getInt("ativo"));
            }
            
            // tudo ocorrendo conforme o planejado executa o commit
            conc.commit();
        }catch(Exception e){
            // ocorreu um problema ai executa o rollback
            try{ conc.rollback();
            } catch(Exception ex) {
            }
        }
        finally{
            // mais por motivo de seguranca fecha na ordem inversa
            try{ ps.close(); 
            } catch(Exception e) {
            }
            try{ conc.close(); 
            } catch(Exception e) {
            }
        }
        return u;
    }

    public Collection<Usuarios> getUsuarios() throws SQLException {
        Collection<Usuarios> usuarios = new ArrayList<Usuarios>();
        Connection conc = getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = conc.prepareStatement("SELECT ID, NOME, LOGIN, SENHA, ativo FROM USUARIO");
            rs = ps.executeQuery();
            while (rs.next()){
                Usuarios u = new Usuarios();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setLogin(rs.getString("login"));
                u.setSenha(rs.getString("senha"));
                u.setAtivo(rs.getInt("ativo"));
                usuarios.add(u);
            }

            //tudo transcorrendo conforme planejado
            conc.commit(); 
        }catch(Exception e){
            try{ conc.rollback(); 
            // algum problema aconteceu 
            } catch(Exception ex) {
            }
        }
        finally{
            
            try{ ps.close(); 
            } catch(Exception e) {
            }
            try{ conc.close(); 
            } catch(Exception e) {
            }
        }
        return usuarios;
    }

    // Editar os usuarios
    public boolean editar(Usuarios usuario) throws SQLException {
        Connection conc = getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conc.prepareStatement("UPDATE usuario SET nome=?,login=?, senha=?, ativo=? WHERE id=?");

            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getLogin());
            ps.setString(3, usuario.getSenha());
            ps.setInt(4, usuario.getAtivo());
            ps.setInt(5, usuario.getId());
            // testa se foi inserido
            int editou = ps.executeUpdate();
            if (editou!=0)
                return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            try { conc.rollback();
            } catch (Exception e) {
            }
        } finally {
            try { rs.close();
            } catch (Exception e) { 
            }
            try { ps.close(); 
            } catch (Exception e) {
            }
            try { conc.close(); 
            } catch (Exception e) {
            }
        }
        return false;
    }

    // gerando a conexao
    private Connection getConnection() throws SQLException{
        return getConnection();
    }
}

