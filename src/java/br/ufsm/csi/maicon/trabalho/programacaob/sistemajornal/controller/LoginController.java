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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.web.portlet.ModelAndView;

/**
 *
 * @author maicon
 */
public class LoginController {
  
     
    @Autowired
    UsuariosDAO dao;
    @Autowired
    NoticiasDAO notdao;

    // teste se o login esta ativo em relacao ao usuario
    @RequestMapping("index.htm")
    public ModelAndView login(String usuario, String senha, HttpSession session, HttpServletRequest request) throws SQLException {
        ModelAndView mv = new ModelAndView("index");
        // vou adicionar a sessao aas noticias relacionadas a ela que serao exibidas na index
        List<Noticias> listaNoticias = notdao.listaNoticias();
        session.setAttribute("listaNoticias", listaNoticias);

        if (usuario != null && senha != null) {
            Usuarios u = dao.loginGeraUsuario(usuario, senha);
            // testo se o usuario se encontra logado ou nao
            if (u == null) {
                //caso nao tenha logado vai indicar a mensagem
                mv.addObject("msg", "Carro Usuario, ouve um problema!<br>Senha ou login incorretos!<br>Caso o problema persistir entrar em contato com a pessoa responsavel pelo site");
            }
            // Usuário Inativo
            else if(u.getAtivo()==0){
                mv.addObject("msg", "Ocorreu um Problema!<br>Usuário encontra-se inativo!<br>Contate os responsaveis para resolver o problema");
            }
            // login esta certo e ativo
            else {
                // limpa a sesao anterior 
                session.invalidate(); 
                session = request.getSession();
                // vou grava das ultimas noticias deste usuario
                session.setAttribute("listaNoticias", listaNoticias);
                // estarei setando o vivente de sessao
                session.setAttribute("usuario", u);

                mv = new ModelAndView("redirect:usuarios.htm");
               
                return mv;
            }
        }
        return mv;
    }

    //sair do sistema 
    @RequestMapping("sair.htm")
    public ModelAndView sair(HttpSession session) {
        ModelAndView mv = new ModelAndView("forward:index.htm");

        // mostra ao usuario que ele saiu do ssitema
        mv.addObject("msg", "Caro Usuario voce acabou de se Desconectar do Sistema!");

        // aqui vai estar invaliando a secao
        session.invalidate();

        return mv;
    }

}


