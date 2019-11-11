/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufsm.csi.maicon.trabalho.programacaob.sistemajornal.dao;

import br.ufsm.csi.maicon.trabalho.programacaob.sistemajornal.model.Noticias;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author maicon
 */
public class NoticiasDAO {
    
    
     private NoticiasDAO(){}


    // Aqui comeca para se adicionar um novo usuario
    public boolean adicionar(Noticias noticias) throws SQLException {
        Connection conc = getConnection();
        PreparedStatement ps = null;
        try{
            ps = conc.prepareStatement("INSERT INTO NOTICIA (titulo, descricao, data_hora, usuariopubli) VALUES (?,?,?,?)");
            // aqui estarei setando os campos 
            ps.setString(1, noticias.getTitulo());
            ps.setString(2, noticias.getDescricao());
            // Data e  hora
            Timestamp datahora = new Timestamp(noticias.getDataEhora().getTimeInMillis());
            ps.setTimestamp(3, datahora);
            // aqui estara a chave extrangeira do usuario
            ps.setInt(4, noticias.getUsuariopubli());

            int inseriu = ps.executeUpdate();
            if(inseriu!=0)
                return true;
            //caso tudo de certo ira entrar aqui
            conc.commit(); 
        }catch(Exception e){
            e.printStackTrace();
            //caso de algum problema ou erro vai dar um rollback
            try{ conc.rollback(); } catch(Exception ex) {}
        }
        finally{
            // sempre vou estar fechando an ordem inversa em funcao de por acaso ainda ter alguma coisa aberta
            try{ ps.close(); } catch(Exception e) {}
            try{ conc.close(); } catch(Exception e) {}
        }
        return false;
    }

    // aqui vai listar as 6 ultimas  notícias que foram postadas
    public List<Noticias> listaNoticias() throws SQLException {
        List<Noticias> noticias = new ArrayList<Noticias>();
        Connection conc = getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int contador = 0;
        try{
            ps = conc.prepareStatement("SELECT ID, titulo, descricao, dataEhora, usuariopubli FROM NOTICIA ORDER BY UNIX_TIMESTAMP(dataEhora) DESC");
            rs = ps.executeQuery();
            while (rs.next() && contador<6){
                Noticias noticia = new Noticias();
                noticia.setId(rs.getInt("id"));
                noticia.setTitulo(rs.getString("titulo"));
                noticia.setDescricao(rs.getString("descricao"));
                noticia.setUsuariopubli(rs.getInt("usuariopubli"));
                // Data e hora
                Calendar cal = Calendar.getInstance();
                cal.setTime(rs.getTimestamp("dataEhora"));
                noticia.setDataEhora(cal);

                noticias.add(noticia);
                //aqui vai incrementar sempre 1 até chegar as 6 noticias
                contador++; 
            }
            
            
            //caso tudo transcorrer  conforme o planejado vai executar 
            conc.commit(); 
        }catch(Exception e){
            // caso de pau ou aconteca algo extranho vai executar o rollback 
            try{ conc.rollback(); 
            } catch(Exception ex) {
            }
        }
        finally{
            // sempre vou estar fechando na ordem inversa por motivo de seguranca
            try{
                ps.close();
            } catch(Exception e) {
            }
            try{ 
                conc.close(); 
            } catch(Exception e) {
            }
        }
        return noticias;
    }

