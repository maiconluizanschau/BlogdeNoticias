/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufsm.csi.maicon.trabalho.programacaob.sistemajornal.controller;

import br.ufsm.csi.maicon.trabalho.programacaob.sistemajornal.dao.NoticiasDAO;
import br.ufsm.csi.maicon.trabalho.programacaob.sistemajornal.model.Noticias;
import br.ufsm.csi.maicon.trabalho.programacaob.sistemajornal.model.Usuarios;
import org.springframework.beans.factory.annotation.Autowired;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.portlet.ModelAndView;
/**
 *
 * @author maicon
 */
public class NoticiasController {
    
      @Autowired
    NoticiasDAO dao;

    @RequestMapping(value="cadastrar-noticia.htm", method = RequestMethod.POST)
    public ModelAndView cadastro(Noticias noticia, HttpSession session) throws SQLException {
        ModelAndView mv = new ModelAndView("cadastrar-noticia");
        // aqui estarei recebendo o usuario da session
        Usuarios usuario = (Usuarios)session.getAttribute("usuario");
        noticia.setUsuariopubli(usuario.getId());

        // Criando  o calendario com o  simple date format
        Calendar cal = Calendar.getInstance();
        // Padrão usado em grande parte dos bancos de dados"datatime" 
        SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // Seta a data e hora atual
        String data = formatador.format(cal.getTime());
        try {cal.setTime( formatador.parse(data)); }
        catch (ParseException e) {e.printStackTrace();}
        noticia.setDataEhora(cal);

        // salva no meu banco de dados 
        boolean adicionou = dao.adicionar(noticia);
        if (adicionou){
            mv.addObject("msg", "Caro usuario sua notícia adicionada com sucesso");
            // Adiciona na session
            List<Noticias> listaNoticias = (List<Noticias>)session.getAttribute("listaNoticias");
            session.setAttribute("listaNoticias", listaNoticias);
        }
        else
            mv.addObject("msg", "Ocorreu algum ERROR<br>Notícia não foi adicionada!");

        return mv;
    }
    // estou usando o get para realizar a criadcao da url
    @RequestMapping(value = "cadastrar-noticia.htm", method = RequestMethod.GET)
    public String cadastro(){
        return "cadastrar-noticia";
    }

    //para mostrar a notícia completa pois na index apenas aparece a descricao 
    @RequestMapping(value = "noticia.htm", method = RequestMethod.GET)
    public ModelAndView busca(int id, HttpServletRequest request){
        ModelAndView mv = new ModelAndView("noticia");

        try {
            Noticias noticia = dao.buscaNoticia(id, -1);
            mv.addObject("noticia",noticia);
        } catch(Exception e){
            // se ocorrer algum erro mostra ao usuario
            mv.addObject("msg", e.getMessage());
        }
        return mv;
    }

    // caso deseje editar a noticia
    @RequestMapping(value = "editar-noticia.htm", method = RequestMethod.POST)
    public ModelAndView editar(Noticias noticia){
        ModelAndView mv = new ModelAndView("editar-noticia");
        boolean editou = false;
        // caso deseje editar a noticia
        try {editou = dao.editar(noticia);
        } catch (SQLException e) {e.printStackTrace();
        }
        if(editou)
            mv.addObject("msg", "Caro usuario sua noticia foi editado com sucesso!");
        else
            mv.addObject("msg", "Não foi possível editar a noticia. <br>Tente novamente!");
        return mv;
    }

    // estarei editando a noticia via o link do get
    @RequestMapping(value = "editar-noticia.htm", method = RequestMethod.GET)
    public ModelAndView exibeEditar(String id) throws SQLException {
        ModelAndView mv = new ModelAndView("editar-noticia");
        // aqui estarei realizandoa a  busca da noticia e apos enviando para a opcao de editar
        Noticias noticia = dao.buscaNoticia(Integer.parseInt(id), -1);
        noticia.setId(Integer.parseInt(id));
        mv.addObject("noticia", noticia);
        return mv;
    }

    // caso deseje excluir a uma noticia
    @RequestMapping(value="remover-noticia.htm", method = RequestMethod.GET)
    public ModelAndView remover(String id, HttpSession session) throws SQLException {
        ModelAndView mv = new ModelAndView("redirect:usuarios.htm");
        boolean removeu = dao.remover(id);
        if(removeu) {
            mv.addObject("msg", "Notícia removida com sucesso!");
            // agora estarei atualizando a lista de notícias do usuário na Sessao
            List<Noticias> listaNoticiasUsuario = (List<Noticias>)session.getAttribute("noticiasUsuario");
            Noticias noticiaRemover = dao.buscaNoticia(Integer.parseInt(id), -1); 
            listaNoticiasUsuario.remove(noticiaRemover);
            session.setAttribute("noticiasUsuario", listaNoticiasUsuario);
        }else
            mv.addObject("msg", "A notícia não foi removida! Tente novamente!");
        return mv;
    }
}
