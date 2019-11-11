/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufsm.csi.maicon.trabalho.programacaob.sistemajornal.controller;

import br.ufsm.csi.maicon.trabalho.programacaob.sistemajornal.model.Usuarios;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 *
 * @author maicon
 */
public class AutenticacaoInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object o) throws Exception {

        if((Usuarios)request.getSession().getAttribute("usuario")== null && !request.getRequestURI().endsWith("noticia.htm") && !request.getRequestURI().endsWith("index.htm") && !request.getRequestURI().endsWith("cadastrar-usuario.htm")) {
            response.sendRedirect("index.htm");
            return false;
        }
        // redirecionar depois que o vivente fizer o login ai quarda a sessão
        return true;
    }

    @Override
    public void postHandle(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse, Object o, org.springframework.web.servlet.ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