    // mostra as noticias do usuario
    public List<Noticias> listaNoticiasUsuario(int id) throws SQLException {
        List<Noticias> noticias = new ArrayList<Noticias>();
        Connection conc = getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = conc.prepareStatement("SELECT ID, titulo, descricao, dataEhora, usuariopubli FROM NOTICIA WHERE fk_usuario=? ORDER BY UNIX_TIMESTAMP(dataEhora) DESC");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()){
                Noticias noticia = new Noticias();
                noticia.setId(rs.getInt("id"));
                noticia.setTitulo(rs.getString("titulo"));
                noticia.setDescricao(rs.getString("descricao"));
                noticia.setUsuariopubli(rs.getInt("usuariopubli"));
                // Data hora
                Calendar cal = Calendar.getInstance();
                cal.setTime(rs.getTimestamp("dataEhora"));
                noticia.setDataEhora(cal);
                noticias.add(noticia);
            }
            
            
            //caso tudo esteja dentro do combinado vai executar conforme o planejado
            conc.commit(); 
        }catch(Exception e){
            try{ 
                //caso ocorra algum problema nao previsto vai executar o roolback
                conc.rollback();
            } catch(Exception ex) {
            }
        }
        finally{
            // sempre estarei fechando na ordem inversa por motivo de seguranca para nao causar algum problema
            try{ ps.close(); 
            
            } catch(Exception e) {
            }try{ conc.close();
            } catch(Exception e) {
            }
        }
        return noticias;
    }

    // aqui inicia  a parte de buscas das noticias
    public Noticias buscaNoticia(int idNoticia, int idUsuario) throws SQLException {
        Connection conc = getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Noticias noticia = null;
        try{
            // estarei realizando a busca pela noticia relacionada ao usuario
            if(idUsuario!=-1){
                ps = conc.prepareStatement("SELECT id, titulo, descricao, dataEhora, usuariopubli FROM noticia WHERE usuariopubli=?");
                ps.setInt(1, idUsuario);
            }
            // estarei realizando a busca da noticia
            else if(idNoticia!=-1){
                ps = conc.prepareStatement("SELECT id, titulo, descricao, dataEhora, usuariopubli FROM noticia WHERE ID=?");
                ps.setInt(1, idNoticia);
            }
            rs = ps.executeQuery();
            if (rs.next()){
                noticia = new Noticias();
                noticia.setId(rs.getInt("id"));
                noticia.setTitulo(rs.getString("titulo"));
                noticia.setDescricao(rs.getString("descricao"));
                // Data e hora
                Calendar cal = Calendar.getInstance();
                cal.setTime(rs.getTimestamp("dataEhora"));
                noticia.setDataEhora(cal);

                noticia.setUsuariopubli(rs.getInt("usuariopubli"));
            }
            // caso tudo esteja dentro do previsto vai executar
            conc.commit(); 
        }catch(Exception e){
            //caso ocorra um erro/ problema ai executa o  rollback 
            try{ conc.rollback(); 
            } catch(Exception ex) {
            }
        }
        finally{
            // estarei fechando na sua ordem inversa
            try{ ps.close(); } catch(Exception e) {
            }try{ conc.close();
            } catch(Exception e) {
            }
        }
        return noticia;
    }

    // para realizar a edicao da noticia
    public boolean editar(Noticias noticia) throws SQLException {
        Connection conc = getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conc.prepareStatement("UPDATE noticia SET titulo=?,descricao=? WHERE id=?");
            ps.setString(1, noticia.getTitulo());
            ps.setString(2, noticia.getDescricao());
            ps.setInt(3, noticia.getId());

            // testa e verifica se a noticia foi inserida
            int editou = ps.executeUpdate();
            if (editou!=0)
                return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            try { conc.rollback();
            } catch (Exception e) { }
        } finally {
            try { rs.close(); 
            } catch (Exception e) { }
            try { ps.close(); 
            } catch (Exception e) { }
            try { conc.close();
            } catch (Exception e) { }
        }
        return false;
    }

    // para realizar a exclusao do vivente
    public boolean remover(String id) throws SQLException {
        Connection conc = getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conc.prepareStatement("DELETE FROM NOTICIA WHERE id=?");
            ps.setInt(1, Integer.parseInt(id));
            // aqui vou testar se os dados foram inseridos corretamentte
            int excluiu = ps.executeUpdate();
            if (excluiu!=0)
                return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            try { conc.rollback(); 
            } catch (Exception e) {
            }
        } finally {
            try { 
                rs.close(); 
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

    // aqui estarei gerando a  conexao
    private Connection getConnection() throws SQLException{
      
        return getConnection();
    }
}
