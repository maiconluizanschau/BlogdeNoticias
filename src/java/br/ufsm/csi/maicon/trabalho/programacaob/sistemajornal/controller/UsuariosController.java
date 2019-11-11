/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufsm.csi.maicon.trabalho.programacaob.sistemajornal.controller;
import br.ufsm.csi.maicon.trabalho.programacaob.sistemajornal.dao.NoticiasDAO;
import br.ufsm.csi.maicon.trabalho.programacaob.sistemajornal.dao.UsuariosDAO;
import br.ufsm.csi.maicon.trabalho.programacaob.sistemajornal.model.Noticias;
import br.ufsm.csi.maicon.trabalho.programacaob.sistemajornal.model.Usuarios;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.portlet.ModelAndView;
/**
 *
 * @author maicon
 */
public class UsuariosController {
 
      @Autowired
    private UsuariosDAO dao;
    @Autowired
    private NoticiasDAO notdao;

    // estarei realizando a busca e mostrando as suas informacoes
    @RequestMapping("busca-usuario.htm")
    public ModelAndView busca(int id){
        ModelAndView mv = new ModelAndView("usuario");

        try {
            Usuarios u = dao.buscaUsuarioPeloId(id);
            mv.addObject("usuario",u);
        } catch(Exception e){
            // caso ocorra um erro mostra ao usuario
            mv.addObject("msg", e.getMessage());
        }
        return mv;
    }

    // para poder editar ao usuario 
    @RequestMapping(value = "editar-usuario.htm", method = RequestMethod.POST)
    public ModelAndView editar(Usuarios usuario){
        ModelAndView mv = new ModelAndView("editar-usuario");
        boolean editou = false;
        // para realizar a edicao do usuário
        try {editou = dao.editar(usuario);} catch (SQLException e) {e.printStackTrace();}
        Integer ativo = usuario.getAtivo();
        if(editou)
            mv.addObject("msg", "Caro usuario seu perfil foi editado com sucesso!");
        else
            mv.addObject("msg", "Não foi possível editar seu perfil.<br> Tente mais Tarde!");
        return mv;
    }

    // Edita o usuário link para acessar ao mesmo via get 
    @RequestMapping(value = "editar-usuario.htm", method = RequestMethod.GET)
    public ModelAndView exibeEditar(String id) throws SQLException {
        ModelAndView mv = new ModelAndView("editar-usuario");
        // realiza a busca do usuario e envia para o opcao editar
        Usuarios usuario = dao.buscaUsuarioPeloId(Integer.parseInt(id));
        mv.addObject("usuario", usuario);
        return mv;
    }

    // Lista todos os usuários e as notícias do usuário logado
    @RequestMapping("usuarios.htm" + "")
    public ModelAndView buscaUsuarios(HttpSession session){
        ModelAndView mv = new ModelAndView("usuarios");
        List<Noticias> listaNoticiasUsuario = new ArrayList<Noticias>();
        try{
            Collection<Usuarios> usuarios = dao.getUsuarios();
            session.setAttribute("listaUsuarios", usuarios);
        }catch (Exception e){}

        // pega os dados do usuario da sessao
        Usuarios usuario = (Usuarios)session.getAttribute("usuario");
        // mostro as noticias do usuario
        try {
            listaNoticiasUsuario = notdao.listaNoticiasUsuario(usuario.getId());
        } catch (SQLException e
                ) 
        {e.printStackTrace();
        }
        // aqui estarei adiconando a lista para a secao do usuario
        session.setAttribute("noticiasUsuario", listaNoticiasUsuario);
        return mv;
    }

    // Cadastrar usuario
    @RequestMapping(value="cadastrar-usuario.htm", method = RequestMethod.POST)
    public ModelAndView cadastraUsuario(@ModelAttribute Usuarios usuario) throws SQLException {
        ModelAndView mv = new ModelAndView("cadastrar-usuario");

        boolean adicionou = dao.adicionar(usuario);

        if (adicionou)
            mv.addObject("msg", " Caro Usuário  voce foi adicionado com sucesso");
        else
            mv.addObject("msg", "OCaro  usuário um ERROR ocorreu! Voce  não está adicionado!");
        return mv;
    }

    // para apagar a um usuario
    @RequestMapping(value="remover-usuario.htm", method = RequestMethod.GET)
    public ModelAndView remover(String id, HttpSession session) throws SQLException {
        ModelAndView mv = new ModelAndView("usuarios");
        boolean removeu = false;
        // testo se o usuario possui alguma noticia cadastrada ou nao
        Noticias noticia = notdao.buscaNoticia(-1, Integer.parseInt(id));

        // caso a informacao seja verdadeira e ele nao tenha noticia ai pega e apago ele
        if(noticia == null)
            removeu = dao.remover(id);
        else{
            // aqui vou estar recebendo o novo que vai ficar inativo e atulizo o banco
            Usuarios usuario = dao.buscaUsuarioPeloId(Integer.parseInt(id));
            usuario.setAtivo(0);
            dao.editar(usuario);
        }

        // testa se mesmo apagou o usuario
        if (removeu){
            // aqui estare recebendo a lista de todos os usarios da minha sessao
            List<Usuarios> listaUsuarios = (List<Usuarios>)session.getAttribute("listaUsuarios");
            Integer idInt = Integer.parseInt(id);
            // vou estar percorrendo a lista e depois removendo os usuarios da minha sessao
            for(Usuarios usuario : listaUsuarios){
                if(usuario.getId()==idInt){
                    listaUsuarios.remove(usuario); break;
                }
            }
            // estarei atualizando a lista de session
            session.setAttribute("listaUsuarios", listaUsuarios);
            mv.addObject("msg", "Usuário removido com sucesso");
        }
        else if(noticia != null)
            mv.addObject("msg", "O usuário possui notícia e foi desativado!");
        else
            mv.addObject("msg", "O usuário não foi removido!");

        return mv;
    }

    // Usa o get para criar url
    @RequestMapping(value = "cadastrar-usuario.htm", method = RequestMethod.GET)
    public String cadastro(){
        return "cadastrar-usuario";
    }
}

